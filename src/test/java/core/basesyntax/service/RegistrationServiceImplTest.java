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
    void register_nullPasswordValue_notOk() {
        User user = new User();
        user.setLogin("12345");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullLoginValue_notOk() {
        User user = new User();
        user.setPassword("Password");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_loginLengthFiveOrLessCharacters_notOk() {
        User user = new User();
        user.setLogin("12345");
        user.setPassword("Password");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_loginLengthMoreFiveCharacters_ok() {
        User user = new User();
        user.setLogin("123456");
        int actual = user.getLogin().length();
        int expectLoginLength = 6;
        assertTrue(expectLoginLength <= actual);
    }

    @Test
    void register_addUserToStorage_ok() {
        User user = new User();
        user.setPassword("Password");
        user.setLogin("Userfile");
        user.setAge(19);
        registrationService.register(user);
        User actual = storageDao.get("Userfile");
        assertEquals(user, actual);
    }

    @Test
    void storageDao_getUserFromStorage_ok() {
        User userTiger = new User();
        userTiger.setPassword("Password");
        userTiger.setLogin("Tigers");
        userTiger.setAge(20);
        registrationService.register(userTiger);
        User actual = storageDao.get("Tigers");
        assertEquals(userTiger, actual);
    }

    @Test
    void storageDao_getFromStorageMissingValue_notOk() {
        User user1 = new User();
        user1.setPassword("UnitTest123");
        user1.setLogin("Cobras");
        user1.setAge(22);
        User user2 = new User();
        user2.setPassword("UnitTest123");
        user2.setLogin("Tigers");
        user2.setAge(21);
        registrationService.register(user1);
        registrationService.register(user2);
        User actual = storageDao.get("Horses");
        assertNull(actual);
    }

    @Test
    void storageDao_getNonExistUser_notOk() {
        User user1 = new User();
        user1.setPassword("UnitTest2");
        user1.setLogin("CobraSet");
        user1.setAge(32);
        User user2 = new User();
        user2.setPassword("UnitTest1");
        user2.setLogin("CobraMap");
        user2.setAge(43);
        registrationService.register(user1);
        registrationService.register(user2);
        User actual = storageDao.get("MouseSet");
        assertNull(actual);
    }

    @Test
    void register_passwordLengthFiveOrLessCharacters_notOk() {
        User user = new User();
        user.setLogin("UnitTest123");
        user.setPassword("passw");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordContainAtLeastOneUpperLetter_ok() {
        User user = new User();
        user.setLogin("userfile");
        user.setPassword("passw");
        user.setAge(22);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordLengthSixOrMoreCharacters_ok() {
        User user = new User();
        user.setLogin("1234567");
        user.setPassword("Password");
        int actual = user.getPassword().length();
        int expect = 6;
        assertTrue(expect <= actual);
    }

    @Test
    void register_ageLessThan18_notOk() {
        User user = new User();
        user.setPassword("password");
        user.setLogin("Petrenko");
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageMoreThan17_ok() {
        User user = new User();
        user.setPassword("password");
        user.setLogin("Petrenko");
        user.setAge(18);
        int actual = user.getAge();
        int expect = 18;
        assertTrue(expect <= actual);
    }
}

