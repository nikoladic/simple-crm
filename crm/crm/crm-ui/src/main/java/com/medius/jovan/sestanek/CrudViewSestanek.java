package com.medius.jovan.sestanek;

import com.medius.jovan.MainLayout;
import com.medius.jovan.backend.DataService;
import com.medius.jovan.backend.data.Sestanek;
import com.medius.jovan.backend.data.Stranka;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;

import java.util.ArrayList;

/**
 * A view for performing create-read-update-delete operations.
 *
 * See also {@link CrudLogicSestanek} for fetching the data, the actual CRUD
 * operations and controlling the view based on events from outside.
 */
@Route(value = "Sestankov", layout = MainLayout.class)
public class CrudViewSestanek extends HorizontalLayout
        implements HasUrlParameter<String> {

    public static final String VIEW_NAME = "Seznam vsih sestankov";
    private SestanekGrid grid;
    private TextField filter;
    private SestanekForm form;

    private CrudLogicSestanek viewLogic = new CrudLogicSestanek(this);
    private Button noviSestanek;

    private SestanekDataProvider dataProvider;

    public CrudViewSestanek() {
        setSizeFull();

        HorizontalLayout topLayout = createTopBar();

        dataProvider = new SestanekDataProvider(new ArrayList<>());
        grid = new SestanekGrid();
        grid.setDataProvider(dataProvider);
        grid.asSingleSelect().addValueChangeListener(
                event -> viewLogic.rowSelected(event.getValue()));

        form = new SestanekForm(viewLogic);

        VerticalLayout barAndGridLayout = new VerticalLayout();
        barAndGridLayout.add(topLayout);
        barAndGridLayout.add(grid);
        barAndGridLayout.setFlexGrow(1, grid);
        barAndGridLayout.setFlexGrow(0, topLayout);
        barAndGridLayout.setSizeFull();
        barAndGridLayout.expand(grid);

        add(barAndGridLayout);
        add(form);

        viewLogic.init();
    }

    public HorizontalLayout createTopBar() {
        filter = new TextField();
        filter.setPlaceholder("Filter location, start and end date or first and last name of stranko");
        // Apply the filter to grid's data provider. TextField value is never null
        filter.addValueChangeListener(event -> dataProvider.setFilter(event.getValue()));

        noviSestanek = new Button("Novi sestanek");
        noviSestanek.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        noviSestanek.setIcon(VaadinIcon.PLUS_CIRCLE.create());
        noviSestanek.addClickListener(click -> viewLogic.newSestanek());

        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.add(filter);
        topLayout.add(noviSestanek);
        topLayout.setVerticalComponentAlignment(Alignment.START, filter);
        topLayout.expand(filter);
        return topLayout;
    }

    public void showSaveNotification(String msg) {
        Notification.show(msg);
    }

    public void setNewSestanekEnabled(boolean enabled) {
        noviSestanek.setEnabled(enabled);
    }

    public void clearSelection() {
        grid.getSelectionModel().deselectAll();
    }

    public void updateSestanek(Sestanek sestanek) {
        dataProvider.save(sestanek);
    }

    public void removeSestanek(Sestanek sestanek) {
        dataProvider.delete(sestanek);
    }

    public void editSestanek(Sestanek sestanek) {
        showForm(sestanek != null);
        form.editSestanek(sestanek);
    }

    public void showForm(boolean show) {
        form.setVisible(show);
    }

    public void toggleNoviSestanek(boolean toggle) {
        noviSestanek.setVisible(toggle);
    }

    @Override
    public void setParameter(BeforeEvent event,
                             @OptionalParameter String parameter) {
        viewLogic.enter(parameter);
        dataProvider.init(parameter);
    }
}
