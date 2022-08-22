package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    public static final String DEFAULT_LOGIN = "Alex";
    public static final String DEFAULT_PASSWORD = "123456";
    public static final int DEFAULT_AGE = 18;
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_validData_Ok() {
        User user = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
        registrationService.register(user);
        assertEquals(Storage.people.get(0), user);
        assertNotNull(Storage.people.get(0).getId());
    }

    @Test
    void register_nullLogin_NotOk() {
        User user = new User(null, DEFAULT_PASSWORD, DEFAULT_AGE);
        runtimeExceptionDetection(user);
    }

    @Test
    void register_nullPassword_NotOk() {
        User user = new User(DEFAULT_LOGIN, null, DEFAULT_AGE);
        runtimeExceptionDetection(user);
    }

    @Test
    void register_nullAge_NotOk() {
        User user = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, null);
        runtimeExceptionDetection(user);
    }

    @Test
    void register_ageLess18_NotOk() {
        User actual = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, 17);
        runtimeExceptionDetection(actual);
    }

    @Test
    void register_negativeAge_NotOk() {
        User user = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, -33);
        runtimeExceptionDetection(user);
    }

    @Test
    void register_emptyLogin_NotOk() {
        User user = new User("", DEFAULT_PASSWORD, DEFAULT_AGE);
        runtimeExceptionDetection(user);
    }

    @Test
    void register_lessThanMinLengthPassword_NotOk() {
        User user = new User(DEFAULT_LOGIN, "55555", DEFAULT_AGE);
        runtimeExceptionDetection(user);
    }

    @Test
    void register_addNullUser_NotOk() {
        User user = null;
        runtimeExceptionDetection(null);
    }

    @Test
    void register_usersWithSameLogins_NotOk() {
        User firstUser = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
        User secondUser = new User(DEFAULT_LOGIN, "666666", 66);
        registrationService.register(firstUser);
        runtimeExceptionDetection(secondUser);
    }

    private void runtimeExceptionDetection(User user) {
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }
}
