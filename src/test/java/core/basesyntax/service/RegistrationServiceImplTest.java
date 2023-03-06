package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final RegistrationService REGISTRATION_SERVICE = new RegistrationServiceImpl();
    private static final StorageDao STORAGE_DAO = new StorageDaoImpl();
    private static final String DEFAULT_NEW_USER_LOGIN = "newUser";
    private static final String DEFAULT_PASSWORD = "123456";
    private static final String INVALID_PASSWORD = "12345";
    private static final User DEFAULT_USER = new User("defaultUser", DEFAULT_PASSWORD,
            User.MIN_AGE);

    @BeforeEach
    void beforeEach() {
        Storage.people.clear();
        STORAGE_DAO.add(DEFAULT_USER);
        DEFAULT_USER.setId(0L);
    }

    @Test
    void validUserRegistration_Ok() {
        User user = new User(DEFAULT_NEW_USER_LOGIN, DEFAULT_PASSWORD, User.MIN_AGE);
        assertEquals(REGISTRATION_SERVICE.register(user), STORAGE_DAO.get(user.getLogin()));
    }

    @Test
    void userIsNull_NotOk() {
        assertThrows(RegistrationException.class, () -> REGISTRATION_SERVICE.register(null));
    }

    @Test
    void userLoginIsNull_NotOk() {
        User user = new User(null, DEFAULT_PASSWORD, User.MIN_AGE);
        assertThrows(RegistrationException.class, () -> REGISTRATION_SERVICE.register(user));
    }

    @Test
    void userPasswordIsNull_NotOk() {
        User user = new User(DEFAULT_NEW_USER_LOGIN, null, User.MIN_AGE);
        assertThrows(RegistrationException.class, () -> REGISTRATION_SERVICE.register(user));
    }

    @Test
    void userAgeIsNull() {
        User user = new User(DEFAULT_NEW_USER_LOGIN, DEFAULT_PASSWORD, null);
        assertThrows(RegistrationException.class, () -> REGISTRATION_SERVICE.register(user));
    }

    @Test
    void userLoginAlreadyExists_NotOk() {
        User user = new User(DEFAULT_USER.getLogin(), DEFAULT_PASSWORD, User.MIN_AGE);
        assertThrows(RegistrationException.class, () -> REGISTRATION_SERVICE.register(user));
    }

    @Test
    void userPasswordLengthLessThanRequirement_NotOk() {
        User user = new User(DEFAULT_NEW_USER_LOGIN, INVALID_PASSWORD, User.MIN_AGE);
        assertThrows(RegistrationException.class, () -> REGISTRATION_SERVICE.register(user));
    }

    @Test
    void userAgeLessThanRequirement_NotOk() {
        User user = new User(DEFAULT_NEW_USER_LOGIN, DEFAULT_PASSWORD, User.MIN_AGE - 1);
        assertThrows(RegistrationException.class, () -> REGISTRATION_SERVICE.register(user));
    }

}
