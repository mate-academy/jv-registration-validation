package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private User user;

    @BeforeAll
    static void init() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("Abraham");
        user.setPassword("fo30re9st");
        user.setAge(28);
    }

    @Test
    void register_NullAge_NotOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullLogin_NotOK() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullPassword_NotOK() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_LessLoginLength_NotOk() {
        user.setLogin("123");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setLogin("1234");
        assertThrows(RegistrationException.class,() -> registrationService.register(user));
        user.setLogin("12345");
        assertThrows(RegistrationException.class,() -> registrationService.register(user));
    }

    @Test
    void register_LessPasswordLength_NotOk() {
        user.setPassword("123");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setPassword("12345");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_AgeLessThanRequired_NotOk() {
        user.setAge(15);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setAge(7);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setAge(0);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_AgeGreaterThan18_Ok() {
        user.setAge(18);
        assertTrue(registrationService.register(user).getAge()
                >= RegistrationServiceImpl.MIN_USER_AGE);
        Storage.people.clear();
        user.setLogin("Abraham");
        user.setPassword("fo30re9st");
        user.setAge(79);
        assertTrue(registrationService.register(user).getAge()
                >= RegistrationServiceImpl.MIN_USER_AGE);
    }

    @Test
    void register_LoginHasCorrectLength_Ok() {
        user.setLogin("abcdef");
        assertTrue(registrationService.register(user).getLogin().length()
                >= RegistrationServiceImpl.MIN_LOGIN_CHARACTERS);
        Storage.people.clear();
        user.setLogin("Bgreekk123565567");
        user.setPassword("fo30re9st");
        user.setAge(28);
        assertTrue(registrationService.register(user).getLogin().length()
                >= RegistrationServiceImpl.MIN_LOGIN_CHARACTERS);
    }

    @Test
    void register_PasswordHasCorrectLength_Ok() {
        user.setPassword("123456");
        assertTrue(registrationService.register(user).getPassword().length()
                >= RegistrationServiceImpl.MIN_PASSWORD_CHARACTERS);
        Storage.people.clear();
        user.setLogin("Abraham");
        user.setPassword("1234567891011121314151617181920212222324252627282930");
        user.setAge(28);
        assertTrue(registrationService.register(user).getPassword().length()
                >= RegistrationServiceImpl.MIN_PASSWORD_CHARACTERS);
    }

    @Test
    void register_UserLoginAlreadyExist_Ok() {
        Storage.people.add(user);
        User sameUser = new User();
        sameUser.setLogin("Abraham");
        sameUser.setPassword("fo30re9st");
        sameUser.setAge(28);
        assertThrows(RegistrationException.class, () -> registrationService.register(sameUser));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
