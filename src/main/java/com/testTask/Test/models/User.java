package com.testTask.Test.models;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@XmlRootElement(name = "user")
@Document(collection = "users")
public class User {
    @Id
    private String id;

    private PersonalInfo personalInfo;
    private ContactInfo contactInfo;
    private Employment employment;
    private Education education;
    private List<String> skills;

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement(name = "personalinfo")
    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    @XmlElement(name = "contactinfo")
    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    @XmlElement(name = "employment")
    public Employment getEmployment() {
        return employment;
    }

    public void setEmployment(Employment employment) {
        this.employment = employment;
    }

    @XmlElement(name = "education")
    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    @XmlElementWrapper(name="skills")
    @XmlElement(name="skill")
    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}

class PersonalInfo {
    private String firstname;
    private String lastname;
    private String email;

    @XmlElement(name = "firstname")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @XmlElement(name = "lastname")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @XmlElement(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

class ContactInfo {
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
class Address {
    private String street;
    private String city;
    private String state;
    private String postalcode;
    private String country;

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getPostalcode() {
        return postalcode;
    }
    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
}

class Employment {
    private String companyname;
    private String position;
    private String startdate;
    private String enddate;

    public String getCompanyname() {
        return companyname;
    }
    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public String getStartdate() {
        return startdate;
    }
    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }
    public String getEnddate() {
        return enddate;
    }
    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }
}

class Education {
    private String universityname;
    private String degree;
    private String graduationyear;

    public String getUniversityname() {
        return universityname;
    }
    public void setUniversityname(String universityname) {
        this.universityname = universityname;
    }
    public String getDegree() {
        return degree;
    }
    public void setDegree(String degree) {
        this.degree = degree;
    }
    public String getGraduationyear() {
        return graduationyear;
    }
    public void setGraduationyear(String graduationyear) {
        this.graduationyear = graduationyear;
    }
}
// Аналогично создайте классы ContactInfo, Employment, Education
