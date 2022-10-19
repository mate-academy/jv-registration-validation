package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User expectedUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        User defaultUser = new User();
        defaultUser.setLogin("defaultLogin");
        defaultUser.setPassword("defaultPassword");
        defaultUser.setAge(20);
        defaultUser.setId(0L);
        Storage.people.add(defaultUser);
        expectedUser = new User();
        expectedUser.setLogin("someLogin");
        expectedUser.setPassword("somePassword");
        expectedUser.setAge(30);
    }

    @Test
    void register_notContainsUserLoginInStorage_ok() {
        User actual = registrationService.register(expectedUser);
        assertEquals(expectedUser, actual);
    }

    @Test
    void register_containsUserLoginInStorage_notOk() {
        expectedUser.setLogin("defaultLogin");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(expectedUser);
        });
    }

    @Test
    void register_userAge_ok() {
        User actual = registrationService.register(expectedUser);
        assertEquals(expectedUser, actual);
    }

    @Test
    void register_userAge_notOk() {
        expectedUser.setAge(10);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(expectedUser);
        });
    }

    @Test
    void register_userPasswordLength_ok() {
        User actual = registrationService.register(expectedUser);
        assertEquals(expectedUser, actual);
    }

    @Test
    void register_userPasswordLength_notOk() {
        expectedUser.setPassword("NotOk");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(expectedUser);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        expectedUser.setLogin(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(expectedUser);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        expectedUser.setPassword(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(expectedUser);
        });
    }

    @Test
    void resister_nullAge_notOk() {
        expectedUser.setAge(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(expectedUser);
        });
    }

    @Test
    void register_nullUserArgument_notOk() {
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(null);
        });
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }
}
