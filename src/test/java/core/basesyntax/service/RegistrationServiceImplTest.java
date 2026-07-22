package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "validLogin";
    private static final String VALID_PASSWORD = "validPassword";
    private static final int VALID_AGE = 20;

    private RegistrationService registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_validUser_ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
        assertEquals(user, Storage.people.get(Storage.people.size() - 1));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_existingLogin_notOk() {
        registrationService.register(user);
        User anotherUser = new User();
        anotherUser.setLogin(VALID_LOGIN);
        anotherUser.setPassword(VALID_PASSWORD);
        anotherUser.setAge(VALID_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(anotherUser));
    }

    @Test
    void register_loginLengthLessThanMin_notOk() {
        user.setLogin("abcde");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginLengthEqualToMin_ok() {
        user.setLogin("abcdef");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLengthLessThanMin_notOk() {
        user.setPassword("abcde");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLengthEqualToMin_ok() {
        user.setPassword("abcdef");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_emptyPassword_notOk() {
        user.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageLessThanMin_notOk() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageEqualToMin_ok() {
        user.setAge(18);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(-5);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_zeroAge_notOk() {
        user.setAge(0);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_zeroAge_notOK() {
        user.setAge(0);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
