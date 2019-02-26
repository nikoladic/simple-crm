package com.medius.jovan.backend;

import com.medius.jovan.backend.data.Sestanek;
import com.medius.jovan.backend.data.Stranka;
import static org.hamcrest.core.Is.is;
import org.junit.Before;
import org.junit.Test;
import com.medius.jovan.backend.mock.MockDataService;

import static org.junit.Assert.*;

/**
 * Simple unit test for the back-end data service.
 */
public class DataServiceTest {

    private DataService service;

    @Before
    public void setUp() throws Exception {
        service = MockDataService.getInstance();
    }

    @Test
    public void testDataServiceCanFetchStranke() throws Exception {
        assertFalse(service.getAllStranke().isEmpty());
    }

    @Test
    public void testUpdateStranko_updatesStranko() throws Exception {
        Stranka s = service.getAllStranke().iterator().next();
        s.setFirstName("My Test Name");
        service.updateStranko(s);
        Stranka s2 = service.getAllStranke().iterator().next();
        assertEquals("My Test Name", s2.getFirstName());
    }

    @Test
    public void testUpdateSestanek_updatesSestanek() throws Exception {
        Sestanek s = service.getAllSestankov().iterator().next();
        s.setLocation("My Location");
        service.updateSestanek(s);
        Sestanek s2 = service.getAllSestankov().iterator().next();
        assertEquals("My Location", s2.getLocation());
    }

    @Test
    public void testGetSestankovForStranko() throws Exception {
        Sestanek ses = new Sestanek();
        ses.setLocation("aaaaa");
        Stranka s = new Stranka();
        s.setId(-1);
        s.getSestankov().add(ses);
        ses.setStranka(s);
        service.updateStranko(s);
        service.updateSestanek(ses);
        assertThat(service.getSeznamSestankovZaStranko(s.getId()).size(), is(1));
        assertThat(service.getSeznamSestankovZaStranko(s.getId()).contains(ses), is(true));
    }
}
