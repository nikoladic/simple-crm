package com.medius.jovan.backend.data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class Stranka {

    @NotNull
    private int id = -1;
    @Size(min=2, message="First name must be at least two letters")
    private String firstName;
    @Size(min=2, message="Last name must be at least two letters")
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private List<Sestanek> sestanci = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Sestanek> getSestankov() {
        return sestanci;
    }

    public void setSestanci(List<Sestanek> sestanci) {
        this.sestanci = sestanci;
    }

    public boolean isNovaStranka(){
        return getId() == -1;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
