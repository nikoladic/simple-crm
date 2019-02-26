package com.medius.jovan.sestanek;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;

import java.time.LocalDate;

public class SestanekDateValidator extends AbstractValidator<LocalDate> {

    private DatePicker beggining;
    private DatePicker ending;

    public SestanekDateValidator(String message, DatePicker beggining, DatePicker ending) {
        super(message);
        this.beggining = beggining;
        this.ending = ending;
    }

    @Override
    public ValidationResult apply(LocalDate localDate, ValueContext valueContext) {
        return this.toResult(localDate, this.isValid(localDate));
    }

    protected boolean isValid(LocalDate ld) {
        return beggining.getValue() != null && ending.getValue() != null ?
                beggining.getValue().isBefore(ending.getValue()) || beggining.getValue().isEqual(ending.getValue())
                : ld != null;
    }
}
