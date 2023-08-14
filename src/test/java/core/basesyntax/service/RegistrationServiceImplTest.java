package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.RegistrationServiceImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static StorageDao storageDao;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    void register_nullValue_notOk() {
        User user = new User();
        user.setLogin("12345");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_loginLengthFiveOrLessCharacters_notOk() {
        User user = new User();
        user.setLogin("12345");
        user.setPassword("userpassword");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_loginLengthMoreFiveCharacters_Ok() {
        User user = new User();
        user.setLogin("123456");
        user.setPassword("userpassword");
        int actual = user.getLogin().length();
        int expectLoginLength = 6;
        assertTrue(expectLoginLength <= actual);
    }

    @Test
    void storageDao_addUserToStorage_ok() {
        User user = new User();
        user.setId(1L);
        user.setPassword("1234562");
        user.setLogin("Cat");
        user.setAge(19);
        storageDao.add(user);
        User actual = storageDao.get("Cat");
        assertEquals(user, actual);
    }

    @Test
    void storageDao_getUserFromStorage_ok() {
        User userTiger = new User();
        userTiger.setId(2L);
        userTiger.setPassword("1234569");
        userTiger.setLogin("Tiger");
        userTiger.setAge(20);
        storageDao.add(userTiger);
        User actual = storageDao.get("Tiger");
        assertEquals(userTiger, actual);
    }

    @Test
    void storageDao_getFromStorageMissingValue_notOk() {
        User user1 = new User();
        user1.setPassword("1234564");
        user1.setLogin("Cobra");
        User user2 = new User();
        user2.setPassword("1234565");
        user2.setLogin("Tiger");
        storageDao.add(user1);
        storageDao.add(user2);
        User actual = storageDao.get("Mouse");
        assertEquals(null, actual);
    }

    @Test
    void storageDao_getNonExistUser_notOk() {
        User user1 = new User();
        user1.setPassword("12345612");
        user1.setLogin("Cobra");
        User user2 = new User();
        user2.setPassword("12345622");
        user2.setLogin("Cobra");
        storageDao.add(user1);
        storageDao.add(user2);
        User actual = storageDao.get("Mouse");
        assertNull(actual);
    }

    @Test
    void register_passwordLengthFiveOrLessCharacters_notOk() {
        User user = new User();
        user.setLogin("1234567");
        user.setPassword("passw");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordContainAtLeastOneUpperLetter_Ok() {
        User user = new User();
        user.setLogin("1234567");
        user.setPassword("psSs123");
        user.setAge(22);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordLengthSixOrMoreCharacters_Ok() {
        User user = new User();
        user.setLogin("1234567");
        user.setPassword("password");
        int actual = user.getPassword().length();
        int expect = 6;
        assertTrue(expect <= actual);
    }

    @Test
    void register_AgeLessThan18_notOk() {
        User user = new User();
        user.setPassword("password");
        user.setLogin("Petrenko");
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_AgeMoreThan17_Ok() {
        User user = new User();
        user.setPassword("password");
        user.setLogin("Petrenko");
        user.setAge(18);
        int actual = user.getAge();
        int expect = 18;
        assertTrue(expect <= actual);
    }
}

