package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidInputDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String LOGIN_EXISTED_IN_STORAGE = "unknownCactus";
    private static final String DEFAULT_LOGIN = "user1";
    private static final String DEFAULT_PASSWORD = "user1";
    private static final int DEFAULT_AGE = 22;
    private static final String WRONG_PASSWORD = "12345";
    private static final int WRONG_AGE = 15;
    private static final String EXCEPTION = InvalidInputDataException.class.toString();

    private static RegistrationServiceImpl registrationService;
    private static StorageDaoImpl storageDao;
    private static User actual;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        User expectedUser = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
        storageDao.add(expectedUser);
    }

    @Test
    void register_loginNull_NotOk() {
        actual = new User(null, DEFAULT_PASSWORD, DEFAULT_AGE);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_wrongPasswordsLength_NotOk() {
        actual = new User(DEFAULT_LOGIN, WRONG_PASSWORD, DEFAULT_AGE);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_wrongAge_NotOk() {
        actual = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, WRONG_AGE);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_loginExists_NotOk() {
        actual = new User(LOGIN_EXISTED_IN_STORAGE, DEFAULT_PASSWORD, DEFAULT_AGE);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_loginAndPasswordWrongValues_NotOk() {
        actual = new User(null, WRONG_PASSWORD, DEFAULT_AGE);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_loginAndAgeWrongValues_NotOk() {
        actual = new User(null, DEFAULT_PASSWORD, WRONG_AGE);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_loginExistsAndPasswordShort_NotOk() {
        actual = new User(LOGIN_EXISTED_IN_STORAGE, WRONG_PASSWORD, DEFAULT_AGE);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_loginExistsAndAgeWrongValue_NotOk() {
        actual = new User(LOGIN_EXISTED_IN_STORAGE, DEFAULT_PASSWORD, WRONG_AGE);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_passwordAndAgeWrongValue_NotOk() {
        actual = new User(DEFAULT_LOGIN, WRONG_PASSWORD, WRONG_AGE);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_loginNullAndOtherWrongValues_NotOk() {
        actual = new User(null, WRONG_PASSWORD, WRONG_AGE);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_loginExistsAndOtherWrongValues_NotOk() {
        actual = new User(LOGIN_EXISTED_IN_STORAGE, WRONG_PASSWORD, WRONG_AGE);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(actual), EXCEPTION);
    }

    @Test
    void register_validLogin_Ok() {
        actual = new User("DEFAULT_LOGIN", "DEFAULT_PASSWORD", DEFAULT_AGE);
        Assertions.assertEquals(actual, registrationService.register(actual),
                "User added");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
