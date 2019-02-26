package com.medius.jovan.stranka;

import com.medius.jovan.authentication.AccessControl;
import com.medius.jovan.authentication.AccessControlFactory;
import com.medius.jovan.backend.DataService;
import com.medius.jovan.backend.data.Stranka;
import com.medius.jovan.sestanek.CrudViewSestanek;
import com.vaadin.flow.component.UI;

import java.io.Serializable;

/**
 * This class provides an interface for the logical operations between the CRUD
 * view, its parts like the stranka editor form and the data source, including
 * fetching and saving stranke.
 *
 * Having this separate from the view makes it easier to test various parts of
 * the system separately, and to e.g. provide alternative views for the same
 * data.
 */
public class CrudLogicStranka implements Serializable {

    private CrudViewStranka view;

    public CrudLogicStranka(CrudViewStranka simpleCrudView) {
        view = simpleCrudView;
    }

    public void init() {
        editStranko(null);
        // Hide and disable if not admin
        if (!AccessControlFactory.getInstance().createAccessControl()
                .isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
            view.setNewStrankoEnabled(false);
        }
    }

    public void cancelStranko() {
        setFragmentParameter("");
        view.clearSelection();
    }

    /**
     * Update the fragment without causing navigator to change view
     */
    private void setFragmentParameter(String strankaId) {
        String fragmentParameter;
        if (strankaId == null || strankaId.isEmpty()) {
            fragmentParameter = "";
        } else {
            fragmentParameter = strankaId;
        }

        UI.getCurrent().navigate(CrudViewStranka.class, fragmentParameter);
    }

    public void enter(String strankaId) {
        if (strankaId != null && !strankaId.isEmpty()) {
            if (strankaId.equals("new")) {
                newStranka();
            } else {
                // Ensure this is selected even if coming directly here from
                // login
                try {
                    int pid = Integer.parseInt(strankaId);
                    Stranka stranka = findStranko(pid);
                    view.selectRow(stranka);
                } catch (NumberFormatException e) {
                }
            }
        } else {
            view.showForm(false);
        }
    }

    private Stranka findStranko(int strankaId) {
        return DataService.get().getStrankoById(strankaId);
    }

    public void saveStranko(Stranka stranka) {
        boolean novaStranka = stranka.isNovaStranka();
        view.clearSelection();
        view.updateStranko(stranka);
        setFragmentParameter("");
        view.showSaveNotification(stranka.getFirstName() + stranka.getLastName()
                + (novaStranka ? " created" : " updated"));
    }

    public void deleteStranko(Stranka stranka) {
        view.clearSelection();
        view.removeStranko(stranka);
        setFragmentParameter("");
        view.showSaveNotification(stranka.getFirstName() + stranka.getLastName() + " removed");
    }

    public void editStranko(Stranka stranka) {
        if (stranka == null) {
            setFragmentParameter("");
        } else {
            setFragmentParameter(stranka.getId() + "");
        }
        view.editStranko(stranka);
    }

    public void newStranka() {
        view.clearSelection();
//        setFragmentParameter("new");
        view.editStranko(new Stranka());
    }

    public void rowSelected(Stranka stranka) {
        if (AccessControlFactory.getInstance().createAccessControl()
                .isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
            editStranko(stranka);
        }
    }

    public void showSestankov(Stranka currentStranka) {
        UI.getCurrent().navigate(CrudViewSestanek.class, String.valueOf(currentStranka.getId()));
    }

    public void showError(String message) {
        view.showError(message);
    }
}
