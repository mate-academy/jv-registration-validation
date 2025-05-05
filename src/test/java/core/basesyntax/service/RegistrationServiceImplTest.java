package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static final int VALID_AGE = 18;
    private static final String VALID_LOGIN = "login@gmail.com";
    private static final String VALID_PASSWORD = "password";
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(VALID_AGE);
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
    }

    @Test
    void register_validUser_Ok() {
        assertEquals(user, registrationService.register(user));
        List<User> expected = List.of(user);
        assertEquals(expected, Storage.people);
    }

    @Test
    void register_ageLessThanAllowed_NotOk() {
        user.setAge(10);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setAge(3);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setAge(0);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageIsNull_NotOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginIsNull_NotOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginLessThanAllowed_NotOk() {
        user.setLogin("qwe");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setLogin(" ");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setLogin("qwert");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordIsNull_NotOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLessThanAllowed_NotOk() {
        user.setPassword("qwert");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setPassword(" ");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setPassword("qwe");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
