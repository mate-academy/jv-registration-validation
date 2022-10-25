package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exseption.InvalidAgeException;
import core.basesyntax.exseption.InvalidLoginException;
import core.basesyntax.exseption.InvalidPasswordException;
import core.basesyntax.exseption.LoginIsTakenException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static String VALID_LOGIN_1;
    private static String VALID_LOGIN_2;
    private static String VALID_PASSWORD;
    private static String INVALID_PASSWORD;
    private static Integer AGE_OVER_18;
    private static Integer AGE_UNDER_18;
    private static Integer AGE_18;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        VALID_LOGIN_1 = "first_person";
        VALID_LOGIN_2 = "second_person";
        VALID_PASSWORD = "12345678";
        INVALID_PASSWORD = "12345";
        AGE_OVER_18 = 22;
        AGE_UNDER_18 = 17;
        AGE_18 = 18;
    }

    @Test
    void userWithValidParameters() {
        User user = new User(VALID_LOGIN_1, VALID_PASSWORD, AGE_OVER_18);
        User actual = registrationService.register(user);
        assertEquals(Storage.people.size(), 1);
        assertEquals(Storage.people.get(0), user);
    }

    @Test
    void userWithNullLogin() {
        User user = new User(null, VALID_PASSWORD, AGE_OVER_18);
        assertThrows(InvalidLoginException.class, () -> registrationService.register(user));
    }

    @Test
    void userWithNullPassword() {
        User user = new User(VALID_LOGIN_1, null, AGE_OVER_18);
        assertThrows(InvalidPasswordException.class, () -> registrationService.register(user));
    }

    @Test
    void userWithNullAge() {
        User user = new User(VALID_LOGIN_1, VALID_PASSWORD, null);
        assertThrows(InvalidAgeException.class, () -> registrationService.register(user));
    }

    @Test
    void userWithInvalidPassword() {
        User user = new User(VALID_LOGIN_1, INVALID_PASSWORD, AGE_OVER_18);
        assertThrows(InvalidPasswordException.class, () -> registrationService.register(user));
    }

    @Test
    void userUnder_18() {
        User user = new User(VALID_LOGIN_1, VALID_PASSWORD, AGE_UNDER_18);
        assertThrows(InvalidAgeException.class, () -> registrationService.register(user));
    }

    @Test
    void userWithAge_18() {
        User user = new User(VALID_LOGIN_1, VALID_PASSWORD, AGE_18);
        registrationService.register(user);
        assertEquals(Storage.people.size(), 1);
        assertEquals(Storage.people.get(0), user);
    }

    @Test
    void twoUsers() {
        User user1 = new User(VALID_LOGIN_1, VALID_PASSWORD, AGE_18);
        User user2 = new User(VALID_LOGIN_2, VALID_PASSWORD, AGE_OVER_18);
        registrationService.register(user1);
        registrationService.register(user2);
        assertEquals(Storage.people.size(), 2);
        assertEquals(Storage.people.get(0), user1);
        assertEquals(Storage.people.get(1), user2);
    }

    @Test
    void twoIdenticalUsers() {
        User user = new User(VALID_LOGIN_1, VALID_PASSWORD, AGE_18);
        registrationService.register(user);
        assertThrows(LoginIsTakenException.class, () -> registrationService.register(user));
        assertEquals(Storage.people.size(), 1);
        assertEquals(Storage.people.get(0), user);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
