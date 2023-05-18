package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl service;
    private static User user;
    private static final long DEFAULT_ID = 200L;
    private static final String DEFAULT_LOGIN = "sasaylalita";
    private static final String DEFAULT_PASSWORD = "qwerty12345";
    private static final int DEFAULT_AGE = 28;

    private void userInit() {
        user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(DEFAULT_AGE);
    }

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        userInit();
    }

    @Test
    void register_validData_ok() {
        service.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_loginHasMinimumLength_notOk() {
        user.setLogin("login");
        assertThrows(ValidationException.class, () -> service.register(user));
        user.setLogin("");
        assertThrows(ValidationException.class, () -> service.register(user));
        user.setLogin(null);
        assertThrows(ValidationException.class, () -> service.register(user));
    }

    @Test
    void register_passwordHasMinimumLength_notOk() {
        user.setPassword("");
        assertThrows(ValidationException.class, () -> service.register(user));
        user.setPassword("12345");
        assertThrows(ValidationException.class, () -> service.register(user));
        user.setPassword(null);
        assertThrows(ValidationException.class, () -> service.register(user));
    }

    @Test
    void register_ageAboveMinimum_notOk() {
        user.setAge(-23);
        assertThrows(ValidationException.class, () -> service.register(user));
        user.setAge(17);
        assertThrows(ValidationException.class, () -> service.register(user));
        user.setAge(null);
        assertThrows(ValidationException.class, () -> service.register(user));
    }

    @Test
    void register_uniqueLogin_notOk() {
        Storage.people.add(user);
        assertThrows(ValidationException.class, () -> service.register(user));
    }

    @Test
    void register_userNotNull_notOk() {
        user = null;
        assertThrows(ValidationException.class, () -> service.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
