package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.NoValidDataException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
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

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_userWithoutData_notOk() {
        assertThrows(NoValidDataException.class, () -> {
            registrationService.register(USER_WITHOUT_DATA);
        });
    }

    @Test
    void register_userWithValidData_Ok() {
        StorageDao storageDao = new StorageDaoImpl();
        registrationService.register(USER_WITH_VALID_DATA);
        User actual = storageDao.get(USER_WITH_VALID_DATA.getLogin());
        User expected = USER_WITH_VALID_DATA;
        assertEquals(expected,actual);
    }

    @Test
    void register_nullLogin_NotOk() {
        assertThrows(NoValidDataException.class, () -> {
            registrationService.register(USER_WITHOUT_LOGIN);
        });
    }

    @Test
    void register_notValidLoginLength_notOk() {
        assertThrows(NoValidDataException.class, () -> {
            registrationService.register(USER_WITH_INVALID_LOGIN_LENGTH);
        });
    }

    @Test
    void register_invalidCharacterInLogin_notOk() {
        assertThrows(NoValidDataException.class, () -> {
            registrationService.register(USER_WITH_INVALID_CHARACTER_IN_LOGIN);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        assertThrows(NoValidDataException.class, () -> {
            registrationService.register(USER_WITHOUT_PASSWORD);
        });
    }

    @Test
    void register_invalidPasswordLength_notOk() {
        assertThrows(NoValidDataException.class, () -> {
            registrationService.register(USER_WITH_INVALID_PASSWORD_LENGTH);
        });
    }

    @Test
    void register_invalidCharacterInPassword_notOk() {
        assertThrows(NoValidDataException.class, () -> {
            registrationService.register(USER_WITH_INVALID_CHARACTER_IN_PASSWORD);
        });
    }

    void register_nullAge_notOk() {
        assertThrows(NoValidDataException.class, () -> {
            registrationService.register(USER_WITHOUT_AGE);
        });
    }

    @Test
    void register_invaligAge_notOk() {
        assertThrows(NoValidDataException.class, () -> {
            registrationService.register(USER_WITH_INVALID_AGE);
        });
    }

    @Test
    void redister_userWithSameLogin_notOk() {
        registrationService.register(USER_WITH_VALID_DATA);
        assertThrows(NoValidDataException.class, () -> {
            registrationService.register(USER_THAT_ALREADY_EXIST);
        });
    }
}
