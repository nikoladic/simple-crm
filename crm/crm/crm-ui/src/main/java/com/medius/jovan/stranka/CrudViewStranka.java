package com.medius.jovan.stranka;

import com.medius.jovan.MainLayout;
import com.medius.jovan.backend.data.Stranka;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;

/**
 * A view for performing create-read-update-delete operations on stranke.
 *
 * See also {@link CrudLogicStranka} for fetching the data, the actual CRUD
 * operations and controlling the view based on events from outside.
 */
@Route(value = "Stranke", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class CrudViewStranka extends HorizontalLayout
        implements HasUrlParameter<String> {

    public static final String VIEW_NAME = "Stranke";
    private StrankaGrid grid;
    private TextField filter;
    private StrankaForm form;

    private CrudLogicStranka viewLogic = new CrudLogicStranka(this);
    private Button newStranka;

    private StrankaDataProvider dataProvider = new StrankaDataProvider();

    public CrudViewStranka() {
        setSizeFull();
        HorizontalLayout topLayout = createTopBar();

        grid = new StrankaGrid();
        grid.setDataProvider(dataProvider);
        grid.asSingleSelect().addValueChangeListener(
                event -> viewLogic.rowSelected(event.getValue()));

        form = new StrankaForm(viewLogic);

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
        filter.setPlaceholder("Filter  first name, last name or email address");
        // Apply the filter to grid's data provider. TextField value is never null
        filter.addValueChangeListener(event -> dataProvider.setFilter(event.getValue()));

        newStranka = new Button("Nova stranka");
        newStranka.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newStranka.setIcon(VaadinIcon.PLUS_CIRCLE.create());
        newStranka.addClickListener(click -> viewLogic.newStranka());

        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.add(filter);
        topLayout.add(newStranka);
        topLayout.setVerticalComponentAlignment(Alignment.START, filter);
        topLayout.expand(filter);
        return topLayout;
    }

    public void showError(String msg) {
        Notification.show(msg);
    }

    public void showSaveNotification(String msg) {
        Notification.show(msg);
    }

    public void setNewStrankoEnabled(boolean enabled) {
        newStranka.setEnabled(enabled);
    }

    public void clearSelection() {
        grid.getSelectionModel().deselectAll();
    }

    public void selectRow(Stranka row) {
        grid.getSelectionModel().select(row);
    }

    public Stranka getSelectedRow() {
        return grid.getSelectedRow();
    }

    public void updateStranko(Stranka stranka) {
        dataProvider.save(stranka);
    }

    public void removeStranko(Stranka stranka) {
        dataProvider.delete(stranka);
    }

    public void editStranko(Stranka stranka) {
        showForm(stranka != null);
        form.editStranko(stranka);
    }

    public void showForm(boolean show) {
        form.setVisible(show);

        /* FIXME The following line should be uncommented when the CheckboxGroup
         * issue is resolved. The category CheckboxGroup throws an
         * IllegalArgumentException when the form is disabled.
         */
        //form.setEnabled(show);
    }

    @Override
    public void setParameter(BeforeEvent event,
                             @OptionalParameter String parameter) {
        viewLogic.enter(parameter);
    }
}
