package core.basesyntax.service;

import static core.basesyntax.db.Storage.people;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User validUser;
    private static User notValidUser;

    @BeforeAll
    public static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        people.clear();
        validUser = null;
        notValidUser = null;
    }

    @Test
    public void registrationService_validUser_Ok() {
        validUser = new User("Bob","123456", 18);
        registrationService.register(validUser);
        assertTrue(people.contains(validUser));
        assertEquals(1,people.size());
        assertEquals(people.get(0),validUser);
    }

    @Test
    public void registrationService_validUserWithOldAge_Ok() {
        validUser = new User("Bob","123456", 99);
        registrationService.register(validUser);
        assertTrue(people.contains(validUser));
        assertEquals(1,people.size());
        assertEquals(people.get(0),validUser);
    }

    @Test
    public void registrationService_userNotValidLogin_NotOk() {
        people.add(new User("Bob","123456", 18));
        notValidUser = new User("Bob", "1111111", 23);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(notValidUser);
        });
    }

    @Test
    public void registrationService_userNotValidPassword_NotOk() {
        notValidUser = new User("Alice","12345",22);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(notValidUser);
        });
    }

    @Test
    public void registrationService_userNotValidAge_NotOk() {
        notValidUser = new User("John", "123456",17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(notValidUser);
        });
    }

    @Test
    public void registrationService_userNegativeAge_NotOk() {
        notValidUser = new User("John", "123456",-22);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(notValidUser);
        });
    }

    @Test
    public void registrationService_userNullLogin_NotOk() {
        notValidUser = new User(null, "1111111", 23);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(notValidUser);
        });
    }

    @Test
    public void registrationService_userNullPassword_NotOk() {
        notValidUser = new User("Alice",null,22);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(notValidUser);
        });
    }

    @Test
    public void registrationService_userNullAge_NotOk() {
        notValidUser = new User("John", "123456",null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(notValidUser);
        });
    }

    @Test
    public void registrationService_userEmptyLineLogin_NotOk() {
        notValidUser = new User("", "1111111", 23);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(notValidUser);
        });
    }

    @Test
    public void registrationService_userEmptyLinePassword_NotOk() {
        notValidUser = new User("Alice","",22);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(notValidUser);
        });
    }
}
