package com.medius.jovan.backend.data;

public enum Sklep {
    DOLOCITEV_NASLEDNJEGA_SESTANKA("Dolocitev naslednjega sestanka"),
    POGODBA("Pogodba"),
    RACUN("Racun");

    private final String name;

    private Sklep(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
