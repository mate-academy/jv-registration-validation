package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private RegistrationService registrationService = new RegistrationServiceImpl();
    private StorageDao storageDao = new StorageDaoImpl();
    private Map<String,User> users;

    @BeforeEach
    void setUp() {
        users = new HashMap<>();
        User testUserOk = new User(); //testUserOk
        testUserOk.setId(12345L);
        testUserOk.setAge(18);
        testUserOk.setLogin("testUserOk");
        testUserOk.setPassword("123456");
        users.put("testUserOk",testUserOk);
        User testUserNamesake = new User(); //testUserNamesake
        testUserNamesake.setId(12347L);
        testUserNamesake.setAge(22);
        testUserNamesake.setLogin("testUserOk");
        testUserNamesake.setPassword("1234568");
        users.put("testUserNamesake", testUserNamesake);
        User testUserJuvenile = new User(); //testUserJuvenile
        testUserJuvenile.setId(12347L);
        testUserJuvenile.setAge(16);
        testUserJuvenile.setLogin("userJuvenile");
        testUserJuvenile.setPassword("1234568");
        users.put("testUserJuvenile", testUserJuvenile);
        User testUserShortPassword = new User(); //testUserShortPassword
        testUserShortPassword.setId(12348L);
        testUserShortPassword.setAge(20);
        testUserShortPassword.setLogin("userShortPassword");
        testUserShortPassword.setPassword("12345");
        users.put("testUserShortPassword", testUserShortPassword);
        User testUserNoId = new User(); //testUserNoId
        testUserNoId.setAge(20);
        testUserNoId.setLogin("userNoId");
        testUserNoId.setPassword("123456");
        users.put("testUserNoId", testUserNoId);
        User testUserNoAge = new User(); //testUserNoAge
        testUserNoAge.setId(12348L);
        testUserNoAge.setLogin("userNoAge");
        testUserNoAge.setPassword("123456");
        users.put("testUserNoAge", testUserNoAge);
        User testUserNoLogin = new User(); //testUserNoLogin
        testUserNoLogin.setId(12348L);
        testUserNoLogin.setAge(20);
        testUserNoLogin.setPassword("123456");
        users.put("testUserNoLogin", testUserNoLogin);
        User testUserNoPassword = new User(); //testUserNoPassword
        testUserNoPassword.setId(12348L);
        testUserNoPassword.setLogin("userNoPassword");
        testUserNoPassword.setAge(20);
        users.put("testUserNoPassword", testUserNoPassword);
    }

    @Test
    void registerUser_Ok() throws AuthenticationException {
        registrationService.register(users.get("testUserOk"));
        assertNotNull(storageDao.get(users.get("testUserOk").getLogin()));
    }

    @Test
    void userAlreadyExist_NotOk() throws AuthenticationException {
        try {
            registrationService.register(users.get("testUserOk"));
            registrationService.register(users.get("testUserNamesake"));
        } catch (AuthenticationException e) {
            return;
        }
        fail("AuthenticationException should be thrown because User has already existed");
    }

    @Test
    void userYoungerThanLimit_NotOk() {
        try {
            registrationService.register(users.get("testUserJuvenile"));
        } catch (AuthenticationException e) {
            return;
        }
        fail("AuthenticationException should be thrown because User younger than 18");
    }

    @Test
    void passwordLessThanLimit_NotOk() {
        try {
            registrationService.register(users.get("testUserShortPassword"));
        } catch (AuthenticationException e) {
            return;
        }
        fail("AuthenticationException should be thrown because Password shorter than 6 characters");
    }

    @Test
    void registerNull_NotOk() {
        try {
            registrationService.register(users.get("testUserNoId"));
        } catch (AuthenticationException e) {
            return;
        }
        fail("AuthenticationException should be thrown because ID can't be null");
        try {
            registrationService.register(users.get("testUserNoAge"));
        } catch (AuthenticationException e) {
            return;
        }
        fail("AuthenticationException should be thrown because Age can't be null");
        try {
            registrationService.register(users.get("testUserNoLogin"));
        } catch (AuthenticationException e) {
            return;
        }
        fail("AuthenticationException should be thrown because Login can't be null");
        try {
            registrationService.register(users.get("testUserNoPassword"));
        } catch (AuthenticationException e) {
            return;
        }
        fail("AuthenticationException should be thrown because Login can't be null");
    }
}
