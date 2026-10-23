package core.basesyntax.service;

import static core.basesyntax.db.Storage.people;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.expection.AlreadyExistException;
import core.basesyntax.expection.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("login123");
        user.setId(1L);
        user.setAge(20);
        user.setPassword("321883218321");
        people.clear();
    }

    @Test
    void register_userExists_notOk() {
        registrationService.register(user);
        assertThrows(AlreadyExistException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userNotAddedBecauseOfAge_NoOk() {
        user.setAge(10);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userNotAddedBecauseOfLogin_NoOk() {
        user.setLogin("ew");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userNotAddedBecauseOfPassword_NoOk() {
        user.setPassword("2941");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userAdded_Ok() {
        User result = registrationService.register(user);
        assertNotNull(result);
    }

    @Test
    void register_userDataIsNull_NoOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_userAgeIsZero_NoOk() {
        user.setAge(0);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userPasswordIsNull_NoOk() {
        user.setPassword(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userLoginIsNull_NoOk() {
        user.setLogin(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userLoginLengthIsFive_NoOk() {
        user.setLogin("rwqer");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userAgeIsSeventeen_NoOk() {
        user.setAge(17);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userPasswordIsEmpty_NoOk() {
        user.setPassword("");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userLoginIsEmpty_NoOk() {
        user.setLogin("");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userCreated_Ok() {
        User result = registrationService.register(user);
        assertEquals(user, result);
    }

    @Test
    void register_userContains_Ok() {
        User result = registrationService.register(user);
        assertTrue(people.contains(result));
    }
}
