package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final User USER_BOB = new User("boblogin", "bobpassword", 28);
    private static final User USER_ALICE = new User("alicelogin", "alicepassword", 19);
    private static final User USER_JOHN = new User("johnlogin", "johnpassword", 23);
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        registrationService.register(USER_BOB);
        registrationService.register(USER_ALICE);
        registrationService.register(USER_JOHN);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullLogin_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User(null, "johnpassword", 23));
        });
    }

    @Test
    void register_nullPassword_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("login123", null, 19));
        });
    }

    @Test
    void register_nullAge_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("login123", "alicepassword", null));
        });
    }

    @Test
    void register_ageLessThan18_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("login123", "alicepassword", 17));
        });
    }

    @Test
    void register_passwordLengthLessThan6_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("login123", "login", 19));
        });
    }

    @Test
    void register_loginAlreadyExists_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("boblogin", "newpassword", 23));
        });
    }

    @Test
    void register_ageMoreThanMax_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("login123", "newpassword", 158));
        });
    }

    @Test
    void registrationCompleteForThreeUsers_ok() {
        List<User> actual = Storage.people;
        assertEquals(3, actual.size());
        assertTrue(actual.contains(USER_BOB));
        assertTrue(actual.contains(USER_ALICE));
        assertTrue(actual.contains(USER_JOHN));
    }

    @Test
    void register_validUser_ok() {
        User expected = new User("testUser", "testUserPassword", 31);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }
}
