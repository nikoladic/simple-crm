package com.medius.jovan.sestanek;

import com.medius.jovan.backend.DataService;
import com.medius.jovan.backend.data.Sestanek;
import com.vaadin.flow.data.provider.ListDataProvider;

import java.util.*;
import java.util.stream.Collectors;

public class SestanekDataProvider extends ListDataProvider<Sestanek> {

    /** Text filter that can be changed separately. */
    private String filterText = "";


    private int strankaId = -1;
    private List<Sestanek> sestankov;

    public SestanekDataProvider(List<Sestanek> sestankov) {
        // we don't have access to id of stranka at this point
        super(sestankov);
        this.sestankov = sestankov;
    }

    public void init(String strankaId) {
        // if parameter @strankaId is empty, or an invalid parameter was manually input (non-number, double number)
        // we load the data provider with all sestankov
        if (strankaId == null || strankaId.isEmpty() || !strankaId.matches("-?\\d+") || !strankaId.matches("^[^.]+$")) {
            // add only new sestankov to list
            sestankov.clear();
            sestankov.addAll(DataService.get().getAllSestankov().stream()
                    .filter(s -> !sestankov.contains(s)).collect(Collectors.toList()));
            refreshAll();
        } else {
            // if parameter @strankaId is not empty, we load the data provider with items for a specific Stranko
            sestankov.clear();
            this.strankaId = Integer.valueOf(strankaId);
            sestankov.addAll(DataService.get().getSeznamSestankovZaStranko(this.strankaId));
        }
    }

    /**
     * Store given sestanek to the backing data service.
     *
     * @param sestanek
     *            the updated or new sestanek
     */
    public void save(Sestanek sestanek) {
        boolean novSestanek = sestanek.isNovSestanek();

        DataService.get().updateSestanek(sestanek);
        if (novSestanek) {
            refreshAll();
        } else {
            refreshItem(sestanek);
        }
    }

    /**
     * Delete given sestanek from the backing data service.
     *
     * @param sestanek
     *            the sestanek to be deleted
     */
    public void delete(Sestanek sestanek) {
        DataService.get().deleteSestanek(sestanek.getId());
        refreshAll();
    }

    /**
     * Sets the filter to use for this data provider and refreshes data.
     * <p>
     * Filter is compared for sestanek location, beggining date and start time and first and last name of it's stranko.
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

        setFilter(sestanek -> passesFilter(sestanek.getLocation(), filterText)
                || passesFilter(sestanek.getBeggining().toString(), filterText)
                || passesFilter(sestanek.getStartTime(), filterText)
                || sestanek.getStranka() != null && passesFilter(sestanek.getStranka().toString(), filterText));
    }

    @Override
    public Integer getId(Sestanek sestanek) {
        Objects.requireNonNull(sestanek,
                "Cannot provide an id for a null sestanek.");

        return sestanek.getId();
    }

    private boolean passesFilter(Object object, String filterText) {
        return object != null && object.toString().toLowerCase(Locale.ENGLISH)
                .contains(filterText.toLowerCase());
    }
}
