package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int VALID_AGE = 18;
    private static final int INVALID_AGE = VALID_AGE - 1;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final String USED_LOGIN = "UsedLogin";
    private static final String NEW_LOGIN = "NewLogin";
    private static final String VALID_PASSWORD = "#".repeat(MIN_PASSWORD_LENGTH);
    private static final String INVALID_PASSWORD = "#".repeat(MIN_PASSWORD_LENGTH - 1);
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setAge(VALID_AGE);
        user.setLogin(USED_LOGIN);
        user.setPassword(VALID_PASSWORD);
        storageDao.add(user);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_userIsNull_notOk() {
        User newUser = null;
        Assertions.assertThrows(InvalidDataException.class,
                () -> {
                    registrationService.register(newUser);
                });
    }

    @Test
    void register_loginIsNull_notOk() {
        User newUser = new User();
        newUser.setAge(VALID_AGE);
        newUser.setPassword(VALID_PASSWORD);
        Assertions.assertThrows(InvalidDataException.class,
                () -> {
                    registrationService.register(newUser);
                });
    }

    @Test
    void register_loginIsTaken_notOk() {
        User newUser = new User();
        newUser.setLogin(USED_LOGIN);
        newUser.setAge(VALID_AGE);
        newUser.setPassword(VALID_PASSWORD);
        Assertions.assertThrows(InvalidDataException.class,
                () -> {
                    registrationService.register(newUser);
                });
    }

    @Test
    void register_ageIsNull_notOk() {
        User newUser = new User();
        newUser.setLogin(NEW_LOGIN);
        newUser.setPassword(VALID_PASSWORD);
        Assertions.assertThrows(InvalidDataException.class,
                () -> {
                    registrationService.register(newUser);
                });
    }

    @Test
    void register_ageIsInvalid_notOk() {
        User newUser = new User();
        newUser.setLogin(NEW_LOGIN);
        newUser.setAge(INVALID_AGE);
        newUser.setPassword(VALID_PASSWORD);
        Assertions.assertThrows(InvalidDataException.class,
                () -> {
                    registrationService.register(newUser);
                });
    }

    @Test
    void register_passwordIsNull_notOk() {
        User newUser = new User();
        newUser.setLogin(NEW_LOGIN);
        newUser.setAge(VALID_AGE);
        Assertions.assertThrows(InvalidDataException.class,
                () -> {
                    registrationService.register(newUser);
                });
    }

    @Test
    void register_passwordIsTooShort_notOk() {
        User newUser = new User();
        newUser.setLogin(NEW_LOGIN);
        newUser.setAge(VALID_AGE);
        newUser.setPassword(INVALID_PASSWORD);
        Assertions.assertThrows(InvalidDataException.class,
                () -> {
                    registrationService.register(newUser);
                });
    }

    @Test
    void register_validUserNotNullReturn_ok() {
        User newUser = new User();
        newUser.setLogin(NEW_LOGIN);
        newUser.setAge(VALID_AGE);
        newUser.setPassword(VALID_PASSWORD);
        Assertions.assertNotNull(registrationService.register(newUser));
    }

    @Test
    void register_returnSameUser_ok() {
        User newUser = new User();
        newUser.setLogin(NEW_LOGIN);
        newUser.setAge(VALID_AGE);
        newUser.setPassword(VALID_PASSWORD);
        User registeredUser = registrationService.register(newUser);
        Assertions.assertEquals(newUser, registeredUser);
    }

    @Test
    void register_sameUserInStorage_ok() {
        User newUser = new User();
        newUser.setLogin(NEW_LOGIN);
        newUser.setAge(VALID_AGE);
        newUser.setPassword(VALID_PASSWORD);
        registrationService.register(newUser);
        User userFromStorage = storageDao.get(newUser.getLogin());
        Assertions.assertEquals(newUser, userFromStorage);
    }
}
