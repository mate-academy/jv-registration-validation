package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.InvalidDataException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationServiceImpl RegistrationServiceImpl;
    private static User testUserOk;
    private static User testUserNotOk;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        RegistrationServiceImpl = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        testUserOk = new User();
        testUserOk.setAge(20);
        testUserOk.setLogin("Login");
        testUserOk.setPassword("Password");
    }

    @BeforeEach
    void setUp() {
        Storage.setPeople();
        testUserNotOk = new User();
    }

    @Test
    void register_alreadyExistLoginUser_notOk() {
        storageDao.add(testUserOk);
        Assertions.assertThrows(InvalidDataException.class, () -> {
            RegistrationServiceImpl.register(testUserOk);
        });
    }

    @Test
    void register_defaultValidUser_ok() {
        Assertions.assertDoesNotThrow(() -> RegistrationServiceImpl.register(testUserOk));
        Assertions.assertNotNull(storageDao.get(testUserOk.getLogin()));
    }

    @Test
    void register_nullUser_notOk() {
        User nullUser = null;
        Assertions.assertThrows(InvalidDataException.class, () -> {
            RegistrationServiceImpl.register(nullUser);
        });
    }

    @Test
    void register_shortPassUser_notOk() {
        testUserNotOk.setAge(20);
        testUserNotOk.setLogin("Login");
        testUserNotOk.setPassword("Pass");
        Assertions.assertThrows(InvalidDataException.class, () -> {
            RegistrationServiceImpl.register(testUserNotOk);
        });
    }

    @Test
    void register_passwordIsRight_Ok() {
        Assertions.assertDoesNotThrow(() -> RegistrationServiceImpl.register(testUserOk));
        String expectedPassword = "Password";
        User userInStorage = storageDao.get(testUserOk.getLogin());
        Assertions.assertEquals(expectedPassword, userInStorage.getPassword());
    }

    @Test
    void register_nullPassUser_notOk() {
        testUserNotOk.setAge(20);
        testUserNotOk.setLogin("Login");
        testUserNotOk.setPassword(null);
        Assertions.assertThrows(InvalidDataException.class, () -> {
            RegistrationServiceImpl.register(testUserNotOk);
        });
    }

    @Test
    void register_christAgeUser_ok() {
        User testUserChristOk = new User();
        testUserChristOk.setAge(33);
        testUserChristOk.setLogin("Login");
        testUserChristOk.setPassword("Password");
        int expectedAge = 33;
        Assertions.assertDoesNotThrow(() -> RegistrationServiceImpl.register(testUserChristOk));
        User userInStorage = storageDao.get(testUserChristOk.getLogin());
        Assertions.assertEquals(expectedAge, userInStorage.getAge());
    }

    @Test
    void register_nullAgeUser_notOk() {
        testUserNotOk.setAge(null);
        testUserNotOk.setLogin("Login");
        testUserNotOk.setPassword("Password");
        Assertions.assertThrows(InvalidDataException.class, () -> {
            RegistrationServiceImpl.register(testUserNotOk);
        });
    }

    @Test
    void register_lowAgeUser_notOk() {
        testUserNotOk.setAge(15);
        testUserNotOk.setLogin("Login");
        testUserNotOk.setPassword("Password");
        Assertions.assertThrows(InvalidDataException.class, () -> {
            RegistrationServiceImpl.register(testUserNotOk);
        });
    }

    @Test
    void register_negativeAgeUser_notOk() {
        testUserNotOk.setAge(-1);
        testUserNotOk.setLogin("Login");
        testUserNotOk.setPassword("Password");
        Assertions.assertThrows(InvalidDataException.class, () -> {
            RegistrationServiceImpl.register(testUserNotOk);
        });
    }

    @Test
    void register_userHasPermissibleAge_Ok() {
        int expectedAge = 20;
        Assertions.assertDoesNotThrow(() -> RegistrationServiceImpl.register(testUserOk));
        User userInStorage = storageDao.get(testUserOk.getLogin());
        Assertions.assertEquals(expectedAge, userInStorage.getAge());
    }

    @Test
    void register_nullLoginUser_notOk() {
        testUserNotOk.setAge(20);
        testUserNotOk.setLogin(null);
        testUserNotOk.setPassword("Password");
        Assertions.assertThrows(InvalidDataException.class, () -> {
            RegistrationServiceImpl.register(testUserNotOk);
        });
    }

    @Test
    void register_shortLoginUser_notOk() {
        testUserNotOk.setAge(20);
        testUserNotOk.setLogin("Bo");
        testUserNotOk.setPassword("Password");
        Assertions.assertThrows(InvalidDataException.class, () -> {
            RegistrationServiceImpl.register(testUserNotOk);
        });
    }
}
