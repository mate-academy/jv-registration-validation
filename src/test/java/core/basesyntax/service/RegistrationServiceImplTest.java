package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.NoValidDataException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final User USER_WITHOUT_DATA = null;
    private static final User USER_WITH_VALID_DATA = new User("Spider", "1234qw", 18);
    private static final User USER_THAT_ALREADY_EXIST = new User("Spider", "12345q", 20);
    private static final User SECOND_USER_WITH_VALID_DATA = new User("SuperMario", "1235qw", 50);
    private static final User THIRD_USER_WITH_VALID_DATA = new User("IronMan", "1236qw", 45);
    private static final User USER_WITH_INVALID_LOGIN_LENGTH = new User("Mario", "1234qw", 18);
    private static final User USER_WITHOUT_LOGIN = new User(null, "1234qw", 18);
    private static final User USER_WITH_INVALID_CHARACTER_IN_LOGIN
            = new User("Mario34", "1234qw", 18);
    private static final User USER_WITH_INVALID_PASSWORD_LENGTH = new User("Spider", "1234q", 18);
    private static final User USER_WITHOUT_PASSWORD = new User("Spider", null, 18);
    private static final User USER_WITH_INVALID_CHARACTER_IN_PASSWORD
            = new User("Spider", "1234q7/_", 18);
    private static final User USER_WITH_INVALID_AGE = new User("Spider", "1234qw", 17);
    private static final User USER_WITHOUT_AGE = new User("Spider", "1234qw", null);
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void expectingExceptionWhenUserIsNull() {
        assertThrows(NoValidDataException.class, () -> {
            registrationService.register(USER_WITHOUT_DATA);
        });
    }

    @Test
    void checkIfUserWasAdded() {
        StorageDao storageDao = new StorageDaoImpl();
        registrationService.register(USER_WITH_VALID_DATA);
        User actual = storageDao.get(USER_WITH_VALID_DATA.getLogin());
        User expected = USER_WITH_VALID_DATA;
        assertEquals(expected,actual);
        storageDao.remote(USER_WITH_VALID_DATA);
    }

    @Test
    void checkIfUserWasRemoted() {
        StorageDao storageDao = new StorageDaoImpl();
        storageDao.add(USER_WITH_VALID_DATA);
        storageDao.remote(USER_WITH_VALID_DATA);
        User actual = storageDao.get(USER_WITH_VALID_DATA.getLogin());
        assertNull(actual);
    }

    @Test
    void checkUsersId() {
        StorageDao storageDao = new StorageDaoImpl();
        storageDao.add(USER_WITH_VALID_DATA);
        storageDao.add(SECOND_USER_WITH_VALID_DATA);
        storageDao.add(THIRD_USER_WITH_VALID_DATA);
        Long actual = THIRD_USER_WITH_VALID_DATA.getId();
        Long expected = 3L;
        assertTrue(expected == actual);
        storageDao.remote(USER_WITH_VALID_DATA);
        storageDao.remote(SECOND_USER_WITH_VALID_DATA);
        storageDao.remote(THIRD_USER_WITH_VALID_DATA);
    }

    @Test
    void expectingExceptionWhenUserDoesNotSetLogin() {
        assertThrows(NoValidDataException.class, () -> {
            registrationService.register(USER_WITHOUT_LOGIN);
        });
    }

    @Test
    void expectingExceptionWhenLoginLength_Not_Ok() {
        assertThrows(NoValidDataException.class, () -> {
            registrationService.register(USER_WITH_INVALID_LOGIN_LENGTH);
        });
    }

    @Test
    void expectingExceptionWhenLoginHasInvalidCharacter() {
        assertThrows(NoValidDataException.class, () -> {
            registrationService.register(USER_WITH_INVALID_CHARACTER_IN_LOGIN);
        });
    }

    @Test
    void expectingExceptionWhenUserDoesNotSetPassword() {
        assertThrows(NoValidDataException.class, () -> {
            registrationService.register(USER_WITHOUT_PASSWORD);
        });
    }

    @Test
    void expectingExceptionWhenPasswordLenghth_Not_Ok() {
        assertThrows(NoValidDataException.class, () -> {
            registrationService.register(USER_WITH_INVALID_PASSWORD_LENGTH);
        });
    }

    @Test
    void expectingExceptionWhenPasswordHasInvalidCharacter() {
        assertThrows(NoValidDataException.class, () -> {
            registrationService.register(USER_WITH_INVALID_CHARACTER_IN_PASSWORD);
        });
    }

    void expectingExceptionWhenUserDoesNotSetAge() {
        assertThrows(NoValidDataException.class, () -> {
            registrationService.register(USER_WITHOUT_AGE);
        });
    }

    @Test
    void expectingExceptionWhenUsersAge_Not_Ok() {
        assertThrows(NoValidDataException.class, () -> {
            registrationService.register(USER_WITH_INVALID_AGE);
        });
    }

    @Test
    void expectingExceptionIfUserAlreadyExist() {
        registrationService.register(USER_WITH_VALID_DATA);
        assertThrows(NoValidDataException.class, () -> {
            registrationService.register(USER_THAT_ALREADY_EXIST);
        });
    }
}
