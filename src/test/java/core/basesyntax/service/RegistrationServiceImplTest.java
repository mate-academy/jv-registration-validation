package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static StorageDao storageDao;
    private static User newUser1;
    private static User newUser2;
    private static User newUser3;
    private static User nullLoginUser;
    private static User sameLoginUser;
    private static User nullPasswordUser;
    private static User shortPasswordUser;
    private static User veryOldUser;
    private static User notBornUser1;
    private static User notBornUser2;
    private static User teenUser;
    private static User nullAgeUser;
    private static int size;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        newUser1 = new User("newLogin1", "newPassword1", 19);
        newUser2 = new User("newLogin2", "newPassword2", 68);
        newUser3 = new User("newLogin3", "newPassword3", 35);
        nullLoginUser = new User(null, "12345678", 24);
        sameLoginUser = new User("newLogin1", "qwerty123", 54);
        nullPasswordUser = new User("nullPass228", null, 87);
        shortPasswordUser = new User("shortLogin", "short", 30);
        veryOldUser = new User("veryOld", "veryOldPassword", 250);
        notBornUser1 = new User("notBorn1", "notBornPassword1", -15);
        notBornUser2 = new User("notBorn2", "notBornPassword2", 0);
        nullAgeUser = new User("withNoAgeUser", "pa$$w0rd", null);
        teenUser = new User("teenager", "teenagerPassword", 13);
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        size = 0;
    }

    @Test
    void register_newUser_Ok() {
        User actual = registrationService.register(newUser1);
        User expected = newUser1;
        size++;
        assertEquals(actual, expected, "Test failed! "
                + "Your register method should return added user");
        assertTrue(Storage.people.contains(newUser1), "Test failed! "
                + "After registering new user, people list must contain this user");
        assertEquals(size, Storage.people.size(), "Test failed! "
                + "After registering new user, people list size should be " + size
                + ", but was " + Storage.people.size());

        actual = registrationService.register(newUser2);
        expected = newUser2;
        size++;
        assertEquals(actual, expected, "Test failed! "
                + "Your register method should return added user");
        assertTrue(Storage.people.contains(newUser2), "Test failed! "
                + "After registering new user, people list must contain this user");
        assertEquals(size, Storage.people.size(), "Test failed! "
                + "After registering new user, people list size should be " + size
                + ", but was " + Storage.people.size());

        actual = registrationService.register(newUser3);
        expected = newUser3;
        size++;
        assertEquals(actual, expected, "Test failed! "
                + "Your register method should return added user");
        assertTrue(Storage.people.contains(newUser3), "Test failed! "
                + "After registering new user, people list must contain this user");
        assertEquals(size, Storage.people.size(), "Test failed! "
                + "After registering new user, people list size should be " + size
                + ", but was " + Storage.people.size());
    }

    @Test
    void register_nullLogin_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(nullLoginUser);
        }, "Test failed! If login is null, we should throw custom unchecked exception");
    }

    @Test
    void register_existingLogin_notOk() {
        storageDao.add(newUser1);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(sameLoginUser);
        }, "Test failed! If user with this login already exists, "
                + "we should throw custom unchecked exception");
    }

    @Test
    void register_nullPassword_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(nullPasswordUser);
        }, "Test failed! If password is null,"
                + " we should throw custom unchecked exception");
    }

    @Test
    void register_incorrectPassword_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(shortPasswordUser);
        }, "Test failed! If password length is invalid,"
                + " we should throw custom unchecked exception");
    }

    @Test
    void register_incorrectAge_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(veryOldUser);
        }, "Test failed! If age is invalid,"
                + " we should throw custom unchecked exception");

        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(notBornUser1);
        }, "Test failed! If age is invalid,"
                + " we should throw custom unchecked exception");

        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(notBornUser2);
        }, "Test failed! If age is invalid,"
                + " we should throw custom unchecked exception");

        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(nullAgeUser);
        }, "Test failed! If age is invalid,"
                + " we should throw custom unchecked exception");

        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(teenUser);
        }, "Test failed! If age is invalid,"
                + " we should throw custom unchecked exception");
    }

    @Test
    void register_NullUser_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(null);
        }, "Test failed! If user is null,"
                + " we should throw custom unchecked exception");
    }
}
