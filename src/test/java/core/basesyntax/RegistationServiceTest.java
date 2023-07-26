package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        User actual = new User(null, "655234322", 21);
        assertThrows(ValidationException.class, () -> {
            registerServiceTest.register(actual);
        });
    }

    @Test
    void register_isNullPassword_notOk() {
        User actual = new User("NickKarlosn", null, 43);
        assertThrows(ValidationException.class, () -> {
            registerServiceTest.register(actual);
        });
    }

    @Test
    void register_isNullAge_notOk() {
        User actual = new User("AlexFreedom", "543234113", null);
        assertThrows(ValidationException.class, () -> {
            registerServiceTest.register(actual);
        });
    }

    @Test
    void register_isEmptyLogin_notOk() {
        User actual = new User("", "3243234", 43);
        assertThrows(ValidationException.class, () -> {
            registerServiceTest.register(actual);
        });
    }

    @Test
    void register_isEmptyPassword_notOk() {
        User actual = new User("LizaVavilova", "", 24);
        assertThrows(ValidationException.class, () -> {
            registerServiceTest.register(actual);
        });
    }

    @Test
    void register_isLoginLength_notOk() {
        User actual = new User("BobA", "4385482", 43);
        assertThrows(ValidationException.class, () -> {
            registerServiceTest.register(actual);
        });
    }

    @Test
    void register_isPasswordLength_notOk() {
        User actual = new User("YuliaMikovskay", "3244", 56);
        assertThrows(ValidationException.class, () -> {
            registerServiceTest.register(actual);
        });
    }

    @Test
    void register_isAgeLessThanRange_notOk() {
        User actual = new User("AlenTurke", "3243234", 17);
        assertThrows(ValidationException.class, () -> {
            registerServiceTest.register(actual);
        });
    }

    @Test
    void register_isLoginStartWithNumbers_notOk() {
        User actual = new User("4LizaValencia", "43243234", 54);
        assertThrows(ValidationException.class, () -> {
            registerServiceTest.register(actual);
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
