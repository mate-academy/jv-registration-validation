package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidRegistrationDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    public static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullUsersLogin_NotOk() {
        User user = new User(null, "qwerty", 18);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_outOfBoundsUsersLogin() {
        User userWithEmptyLogin = new User("", "qwerty", 19);
        User userWithLargeLogin = new User("Bartholomew", "qwerty", 19);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(userWithEmptyLogin);
        });
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(userWithLargeLogin);
        });
    }

    @Test
    void register_usersLoginWithSpaces_NotOk() {
        User user1 = new User(" ", "qwerty", 19);
        User user2 = new User("Bob Alicon", "qwerty", 19);
        User user3 = new User("B o b", "qwerty", 19);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user1);
        });
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user2);
        });
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user3);
        });
    }

    @Test
    void register_addByExistingLogin_NotOk() {
        StorageDao storageDao = new StorageDaoImpl();
        User user1 = new User("Bob228", "qwerty", 19);
        User user2 = new User("Bob228", "zxcvbn", 25);
        storageDao.add(user1);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user2);
        });
        Storage.people.clear();
    }

    @Test
    void register_nullUsersPassword_NotOk() {
        User user = new User("Bob", null, 18);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_outOfBoundsUsersPassword() {
        User userWithShortPassword = new User("Bob", "qwert", 22);
        User userWithLongPassword = new User("Bob", "qwertyuiopasdfghj", 22);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(userWithShortPassword);
        });
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(userWithLongPassword);
        });
    }

    @Test
    void register_nullUsersAge_NotOk() {
        User user = new User("Bob", "qwerty", null);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_outOfBoundsUsersAge() {
        User tooYoungUser = new User("Bob", "qwerty", 17);
        User tooOldUser = new User("Bob", "qwerty", 131);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(tooYoungUser);
        });
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(tooOldUser);
        });
    }

    @Test
    void register_negativeUsersAge_NotOk() {
        User userWithNegativeAge = new User("Bob", "qwerty", -18);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(userWithNegativeAge);
        });
    }

    @Test
    void register_addUserWithCorrectData() {
        User correctUser = new User("Bob", "qwerty", 18);
        User actual = registrationService.register(correctUser);
        assertEquals(correctUser, actual);
        assertEquals(1, actual.getId());
        Storage.people.clear();
    }

    @Test
    void register_checkStorageSize() {
        User correctUser1 = new User("Bob", "qwerty", 18);
        User correctUser2 = new User("Mike", "123456", 19);
        User correctUser3 = new User("Jake", "asdfgh", 20);
        registrationService.register(correctUser1);
        registrationService.register(correctUser2);
        registrationService.register(correctUser3);
        assertEquals(3, Storage.people.size());
        Storage.people.clear();
    }
}
