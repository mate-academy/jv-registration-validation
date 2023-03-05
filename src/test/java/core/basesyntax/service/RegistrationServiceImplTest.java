package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static User userOne = new User();
    private static User userTwo = new User();
    private static User userThree = new User();
    private static User userFour = new User();
    private static User userFive = new User();
    private RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

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

        userFive.setLogin(null);
        userFive.setPassword(null);
        userFive.setAge(null);
    }

    @Test
    void userAge_Ok() {
        try {
            assertEquals(userFour, registrationService.register(userFour));
        } catch (RegistrationFailedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void userAge_NotOk() {
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(userOne);
        }, "Your age must be over 18!");
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(userFive);
        }, "Your age must be over 18!");
    }

    @Test
    void userLogin_Ok() {
        try {
            assertEquals(userFour, registrationService.register(userFour));
        } catch (RegistrationFailedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void userLogin_NotOk() {
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(userThree);
        }, "Your login already exist!");
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(userFive);
        }, "You wrote an empty login!");
    }

    @Test
    void userPassword_Ok() {
        try {
            assertEquals(userFour, registrationService.register(userFour));
        } catch (RegistrationFailedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void userPassword_NotOk() {
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(userTwo);
        }, "Your password must be at least 6 characters!");
    }
}
