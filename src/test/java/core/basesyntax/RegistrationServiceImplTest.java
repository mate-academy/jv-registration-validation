package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void beforeEach() {
        user = new User();
        user.setLogin("BobBlink");
        user.setAge(25);
        user.setPassword("123456789");
    }

    @AfterEach
    void afterEach() {
        Storage.people.clear();
    }

    @Test
    void checkLoginIsNull_NotOk() {
        user.setLogin(null);
        checkException(user, "Login can't be NULL");
    }

    @Test
    void checkPasswordIsNull_NotOk() {
        user.setPassword(null);
        checkException(user, "Password can't be NULL");
    }

    @Test
    void checkAgeIsLessThanNormal_NotOk() {
        user.setAge(17);
        checkException(user, "Age can't be less than 18");
    }

    @Test
    void checkLoginTheSame_NotOk() {
        registrationService.register(user);
        checkException(user, "Login can't be the same");
    }

    @Test
    void checkLoginHuge_NotOk() {
        user.setLogin("BobBlinkBobBlinkBobBlink");
        checkException(user, "Login can't be bigger than 20 characters");
    }

    @Test
    void checkPasswordSmall_NotOk() {
        user.setPassword("12345");
        checkException(user, "Password can't be lower than 6 characters");
    }

    @Test
    void checkAddUser_Ok() {
        registrationService.register(user);
        assertEquals(user,storageDao.get(user.getLogin()), "User not added");
    }

    @Test
    void checkAgeIsNull_NotOk() {
        user.setAge(null);
        checkException(user, "Age can't be null");
    }

    @Test
    void checkUserIsNull_NotOk() {
        checkException(null, "User can't be NULL");
    }

    private void checkException(User user, String message) {
        assertThrows(RuntimeException.class, () -> registrationService.register(user), message);
    }
}
