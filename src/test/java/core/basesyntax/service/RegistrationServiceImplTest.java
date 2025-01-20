package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private final User testUser = new User();

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        testUser.setLogin("Albertino");
        testUser.setPassword("password");
        testUser.setAge(25);
    }

    @Test
    void nullUser_NotOk() {
        try {
            registrationService.register(null);
        } catch (InvalidDataException e) {
            return;
        }
        fail("InvalidDataException should be thrown if User is null!");
    }

    @Test
    void youngUser_NotOk() {
        testUser.setAge(-5);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser),
                "User's age is less than 18");
        testUser.setAge(0);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser),
                "User's age is less than 18");
        testUser.setAge(15);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser),
                "User's age is less than 18");
    }

    @Test
    void shortUsersLogin_NotOk() {
        testUser.setLogin("Bob");
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser),
                "InvalidDataException should be thrown if User's login is no longer "
                + "than 6 symbols!");
    }

    @Test
    void shortUsersPassword_NotOk() {
        String errorMessage = "User's password is less than 6 symbols";
        testUser.setPassword(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser),
                errorMessage);
        testUser.setPassword("Bob");
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser),
                errorMessage);
    }

    @Test
    void correctPasswordLength_Ok() {
        testUser.setPassword("Bobina");
        try {
            User registeredUser = registrationService.register(testUser);
            int actual = registeredUser.getPassword().length();
            assertEquals(6, actual);
        } catch (InvalidDataException e) {
            throw new RuntimeException(e);
        }
    }
}
