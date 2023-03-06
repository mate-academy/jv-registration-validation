package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    private static final int DEFAULT_AGE = 24;
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
        defaultUser.setAge(DEFAULT_AGE);
        defaultUser.setLogin(DEFAULT_LOGIN);
        defaultUser.setPassword(DEFAULT_PASSWORD);
    }

    @AfterEach
    void after() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(null);
        }, "InvalidDataException expected for null user");
    }

    @Test
    void register_alreadyExistedUser_notOk() {
        User existingUser = new User();
        existingUser.setLogin(NON_UNIQUE_LOGIN);
        Storage.people.add(existingUser);
        defaultUser.setLogin(NON_UNIQUE_LOGIN);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(defaultUser);
        }, "InvalidDataException expected for user duplicate");
    }

    @Test
    void register_passwordLength_notOk() {
        defaultUser.setPassword("");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(defaultUser);
        }, "InvalidDataException expected for password with length less than 6");
    }

    @Test
    void register_passwordIsNull_notOk() {
        defaultUser.setPassword(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(defaultUser);
        }, "InvalidDataException expected for null password");
    }

    @Test
    void register_userLoginIsEmpty_notOk() {
        defaultUser.setLogin("");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(defaultUser);
        }, "InvalidDataException expected for user with empty login");
    }

    @Test
    void register_ageNull_notOk() {
        defaultUser.setAge(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(defaultUser);
        },"InvalidDataException expected for user with null age");
    }

    @Test
    void register_ageMoreThan130_notOk() {
        defaultUser.setAge(131);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(defaultUser);
        }, "InvalidDataException expected for user with the age more than 130");
    }

    @Test
    void register_loginIsNull_notOk() {
        defaultUser.setLogin(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(defaultUser);
        },"InvalidDataException expected for user with null login");
    }

    @Test
    void register_ageLessThan18_notOk() {
        defaultUser.setAge(17);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(defaultUser);
        },"InvalidDataException expected for user whose age is under 18");
    }

    @Test
    void register_user_ok() {
        registrationService.register(defaultUser);
        assertNotNull(defaultUser.getId());
        assertNotNull(defaultUser.getLogin());
        assertNotNull(defaultUser.getPassword());
    }

    @Test
    void register_storageSizeIncreased_ok() {
        int startSize = Storage.people.size();
        registrationService.register(defaultUser);
        assertEquals(startSize + 1, Storage.people.size());
    }
}
