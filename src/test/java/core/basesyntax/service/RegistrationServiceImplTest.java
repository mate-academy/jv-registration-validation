package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static final String PASSWORD_CORRECT =
            "1".repeat(RegistrationServiceImpl.MIN_LENGTH_PASSWORD);
    private static final int AGE_CORRECT = RegistrationServiceImpl.MIN_AGE;
    private static final String LOGIN_DEFAULT = "login";
    private static final User USER_CORRECT =
            new User(LOGIN_DEFAULT, PASSWORD_CORRECT, AGE_CORRECT);

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    void register_NotOk_userIsNull() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_NotOk_loginFieldIsNull() {
        assertThrows(RegistrationException.class,
                () -> registrationService
                        .register(new User(null, PASSWORD_CORRECT, AGE_CORRECT)));
    }

    @Test
    void register_NotOk_passwordFieldIsNull() {
        assertThrows(RegistrationException.class,
                () -> registrationService
                        .register(new User(LOGIN_DEFAULT, null, AGE_CORRECT)));
    }

    @Test
    void register_NotOk_ageFieldIsNull() {
        assertThrows(RegistrationException.class,
                () -> registrationService
                        .register(new User(LOGIN_DEFAULT, PASSWORD_CORRECT, null)));
    }

    @Test
    void register_NotOk_ageFieldLessMinAge() {
        int ageIncorrect = RegistrationServiceImpl.MIN_AGE - 1;
        assertThrows(RegistrationException.class,
                () -> registrationService.register(
                        new User(LOGIN_DEFAULT, PASSWORD_CORRECT, ageIncorrect)));
    }

    @Test
    void register_NotOk_passwordFieldLengthLessMinPasswordLength() {
        String passwordIncorrect = "1".repeat(RegistrationServiceImpl.MIN_LENGTH_PASSWORD - 1);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(
                        new User(LOGIN_DEFAULT, passwordIncorrect, AGE_CORRECT)));
    }

    @Test
    void register_NotOk_userInStorage() {
        storageDao.add(USER_CORRECT);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(USER_CORRECT));
    }

    @Test
    void register_Ok_registerNewUser() {
        int sizeStorageBefore = Storage.people.size();
        long id = USER_CORRECT.getId();
        User registeredUser = registrationService.register(USER_CORRECT);
        assertTrue(registeredUser.getId() != id);
        int sizeStorageAfter = Storage.people.size();
        assertEquals(sizeStorageAfter, sizeStorageBefore + 1);
    }

    @AfterEach
    void afterEach() {
        Storage.people.clear();
    }
}
