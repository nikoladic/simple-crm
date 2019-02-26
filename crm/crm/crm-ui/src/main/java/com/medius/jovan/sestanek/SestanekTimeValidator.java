package com.medius.jovan.sestanek;

import com.medius.jovan.backend.data.Sestanek;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SestanekTimeValidator extends AbstractValidator<String> {

    private TextField startTime;
    private TextField endTime;
    private DatePicker beggining;
    private DatePicker ending;

    public SestanekTimeValidator (String message, TextField startTime, TextField endTime, DatePicker beggining, DatePicker ending) {
        super(message);
        this.startTime = startTime;
        this.endTime = endTime;
        this.beggining = beggining;
        this.ending = ending;
    }

    @Override
    public ValidationResult apply(String time, ValueContext valueContext) {
        // we don't require @time paremeter, since we pass all fields through constructor but it is required in method signature
        return this.toResult(time, this.isValid());
    }

    protected boolean isValid() {
        if(endTime.isEmpty() || startTime.isEmpty() || beggining.getValue() == null || ending.getValue() == null) {
            return true;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        Date start = null;
        Date end = null;
        try {
            start = sdf.parse(startTime.getValue());
            end = sdf.parse(endTime.getValue());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // if the dates are equal then end time must be after start time, otherwise no
        return beggining.getValue().isEqual(ending.getValue()) ? end.after(start) : true;
    }
}
