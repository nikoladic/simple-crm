package com.medius.jovan.backend;

import java.io.Serializable;
import java.util.Collection;

import com.medius.jovan.backend.data.Sestanek;
import com.medius.jovan.backend.data.Stranka;
import com.medius.jovan.backend.mock.MockDataService;

/**
 * Back-end service interface for retrieving and updating data.
 */
public abstract class DataService implements Serializable {

    public abstract Collection<Stranka> getAllStranke();

    public abstract void updateStranko(Stranka s);

    public abstract void updateSestanek(Sestanek s);

    public abstract void deleteStranko(int strankaId);

    public abstract void deleteSestanek(int sestanekId);

    public abstract Stranka getStrankoById(int strankaId);

    public abstract Sestanek getSestanekById(int sestanekId);

    public static DataService get() {
        return MockDataService.getInstance();
    }

    public abstract Collection<Sestanek> getSeznamSestankovZaStranko(int strankaId);
    public abstract Collection<Sestanek> getAllSestankov();
}
