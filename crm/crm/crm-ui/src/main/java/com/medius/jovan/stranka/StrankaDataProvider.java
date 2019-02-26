package com.medius.jovan.stranka;

import com.medius.jovan.backend.DataService;
import com.medius.jovan.backend.data.Stranka;
import com.vaadin.flow.data.provider.ListDataProvider;

import java.util.Locale;
import java.util.Objects;

public class StrankaDataProvider extends ListDataProvider<Stranka> {

    /** Text filter that can be changed separately. */
    private String filterText = "";

    public StrankaDataProvider() {
        super(DataService.get().getAllStranke());
    }

    /**
     * Store given stranko to the backing data service.
     * 
     * @param stranka
     *            the updated or new stranko
     */
    public void save(Stranka stranka) {
        boolean novaStranka = stranka.isNovaStranka();

        DataService.get().updateStranko(stranka);
        if (novaStranka) {
            refreshAll();
        } else {
            refreshItem(stranka);
        }
    }

    /**
     * Delete given stranko from the backing data service.
     * 
     * @param stranka
     *            the stranka to be deleted
     */
    public void delete(Stranka stranka) {
        DataService.get().deleteStranko(stranka.getId());
        refreshAll();
    }

    /**
     * Sets the filter to use for this data provider and refreshes data.
     * <p>
     * Filter is compared for stranka firstName, lastName, email.
     * 
     * @param filterText
     *            the text to filter by, never null
     */
    public void setFilter(String filterText) {
        Objects.requireNonNull(filterText, "Filter text cannot be null.");
        if (Objects.equals(this.filterText, filterText.trim())) {
            return;
        }
        this.filterText = filterText.trim();

        setFilter(stranka -> passesFilter(stranka.getFirstName(), filterText)
                || passesFilter(stranka.getLastName(), filterText)
                || passesFilter(stranka.getEmailAddress(), filterText));
    }

    @Override
    public Integer getId(Stranka stranka) {
        Objects.requireNonNull(stranka,
                "Cannot provide an id for a null stranko.");

        return stranka.getId();
    }

    private boolean passesFilter(Object object, String filterText) {
        return object != null && object.toString().toLowerCase(Locale.ENGLISH)
                .contains(filterText.toLowerCase());
    }
}
