package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    private static final Integer BIG_AGE = 150;
    private static final Long ID = 1234L;
    private static final Long NEGATIVE_ID = -5L;
    private final RegistrationService registerService = new RegistrationServiceImpl();
    private User testUser = new User();

    @BeforeEach
    void setUp() {
        testUser.setPassword(VALID_PASSWORD);
        testUser.setLogin(VALID_LOGIN);
        testUser.setId(ID);
        testUser.setAge(VALID_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void userDataCorrect_ok() {
        registerService.register(testUser);
        boolean actual = Storage.people.contains(testUser);
        assertTrue(actual);
    }

    @Test
    void userAreNull_notOk() {
        assertThrows(IncorrectDataExeption.class, () -> {
            registerService.register(null);
        }, "User can not be null");
    }

    @Test
    void userAgeAreLessThat18_notOk() {
        testUser.setAge(INVALID_AGE);
        assertThrows(IncorrectDataExeption.class, () -> {
            registerService.register(testUser);
        }, "User age must be 18 or bigger");
    }

    @Test
    void userAgeAreNegative_notOk() {
        testUser.setAge(NEGATIVE_AGE);
        assertThrows(IncorrectDataExeption.class, () -> {
            registerService.register(testUser);
        }, "User age must be bigger then 0");
    }

    @Test
    void userAgeAreBiggerThen100_notOk() {
        testUser.setAge(BIG_AGE);
        assertThrows(IncorrectDataExeption.class, () -> {
            registerService.register(testUser);
        }, "User age must be less then 100");
    }

    @Test
    void userAgeAreNull_notOk() {
        testUser.setAge(null);
        assertThrows(IncorrectDataExeption.class, () -> {
            registerService.register(testUser);
        }, "User age must be not null");
    }

    @Test
    void userPasswordAreNull_notOk() {
        testUser.setPassword(null);
        assertThrows(IncorrectDataExeption.class, () -> {
            registerService.register(testUser);
        }, "User password must be not null");
    }

    @Test
    void userPasswordAreInvalid_notOk() {
        testUser.setPassword(INVALID_PASSWORD);
        assertThrows(IncorrectDataExeption.class, () -> {
            registerService.register(testUser);
        }, "User password must be 6 symbol or bigger");
    }

    @Test
    void userLoginAreNull_notOk() {
        testUser.setLogin(null);
        assertThrows(IncorrectDataExeption.class, () -> {
            registerService.register(testUser);
        }, "Login can`t be null");
    }

    @Test
    void userLoginInvalid_notOk() {
        testUser.setLogin(INVALID_LOGIN);
        assertThrows(IncorrectDataExeption.class, () -> {
            registerService.register(testUser);
        }, "Login must contain @gmail.com");
    }

    @Test
    void userIdAreNull_notOk() {
        testUser.setId(null);
        assertThrows(IncorrectDataExeption.class, () -> {
            registerService.register(testUser);
        }, "Id can`t be null");
    }

    @Test
    void userIdAreNegative_notOk() {
        testUser.setId(NEGATIVE_ID);
        assertThrows(IncorrectDataExeption.class, () -> {
            registerService.register(testUser);
        }, "Id can`t be less then 0");
    }

    @Test
    void storageAlreadyContainsUser_notOk() {
        registerService.register(testUser);
        assertThrows(IncorrectDataExeption.class, () -> {
            registerService.register(testUser);
        }, "User already exists in storage");
    }
}
