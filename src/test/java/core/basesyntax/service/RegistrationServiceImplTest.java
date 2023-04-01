package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    void register_user_Ok() throws AuthenticationException {
        User testUserOk = new User(); //testUserOk
        testUserOk.setId(12345L);
        testUserOk.setAge(18);
        testUserOk.setLogin("testUser");
        testUserOk.setPassword("123456");
        registrationService.register(testUserOk);
        assertNotNull(storageDao.get(testUserOk.getLogin()));
    }

    @Test
    void register_userAlreadyExist_NotOk() throws AuthenticationException {
        User testUserOk = new User(); //testUserOk
        testUserOk.setId(12345L);
        testUserOk.setAge(18);
        testUserOk.setLogin("testUserOk");
        testUserOk.setPassword("123456");
        User testUserNamesake = new User(); //testUserNamesake
        testUserNamesake.setId(12347L);
        testUserNamesake.setAge(22);
        testUserNamesake.setLogin("testUserOk");
        testUserNamesake.setPassword("1234568");
        try {
            registrationService.register(testUserOk);
            registrationService.register(testUserNamesake);
        } catch (AuthenticationException e) {
            return;
        }
        fail("AuthenticationException should be thrown because User has already existed");
    }

    @Test
    void register_userAgeLessThanLimit_NotOk() {
        User testUserJuvenile = new User(); //testUserJuvenile
        testUserJuvenile.setId(12347L);
        testUserJuvenile.setAge(16);
        testUserJuvenile.setLogin("userJuvenile");
        testUserJuvenile.setPassword("1234568");
        try {
            registrationService.register(testUserJuvenile);
        } catch (AuthenticationException e) {
            return;
        }
        fail("AuthenticationException should be thrown because User younger than 18");
    }

    @Test
    void register_passwordLessThanLimit_NotOk() {
        User testUserShortPassword = new User(); //testUserShortPassword
        testUserShortPassword.setId(12348L);
        testUserShortPassword.setAge(20);
        testUserShortPassword.setLogin("userShortPassword");
        testUserShortPassword.setPassword("12345");
        try {
            registrationService.register(testUserShortPassword);
        } catch (AuthenticationException e) {
            return;
        }
        fail("AuthenticationException should be thrown because Password shorter than 6 characters");
    }

    @Test
    void register_NoId_NotOk() {
        User testUserNoId = new User(); //testUserNoId
        testUserNoId.setAge(20);
        testUserNoId.setLogin("userNoId");
        testUserNoId.setPassword("123456");
        try {
            registrationService.register(testUserNoId);
        } catch (AuthenticationException e) {
            return;
        }
        fail("AuthenticationException should be thrown because ID can't be null");
    }

    @Test
    void register_NoAge_NotOk() {
        User testUserNoAge = new User(); //testUserNoAge
        testUserNoAge.setId(12348L);
        testUserNoAge.setLogin("userNoAge");
        testUserNoAge.setPassword("123456");
        try {
            registrationService.register(testUserNoAge);
        } catch (AuthenticationException e) {
            return;
        }
        fail("AuthenticationException should be thrown because Age can't be null");
    }

    @Test
    void register_NoLogin_NotOk() {
        User testUserNoLogin = new User(); //testUserNoLogin
        testUserNoLogin.setId(12348L);
        testUserNoLogin.setAge(20);
        testUserNoLogin.setPassword("123456");
        try {
            registrationService.register(testUserNoLogin);
        } catch (AuthenticationException e) {
            return;
        }
        fail("AuthenticationException should be thrown because Login can't be null");
    }

    @Test
    void register_NoPassword_NotOk() {
        User testUserNoPassword = new User(); //testUserNoPassword
        testUserNoPassword.setId(12348L);
        testUserNoPassword.setLogin("userNoPassword");
        testUserNoPassword.setAge(20);
        try {
            registrationService.register(testUserNoPassword);
        } catch (AuthenticationException e) {
            return;
        }
        fail("AuthenticationException should be thrown because Password can't be null");
    }
}


