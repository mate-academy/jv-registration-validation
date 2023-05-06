package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void register_TooShortLogin_NotOk() {
        user.setLogin("123");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setLogin("1234");
        assertThrows(RegistrationException.class,() -> registrationService.register(user));
        user.setLogin("12345");
        assertThrows(RegistrationException.class,() -> registrationService.register(user));
    }

    @Test
    void register_TooShortPassword_NotOk() {
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
        user.setAge(Integer.MIN_VALUE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_AgeGreaterThan18_Ok() {
        user.setAge(18);
        assertEquals(user.getAge(), registrationService.register(user).getAge());
        Storage.people.clear();
        user.setLogin("Abraham");
        user.setPassword("fo30re9st");
        user.setAge(87);
        assertEquals(user.getAge(), registrationService.register(user).getAge());
        Storage.people.clear();
        user.setLogin("Abraham");
        user.setPassword("fo30re9st");
        user.setAge(Integer.MAX_VALUE);
        assertEquals(user.getAge(), registrationService.register(user).getAge());
    }

    @Test
    void register_LoginHasCorrectLength_Ok() {
        user.setLogin("abcdef");
        assertEquals(user.getLogin().length(),
                registrationService.register(user).getLogin().length());
        Storage.people.clear();
        user.setLogin("Bgreekk123565567");
        user.setPassword("fo30re9st");
        user.setAge(28);
        assertEquals(user.getLogin().length(),
                registrationService.register(user).getLogin().length());
    }

    @Test
    void register_PasswordHasCorrectLength_Ok() {
        user.setPassword("123456");
        assertEquals(user.getPassword().length(),
                registrationService.register(user).getPassword().length());
        Storage.people.clear();
        user.setLogin("Abraham");
        user.setAge(28);
        user.setPassword("1234567891011121314151617181920212222324252627282930");
        assertEquals(user.getPassword().length(),
                registrationService.register(user).getPassword().length());
    }

    @Test
    void register_UserLoginAlreadyExist_NotOk() {
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
