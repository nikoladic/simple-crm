package com.medius.jovan.stranka;

import com.medius.jovan.backend.data.Stranka;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.value.ValueChangeMode;

/**
 * A form for editing a single stranko.
 */
public class StrankaForm extends Div {

    private VerticalLayout content;

    private TextField firstName;
    private TextField lastName;
    private TextField email;
    private TextField phoneNumber;
    private Button sestanci;
    private Button save;
    private Button discard;
    private Button cancel;
    private Button delete;

    private CrudLogicStranka viewLogic;
    private Binder<Stranka> binder;
    private Stranka currentStranka;


    public StrankaForm(CrudLogicStranka sampleCrudLogic) {
        setClassName("stranka-form");

        content = new VerticalLayout();
        content.setSizeUndefined();
        add(content);

        viewLogic = sampleCrudLogic;

        firstName = new TextField("First Name");
        firstName.setWidth("100%");
        firstName.setRequired(true);
        firstName.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(firstName);

        lastName = new TextField("Last Name");
        lastName.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        firstName.setRequired(true);
        lastName.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(lastName);

        email = new TextField("Email address");
        email.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        email.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(email);

        phoneNumber = new TextField("Phone");
        phoneNumber.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        phoneNumber.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(phoneNumber);

        binder = new BeanValidationBinder<>(Stranka.class);
        binder.forField(email).withValidator(new EmailValidator("Email not valid."))
                .bind("emailAddress");
        binder.bindInstanceFields(this);

        // enable/disable save button while editing
        binder.addStatusChangeListener(event -> {
            boolean isValid = !event.hasValidationErrors();
            boolean hasChanges = binder.hasChanges();
            save.setEnabled(hasChanges && isValid);
            discard.setEnabled(hasChanges);
        });

        sestanci = new Button("Seznam sestankov");
        sestanci.setWidth("100%");
        sestanci.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        sestanci.addClickListener(event -> {
            if (currentStranka != null) {
                viewLogic.showSestankov(currentStranka);
            } else {
                viewLogic.showError("Can not display seznam sestankov");
            }
        });
        content.add(sestanci);

        save = new Button("Save");
        save.setWidth("100%");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(event -> {
            if (currentStranka != null
                    && binder.writeBeanIfValid(currentStranka)) {
                viewLogic.saveStranko(currentStranka);
            }
        });

        discard = new Button("Discard changes");
        discard.setWidth("100%");
        discard.addClickListener(
                event -> viewLogic.editStranko(currentStranka));

        cancel = new Button("Cancel");
        cancel.setWidth("100%");
        cancel.addClickListener(event -> viewLogic.cancelStranko());
        getElement()
                .addEventListener("keydown", event -> viewLogic.cancelStranko())
                .setFilter("event.key == 'Escape'");

        delete = new Button("Delete");
        delete.setWidth("100%");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
        delete.addClickListener(event -> {
            if (currentStranka != null) {
                viewLogic.deleteStranko(currentStranka);
            }
        });

        content.add(save, discard, delete, cancel);
    }

    public void editStranko(Stranka stranka) {
        if (stranka == null) {
            stranka = new Stranka();
        }
        currentStranka = stranka;
        binder.readBean(stranka);
    }
}
