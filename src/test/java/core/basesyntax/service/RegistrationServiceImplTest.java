package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private static User user1;
    private static User user2;
    private static User user3;
    private static final long DEFAULT_ID = 200L;
    private static final String DEFAULT_LOGIN = "sasaylalita";
    private static final String DEFAULT_PASSWORD = "qwerty12345";
    private static final int DEFAULT_AGE = 28;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setId(DEFAULT_ID);
        user1.setLogin(DEFAULT_LOGIN);
        user1.setPassword(DEFAULT_PASSWORD);
        user1.setAge(DEFAULT_AGE);
        user2 = new User();
        user2.setId(DEFAULT_ID);
        user2.setLogin(DEFAULT_LOGIN);
        user2.setPassword(DEFAULT_PASSWORD);
        user2.setAge(DEFAULT_AGE);
        user3 = new User();
        user3.setId(DEFAULT_ID);
        user3.setLogin(DEFAULT_LOGIN);
        user3.setPassword(DEFAULT_PASSWORD);
        user3.setAge(DEFAULT_AGE);
    }

    @Test
    void registration_validData_ok() {
        User expected = user1;
        service.register(expected);
        User actual = Storage.people.get(0);
        assertEquals(expected, actual);
    }

    @Test
    void isValid_hasSixChar_ok() {
        user2.setLogin("loginloginloginloginloginloginloginloginloginloginlogin");
        user3.setLogin("login1");
        user2.setPassword("qwertyqwertyqwertyqwertyqwertyqwertyqwertyqwerty");
        user3.setPassword("qwerty");
        assertTrue(service.isValid(user1));
        assertTrue(service.isValid(user2));
        assertTrue(service.isValid(user3));
    }

    @Test
    void isValid_minAge_ok() {
        user2.setAge(1234567890);
        user3.setAge(18);
        assertTrue(service.isValid(user1));
        assertTrue(service.isValid(user2));
        assertTrue(service.isValid(user3));
    }

    @Test
    void isValid_sameLogin_ok() {
        assertTrue(service.isValid(user1));
    }

    @Test
    void isValid_userNull_ok() {
        assertTrue(service.isValid(user1));
    }

    @Test
    void isValid_idNull_ok() {
        assertTrue(service.isValid(user1));
    }

    @Test
    void isValid_hasSixChar_NotOk() {
        user1.setLogin("login");
        user2.setLogin("");
        user3.setLogin(null);
        user1.setPassword("");
        user2.setPassword("12345");
        user3.setPassword(null);
        assertThrows(ValidationException.class, () -> service.isValid(user1));
        assertThrows(ValidationException.class, () -> service.isValid(user2));
        assertThrows(ValidationException.class, () -> service.isValid(user3));
    }

    @Test
    void isValid_minAge_NotOk() {
        user1.setAge(-23);
        user2.setAge(17);
        user3.setAge(null);
        assertThrows(ValidationException.class, () -> service.isValid(user1));
        assertThrows(ValidationException.class, () -> service.isValid(user2));
        assertThrows(ValidationException.class, () -> service.isValid(user3));
    }

    @Test
    void isValid_sameLogin_NotOk() {
        Storage.people.add(user1);
        user2 = user1;
        user3.setLogin(user1.getLogin());
        assertThrows(ValidationException.class, () -> service.isValid(user2));
        assertThrows(ValidationException.class, () -> service.isValid(user3));
    }

    @Test
    void isValid_userNull_NotOk() {
        user1 = null;
        assertThrows(ValidationException.class, () -> service.isValid(user1));
    }

    @Test
    void isValid_idNull_NotOk() {
        user1.setId(null);
        assertThrows(ValidationException.class, () -> service.isValid(user1));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
