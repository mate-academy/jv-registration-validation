package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private User userCorrect;
    private User userAgeLess18;
    private User userWrongPassword;
    private StorageDao storageDao = new StorageDaoImpl();
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        userCorrect = new User("userLogin", "password", 18);
        userAgeLess18 = new User("userLogin2", "password", 16);
        userWrongPassword = new User("userLogin2", "pas", 20);
    }

    @Test
    void userRegistered_ok() {
        User actualUser = new User("userLogin3", "password3", 19);
        registrationService.register(actualUser);
        assertEquals(storageDao.get(actualUser.getLogin()), actualUser);
    }

    @Test
    void passwordLengthLessThenSixSymbols_notOk() {
        try {
            registrationService.register(userWrongPassword);
        } catch (RuntimeException e) {
            return;
        }
        fail("test should fail -> password length less then 6 symbols");
    }

    @Test
    void userIsNull_notOk() {
        try {
            registrationService.register(null);
        } catch (RuntimeException e) {
            return;
        }
        fail("test should fail -> user is null");
    }

    @Test
    void userLoginIsNull_notOk() {
        User actual = new User(null, "password", 25);
        assertThrows(RuntimeException.class, () ->{
            registrationService.register(actual);
        });
    }

    @Test
    void userLoginIsEmpty_notOk() {
        User actual = new User("", "password", 25);
        assertThrows(RuntimeException.class, () ->{
            registrationService.register(actual);
        });
    }

    @Test
    void checkUserLoginWithSameLogin_notOk() {
        registrationService.register(userCorrect);
        try {
            registrationService.register(userCorrect);
        } catch (RuntimeException e) {
            return;
        }
        fail("test should fail -> this login exist in DB");
    }

    @Test
    void userPasswordIsNull_notOk() {
        User actual = new User("login", null, 25);
        assertThrows(RuntimeException.class, () ->{
            registrationService.register(actual);
        });
    }

    @Test
    void userPasswordIsEmpty_notOk() {
        User actual = new User("login", "", 25);
        assertThrows(RuntimeException.class, () ->{
            registrationService.register(actual);
        });
    }

    @Test
    void userAgeLessThen18() {
        try {
            registrationService.register(userAgeLess18);
        } catch (RuntimeException e) {
            return;
        }
        fail("Test should fall, your age is less then 18");
    }

    @Test
    void userWithNullAge() {
        User currentUser = new User("login", "password", null);
        assertThrows(RuntimeException.class, () ->{
            registrationService.register(currentUser);
        });
    }

    @Test
    void userWithZeroAge() {
        User currentUser = new User("login", "password", 0);
        assertThrows(RuntimeException.class, () ->{
            registrationService.register(currentUser);
        });
    }

    @Test
    void userWithNegativeAge() {
        User currentUser = new User("login", "password", -26);
        assertThrows(RuntimeException.class, () ->{
            registrationService.register(currentUser);
        });
    }
}
