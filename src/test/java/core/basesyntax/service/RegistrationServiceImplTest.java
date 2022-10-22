package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService service;
    private User testUser;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        final StorageDao storageDao = new StorageDaoImpl();

        User validUser1 = new User();
        validUser1.setLogin("oleg");
        validUser1.setPassword("123456");
        validUser1.setAge(28);

        User validUser2 = new User();
        validUser2.setLogin("stepan");
        validUser2.setPassword("6543210");
        validUser2.setAge(18);

        storageDao.add(validUser1);
        storageDao.add(validUser2);

        testUser = new User();
        testUser.setLogin("oleksiy");
        testUser.setPassword("123456123");
        testUser.setAge(40);
    }

    @AfterEach
    void afterEach() {
        Storage.people.clear();
    }

    @Test
    void register_validUser_Ok() {
        User actual = service.register(testUser);
        assertEquals(testUser, actual);
    }

    @Test
    void register_userLoginNull_NotOk() {
        testUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            service.register(testUser);
        });
    }

    @Test
    void register_userLoginAlreadyExists_NotOk() {
        testUser.setLogin("oleg");
        assertThrows(RuntimeException.class, () -> {
            service.register(testUser);
        });
    }

    @Test
    void register_userPassIsNull_NotOk() {
        testUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            service.register(testUser);
        });
    }

    @Test
    void register_userPassShorterThen_6_NotOk() {
        testUser.setPassword("123");
        assertThrows(RuntimeException.class, () -> {
            service.register(testUser);
        });
    }

    @Test
    void register_userPassEquals_6_Ok() {
        testUser.setPassword("123456");
        User actual = service.register(testUser);
        assertEquals(testUser, actual);
    }

    @Test
    void register_userPassMoreThen_6_Ok() {
        testUser.setPassword("1234567");
        User actual = service.register(testUser);
        assertEquals(testUser, actual);
    }

    @Test
    void register_userAgeIsNull_NotOk() {
        testUser.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            service.register(testUser);
        });
    }

    @Test
    void register_userAgeLessThen_18_NotOk() {
        testUser.setAge(16);
        assertThrows(RuntimeException.class, () -> {
            service.register(testUser);
        });
    }

    @Test
    void register_userAgeEquals_18_Ok() {
        testUser.setAge(18);
        User actual = service.register(testUser);
        assertEquals(testUser, actual);
    }

    @Test
    void register_userAgeMoreThen_18_Ok() {
        testUser.setAge(31);
        User actual = service.register(testUser);
        assertEquals(testUser, actual);
    }
}
