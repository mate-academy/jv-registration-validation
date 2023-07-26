package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistationServiceTest {
    private static RegistrationService registerServiceTest;
    private static StorageDaoImpl storageDao;

    @BeforeEach
    void initService() {
        Storage.people.clear();
        registerServiceTest = new RegistrationServiceImpl();
    }

    @Test
    void register_isNullUser_notOk() {
        User nullUser = null;
        assertThrows(NullPointerException.class, () -> {
            registerServiceTest.register(nullUser);
        });
    }

    @Test
    void register_isNullLogin_notOk() {
        User actualUser = new User(null, "655234322", 21);
        assertThrows(ValidationException.class, () -> {
            registerServiceTest.register(actualUser);
        });
    }

    @Test
    void register_isNullPassword_notOk() {
        User actualUser = new User("NickKarlosn", null, 43);
        assertThrows(ValidationException.class, () -> {
            registerServiceTest.register(actualUser);
        });
    }

    @Test
    void register_isNullAge_notOk() {
        User actualUser = new User("AlexFreedom", "543234113", null);
        assertThrows(ValidationException.class, () -> {
            registerServiceTest.register(actualUser);
        });
    }

    @Test
    void register_isEmptyLogin_notOk() {
        User actualUser = new User("", "3243234", 43);
        assertThrows(ValidationException.class, () -> {
            registerServiceTest.register(actualUser);
        });
    }

    @Test
    void register_isEmptyPassword_notOk() {
        User actualUser = new User("LizaVavilova", "", 24);
        assertThrows(ValidationException.class, () -> {
            registerServiceTest.register(actualUser);
        });
    }

    @Test
    void register_isLogin_3_Length_notOk() {
        User actualUser = new User("Bob", "4385482", 43);
        assertThrows(ValidationException.class, () -> {
            registerServiceTest.register(actualUser);
        });
    }

    @Test
    void register_isLogin_5_Length_notOk() {
        User actualUser = new User("BobAl", "4385482", 43);
        assertThrows(ValidationException.class, () -> {
            registerServiceTest.register(actualUser);
        });
    }

    @Test
    void register_isLogin_6_Length_is_Ok() {
        User actualUser = new User("BobAly", "4385482", 43);
        registerServiceTest.register(actualUser);
        boolean expected = Storage.people.contains(actualUser);
        assertTrue(expected);
    }

    @Test
    void register_isLogin_8_Length_is_Ok() {
        User actualUser = new User("BobAlyni", "4385482", 43);
        registerServiceTest.register(actualUser);
        boolean expected = Storage.people.contains(actualUser);
        assertTrue(expected);
    }

    @Test
    void register_isPasswordLength_3_notOk() {
        User actualUser = new User("YuliaMikovskay", "324", 56);
        assertThrows(ValidationException.class, () -> {
            registerServiceTest.register(actualUser);
        });
    }

    @Test
    void register_isPasswordLength_5_notOk() {
        User actualUser = new User("AlenTurke", "32435", 43);
        assertThrows(ValidationException.class, () -> {
            registerServiceTest.register(actualUser);
        });
    }

    @Test
    void register_isPasswordLength_6_is_Ok() {
        User actualUser = new User("AlenTurke", "324353", 43);
        registerServiceTest.register(actualUser);
        boolean expected = Storage.people.contains(actualUser);
        assertTrue(expected);
    }

    @Test
    void register_isPasswordLength_8_is_Ok() {
        User actualUser = new User("AlenTurke", "32435334", 43);
        registerServiceTest.register(actualUser);
        boolean expected = Storage.people.contains(actualUser);
        assertTrue(expected);
    }

    @Test
    void register_isAgeLessThan_18_not_Ok() {
        User actualUser = new User("AlenTurke", "324353", 17);
        assertThrows(ValidationException.class, () -> {
            registerServiceTest.register(actualUser);
        });
    }

    @Test
    void register_isAge_18_is_Ok() {
        User actualUser = new User("AlenTurke", "324353", 18);
        registerServiceTest.register(actualUser);
        boolean expected = Storage.people.contains(actualUser);
        assertTrue(expected);
    }

    @Test
    void register_isAge_24_is_Ok() {
        User actualUser = new User("AlenTurke", "324353", 24);
        registerServiceTest.register(actualUser);
        boolean expected = Storage.people.contains(actualUser);
        assertTrue(expected);
    }

    @Test
    void register_isLoginStartWithNumbers_notOk() {
        User actualUser = new User("4LizaValencia", "43243234", 54);
        assertThrows(ValidationException.class, () -> {
            registerServiceTest.register(actualUser);
        });
    }

    @Test
    void registerData_getUserByLogin_notOk() {
        User user1 = new User("FillCurrent", "12345678", 23);
        User user2 = new User("JhonMelkavich", "987654321", 34);
        StorageDaoImpl storageDao = new StorageDaoImpl();
        storageDao.add(user1);
        storageDao.add(user2);
        String loginUser = "YuilaMelek";
        boolean actual = storageDao.isContainsUserInList(loginUser);
        assertFalse(actual);
    }

    @Test
    void registerData_getUserByLoginCorrect_Ok() {
        User user1 = new User("FillCurrent", "12345678", 23);
        User user2 = new User("JhonMelkavich", "987654321", 34);
        StorageDaoImpl storageDao = new StorageDaoImpl();
        storageDao.add(user1);
        storageDao.add(user2);
        String loginUser = "FillCurrent";
        boolean actual = storageDao.isContainsUserInList(loginUser);
        assertTrue(actual);
    }

    @Test
    void registerData_registerSameUserAlreadyRegisted_notOk() {
        User user1 = new User("FillCurrent", "12345678", 23);
        registerServiceTest.register(user1);
        assertThrows(ValidationException.class, () -> {
            registerServiceTest.register(user1);
        });
    }
}
