package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidUserException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private static final int MIN_AGE = 18;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void defaultUser() {
        user = new User();
        user.setLogin("stim_one");
        user.setAge(MIN_AGE);
        user.setPassword("MyCatNameDin");
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void register_userValid_Ok() {
        registrationService.register(user);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));

    }

    @Test
    void register_loginNull_notOk() {
        user.setLogin(null);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_lowAge_notOk() {
        user.setAge(MIN_AGE - 1);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageNull_notOk() {
        user.setAge(null);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLength_notOk() {
        user.setPassword("1234");
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordNull_notOk() {
        user.setPassword(null);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }
}
