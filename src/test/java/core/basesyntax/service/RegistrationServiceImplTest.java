package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private RegistrationService registrationService = new RegistrationServiceImpl();
    private StorageDao storageDao = new StorageDaoImpl();

    @Test
    void registerUser_Ok() throws AuthenticationException {
        User testUser1 = new User(); //testUser1
        testUser1.setId(12345L);
        testUser1.setAge(18);
        testUser1.setLogin("user1");
        testUser1.setPassword("123456");
        registrationService.register(testUser1);
        assertTrue(storageDao.get(testUser1.getLogin()) != null);
    }

    @Test
    void userAlreadyExist_NotOk() throws AuthenticationException {
        User testUser2 = new User(); //testUser2
        testUser2.setId(12346L);
        testUser2.setAge(20);
        testUser2.setLogin("user2");
        testUser2.setPassword("1234567");
        registrationService.register(testUser2);
        User testUser3 = new User(); //testUser3
        testUser3.setId(12347L);
        testUser3.setAge(22);
        testUser3.setLogin("user2");
        testUser3.setPassword("1234568");
        try {
            registrationService.register(testUser3);
        } catch (AuthenticationException e) {
            return;
        }
        fail("AuthenticationException should be thrown because User has already existed");
    }

    @Test
    void userYoungerThan18_NotOk() {
        User testUser4 = new User(); //testUser4
        testUser4.setId(12347L);
        testUser4.setAge(16);
        testUser4.setLogin("user4");
        testUser4.setPassword("1234568");
        try {
            registrationService.register(testUser4);
        } catch (AuthenticationException e) {
            return;
        }
        fail("AuthenticationException should be thrown because User younger than 18");
    }

    @Test
    void passwordLessThan6Char_NotOk() {
        User testUser5 = new User(); //testUser5
        testUser5.setId(12348L);
        testUser5.setAge(20);
        testUser5.setLogin("user5");
        testUser5.setPassword("12345");
        try {
            registrationService.register(testUser5);
        } catch (AuthenticationException e) {
            return;
        }
        fail("AuthenticationException should be thrown because Password shorter than 6 characters");
    }

    @Test
    void registerNull_NotOk() {
        User testUser6 = new User(); //testUser6 NoID
        testUser6.setAge(20);
        testUser6.setLogin("user6");
        testUser6.setPassword("123456");
        try {
            registrationService.register(testUser6);
        } catch (AuthenticationException e) {
            return;
        }
        fail("AuthenticationException should be thrown because ID can't be null");
        User testUser7 = new User(); //testUser7 NoAge
        testUser7.setId(12348L);
        testUser7.setLogin("user7");
        testUser7.setPassword("123456");
        try {
            registrationService.register(testUser7);
        } catch (AuthenticationException e) {
            return;
        }
        fail("AuthenticationException should be thrown because Age can't be null");
        User testUser8 = new User(); //testUser8 NoLogin
        testUser8.setId(12348L);
        testUser8.setAge(20);
        testUser8.setPassword("123456");
        try {
            registrationService.register(testUser8);
        } catch (AuthenticationException e) {
            return;
        }
        fail("AuthenticationException should be thrown because Login can't be null");
        User testUser9 = new User(); //testUser9 NoPassword
        testUser9.setId(12348L);
        testUser9.setLogin("user9");
        testUser9.setAge(20);
        try {
            registrationService.register(testUser9);
        } catch (AuthenticationException e) {
            return;
        }
        fail("AuthenticationException should be thrown because Login can't be null");
    }
}
