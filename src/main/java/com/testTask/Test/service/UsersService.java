package com.testTask.Test.service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;

import com.testTask.Test.models.ContactInfo;
import com.testTask.Test.models.Education;
import com.testTask.Test.models.Employment;
import com.testTask.Test.models.PersonalInfo;
import com.testTask.Test.models.User;
import com.testTask.Test.models.Users;
import com.testTask.Test.repository.interfaces.iUserRepository;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

@Service
public class UsersService {
    @Autowired
    private iUserRepository userRepository;

    @Async
    public CompletableFuture<String> parseXml(String xmlString) {
        Users users;
        try {
            InputStream inputStream = new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8));
            JAXBContext jaxbContext = JAXBContext.newInstance(Users.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            users = (Users) unmarshaller.unmarshal(inputStream);
            for (User user : users.getUserList()) {
                userRepository.save(user);
            }
        } catch (Exception e) {
            return CompletableFuture.completedFuture("Error: " + e.getMessage());
        }

        return CompletableFuture.completedFuture("Processed: " + users.getUserList().size());
    }

    @Async
    public CompletableFuture<String> parseLargeXml(String xmlString) {
        Integer count = 0;
        try {
            
            InputStream xmlInputStream = new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8));
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(xmlInputStream, StandardCharsets.UTF_8.name());

            User user = null;
            PersonalInfo personalInfo = null;
            ContactInfo  contactInfo = null;
            com.testTask.Test.models.Address address = null;
            Employment   employment = null;
            Education    education = null;
            List<String> skils = null;

            String elementName = null;

            // Чтение XML по частям
            while (reader.hasNext()) {
                int event = reader.next();

                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        elementName = reader.getLocalName();
                        switch (elementName) {
                            case "user":
                                String id = reader.getAttributeValue(0);
                                user = new User();
                                user.setId(id);
                                break;
                            case "personalinfo":
                                personalInfo = new PersonalInfo();
                                break;
                            case "contactinfo":
                                contactInfo = new ContactInfo();
                                break;
                            case "address":
                                address = new com.testTask.Test.models.Address();
                                break;
                            case "employment":
                                employment = new Employment();
                                break;
                            case "education":
                                education = new Education();
                                break;
                            case "skills":
                                skils = new ArrayList<String>();
                                break;
                            default:
                                break;
                        }
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        if (user != null && elementName != null) {
                            String content = reader.getText().trim();
                            switch (elementName) {
                                case "id":
                                    user.setId(content);
                                    break;
                                // personalInfo
                                case "firstname":
                                    personalInfo.setFirstname(content);
                                    break;
                                case "lastname":
                                    personalInfo.setLastname(content);
                                    ;
                                    break;
                                case "email":
                                    personalInfo.setEmail(content);
                                    break;
                                case "dateofbirth":
                                    personalInfo.setDateofbirth(content);
                                    break;
                                case "gender":
                                    personalInfo.setGender(content);
                                    break;
                                //contactinfo
                                case "phonenumber":
                                    contactInfo.setPhonenumber(content);
                                    break;
                                //contactinfo/address
                                case "street":
                                    address.setStreet(content);
                                break;
                                case "city":
                                    address.setCity(content);
                                break;
                                case "state":
                                    address.setState(content);
                                break;
                                case "postalcode":
                                    address.setPostalcode(content);
                                break;
                                case "country":
                                    address.setCountry(content);
                                break;
                                //employment
                                case "companyname":
                                    employment.setCompanyname(content);
                                break;
                                case "position":
                                    employment.setPosition(content);
                                break;
                                case "startdate":
                                    employment.setStartdate(content);
                                break;
                                case "enddate":
                                    employment.setEnddate(content);
                                break;
                                //education
                                case "universityname":
                                    education.setUniversityname(content);
                                break;
                                case "degree":
                                    education.setDegree(content);
                                break;
                                case "graduationyear":
                                    education.setGraduationyear(content);
                                break;
                                // skills
                                case "skill":
                                    skils.add(content);
                                    break;
                            }
                        }
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        elementName = null;
                        if("address".equals(reader.getLocalName())) {
                            contactInfo.setAddress(address);
                        } else if ("user".equals(reader.getLocalName())) {
                            user.setPersonalInfo(personalInfo);
                            user.setEducation(education);
                            user.setEmployment(employment);
                            user.setContactInfo(contactInfo);
                            user.setSkills(skils);
                            userRepository.save(user);
                            user = null; // Reset user object
                            personalInfo = null;
                            contactInfo = null;
                            address = null;
                            employment = null;
                            education = null;
                            skils = null;
                            ++count;
                        }
                        break;
                }
            }

            reader.close();
        } catch (Exception e) {
            return CompletableFuture.completedFuture("Error: " + e.getMessage());
        }

        return CompletableFuture.completedFuture("Processed: " + Integer.toString(count));
    }

    @Async
    public CompletableFuture<List<User>> getAll() {
        return CompletableFuture.completedFuture(userRepository.findAll());
    }

    @Async
    public CompletableFuture<Optional<User>> findUser(String id) {
        return CompletableFuture.completedFuture(userRepository.findById(id));
    }

    @Async
    public CompletableFuture<String> updateUser(String id, String xmlString) {
        if (!userRepository.findById(id).isPresent()) {
            return CompletableFuture.completedFuture("User not found");
        }
        try {
            InputStream inputStream = new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8));
            JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            User user = (User) unmarshaller.unmarshal(inputStream);
            user.setId(id);
            userRepository.save(user);
        } catch (Exception e) {
            return CompletableFuture.completedFuture(e.getMessage());
        }

        return CompletableFuture.completedFuture("Updated");
    }

    @Async
    public CompletableFuture<String> deleteUser(String id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            return CompletableFuture.completedFuture("Error: " + e.getMessage());
        }

        return CompletableFuture.completedFuture("Done");
    }
}
