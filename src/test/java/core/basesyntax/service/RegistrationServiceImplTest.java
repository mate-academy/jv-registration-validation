package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.IncorrectUserDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static StorageDaoImpl storage;
    private static final User USER = new User();
    private static final User ANOTHER_USER = new User();

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storage = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        USER.setAge(18);
        USER.setLogin("user1_login");
        USER.setPassword("user1Password1");
        ANOTHER_USER.setAge(20);
        ANOTHER_USER.setLogin("anotherLogin");
        ANOTHER_USER.setPassword("AnotherPassword");
    }

    @Test
    void storageHasUser_notOk() {
        registrationService.register(ANOTHER_USER);
        User actual = registrationService.register(ANOTHER_USER);
        Assertions.assertNull(actual);
    }

    @Test
    void getUserFromStorage_notNull_Ok() {
        registrationService.register(USER);
        User actualUser = storage.get(USER.getLogin());
        Assertions.assertNotNull(actualUser, "Storage has such user, it can't be null");
    }

    @Test
    void checkUser_isNull_notOk() {
        Assertions.assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(null), "Incorrect data: user can't be null");
    }

    @Test
    void checkUserAge_atLeast18_notOk() {
        USER.setAge(17);
        Assertions.assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(USER),
                "Incorrect data: user age must be bigger than 18");
    }

    @Test
    void checkUserPassword_IsNull_notOk() {
        USER.setPassword(null);
        Assertions.assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(USER),
                "Incorrect data: user password can't be null");
    }

    @Test
    void checkUserPassword_atLeastSixSymbols_notOk() {
        USER.setPassword("12345");
        Assertions.assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(USER),
                "Incorrect data: user password can't be null");
    }

    @Test
    void checkUserLogin_isNull_notOk() {
        USER.setLogin(null);
        Assertions.assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(USER),
                "Incorrect data: user login can't be null");
    }
}
