package com.testTask.Test;

import com.testTask.Test.models.ContactInfo;
import com.testTask.Test.models.Education;
import com.testTask.Test.models.Employment;
import com.testTask.Test.models.PersonalInfo;
import com.testTask.Test.models.User;
import com.testTask.Test.models.Users;
import com.testTask.Test.repository.interfaces.iUserRepository;
import com.testTask.Test.service.UsersService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTest {

    @Mock
    private iUserRepository userRepository;

    @InjectMocks
    private UsersService usersService;

    private final ExecutorService executorService = Executors.newFixedThreadPool(2);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testParseXml() throws ExecutionException, InterruptedException {
        // Arrange
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><users>    <user id=\"1\">        <personalinfo>            <firstname>John</firstname>            <lastname>Doe</lastname>            <email>john.doe@example.com</email>            <dateofbirth>1990-01-01</dateofbirth>            <gender>Male</gender>        </personalinfo>        <contactinfo>            <phonenumber>+1234567890</phonenumber>            <address>                <street>123 Main St</street>                <city>New York</city>                <state>NY</state>                <postalcode>10001</postalcode>                <country>USA</country>            </address>        </contactinfo>        <employment>            <companyname>Example Corp</companyname>            <position>Software Engineer</position>            <startdate>2015-06-01</startdate>            <enddate>Present</enddate>        </employment>        <education>            <universityname>Example University</universityname>            <degree>Bachelor's in Computer Science</degree>            <graduationyear>2014</graduationyear>        </education>        <skills>            <skill>Java</skill>            <skill>Spring Boot</skill>            <skill>MongoDB</skill>            <skill>REST API</skill>        </skills>    </user></users>        ";

        // Act
        String result = usersService.parseXml(xmlString).get();

        // Assert
        assertEquals("Processed: 1", result);
    }

    @Test
    public void testParseXmlWithInvalidXml() throws ExecutionException, InterruptedException {
        // Arrange
        String invalidXml = "<users><user><id>1</id><name>John Doe</name>";

        // Act
        String result = usersService.parseXml(invalidXml).get();

        // Assert
        assertTrue(result.contains("Error"));
    }

    @Test
    public void testGetAll() throws ExecutionException, InterruptedException {
        // Arrange
        User user1 = new User();
        User user2 = new User();
        PersonalInfo personalInfo = new PersonalInfo();
        ContactInfo  contactInfo = new ContactInfo();
        com.testTask.Test.models.Address address = new com.testTask.Test.models.Address();
        Employment   employment = new Employment();
        Education    education = new Education();

        personalInfo.setFirstname("FirstName");
        personalInfo.setLastname("LastName");
        personalInfo.setEmail("test@test.com");
        personalInfo.setDateofbirth("1990-01-01");
        personalInfo.setGender("Male");

        contactInfo.setPhonenumber("+3754411111111");
        address.setCity("city");
        address.setCountry("c");
        address.setStreet("S");
        address.setState("State");
        address.setPostalcode("code");
        contactInfo.setAddress(address);

        employment.setCompanyname("company");
        employment.setPosition("position");
        employment.setEnddate("2015-06-01");
        employment.setStartdate("2012-06-01");

        education.setDegree("d");
        education.setGraduationyear("g");
        education.setUniversityname("name");

        List<String> skils = new ArrayList<String>();
        skils.add("1");
        skils.add("2");
        skils.add("3");

        user1.setId("1");
        user1.setContactInfo(contactInfo);
        user1.setEducation(education);
        user1.setEmployment(employment);
        user1.setPersonalInfo(personalInfo);
        user1.setSkills(skils);
        user2.setId("2");
        user2.setContactInfo(contactInfo);
        user2.setEducation(education);
        user2.setEmployment(employment);
        user2.setPersonalInfo(personalInfo);
        user2.setSkills(skils);

        List<User> userList = new ArrayList<User>();

        userList.add(user1);
        userList.add(user2);

        when(userRepository.findAll()).thenReturn(userList);

        // Act
        List<User> result = usersService.getAll().get();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    public void testFindUser() throws ExecutionException, InterruptedException {
        User user1 = new User();
        PersonalInfo personalInfo = new PersonalInfo();
        ContactInfo  contactInfo = new ContactInfo();
        com.testTask.Test.models.Address address = new com.testTask.Test.models.Address();
        Employment   employment = new Employment();
        Education    education = new Education();

        personalInfo.setFirstname("FirstName");
        personalInfo.setLastname("LastName");
        personalInfo.setEmail("test@test.com");
        personalInfo.setDateofbirth("1990-01-01");
        personalInfo.setGender("Male");

        contactInfo.setPhonenumber("+3754411111111");
        address.setCity("city");
        address.setCountry("c");
        address.setStreet("S");
        address.setState("State");
        address.setPostalcode("code");
        contactInfo.setAddress(address);

        employment.setCompanyname("company");
        employment.setPosition("position");
        employment.setEnddate("2015-06-01");
        employment.setStartdate("2012-06-01");

        education.setDegree("d");
        education.setGraduationyear("g");
        education.setUniversityname("name");

        List<String> skils = new ArrayList<String>();
        skils.add("1");
        skils.add("2");
        skils.add("3");

        user1.setId("1");
        user1.setContactInfo(contactInfo);
        user1.setEducation(education);
        user1.setEmployment(employment);
        user1.setPersonalInfo(personalInfo);
        user1.setSkills(skils);

        when(userRepository.findById("1")).thenReturn(Optional.of(user1));

        // Act
        Optional<User> result = usersService.findUser("1").get();

        // Assert
        assertTrue(result.isPresent());
        assertEquals("FirstName", result.get().getPersonalInfo().getFirstname());
    }

    @Test
    public void testFindUserNotFound() throws ExecutionException, InterruptedException {
        // Arrange
        when(userRepository.findById("999")).thenReturn(Optional.empty());

        // Act
        Optional<User> result = usersService.findUser("999").get();

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    public void testUpdateUser() throws ExecutionException, InterruptedException {
        // Arrange
        String id = "2";
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><user id=\"2\">        <personalinfo>            <firstname>John2new</firstname>            <lastname>Doe</lastname>            <email>john2.doe@example.com</email>            <dateofbirth>1990-01-02</dateofbirth>            <gender>Male</gender>        </personalinfo>        <contactinfo>            <phonenumber>+1234567892</phonenumber>            <address>                <street>1234 Main St</street>                <city>New York</city>                <state>NY</state>                <postalcode>10001</postalcode>                <country>USA</country>            </address>        </contactinfo>        <employment>            <companyname>Example Corp</companyname>            <position>Software Engineer</position>            <startdate>2015-06-01</startdate>            <enddate>Present</enddate>        </employment>        <education>            <universityname>Example University</universityname>            <degree>Bachelor's in Computer Science</degree>            <graduationyear>2014</graduationyear>        </education>        <skills>            <skill>Java</skill>            <skill>Spring Boot</skill>            <skill>MongoDB</skill>            <skill>REST API</skill>        </skills>    </user>";
        
        User user1 = new User();
        PersonalInfo personalInfo = new PersonalInfo();
        ContactInfo  contactInfo = new ContactInfo();
        com.testTask.Test.models.Address address = new com.testTask.Test.models.Address();
        Employment   employment = new Employment();
        Education    education = new Education();

        personalInfo.setFirstname("FirstName");
        personalInfo.setLastname("LastName");
        personalInfo.setEmail("test@test.com");
        personalInfo.setDateofbirth("1990-01-01");
        personalInfo.setGender("Male");

        contactInfo.setPhonenumber("+3754411111111");
        address.setCity("city");
        address.setCountry("c");
        address.setStreet("S");
        address.setState("State");
        address.setPostalcode("code");
        contactInfo.setAddress(address);

        employment.setCompanyname("company");
        employment.setPosition("position");
        employment.setEnddate("2015-06-01");
        employment.setStartdate("2012-06-01");

        education.setDegree("d");
        education.setGraduationyear("g");
        education.setUniversityname("name");

        List<String> skils = new ArrayList<String>();
        skils.add("1");
        skils.add("2");
        skils.add("3");

        user1.setId(id);
        user1.setContactInfo(contactInfo);
        user1.setEducation(education);
        user1.setEmployment(employment);
        user1.setPersonalInfo(personalInfo);
        user1.setSkills(skils);

        when(userRepository.findById(id)).thenReturn(Optional.of(user1));
        when(userRepository.save(any(User.class))).thenReturn(user1);

        // Act
        String result = usersService.updateUser(id, xmlString).get();

        // Assert
        assertEquals("Updated", result);
    }

    @Test
    public void testUpdateUserNotFound() throws ExecutionException, InterruptedException {
        // Arrange
        String id = "999";
        String xmlString = "<user><id>999</id><name>Updated Name</name></user>";
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        String result = usersService.updateUser(id, xmlString).get();

        // Assert
        assertEquals("User not found", result);
    }

    @Test
    public void testDeleteUser() throws ExecutionException, InterruptedException {
        // Arrange
        String id = "1";
        doNothing().when(userRepository).deleteById(id);

        // Act
        String result = usersService.deleteUser(id).get();

        // Assert
        assertEquals("Done", result);
        verify(userRepository, times(1)).deleteById(id);
    }
}