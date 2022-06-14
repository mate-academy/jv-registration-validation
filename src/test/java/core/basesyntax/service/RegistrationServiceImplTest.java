package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "Emma";
    private static final String VALID_PASSWORD = "qa#wd4f_tp";
    private static final int VALID_AGE = 20;
    private static RegistrationServiceImpl service;
    private static StorageDaoImpl storageDao;
    private User user;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        User user1 = new User();
        user1.setLogin("Anna");
        user1.setPassword("qwerty");
        user1.setAge(18);
        storageDao.add(user1);
        User user2 = new User();
        user2.setLogin("Bob");
        user2.setPassword("asdfghj");
        user2.setAge(34);
        storageDao.add(user2);
        User user3 = new User();
        user3.setLogin("Lucy");
        user3.setPassword("zxcvbnm$");
        user3.setAge(20);
        storageDao.add(user3);
    }

    @Test
    void validUser_Ok() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        User actual = service.register(user);
        assertEquals(user, actual);
        assertTrue(Storage.people.contains(user));
        assertEquals(4, Storage.people.size());
    }

    @Test
    void existedUserByLogin_NotOk() {
        user.setLogin("Bob");
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
        assertFalse(Storage.people.contains(user));
        assertEquals(3, Storage.people.size());
    }

    @Test
    void tooYoungUser_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(15);
        assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
        assertFalse(Storage.people.contains(user));
        assertEquals(3, Storage.people.size());
    }

    @Test
    void shortPassword_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword("Q&");
        user.setAge(VALID_AGE);
        assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
        assertFalse(Storage.people.contains(user));
        assertEquals(3, Storage.people.size());
    }

    @Test
    void nullUser_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(null);
        });
        assertFalse(Storage.people.contains(null));
        assertEquals(3, Storage.people.size());
    }

    @Test
    void loginNull_NotOk() {
        user.setLogin(null);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
        assertFalse(Storage.people.contains(user));
        assertEquals(3, Storage.people.size());
    }

    @Test
    void passwordNull_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(null);
        user.setAge(VALID_AGE);
        assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
        assertFalse(Storage.people.contains(user));
        assertEquals(3, Storage.people.size());
    }

    @Test
    void emptyLogin_Ok() {
        user.setLogin("");
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        User actual = service.register(user);
        assertEquals(user, actual);
        assertTrue(Storage.people.contains(user));
        assertEquals(4, Storage.people.size());
    }

    @Test
    void emptyPassword_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword("");
        user.setAge(VALID_AGE);
        assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
        assertFalse(Storage.people.contains(user));
        assertEquals(3, Storage.people.size());
    }

    @Test
    void ageExceededMaxValue_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(Integer.MAX_VALUE + 1);
        assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
        assertFalse(Storage.people.contains(user));
        assertEquals(3, Storage.people.size());
    }

    @Test
    void negativeAge_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(-19);
        assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
        assertFalse(Storage.people.contains(user));
        assertEquals(3, Storage.people.size());
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
