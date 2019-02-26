package com.medius.jovan.sestanek;

import com.medius.jovan.backend.DataService;
import com.medius.jovan.backend.data.Sestanek;
import com.medius.jovan.backend.data.Stranka;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.*;
import com.vaadin.flow.data.validator.AbstractValidator;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

/**
 * A form for editing a single sestanek.
 */
public class SestanekForm extends Div {

    private VerticalLayout content;

    private TextField location;
    private DatePicker beggining;
    private TextField startTime;
    private DatePicker ending;
    private TextField endTime;
    private ComboBox<Stranka> stranka;
    private Button save;
    private Button discard;
    private Button cancel;
    private Button delete;

    private CrudLogicSestanek viewLogic;
    private Binder<Sestanek> binder;
    private Sestanek currentSestanek;


    public SestanekForm(CrudLogicSestanek sampleCrudLogic) {
        setClassName("sestanek-form");

        content = new VerticalLayout();
        content.setSizeUndefined();
        add(content);

        viewLogic = sampleCrudLogic;

        location = new TextField("Location");
        location.setWidth("100%");
        location.setRequired(true);
        location.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(location);

        beggining = new DatePicker("Start date");
        content.add(beggining);

        startTime = new TextField("Start time");
        startTime.setWidth("100%");
        startTime.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(startTime);

        ending = new DatePicker("End date");
        content.add(ending);

        endTime = new TextField("End time");
        endTime.setWidth("100%");
        endTime.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(endTime);

        stranka = new ComboBox<>("Stranka");
        stranka.setItems(currentSestanek != null && currentSestanek.getStranka() != null
                ? Arrays.asList(currentSestanek.getStranka()) : DataService.get().getAllStranke());
        content.add(stranka);

        binder = new BeanValidationBinder<>(Sestanek.class);

        binder.forField(startTime)
                .withValidator(new RegexpValidator("Time needs to be in hh:mm format", "([01]?[0-9]|2[0-3]):[0-5][0-9]"))
                .withValidator(new SestanekTimeValidator("End time must be after start time", startTime, endTime, beggining, ending))
                .bind("startTime");

        binder.forField(endTime)
                .withValidator(new RegexpValidator("Time needs to be in hh:mm format", "([01]?[0-9]|2[0-3]):[0-5][0-9]"))
                .withValidator(new SestanekTimeValidator("End time must be after start time", startTime, endTime, beggining, ending))
                .bind("endTime");

        binder.forField(ending)
                .withValidator(new SestanekDateValidator("Please enter a date later than start date", beggining,ending))
                .bind("ending");

        binder.forField(beggining)
                .withValidator(new SestanekDateValidator("Please enter a date before end date", beggining,ending))
                .bind("beggining");

        binder.bindInstanceFields(this);

        // enable/disable save button while editing
        binder.addStatusChangeListener(event -> {
            boolean isValid = !event.hasValidationErrors();
            boolean hasChanges = binder.hasChanges();
            save.setEnabled(hasChanges && isValid);
            discard.setEnabled(hasChanges);
        });

        save = new Button("Save");
        save.setWidth("100%");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(event -> {
            if (currentSestanek != null
                    && binder.writeBeanIfValid(currentSestanek)) {
                viewLogic.saveSestanek(currentSestanek);
            }
        });

        discard = new Button("Discard changes");
        discard.setWidth("100%");
        discard.addClickListener(
                event -> viewLogic.editSestanek(currentSestanek));

        cancel = new Button("Cancel");
        cancel.setWidth("100%");
        cancel.addClickListener(event -> viewLogic.cancelSestanek());
        getElement()
                .addEventListener("keydown", event -> viewLogic.cancelSestanek())
                .setFilter("event.key == 'Escape'");

        delete = new Button("Delete");
        delete.setWidth("100%");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
        delete.addClickListener(event -> {
            if (currentSestanek != null) {
                viewLogic.deleteSestanek(currentSestanek);
            }
        });

        content.add(save, discard, delete, cancel);
    }

    public void editSestanek(Sestanek sestanek) {
        if (sestanek == null) {
            sestanek = new Sestanek();
        }
        currentSestanek = sestanek;
        binder.readBean(sestanek);
    }
}
