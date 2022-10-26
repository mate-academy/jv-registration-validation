package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {

    RegistrationService registrationService  = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();
    private final User defaultUser = new User();
    private final String correctLogin = "Johnson";
    private final int correctAge = 20;
    private final String correctPassword = "John328";

    @Test
    void correctData_ok() {
        defaultUser.setLogin(correctLogin);
        defaultUser.setAge(correctAge);
        defaultUser.setPassword(correctPassword);
        storageDao.add(defaultUser);
        boolean actual = defaultUser.equals(registrationService.register(defaultUser));
        assertTrue(actual);
    }

    @Test
    void checkAlreadyExistedUser_ok() {
        defaultUser.setLogin(correctLogin);
        defaultUser.setAge(correctAge);
        defaultUser.setPassword(correctPassword);
        storageDao.add(defaultUser);
        boolean actual = defaultUser.equals(registrationService.register(defaultUser));
        assertTrue(actual);
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
        final int youngAge = 10;
        defaultUser.setLogin(correctLogin);
        defaultUser.setAge(youngAge);
        defaultUser.setPassword(correctPassword);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void registerUserWithInvalidAge_notOk() {
        final int incorrectAge = -5;
        defaultUser.setLogin(correctLogin);
        defaultUser.setAge(incorrectAge);
        defaultUser.setPassword(correctPassword);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void registerUserWithSmallPassword_notOk() {
        final String smallPassword = "lol";
        defaultUser.setLogin(correctLogin);
        defaultUser.setAge(correctAge);
        defaultUser.setPassword(smallPassword);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }
}