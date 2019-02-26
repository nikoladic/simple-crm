package com.medius.jovan.backend.mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.medius.jovan.backend.data.Sestanek;
import com.medius.jovan.backend.data.Stranka;

public class MockDataGenerator {
    private static int nextStrankaId = 1;
    private static int nextSestanekId = 1;
    private static final Random random = new Random(1);

    private static String[] names = {"Joze", "Rok", "Matej", "Petra", "Ana", "Milica", "Nina", "David", "Luka", "Igor"};
    private static String[] lastName = {"Zupan", "Svara", "Pucnik", "Oblak", "Gomiscek", "Loboda", "Penca", "Wolf"};
    private static String[] locations = {"BTC", "FRI", "FKKT", "Medius", "AKC Metelkova"};

    static List<Stranka> createStranke() {
        List<Stranka> stranke = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Stranka s = createStranko();
            stranke.add(s);
        }

        return stranke;
    }

    private static Stranka createStranko() {
        Stranka s = new Stranka();
        s.setId(nextStrankaId++);
        s.setFirstName(names[random.nextInt(names.length)]);
        s.setLastName(lastName[random.nextInt(lastName.length)]);

        return s;
    }

    public static List<Sestanek> createSestankov() {
        List<Sestanek> sestankov = new ArrayList<>();
        for (int i = 0; i < 80; i++) {
            Sestanek ses = createSestanek();
            sestankov.add(ses);
        }
        return sestankov;
    }

    private static Sestanek createSestanek() {
        Sestanek ses = new Sestanek();
        ses.setId(nextSestanekId++);
        int hourStart = random.nextInt(24);
        int minuteStart = random.nextInt(60);
        ses.setStartTime((hourStart < 10 ? "0" + hourStart : hourStart) + ":" + (minuteStart < 10 ? "0" + minuteStart : minuteStart));
        int hourEnd = random.nextInt(24);
        int minuteEnd = random.nextInt(60);
        ses.setEndTime((hourEnd < 10 ? "0" + hourEnd : hourEnd) + ":" + (minuteEnd < 10 ? "0" + minuteEnd : minuteEnd));
        int dayEnd = random.nextInt(27) + 1;
        int monthEnd = random.nextInt(11) + 1;
        ses.setEnding(LocalDate.of(2019, monthEnd + 1, dayEnd + 1));
        int dayStart = random.nextInt(dayEnd) + 1;
        int monthStart = random.nextInt(monthEnd) + 1;
        ses.setBeggining(LocalDate.of(2019, monthStart, dayStart));
        ses.setLocation(locations[random.nextInt(locations.length)]);
        return ses;
    }
}
