package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeEach
    void beforeEach() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storageDao);
    }

    @Test
    void registerOneUser_Ok() {
        User oneUser = new User();
        oneUser.setLogin("oneuserlog");
        oneUser.setPassword("oneuserpass");
        oneUser.setAge(25);
        registrationService.register(oneUser);
        assertEquals(1, Storage.people.size(),
                "Storage should contains one user");
        assertEquals(oneUser, storageDao.get(oneUser.getLogin()),
                "User with valid data should be registered successfully");
    }

    @Test
    void registerMultipleUsers_differentDataLength_ok() {
        User first = new User();
        first.setLogin("firstL");
        first.setPassword("firstp");
        first.setAge(18);
        User second = new User();
        second.setLogin("secondlo");
        second.setPassword("secondpa");
        second.setAge(19);
        User third = new User();
        third.setLogin("thirdlogin");
        third.setPassword("thirdpass");
        third.setAge(25);
        User fourth = new User();
        fourth.setLogin("oneuserlog");
        fourth.setPassword("oneuserpass");
        fourth.setAge(50);
        registrationService.register(first);
        registrationService.register(second);
        registrationService.register(third);
        registrationService.register(fourth);
        assertEquals(4, Storage.people.size(), "Storage should contain 4 users");
    }

    @Test
    void register_nullPassword_notOK() {
        User nullPassUser = new User();
        nullPassUser.setLogin("qw3wlfoaad");
        nullPassUser.setPassword(null);
        nullPassUser.setAge(25);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(nullPassUser);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        User nullLog = new User();
        nullLog.setLogin(null);
        nullLog.setPassword("12334566332");
        nullLog.setAge(23);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(nullLog);
        });
    }

    @Test
    void register_nullPasswordAndLogin_notOk() {
        User nullPasLog = new User();
        nullPasLog.setLogin(null);
        nullPasLog.setPassword(null);
        nullPasLog.setAge(25);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(nullPasLog);
        });
    }

    @Test
    void register_nullAge_notOk() {
        User nullAge = new User();
        nullAge.setLogin("qw3qweq");
        nullAge.setPassword("12334566");
        nullAge.setAge(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(nullAge);
        });
    }

    @Test
    void register_NotValidData_NotOk() {
        User first = new User();
        first.setLogin("qw");
        first.setPassword("12");
        first.setAge(25);
        User second = new User();
        second.setLogin("qwer");
        second.setPassword("1234");
        second.setAge(24);
        User third = new User();
        third.setLogin("qwert");
        third.setPassword("12345");
        third.setAge(51);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(first);
            registrationService.register(second);
            registrationService.register(third);
        });
    }

    @Test
    void register_notValidLogin_validPassword_notOk() {
        User testUser = new User();
        testUser.setLogin("qw3");
        testUser.setPassword("12334566");
        testUser.setAge(25);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_notValidPassword_validLogin_notOk() {
        User testUser = new User();
        testUser.setLogin("ValidLogin");
        testUser.setPassword("123");
        testUser.setAge(21);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_validAge_Ok() {
        User first = new User();
        first.setLogin("CorrectLogin");
        first.setPassword("corRectPasss");
        first.setAge(18);
        registrationService.register(first);
        assertTrue(storageDao.get(first.getLogin()).getAge() >= 18,
                "User`s age should be 18 or more");
        User second = new User();
        second.setLogin("user2login");
        second.setPassword("secondpass");
        second.setAge(19);
        registrationService.register(second);
        assertEquals(2, Storage.people.size());
    }

    @Test
    void register_notValidAge_notOk() {
        User testUser = new User();
        testUser.setLogin("1qweqweww");
        testUser.setPassword("123213123");
        testUser.setAge(17);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_userWithSameLogin_notOk() {
        User first = new User();
        first.setLogin("firstL");
        first.setPassword("firstp");
        first.setAge(18);
        User second = new User();
        second.setLogin("secondlo");
        second.setPassword("secondpa");
        second.setAge(19);
        User third = new User();
        third.setLogin("thirdlogin");
        third.setPassword("thirdpass");
        third.setAge(25);
        Storage.people.add(first);
        Storage.people.add(second);
        Storage.people.add(third);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(second);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
