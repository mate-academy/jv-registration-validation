package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private User newUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        newUser = new User();
        newUser.setLogin("login");
        newUser.setPassword("password");
        newUser.setAge(18);
    }

    @Test
    void register_userIsNull_notOk() {
        newUser = null;
        assertThrows(NullPointerException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_userLoginIsNull_notOk() {
        newUser.setLogin(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_userLoginLengthTooShort_notOk() {
        newUser.setLogin("abc");
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_userLoginLengthTooLong_notOk() {
        newUser.setLogin("abcdefghijklmnopqrstuvwxyzABCDE");
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_userPasswordIsNull_notOk() {
        newUser.setPassword(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_userPasswordIsTooShort_notOk() {
        newUser.setPassword("abcde");
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_userPasswordIsTooLong_notOk() {
        newUser.setPassword("abcdefghijklmnopqrstuvwxyzABCDE");
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_userAgeIsNull_notOk() {
        newUser.setAge(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_userAgeIsNegative_notOk() {
        newUser.setAge(-1);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_userAgeIsZero_notOk() {
        newUser.setAge(0);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_userAgeIsTooSmall_notOk() {
        newUser.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_userAgeIsValid_ok() {
        int expectedSize = 0;
        int actualSize = Storage.people.size();
        assertEquals(expectedSize, actualSize);

        newUser.setAge(51);
        registrationService.register(newUser);

        expectedSize = 1;
        actualSize = Storage.people.size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void register_addUserAndAddUserAlreadyExists_notOk() {
        int expectedSize = 0;
        int actualSize = Storage.people.size();
        assertEquals(expectedSize, actualSize);

        registrationService.register(newUser);
        StorageDaoImpl storageDao = new StorageDaoImpl();
        User userFromStorage = storageDao.get(newUser.getLogin());
        assertEquals(newUser, userFromStorage);

        expectedSize = 1;
        actualSize = Storage.people.size();
        assertEquals(expectedSize, actualSize);

        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));

        assertEquals(expectedSize, actualSize);
    }
}
