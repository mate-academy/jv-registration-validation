package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "default_login";
    private static final String DEFAULT_PASSWORD = "default_password";
    private static final String NON_UNIQUE_LOGIN = "nonunique_login";
    private static final int INITIAL_AGE = 24;
    private static final int MIN_AGE = 18;
    private static RegistrationService registrationService;
    private User defaultUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void before() {
        defaultUser = new User();
        defaultUser.setAge(INITIAL_AGE);
        defaultUser.setLogin(DEFAULT_LOGIN);
        defaultUser.setPassword(DEFAULT_PASSWORD);
    }

    @AfterEach
    void after() {
        Storage.people.clear();
    }

    @Test
    void registerNull_notOk() {
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void registerAlreadyExistedUser_notOk() {
        User existingUser = new User();
        existingUser.setLogin(NON_UNIQUE_LOGIN);
        Storage.people.add(existingUser);
        defaultUser.setLogin(NON_UNIQUE_LOGIN);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(defaultUser);
        }, "method does not allow to store duplicates");
    }

    @Test
    void register_passwordLength_notOk() {
        defaultUser.setPassword("");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(defaultUser);
        }, "method does not allow register user with shor passwords");
    }

    @Test
    void register_passwordIsNull_notOk() {
        defaultUser.setPassword(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(defaultUser);
        }, "password doesn`t have to be null");
    }

    @Test
    void register_ageEquals18_ok() {
        defaultUser.setAge(18);
        assertEquals(defaultUser.getAge(),MIN_AGE);
    }

    @Test
    void register_userLoginIsEmpty_notOk() {
        defaultUser.setLogin("");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(defaultUser);
        });
    }

    @Test
    void register_ageMoreThan130_notOk() {
        defaultUser.setAge(131);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(defaultUser);
        });
    }

    @Test
    void register_loginIsNull_notOk() {
        defaultUser.setLogin(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(defaultUser);
        });
    }

    @Test
    void register_ageLessThan18_notOk() {
        defaultUser.setAge(17);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(defaultUser);
        });
    }

    @Test
    void register_user_ok() {
        registrationService.register(defaultUser);
        defaultUser.setId(1L);
        assertEquals(1,defaultUser.getId());
        assertEquals(DEFAULT_LOGIN,defaultUser.getLogin());
        assertEquals(DEFAULT_PASSWORD,defaultUser.getPassword());
    }
}
