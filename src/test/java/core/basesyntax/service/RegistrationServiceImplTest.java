package core.basesyntax.service;

import core.basesyntax.UserSupplier;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    // age must be bigger than 18
    // login can't be null
    // password can't be null
    // login length must be bigger than 8
    // passwords length must be bigger than 8
    // password must be equal to repeat password
    private static final User[] VALID_USERS = {
            new UserSupplier().of("testLogin", "qqqwertd123", 18),
            new UserSupplier().of("tutifruti", "zxcqwer345", 30)
    };
    private static final String[] VALID_LOGINS = {
            "validlogin", "alsovalidlogin", "lavinart",
            "blockblock", "qqwertfff", "fttpfres"
    };
    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int THIRD = 2;
    private static final int FOURTH = 3;
    private static final int FIFTH = 4;
    private static final int SIXTH = 5;
    private static final int LENGTH = VALID_USERS.length;
    private static final String VALID_PASSWORD = "validpassword";
    private static final int VALID_AGE = 18;
    private static final String INVALID_PASSWORD_LOGIN = "qwerv";
    private static final String UPPER_CASE_LOGIN = "UPPERCASE";
    private static final int INVALID_AGE = 17;

    private StorageDao storageDao;
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
        for (int i = 0; i < LENGTH; i++) {
            storageDao.add(VALID_USERS[i]);
        }
    }

    @Test
    void addNewUser_ok() {
        User newUser = new UserSupplier().of(VALID_LOGINS[FIRST], VALID_PASSWORD, VALID_AGE);
        int expectedStorageSize = LENGTH + 1;
        registrationService.register(newUser);
        int actualStorageSize = Storage.people.size();
        assertEquals(expectedStorageSize, actualStorageSize);
        assertEquals(Storage.people.get(LENGTH), newUser);
    }

    @Test
    void addUserWithExistLogin_notOk() {
        String existLogin = VALID_USERS[FIRST].getLogin();
        User newUser = new UserSupplier().of(existLogin, VALID_PASSWORD, VALID_AGE);
        failTest(registrationService, newUser);
    }

    @Test
    void addUserWithLoginNull_notOk() {
        User newUser = new UserSupplier().of(null, VALID_PASSWORD, VALID_AGE);
        failTest(registrationService, newUser);
    }

    @Test
    void addUserWithPasswordNull_notOk() {
        User newUser = new UserSupplier().of(VALID_LOGINS[SECOND], null, VALID_AGE);
        failTest(registrationService, newUser);
    }

    @Test
    void addUserWithAgeNull_notOk() {
        User newUser = new UserSupplier().of(VALID_LOGINS[THIRD], VALID_PASSWORD, null);
        failTest(registrationService, newUser);
    }

    @Test
    void addUserInvalidLoginLength_notOk() {
        User newUser = new UserSupplier().of(INVALID_PASSWORD_LOGIN, VALID_PASSWORD, VALID_AGE);
        failTest(registrationService, newUser);
    }

    @Test
    void addUserInvalidPasswordLength_notOk() {
        User newUser = new UserSupplier().of(VALID_LOGINS[FOURTH], INVALID_PASSWORD_LOGIN, VALID_AGE);
        failTest(registrationService, newUser);
    }

    @Test
    void addUserInvalidAge_notOk() {
        User newUser = new UserSupplier().of(VALID_LOGINS[FIFTH], VALID_PASSWORD, INVALID_AGE);
        failTest(registrationService, newUser);
    }

    @Test
    void addUserUpperCaseLogin_notOk() {
        User newUser = new UserSupplier().of(UPPER_CASE_LOGIN, VALID_PASSWORD, INVALID_AGE);
        failTest(registrationService, newUser);
    }

    private void failTest(RegistrationService registrationService, User user) {
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}