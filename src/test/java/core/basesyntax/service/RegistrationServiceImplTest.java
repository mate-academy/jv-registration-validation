package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static User defaultUser;
    private static String correctLogin;
    private static String checkLogin;
    private static int correctAge;
    private static int youngAge;
    private static int incorrectAge;
    private static String correctPassword;
    private static String wrongPassword;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void beforeEach() {
        storageDao = new StorageDaoImpl();
        defaultUser = new User();
        correctLogin = "Johnson";
        checkLogin = "Lara";
        correctAge = 20;
        youngAge = 17;
        incorrectAge = -5;
        correctPassword = "John328";
        wrongPassword = "lol";
    }

    @Test
    void correctData_ok() {
        defaultUser.setLogin(checkLogin);
        defaultUser.setAge(correctAge);
        defaultUser.setPassword(correctPassword);
        assertEquals(defaultUser, registrationService.register(defaultUser));
    }

    @Test
    public void checkAlreadyExistedUser_notOk() {
        User user = new User();
        user.setLogin(correctLogin);
        user.setPassword(correctPassword);
        user.setAge(correctAge);
        storageDao.add(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserWithNullLogin_notOk() {
        defaultUser.setLogin(null);
        defaultUser.setAge(correctAge);
        defaultUser.setPassword(correctPassword);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void registerUserWithNullAge_notOk() {
        defaultUser.setLogin(correctLogin);
        defaultUser.setAge(null);
        defaultUser.setPassword(correctPassword);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void registerUserWithSmallAge_notOk() {
        defaultUser.setLogin(correctLogin);
        defaultUser.setAge(youngAge);
        defaultUser.setPassword(correctPassword);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void registerUserWithInvalidAge_notOk() {
        defaultUser.setLogin(correctLogin);
        defaultUser.setAge(incorrectAge);
        defaultUser.setPassword(correctPassword);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void registerUserWithSmallPassword_notOk() {
        defaultUser.setLogin(correctLogin);
        defaultUser.setAge(correctAge);
        defaultUser.setPassword(wrongPassword);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @AfterEach
    void afterEach() {
        storageDao = null;
    }
}
