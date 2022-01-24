package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private static final String LOGIN_1 = "Roman";
    private static final String PASSWORD_1 = "qwerty";
    private static final String PASSWORD_2 = "asdfgh";
    private static final String SMALL_PASSWORD = "12345";
    private static final Integer AGE_1 = 25;
    private static final Integer AGE_2 = 25;
    private static final Integer SMALL_AGE = 17;
    private static final Long ID_1 = 1L;
    private static final Long ID_2 = 2L;
    private static final int EXPECTED_SIZE = 1;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void registerUser_correctData_ok() {
        User user = new User(ID_1, LOGIN_1, PASSWORD_1, AGE_1);
        registrationService.register(user);
        assertEquals(EXPECTED_SIZE, Storage.people.size());
        assertEquals(user, Storage.people.get(0));
    }

    @Test
    void registerUser_loginIsNull_notOk() {
        User user = new User(ID_1, null, PASSWORD_1, AGE_1);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
        assertTrue(Storage.people.isEmpty());
    }

    @Test
    void registerUser_loginIsEmpty_notOk() {
        User user = new User(ID_1, "", PASSWORD_1, AGE_1);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
        assertTrue(Storage.people.isEmpty());
    }

    @Test
    void registerUser_passwordIsNull_notOk() {
        User user = new User(ID_1, LOGIN_1, null, AGE_1);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
        assertTrue(Storage.people.isEmpty());
    }

    @Test
    void registerUser_smallPassword_notOk() {
        User user = new User(ID_1, LOGIN_1, SMALL_PASSWORD, AGE_1);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
        assertTrue(Storage.people.isEmpty());
    }

    @Test
    void registerUser_ageIsNull_notOk() {
        User user = new User(ID_1, LOGIN_1, PASSWORD_1, null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
        assertTrue(Storage.people.isEmpty());
    }

    @Test
    void registerUser_ageIsLessThan18_notOk() {
        User user = new User(ID_1, LOGIN_1, PASSWORD_1, SMALL_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
        assertTrue(Storage.people.isEmpty());
    }

    @Test
    void registerUser_registerSameLogin_notOk() {
        registrationService.register(new User(ID_1, LOGIN_1, PASSWORD_1, AGE_1));
        User user2 = new User(ID_2, LOGIN_1, PASSWORD_2, AGE_2);
        assertThrows(RuntimeException.class, () -> registrationService.register(user2));
        assertEquals(EXPECTED_SIZE, Storage.people.size());
    }
}
