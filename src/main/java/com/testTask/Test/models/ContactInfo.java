package com.testTask.Test.models;

import jakarta.xml.bind.annotation.XmlElement;

public class ContactInfo {
    private String phonenumber;
    private Address address;

    @XmlElement(name = "phonenumber")
    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    @XmlElement(name = "address")
    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}