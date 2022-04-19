package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int ADULT_AGE = 18;
    private static final int PASSWORD_VALID_LENGTH = 6;
    private RegistrationService registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
        user.setAge(24);
        user.setLogin("abababalamaga");
        user.setPassword("19840321");
        user.setId(59L);
    }

    @Test
    void register_validUser_Ok() {
        User newUser = registrationService.register(user);
        assertTrue(Storage.people.contains(newUser));
        assertTrue(newUser.getPassword().length() >= PASSWORD_VALID_LENGTH);
        assertTrue(newUser.getAge() >= ADULT_AGE);
    }

    @Test
    void register_isNotAdult_notOk() {
        user.setAge(14);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageIsNotValid_notOk() {
        user.setAge(-50);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLength_notOk() {
        user.setPassword("666");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_isPasswordEmpty_notOk() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userIsNull_notOk() {
        user = null;
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordIsNull_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginIsNull_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageIsNull_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userWithExistingLogin_notOk() {
        registrationService.register(user);
        User current = new User();
        current.setAge(25);
        current.setLogin("abababalamaga");
        current.setPassword("blablabla");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

}
