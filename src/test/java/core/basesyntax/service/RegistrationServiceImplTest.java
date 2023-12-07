package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;

    void initDefaultUser() {
        user = new User();
        user.setAge(19);
        user.setLogin("someLogin");
        user.setPassword("somePassword");
    }

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        initDefaultUser();
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortLogin_notOk() {
        user.setLogin("");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        user.setLogin("abc");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        user.setLogin("abcdf");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_validLogin_Ok() {
        user.setLogin("someValidLogin");
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
        initDefaultUser();
        user.setLogin("verryyyBiiigLogin1234$%@#");
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortPassword_notOk() {
        user.setLogin("newLogin");
        user.setPassword("");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });

        user.setPassword("123");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        user.setPassword("12345");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_validPassword_Ok() {
        user.setLogin("newLoginABC");
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
        initDefaultUser();
        user.setLogin("123login456");
        user.setPassword("someDifficultPaSsWoRd123");
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));

    }

    @Test
    void register_nullAge_notOk() {
        user.setLogin("nullAgeUserLogin");
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_negativeAge_notOk() {
        user.setLogin("negativeAgeLogin");
        user.setAge(-5);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        user.setAge(-1);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_underEighteen_notOk() {
        user.setLogin("under18Login");
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        user.setAge(2);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_eighteen_Ok() {
        user.setLogin("eighteenTestLogin");
        user.setAge(18);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_overEighteen_Ok() {
        user.setLogin("over18Log");
        user.setAge(56);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }
}
