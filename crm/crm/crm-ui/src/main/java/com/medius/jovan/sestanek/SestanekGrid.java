package com.medius.jovan.sestanek;

import com.medius.jovan.backend.data.Sestanek;
import com.medius.jovan.backend.data.Stranka;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.renderer.TemplateRenderer;

import java.util.Comparator;

public class SestanekGrid extends Grid<Sestanek> {

    public SestanekGrid() {
        setSizeFull();

        addColumn(Sestanek::getId)
                .setHeader("Id sestankov")
                .setSortable(true);

        final String stranka = "<div style='text-align: right'>[[item.stranka]]</div>";
        addColumn(TemplateRenderer.<Sestanek>of(stranka)
                .withProperty("stranka", sestanek -> sestanek.getStranka() != null ?
                        sestanek.getStranka().getFirstName() + " " + sestanek.getStranka().getLastName() : null))
                .setComparator(Comparator.comparing(sestanek -> sestanek.getStranka() != null
                        ? sestanek.getStranka().getFirstName() + sestanek.getStranka().getLastName()
                        : ""))
                .setHeader("Stranka");

        // To change the text alignment of the column, a template is used.
        final String location = "<div style='text-align: right'>[[item.location]]</div>";
        addColumn(TemplateRenderer.<Sestanek>of(location)
                .withProperty("location", sestanek -> sestanek.getLocation()))
                .setHeader("Location")
                .setComparator(Comparator.comparing(Sestanek::getLocation));
//                .setFlexGrow(3);


        // To change the text alignment of the column, a template is used.
        final String beggining = "<div style='text-align: right'>[[item.beggining]]</div>";
        addColumn(TemplateRenderer.<Sestanek>of(beggining)
                .withProperty("beggining", sestanek -> sestanek.getBeggining() != null ? sestanek.getBeggining().toString() : null))
                .setHeader("Beggining")
                .setComparator(Comparator.comparing(Sestanek::getBeggining));

        final String startTime = "<div style='text-align: right'>[[item.startTime]]</div>";
        addColumn(TemplateRenderer.<Sestanek>of(startTime)
                .withProperty("startTime", sestanek -> sestanek.getStartTime()))
                .setHeader("Start time")
                .setComparator(Comparator.comparing(Sestanek::getStartTime));

        final String ending = "<div style='text-align: right'>[[item.ending]]</div>";
        addColumn(TemplateRenderer.<Sestanek>of(ending)
                .withProperty("ending", sestanek -> sestanek.getEnding() != null ? sestanek.getEnding().toString() : null))
                .setHeader("Ending")
                .setComparator(Comparator.comparing(Sestanek::getBeggining));

        final String endTime = "<div style='text-align: right'>[[item.endTime]]</div>";
        addColumn(TemplateRenderer.<Sestanek>of(endTime)
                .withProperty("endTime", sestanek -> sestanek.getEndTime()))
                .setHeader("End time")
                .setComparator(Comparator.comparing(Sestanek::getEndTime));

    }

    public Sestanek getSelectedRow() {
        return asSingleSelect().getValue();
    }
}
