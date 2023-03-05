package core.basesyntax.service;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    static User userOne = new User();
    static User userTwo = new User();
    static User userThree = new User();
    static User userFour = new User();
    RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

    @BeforeAll
    static void beforeAll() {
        userOne.setLogin("Sakura");
        userOne.setPassword("12345678");
        userOne.setAge(16);

        userTwo.setLogin("CherryTree");
        userTwo.setPassword("12345");
        userTwo.setAge(19);

        userThree.setLogin("Sakura");
        userThree.setPassword("123456");
        userThree.setAge(18);

        userFour.setLogin("ILoveCherry");
        userFour.setPassword("ireallylovecherry");
        userFour.setAge(21);
    }

    @Test
    void userAge_Ok() {
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(userOne);
        }, "Your age must be over 18!");
        assertThrows(RegistrationFailedException.class, () -> {
           registrationService.register(userTwo);
        }, "Your password must be more than 6 characters!");
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(userThree);
        }, "Your login already exist!");
        try {
            assertEquals(userFour, registrationService.register(userFour));
        } catch (RegistrationFailedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void userTwo() {
    }

    @Test
    void userLogin_Ok() {
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(userThree);
        }, "Your login already exist!");
        try {
            assertEquals(userFour, registrationService.register(userFour));
        } catch (RegistrationFailedException e) {
            throw new RuntimeException(e);
        }
        try {
            assertEquals(userOne, registrationService.register(userOne));
        } catch (RegistrationFailedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void userPassword_Ok() {
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(userTwo);
        }, "Your password must be more than 6 characters!");
        try {
            assertEquals(userFour, registrationService.register(userFour));
        } catch (RegistrationFailedException e) {
            throw new RuntimeException(e);
        }
        try {
            assertEquals(userThree, registrationService.register(userThree));
        } catch (RegistrationFailedException e) {
            throw new RuntimeException(e);
        }
    }
}