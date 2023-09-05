package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(111L);
        user.setLogin("reexxx");
        user.setPassword("qwerty");
        user.setAge(25);
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.clear();
    }

    @Test
    void registerValidUser() {
        User actual = registrationService.register(user);
        assertEquals(actual, user);
    }

    @Test
    void registerUserWithShortLogin() {
        user.setLogin("reex");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserWithNullLogin() {
        user.setLogin(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserWithShortPassword() {
        user.setPassword("qwert");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserWithNullPassword() {
        user.setPassword(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserWithInvalidAge() {
        user.setAge(17);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserWithNullAge() {
        user.setAge(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void registerAlreadyRegisteredUser() {
        registrationService.register(user);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }
}
