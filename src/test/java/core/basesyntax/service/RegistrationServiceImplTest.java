package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        User user1 = new User();
        user1.setLogin("FirstUser");
        user1.setAge(34);
        user1.setPassword("45jisf3");
        User user2 = new User();
        user2.setLogin("SecondUser");
        user2.setAge(18);
        user2.setPassword("newark");
        User user3 = new User();
        user3.setLogin("ThirdUser");
        user3.setAge(28);
        user3.setPassword("vo*edekmybr");
        storageDao.add(user1);
        storageDao.add(user2);
        storageDao.add(user3);
    }

    @Test
    void register_uniqueLogin_ok() {
        User user = new User();
        user.setLogin("FourthUser");
        user.setAge(26);
        user.setPassword("654gh891");
        User actual;
        try {
            actual = registrationService.register(user);
        } catch (InvalidDataException e) {
            throw new RuntimeException("User must be registered");
        }
        User expected = storageDao.get(user.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    void register_nullUser_notOk() {
        User user = null;
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_existingLogin_notOk() {
        User user = new User();
        user.setLogin("ThirdUser");
        user.setAge(19);
        user.setPassword("thirdpass");
        try {
            registrationService.register(user);
        } catch (InvalidDataException e) {
            return;
        }
        fail("InvalidDataException should be thrown if user with such login is already exist");
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setAge(65);
        user.setPassword("biscuit");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("FifthUser");
        user.setAge(45);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_invalidPassword_notOk() {
        User user = new User();
        user.setLogin("SixthUser");
        user.setAge(65);
        user.setPassword("pssw");
        try {
            registrationService.register(user);
        } catch (InvalidDataException e) {
            return;
        }
        fail("InvalidDataException should be thrown if login is less than 6 characters");
    }

    @Test
    void register_validPasswordWithRequiredLength_ok() {
        User user = new User();
        user.setLogin("SeventhUser");
        user.setAge(36);
        user.setPassword("123456");
        User actual;
        try {
            actual = registrationService.register(user);
        } catch (InvalidDataException e) {
            throw new RuntimeException("User must be registered");
        }
        User expected = storageDao.get(user.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("EighthUser");
        user.setPassword("poiuyt8");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_negativeValueOfAge_notOk() {
        User user = new User();
        user.setLogin("NinthUser");
        user.setAge(-18);
        user.setPassword("tyrplage");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });

    }

    @Test
    void register_ageLessThenRequire_notOk() {
        User user = new User();
        user.setLogin("TenthUser");
        user.setAge(15);
        user.setPassword("jhu96eul");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageEqualsRequiredValue_ok() {
        User user = new User();
        user.setLogin("EleventhUser");
        user.setAge(18);
        user.setPassword("5554321");
        User actual;
        try {
            actual = registrationService.register(user);
        } catch (InvalidDataException e) {
            throw new RuntimeException("User must be registered");
        }
        User expected = storageDao.get(user.getLogin());
        assertEquals(expected, actual);
    }
}
