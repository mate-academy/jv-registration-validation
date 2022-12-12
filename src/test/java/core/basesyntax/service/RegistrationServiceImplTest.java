package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static User userOne;
    private static User userTwo;
    private static User userThree;
    private static User nullUser;
    private static User userFour;

    @BeforeAll
    public static void createEntities() {
        registrationService = new RegistrationServiceImpl();
        userOne = new User();
        userTwo = new User();
        userThree = new User();
        userFour = new User();
        nullUser = null;

        //create first User with valid credentials
        userOne.setLogin("John");
        userOne.setAge(18);
        userOne.setPassword("qwerty");

        //create second User with invalid password
        userTwo.setLogin("Corey");
        userTwo.setAge(42);
        userTwo.setPassword("asdf");

        //create third User with invalid age
        userThree.setLogin("John Deere");
        userThree.setAge(12);
        userThree.setPassword("qwerty");

        //create fourth User with valid credentials
        userFour.setLogin("James");
        userFour.setAge(58);
        userFour.setPassword("metallica");
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void registerUsersWithInvalidCredentials_notOk() {
        assertThrows(ValidationException.class, () -> registrationService.register(userTwo));
        assertThrows(ValidationException.class, () -> registrationService.register(userThree));
        assertThrows(ValidationException.class, () -> registrationService.register(nullUser));

    }

    @Test
    void registerWithValidAndInvalidCredentialsUsers() {
        assertEquals(userOne, registrationService.register(userOne));
        assertThrows(ValidationException.class, () -> registrationService.register(userOne));
        assertThrows(ValidationException.class, () -> registrationService.register(nullUser));
        assertThrows(ValidationException.class, () -> registrationService.register(userTwo));
        assertEquals(userFour, registrationService.register(userFour));
    }

    @Test
    void registerWithSameUser_notOk() {
        assertEquals(userOne, registrationService.register(userOne));
        assertThrows(ValidationException.class, () -> registrationService.register(userOne));
    }

    @Test
    void register_Ok() {
        assertEquals(userOne, registrationService.register(userOne));
        assertEquals(userFour, registrationService.register(userFour));
    }
}
