package core.basesyntax.service;

import static core.basesyntax.db.Storage.people;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidUserException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private static final String VALID_LOGIN = "slaybrute";
    private static final String INVALID_LOGIN = "login";
    private static final String VALID_PASSWORD = "password12345";
    private static final String INVALID_PASSWORD = "12345";
    private static final int VALID_AGE = 20;
    private static final int INVALID_AGE = 10;
    private static final int SIZE_OF_EMPTY_ARRAY = 0;
    private static final int SIZE_OF_ARRAY_WITH_ONE_ELEMENT = 1;
    private static StorageDao storageDao;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        people.clear();
        if (user == null) {
            user = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        } else {
            user.setLogin(VALID_LOGIN);
            user.setPassword(VALID_PASSWORD);
            user.setAge(VALID_AGE);
        }
    }

    @Test
    void validUser_Ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
        assertEquals(user, storageDao.get(user.getLogin()));
        assertEquals(SIZE_OF_ARRAY_WITH_ONE_ELEMENT, people.size());
    }

    @Test
    void equalValidUser_notOk() {
        registrationService.register(user);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
        assertEquals(SIZE_OF_ARRAY_WITH_ONE_ELEMENT, people.size());
    }

    @Test
    void invalidLogin_notOK() {
        user.setLogin(INVALID_LOGIN);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
        assertEquals(SIZE_OF_EMPTY_ARRAY, people.size());
    }

    @Test
    void invalidPassword_notOK() {
        user.setPassword(INVALID_PASSWORD);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
        assertEquals(SIZE_OF_EMPTY_ARRAY, people.size());
    }

    @Test
    void invalidAge_notOK() {
        user.setAge(INVALID_AGE);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
        assertEquals(SIZE_OF_EMPTY_ARRAY, people.size());
    }

    @Test
    void nullUser_notOK() {
        user = null;
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
        assertEquals(SIZE_OF_EMPTY_ARRAY, people.size());
    }

    @Test
    void nullLogin_notOK() {
        user.setLogin(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
        assertEquals(SIZE_OF_EMPTY_ARRAY, people.size());
    }

    @Test
    void nullPassword_notOK() {
        user.setPassword(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
        assertEquals(SIZE_OF_EMPTY_ARRAY, people.size());
    }

    @Test
    void nullAge_notOK() {
        user.setAge(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
        assertEquals(SIZE_OF_EMPTY_ARRAY, people.size());
    }
}
