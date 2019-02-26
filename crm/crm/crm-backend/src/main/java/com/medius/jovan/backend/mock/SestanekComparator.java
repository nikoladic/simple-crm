package com.medius.jovan.backend.mock;

import com.medius.jovan.backend.data.Sestanek;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class SestanekComparator implements Comparator<Sestanek> {

    private SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");

    @Override
    public int compare(Sestanek o1, Sestanek o2) {
        if(o1.getBeggining() == null || o2.getBeggining() == null) {
            return 0;
        }
        // in case o1 sestanek starts before o2
        if (o1.getBeggining().isBefore(o2.getBeggining())) {
            return -1;
        }
        if (o1.getBeggining().isEqual(o2.getBeggining())) {
            return compareTimes(o1.getStartTime(), o1.getStartTime());
        }
        return 1;

    }

    private int compareTimes(String s1, String s2) {
        Date t1 = null;
        Date t2 = null;
        try {
            t1 = sdf.parse(s1);
            t2 = sdf.parse(s2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (t1.before(t2)) {
            return -1;
        }
        if (t1.equals(t2)) {
            return 0;
        }
        return 1;
    }
}
