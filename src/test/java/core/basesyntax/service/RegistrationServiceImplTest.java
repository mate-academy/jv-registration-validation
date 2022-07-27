package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        User user = new User();
        user.setLogin("Bob");
        user.setPassword("qweasdzxc");
        user.setAge(20);
        storageDao.add(user);
        user = new User();
        user.setLogin("Mary");
        user.setPassword("qw23fdfd");
        user.setAge(33);
        storageDao.add(user);
    }

    @Test
    void registration_NullUser_NotOK() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void registration_loginNull_NotOK() {
        User user = new User();
        user.setPassword("kfnvofod");
        user.setAge(22);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
        assertEquals(2, Storage.people.size(),
                "Test failed! The user must not be added to the storage");
    }

    @Test
    void registration_unique_OK() {
        User user = new User();
        user.setLogin("Alex");
        user.setPassword("kfnvofod");
        user.setAge(22);
        User actual = registrationService.register(user);
        assertNotNull(actual,
                "When user is not unique, the method should not return \"null\"");
        assertEquals(3, Storage.people.size(),
                "Test failed! The user must be once added to the storage");
    }

    @Test
    void registration_unique_NotOK() {
        User user = new User();
        user.setLogin("Mary");
        user.setPassword("kfnvofod");
        user.setAge(22);
        User actual = registrationService.register(user);
        assertNull(actual,
                "When user is not unique, the method should return \"null\"");
        assertEquals(2, Storage.people.size(),
                "Test failed! The user must not be added to the storage");
    }

    @Test
    void registration_age_OK() {
        User user = new User();
        user.setLogin("Ann");
        user.setPassword("kfnvofod");
        user.setAge(22);
        User actual = registrationService.register(user);
        assertNotNull(actual, "When user's age more or equal then "
                + RegistrationServiceImpl.MIN_AGE_USER + ", the method should not return \"null\"");
        assertEquals(3, Storage.people.size(),
                "Test failed! The user must be once added to the storage");
    }

    @Test
    void registration_age_NotOK() {
        User user = new User();
        user.setLogin("Ann");
        user.setPassword("kfnvofod");
        user.setAge(15);
        User actual = registrationService.register(user);
        assertNull(actual, "When user's age less then "
                + RegistrationServiceImpl.MIN_AGE_USER + ", the method should return \"null\"");
        assertEquals(2, Storage.people.size(),
                "Test failed! The user must not be added to the storage");
    }

    @Test
    void registration_ageNull_NotOK() {
        User user = new User();
        user.setLogin("Ann");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
        assertEquals(2, Storage.people.size(),
                "Test failed! The user must not be added to the storage");
    }

    @Test
    void registration_lengthPassword_OK() {
        User user = new User();
        user.setLogin("Ann");
        user.setAge(22);
        user.setPassword("qsc23rf");
        User actual = registrationService.register(user);
        assertNotNull(actual, "When user password length is more or equal then "
                + RegistrationServiceImpl.MIN_LENGT_PATHWORD
                + ", the method should not return \"null\"");
        assertEquals(3, Storage.people.size(),
                "Test failed! The user must be once added to the storage");
    }

    @Test
    void registration_lengthPassword_NotOK() {
        User user = new User();
        user.setLogin("Ann");
        user.setAge(22);
        user.setPassword("qscf");
        User actual = registrationService.register(user);
        assertNull(actual, "When user password length is less then "
                + RegistrationServiceImpl.MIN_LENGT_PATHWORD
                + ", the method should return \"null\"");
        assertEquals(2, Storage.people.size(),
                "Test failed! The user must not be added to the storage");
    }

    @Test
    void registration_passwordNull_NotOK() {
        User user = new User();
        user.setLogin("Ann");
        user.setAge(22);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
        assertEquals(2, Storage.people.size(),
                "Test failed! The user must not be added to the storage");
    }
}
