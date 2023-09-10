package core.basesyntax.service;

import core.basesyntax.Exception.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    RegistrationService registration = new RegistrationServiceImpl();
    StorageDao storage = new StorageDaoImpl();
    private final static String VALID_LOGIN1 = "A23456";
    private final static String VALID_LOGIN2 = "B23456";
    private final static String VALID_PASSWORD = "P23456";
    private final static String VALID_SOME_LARGE_VALUE = "V2345678";
    private final static int VALID_AGE = 19;
    private final static String INVALID_LOGIN1 = "A234";
    private final static String INVALID_LOGIN2 = "A2345";
    private final static String INVALID_PASSWORD1 = "P234";
    private final static String INVALID_PASSWORD2 = "P2345";
    private final static int INVALID_AGE1 = 17;
    private final static int INVALID_AGE2 = -1;

    @Test
    void register_loginNull_notOk() {
        User user = new User(null, VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () -> registration.register(user));
    }

    @Test
    void register_loginLengthMin_notOk() {
        User user = new User("", VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () -> registration.register(user));
        user.setLogin(INVALID_LOGIN1);
        assertThrows(RegistrationException.class, () -> registration.register(user));
        user.setLogin(INVALID_LOGIN2);
        assertThrows(RegistrationException.class, () -> registration.register(user));
    }

    @Test
    void register_loginLengthEqualMin_Ok() {
        User user = new User(VALID_LOGIN1, VALID_SOME_LARGE_VALUE, VALID_AGE);
        try {
            registration.register(user);
        } catch (RegistrationException e) {
            fail("Test failed - user's login with min length must be registered but " + e.getMessage());
        }
    }

    @Test
    void register_loginLengthMoreThanMin_Ok() {
        User user = new User(VALID_SOME_LARGE_VALUE, VALID_PASSWORD, VALID_AGE);
        try {
            registration.register(user);
        } catch (RegistrationException e) {
            fail("Test failed - user's login length = " + VALID_SOME_LARGE_VALUE.length() +
                    " and password length = " + VALID_PASSWORD.length() +
                    " but " + e.getMessage());
        }

    }

    @Test
    void register_passwordNull_notOk() {
        User user = new User(VALID_LOGIN1, null, VALID_AGE);
        assertThrows(RegistrationException.class, () -> registration.register(user));
    }

    @Test
    void register_passwordLengthMin_notOk() {
        User user1 = new User(VALID_LOGIN1, INVALID_PASSWORD1, VALID_AGE);
        assertThrows(RegistrationException.class, () -> registration.register(user1));
        User user2 = new User(VALID_SOME_LARGE_VALUE, INVALID_PASSWORD2, VALID_AGE);
        assertThrows(RegistrationException.class, () -> registration.register(user2));
    }

    @Test
    void register_loginPasswordLengthEqualMin_Ok() {
        User user = new User(VALID_LOGIN2, VALID_PASSWORD, VALID_AGE);
        try {
            registration.register(user);
        } catch (RegistrationException e) {
            fail("Test failed - user's login length = " + VALID_LOGIN2.length() +
                    " and password length = " + VALID_PASSWORD.length() +
                    " but " + e.getMessage());
        }
    }

    @Test
    void register_ageNull_notOk() {
        User user = new User(VALID_LOGIN1, VALID_PASSWORD, 0);
        assertThrows(RegistrationException.class, () -> registration.register(user));
    }

    @Test
    void register_ageNegative_notOk() {
        User user = new User(VALID_LOGIN1, VALID_PASSWORD, INVALID_AGE2);
        assertThrows(RegistrationException.class, () -> registration.register(user));
    }

    @Test
    void register_ageLess_notOk() {
        User user = new User(VALID_LOGIN1, VALID_PASSWORD, INVALID_AGE1);
        assertThrows(RegistrationException.class, () -> registration.register(user));
    }


    @Test
    void register_userInStorage_notOk() {
        User user = new User(VALID_LOGIN2, VALID_PASSWORD, VALID_AGE);
        storage.add(user);
        assertThrows(RegistrationException.class, () -> registration.register(user));
    }
}