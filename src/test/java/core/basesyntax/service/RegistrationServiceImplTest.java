package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User user;
    private static StorageDao storageDao;
    private static final int VALID_AGE = 18;
    private static final String VALID_PASSWORD = "MyPassword";
    private static final String VALID_LOGIN = "6575MyLogin";
    private static final String VALID_LOGIN_NEW = "94MyLogin95";

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(VALID_AGE);
        user.setPassword(VALID_PASSWORD);
        user.setLogin(VALID_LOGIN);
        storageDao.add(user);
    }

    @AfterEach
    void clean() {
        Storage.people.clear();
    }

    @Test
    void register_validUser_Ok() {
        User newUser = new User();
        newUser.setAge(VALID_AGE);
        newUser.setPassword(VALID_PASSWORD);
        newUser.setLogin(VALID_LOGIN_NEW);
        registrationService.register(newUser);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));

    }

    @Test
    void register_lowAge_NotOk() {
        user.setAge(12);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userLoginIsNull_NotOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_NotOk() {
        user.setPassword("1");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setPassword("1234");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setPassword("12345");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userPasswordIsNull_NotOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userExistInSuchStorage_NotOk() {
        storageDao.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
