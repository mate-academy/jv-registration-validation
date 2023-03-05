package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_PASSWORD = "password";
    private static final String INVALID_PASSWORD = "pswrd";
    private static final String VALID_LOGIN = "Valid_login@gmail.com";
    private static final String INVALID_LOGIN = "invalid_login";
    private static final Integer VALID_AGE = 20;
    private static final Integer INVALID_AGE = 12;
    private static final Integer NEGATIVE_AGE = -10;
    private static final Integer BIG_AGE = 110;
    private static final Long ID = 1234L;
    private static final Long NEGATIVE_ID = -5L;
    private final RegistrationService registerService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();
    private final User testUser = new User();

    @BeforeEach
    void setUp() {
        testUser.setPassword(VALID_PASSWORD);
        testUser.setLogin(VALID_LOGIN);
        testUser.setId(ID);
        testUser.setAge(VALID_AGE);
    }

    @Test
    void userAreNull_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registerService.register(null);
        });
    }

    @Test
    void userAgeAreLessThat18_NotOk() {
        testUser.setAge(INVALID_AGE);
        assertThrows(RuntimeException.class, () -> {
            registerService.register(testUser);
        }, "User age must be 18 or bigger");
    }

    @Test
    void userAgeAreNegative_NotOk() {
        testUser.setAge(NEGATIVE_AGE);
        assertThrows(RuntimeException.class, () -> {
            registerService.register(testUser);
        }, "User age must be bigger then 0");
    }

    @Test
    void userAgeAreBiggerThen100_NotOk() {
        testUser.setAge(BIG_AGE);
        assertThrows(RuntimeException.class, () -> {
            registerService.register(testUser);
        }, "User age must be less then 100");
    }

    @Test
    void userAgeAreNull_NotOk() {
        testUser.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registerService.register(testUser);
        }, "User age must be not null");
    }

    @Test
    void userPasswordAreNull_NotOk() {
        testUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registerService.register(testUser);
        }, "User password must be not null");
    }

    @Test
    void userPasswordAreInvalid_NotOk() {
        testUser.setPassword(INVALID_PASSWORD);
        assertThrows(RuntimeException.class, () -> {
            registerService.register(testUser);
        }, "User password must be 8 symbol or bigger");
    }

    @Test
    void userLoginAreNull_NotOk() {
        testUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registerService.register(testUser);
        }, "Login can`t be null");
    }

    @Test
    void userLoginInvalid_NotOk() {
        testUser.setLogin(INVALID_LOGIN);
        assertThrows(RuntimeException.class, () -> {
            registerService.register(testUser);
        }, "Login must contain @gmail.com");
    }

    @Test
    void userIdAreNull_NotOk() {
        testUser.setId(null);
        assertThrows(RuntimeException.class, () -> {
            registerService.register(testUser);
        }, "Id can`t be null");
    }

    @Test
    void userIdAreNegative_NotOk() {
        testUser.setId(NEGATIVE_ID);
        assertThrows(RuntimeException.class, () -> {
            registerService.register(testUser);
        }, "Id can`t be less then 0");
    }

    @Test
    void storageAlreadyContainsUser_NotOk() {
        storageDao.add(testUser);
        assertThrows(RuntimeException.class, () -> {
            registerService.register(testUser);
        }, "User already exists in storage");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
