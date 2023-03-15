package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidUserDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final String EXCEPTION_MASSAGE = InvalidUserDataException.class.toString();
    private static final String VALID_LOGIN = "Acxbbmnb";
    private static final String VALID_PASSWORD = "123456";
    private static int VALID_USER_AGE = 18;
    private static Long VALID_ID = Long.valueOf(12334);
    private static User defaultValidUser;
    private static RegistrationService registrationService;
    private static StorageDao storage;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storage = new StorageDaoImpl();
    }

    @BeforeEach
    public void setUp() {
        defaultValidUser = new User(VALID_ID, VALID_LOGIN,
                VALID_PASSWORD,VALID_USER_AGE);
    }

    @Test
    void register_nullLogin_NotOk() {
        User userTest = new User(VALID_ID, null, VALID_PASSWORD, VALID_USER_AGE);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(userTest));
    }

    @Test
    void register_shortPassword_NotOk() {
        User userTest = new User(VALID_ID, VALID_LOGIN, "123", VALID_USER_AGE);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(userTest));
    }

    @Test
    void register_nullAge_NotOk() {
        User userTest = new User(VALID_ID, VALID_LOGIN, VALID_PASSWORD, null);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(userTest));
    }

    @Test
    void register_underAge_NotOk() {
        User userTest = new User(VALID_ID, VALID_LOGIN, VALID_PASSWORD, 17);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(userTest));
    }

    @Test
    void register_nullPassword_NotOk() {
        User userTest = new User(VALID_ID, VALID_LOGIN, null, VALID_USER_AGE);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(userTest));
    }

    @Test
    void register_minusAge_NotOk() {
        User userTest = new User(VALID_ID, VALID_LOGIN, VALID_PASSWORD, -20);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(userTest));
    }

    @Test
    void register_defaultValidUser_ok() throws InvalidUserDataException {
        User actual = registrationService.register(defaultValidUser);
        assertEquals(defaultValidUser, actual);
    }

    @Test
    void register_alreadyExistLoginUser_notOk() throws InvalidUserDataException {
        User actual = registrationService.register(defaultValidUser);
        assertEquals(defaultValidUser, actual);
        User clone = new User(VALID_ID, actual.getLogin(), VALID_PASSWORD, VALID_USER_AGE);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(clone);
        }, "Expected " + InvalidUserDataException.class.getName()
                + "for already exist login!");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}

