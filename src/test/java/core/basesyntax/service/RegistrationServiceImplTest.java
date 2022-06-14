package core.basesyntax.service;

import static core.basesyntax.db.Storage.people;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private StorageDao storageDao;
    private User user;

    @BeforeAll
    public static void initialSetup() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        Storage.people.clear();
        storageDao = new StorageDaoImpl();
        user = new User(0L, "user1", "12345678", 19);
    }

    @Test
    void register_addOneCorrectUser_ok() {
        storageDao.add(registrationService.register(user));
        List<User> expected = new ArrayList<>();
        expected.add(user);
        assertEquals(expected, people);
    }

    @Test
    void register_addOneUserWithLoginNull_notOk() {
        user.setLogin(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_addOneUserWithNullPassword_notOk() {
        user.setPassword(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_addOneUserWithNullAge_notOk() {
        user.setAge(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_addOneUserWithIncorectPassword_notOk() {
        user.setPassword("12345");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_addOneUserWithIncorectAge_notOk() {
        user.setAge(15);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_addTwoUsersWithSameLogin_notOk() {
        User user1 = new User(0L, "user1", "12345678", 19);
        User user2 = new User(0L, "user1", "12345678", 18);
        storageDao.add(registrationService.register(user1));
        assertThrows(InvalidDataException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_addFourCorrectUser_ok() {
        User user1 = new User(0L, "user1", "12345678", 19);
        User user2 = new User(0L, "user2", "12345678", 18);
        User user3 = new User(0L, "user3", "12345678", 19);
        User user4 = new User(0L, "user4", "12345678", 19);
        storageDao.add(registrationService.register(user1));
        storageDao.add(registrationService.register(user2));
        storageDao.add(registrationService.register(user3));
        storageDao.add(registrationService.register(user4));
        List<User> expected = new ArrayList<>();
        expected.add(user1);
        expected.add(user2);
        expected.add(user3);
        expected.add(user4);
        assertEquals(expected, people);
    }
}
