package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    private static User newUser;
    private static User nullLoginUser;
    private static User sameLoginUser;
    private static User nullPasswordUser;
    private static User shortPasswordUser;
    private static User veryOldUser;
    private static User notBornUser;
    private static User teenUser;
    private static User nullAgeUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        newUser = new User("newLogin", "newPassword", 19);
        nullLoginUser = new User(null, "12345678", 24);
        sameLoginUser = new User("newLogin", "qwerty123", 54);
        nullPasswordUser = new User("nullPass228", null, 87);
        shortPasswordUser = new User("shortLogin", "short", 30);
        veryOldUser = new User("veryOld", "veryOldPassword", 250);
        notBornUser = new User("notBorn", "notBornPassword", -15);
        nullAgeUser = new User("withNoAgeUser", "pa$$w0rd", null);
        teenUser = new User("teenager", "teenagerPassword", 13);
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    @Test
    void register_newUser_Ok() {
        assertEquals(registrationService.register(newUser), newUser, "Test failed! "
                + "Your register method should return added user");
    }

    @Test
    void register_nullLogin_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(nullLoginUser);
        }, "Test failed! If login is null, we should throw custom unchecked exception");
    }

    @Test
    void register_existingLogin_notOk() {
        storageDao.add(newUser);
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
    void register_veryOldUser_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(veryOldUser);
        }, "Test failed! If age is invalid,"
                + " we should throw custom unchecked exception");
    }

    @Test
    void register_notBornUser_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(notBornUser);
        }, "Test failed! If age is invalid,"
                + " we should throw custom unchecked exception");
    }

    @Test
    void register_nullAge() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(nullAgeUser);
        }, "Test failed! If age is invalid,"
                + " we should throw custom unchecked exception");
    }

    @Test
    void register_teenUser_notOK() {
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
