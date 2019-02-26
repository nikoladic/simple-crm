package com.medius.jovan;

import com.medius.jovan.about.AboutView;
import com.medius.jovan.sestanek.CrudViewSestanek;
import com.medius.jovan.stranka.CrudViewStranka;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

/**
 * The layout of the pages e.g. About and Stranke.
 */
@HtmlImport("css/shared-styles.html")
@Theme(value = Lumo.class)
public class MainLayout extends FlexLayout implements RouterLayout {
    private Menu menu;

    public MainLayout() {
        setSizeFull();
        setClassName("main-layout");

        menu = new Menu();
        menu.addView(CrudViewStranka.class, CrudViewStranka.VIEW_NAME,
                VaadinIcon.EDIT.create());
        menu.addView(CrudViewSestanek.class, CrudViewSestanek.VIEW_NAME,
                VaadinIcon.EDIT.create());
        menu.addView(AboutView.class, AboutView.VIEW_NAME,
                VaadinIcon.INFO_CIRCLE.create());

        add(menu);
    }
}
