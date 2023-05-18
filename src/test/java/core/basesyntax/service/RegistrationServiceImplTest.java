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
    private static RegistrationServiceImpl service;
    private static User user1;
    private static User user2;
    private static User user3;
    private static final long DEFAULT_ID = 200L;
    private static final String DEFAULT_LOGIN = "sasaylalita";
    private static final String DEFAULT_PASSWORD = "qwerty12345";
    private static final int DEFAULT_AGE = 28;

    private void userInit(User user) {
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
        user1 = new User();
        userInit(user1);
        user2 = new User();
        userInit(user2);
        user3 = new User();
        userInit(user3);
    }

    @Test
    void register_validData_ok() {
        service.register(user1);
        User actual = Storage.people.get(0);
        assertEquals(user1, actual);
    }

    @Test
    void register_hasMinimumLength_ok() {
        user2.setLogin("loginloginloginloginloginloginloginloginloginloginlogin");
        user3.setLogin("login1");
        user2.setPassword("qwertyqwertyqwertyqwertyqwertyqwertyqwertyqwerty");
        user3.setPassword("qwerty");
        assertEquals(user1, service.register(user1));
        assertEquals(user2, service.register(user2));
        assertEquals(user3, service.register(user3));
    }

    @Test
    void register_ageAboveMinimum_ok() {
        user2.setAge(1234567890);
        user2.setLogin("loginn");
        user3.setAge(18);
        user3.setLogin("llogin");
        assertEquals(user1, service.register(user1));
        assertEquals(user2, service.register(user2));
        assertEquals(user3, service.register(user3));
    }

    @Test
    void register_uniqueLogin_ok() {
        assertEquals(user1, service.register(user1));
    }

    @Test
    void register_userNotNull_ok() {
        assertEquals(user1, service.register(user1));
    }

    @Test
    void register_hasMinimumLength_NotOk() {
        user1.setLogin("login");
        user2.setLogin("");
        user3.setLogin(null);
        user1.setPassword("");
        user2.setPassword("12345");
        user3.setPassword(null);
        assertThrows(ValidationException.class, () -> service.register(user1));
        assertThrows(ValidationException.class, () -> service.register(user2));
        assertThrows(ValidationException.class, () -> service.register(user3));
    }

    @Test
    void register_ageAboveMinimum_NotOk() {
        user1.setAge(-23);
        user2.setAge(17);
        user3.setAge(null);
        assertThrows(ValidationException.class, () -> service.register(user1));
        assertThrows(ValidationException.class, () -> service.register(user2));
        assertThrows(ValidationException.class, () -> service.register(user3));
    }

    @Test
    void register_uniqueLogin_NotOk() {
        Storage.people.add(user1);
        user2 = user1;
        user3.setLogin(user1.getLogin());
        assertThrows(ValidationException.class, () -> service.register(user2));
        assertThrows(ValidationException.class, () -> service.register(user3));
    }

    @Test
    void register_userNotNull_NotOk() {
        user1 = null;
        assertThrows(ValidationException.class, () -> service.register(user1));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
