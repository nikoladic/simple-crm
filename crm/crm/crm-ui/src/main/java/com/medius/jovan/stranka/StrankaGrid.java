package com.medius.jovan.stranka;

import com.medius.jovan.backend.data.Stranka;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.renderer.TemplateRenderer;

import java.util.Comparator;

/**
 * Grid of stranke, handling the visual presentation and filtering of a set of
 * items. This version uses an in-memory data source that is suitable for small
 * data sets.
 */
public class StrankaGrid extends Grid<Stranka> {

    public StrankaGrid() {
        setSizeFull();

        addColumn(Stranka::getId)
                .setHeader("Id stranke")
                .setSortable(true);

        // To change the text alignment of the column, a template is used.
        final String firstName = "<div style='text-align: right'>[[item.firstName]]</div>";
        addColumn(TemplateRenderer.<Stranka>of(firstName)
                .withProperty("firstName", stranka -> stranka.getFirstName()))
                .setHeader("First Name")
                .setComparator(Comparator.comparing(Stranka::getFirstName));
//                .setFlexGrow(3);


        // To change the text alignment of the column, a template is used.
        final String lastName = "<div style='text-align: right'>[[item.lastName]]</div>";
        addColumn(TemplateRenderer.<Stranka>of(lastName)
                .withProperty("lastName", stranka -> stranka.getLastName()))
                .setHeader("Last name")
                .setComparator(Comparator.comparing(Stranka::getLastName))
                .setFlexGrow(3);

        final String emailAddress = "<div style='text-align: right'>[[item.emailAddress]]</div>";
        addColumn(TemplateRenderer.<Stranka>of(emailAddress)
                .withProperty("emailAddress", stranka -> stranka.getEmailAddress()))
                .setHeader("Email address")
                .setComparator(Comparator.comparing(Stranka::getEmailAddress))
                .setFlexGrow(3);

        final String phoneNumber = "<div style='text-align: right'>[[item.phoneNumber]]</div>";
        addColumn(TemplateRenderer.<Stranka>of(phoneNumber)
                .withProperty("phoneNumber", stranka -> stranka.getPhoneNumber()))
                .setHeader("Phone number")
                .setComparator(Comparator.comparing(Stranka::getPhoneNumber))
                .setFlexGrow(3);

    }

    public Stranka getSelectedRow() {
        return asSingleSelect().getValue();
    }

    public void refresh(Stranka stranka) {
        getDataCommunicator().refresh(stranka);
    }

}
