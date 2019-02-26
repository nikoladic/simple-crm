package com.medius.jovan.backend.data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Sestanek {

    private int id = -1;
    private String location;
    private LocalDate beggining;
    private String startTime;
    private LocalDate ending;
    private String endTime;
    private List<Sklep> sklepi;
    private Stranka stranka;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isNovSestanek(){
        return id == -1;
    }

    public String getLocation() { return location; }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getBeggining() {
        return beggining;
    }

    public void setBeggining(LocalDate beggining) {
        this.beggining = beggining;
    }

    public LocalDate getEnding() {
        return ending;
    }

    public void setEnding(LocalDate ending) {
        this.ending = ending;
    }

    public List<Sklep> getSklepi() {
        return sklepi;
    }

    public void setSklepi(List<Sklep> sklepi) {
        this.sklepi = sklepi;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Stranka getStranka() {
        return stranka;
    }

    public void setStranka(Stranka stranka) {
        this.stranka = stranka;
    }

}
