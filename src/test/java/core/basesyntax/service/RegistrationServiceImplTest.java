package core.basesyntax.service;

import static core.basesyntax.db.Storage.people;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User validUserBob;
    private static User validUserKyle;
    private static User validUserGreg;
    private static User userWithNotValidLogin;
    private static User userWithNotValidAge;
    private static User userWithNotValidPassword;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        validUserBob = new User("Bob", "123456",18);
        validUserKyle = new User("Kyle", "1234567",99);
        validUserGreg = new User("Greg", "qwerty",33);
    }

    @BeforeEach
    void setUp() {
        people.clear();
        people.add(validUserGreg);
    }

    @Test
    void validUser_Ok() {
        registrationService.register(validUserBob);
        assertTrue(people.contains(validUserBob));
        assertEquals(2,people.size());
        assertEquals(people.get(1),validUserBob);
    }

    @Test
    void validUserWithOldAge_Ok() {
        people.add(validUserBob);
        registrationService.register(validUserKyle);
        assertTrue(people.contains(validUserKyle));
        assertEquals(3,people.size());
        assertEquals(people.get(2),validUserKyle);
    }

    @Test
    void userNotValidLogin_NotOk() {
        people.add(validUserBob);
        userWithNotValidLogin = new User("Bob", "1111111", 23);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(userWithNotValidLogin);
        });
    }

    @Test
    void userNotValidPassword_NotOk() {
        userWithNotValidPassword = new User("Alice","12345",22);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(userWithNotValidPassword);
        });
    }

    @Test
    void userNotValidAge_NotOk() {
        userWithNotValidAge = new User("John", "123456",17);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(userWithNotValidAge);
        });
    }

    @Test
    void userNegativeAge_NotOk() {
        userWithNotValidAge = new User("John", "123456",-22);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(userWithNotValidAge);
        });
    }

    @Test
    void userNullLogin_NotOk() {
        userWithNotValidLogin = new User(null, "1111111", 23);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(userWithNotValidLogin);
        });
    }

    @Test
    void userNullPassword_NotOk() {
        userWithNotValidPassword = new User("Alice",null,22);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(userWithNotValidPassword);
        });
    }

    @Test
    void userNullAge_NotOk() {
        userWithNotValidAge = new User("John", "123456",null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(userWithNotValidAge);
        });
    }

    @Test
    void userEmptyLineLogin_NotOk() {
        userWithNotValidLogin = new User("", "1111111", 23);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(userWithNotValidLogin);
        });
    }

    @Test
    void userEmptyLinePassword_NotOk() {
        userWithNotValidPassword = new User("Alice","",22);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(userWithNotValidPassword);
        });
    }
}
