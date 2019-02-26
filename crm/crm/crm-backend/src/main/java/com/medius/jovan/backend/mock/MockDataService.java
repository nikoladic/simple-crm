package com.medius.jovan.backend.mock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.medius.jovan.backend.DataService;
import com.medius.jovan.backend.data.Sestanek;
import com.medius.jovan.backend.data.Stranka;

/**
 * Mock data model. This implementation has very simplistic locking and does not
 * notify users of modifications.
 */
public class MockDataService extends DataService {

    private static MockDataService instance;

    private List<Stranka> stranke;
    private List<Sestanek> sestankov;
    private int nextStrankaId;
    private int nextSestanekId;
    private SestanekComparator comparator;

    private MockDataService() {
        stranke = MockDataGenerator.createStranke();
        sestankov = MockDataGenerator.createSestankov();
        assignSestankov();
        nextStrankaId = stranke.size() + 1;
        nextSestanekId = sestankov.size() + 1;
        comparator = new SestanekComparator();
    }

    private void assignSestankov() {
        int noOfSestPerStrank = Math.floorDiv(sestankov.size(), stranke.size());
        for (int i = 0; i < stranke.size(); i++) {
            for (int j = 0; j < noOfSestPerStrank; j++) {
                Sestanek ses = sestankov.get(j + i * noOfSestPerStrank);
                stranke.get(i).getSestankov().add(ses);
                ses.setStranka(stranke.get(i));
            }
        }
    }

    public synchronized static DataService getInstance() {
        if (instance == null) {
            instance = new MockDataService();
        }
        return instance;
    }

    public synchronized List<Stranka> getAllStranke() {
        return stranke;
    }

    @Override
    public synchronized void updateStranko(Stranka s) {
        if (s.getId() < 0) {
            // Nova stranka
            s.setId(nextStrankaId++);
            stranke.add(s);
            return;
        }
        for (int i = 0; i < stranke.size(); i++) {
            if (stranke.get(i).getId() == s.getId()) {
                stranke.set(i, s);
                return;
            }
        }

        throw new IllegalArgumentException("Nima stranke z id " + s.getId());
    }

    @Override
    public void updateSestanek(Sestanek s) {
        if (s.getId() < 0) {
            // Nova stranka
            s.setId(nextSestanekId++);
            sestankov.add(s);
            // sort after anuy new is added
            sestankov.sort(comparator);
            return;
        }
        for (int i = 0; i < sestankov.size(); i++) {
            if (sestankov.get(i).getId() == s.getId()) {
                sestankov.set(i, s);
                return;
            }
        }

        throw new IllegalArgumentException("Nema sestanka z id " + s.getId());
    }

    @Override
    public synchronized Stranka getStrankoById(int strankaId) {
        for (int i = 0; i < stranke.size(); i++) {
            if (stranke.get(i).getId() == strankaId) {
                return stranke.get(i);
            }
        }
        return null;
    }

    @Override
    public synchronized Sestanek getSestanekById(int sestanekId) {
        for (int i = 0; i < sestankov.size(); i++) {
            if (sestankov.get(i).getId() == sestanekId) {
                return sestankov.get(i);
            }
        }
        return null;
    }

    @Override
    public Collection<Sestanek> getSeznamSestankovZaStranko(int strankaId) {
        List<Sestanek> retList = sestankov.stream().filter(s -> s.getStranka() != null && s.getStranka().getId() == strankaId).collect(Collectors.toList());
        retList.sort(comparator);
        return retList;
    }

    @Override
    public Collection<Sestanek> getAllSestankov() {
        return sestankov;
    }

    @Override
    public synchronized void deleteStranko(int strankaId) {
        Stranka s = getStrankoById(strankaId);
        if (s == null) {
            throw new IllegalArgumentException("Nima stranke z id " + strankaId);
        }
        stranke.remove(s);
        sestankov.removeIf(ses -> ses.getStranka() != null && ses.getStranka().getId() == strankaId);
    }

    @Override
    public void deleteSestanek(int sestanekId) {
        Sestanek s = getSestanekById(sestanekId);
        if (s == null) {
            throw new IllegalArgumentException("Nima ssestankov z id " + sestanekId);
        }
        sestankov.remove(s);
        if (s.getStranka() != null) {
            Stranka st = findById(s.getStranka().getId());
            st.getSestankov().removeIf(sest -> sest.getId() == sestanekId);
        }
    }

    private Stranka findById(int id) {
        return stranke.stream().filter(str -> str.getId() == id).findFirst().get();
    }
}
