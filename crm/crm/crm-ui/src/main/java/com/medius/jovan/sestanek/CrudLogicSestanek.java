package com.medius.jovan.sestanek;

import com.medius.jovan.authentication.AccessControl;
import com.medius.jovan.authentication.AccessControlFactory;
import com.medius.jovan.backend.DataService;
import com.medius.jovan.backend.data.Sestanek;
import com.medius.jovan.backend.data.Stranka;
import com.vaadin.flow.component.UI;

import java.io.Serializable;

/**
 * This class provides an interface for the logical operations between the CRUD
 * view, its parts like the sestanek editor form and the data source, including
 * fetching and saving sestankov.
 *
 * Having this separate from the view makes it easier to test various parts of
 * the system separately, and to e.g. provide alternative views for the same
 * data.
 */
public class CrudLogicSestanek implements Serializable {

    private CrudViewSestanek view;

    public CrudLogicSestanek(CrudViewSestanek simpleCrudView) {
        view = simpleCrudView;
    }

    public void init() {
        editSestanek(null);
        // Hide and disable if not admin
        if (!AccessControlFactory.getInstance().createAccessControl()
                .isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
            view.setNewSestanekEnabled(false);
        }
    }

    public void cancelSestanek() {
        setFragmentParameter("");
        view.clearSelection();
    }

    /**
     * Update the fragment without causing navigator to change view
     */
    private void setFragmentParameter(String sestanekId) {
        String fragmentParameter;
        if (sestanekId == null || sestanekId.isEmpty()) {
            fragmentParameter = "";
        } else {
            fragmentParameter = sestanekId;
        }

        UI.getCurrent().navigate(CrudViewSestanek.class, fragmentParameter);
    }

    public void enter(String strankaId) {
        // enable creation of new sestankov only on the main view
        view.toggleNoviSestanek(strankaId == null);

        view.showForm(false);
    }

    public void saveSestanek(Sestanek sestanek) {
        boolean novSestanek = sestanek.isNovSestanek();
        view.clearSelection();
        view.updateSestanek(sestanek);
        setFragmentParameter("");
        view.showSaveNotification("Sestanek at " + sestanek.getLocation()
                + (novSestanek ? " created" : " updated"));
    }

    public void deleteSestanek(Sestanek sestanek) {
        view.clearSelection();
        view.removeSestanek(sestanek);
        setFragmentParameter("");
        view.showSaveNotification("Sestanek at " + sestanek.getLocation() + " removed");
    }

    public void editSestanek(Sestanek sestanek) {
        if (sestanek == null) {
            setFragmentParameter("");
        } else {
            setFragmentParameter(sestanek.getId() + "");
        }
        view.editSestanek(sestanek);
    }

    public void newSestanek() {
        view.clearSelection();
        view.editSestanek(new Sestanek());
    }

    public void rowSelected(Sestanek sestanek) {
        if (AccessControlFactory.getInstance().createAccessControl()
                .isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
            editSestanek(sestanek);
        }
    }
}
