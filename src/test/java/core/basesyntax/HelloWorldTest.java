package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class HelloWorldTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static User user;
    private static User differentUser;
    private static User sameUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        user = new User();
        differentUser = new User();
        sameUser = new User();
    }

    @AfterEach
    void tearDown() {
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user.setId(3L);
        user.setLogin("User");
        user.setPassword("1234567");
        user.setAge(18);

        differentUser.setId(123L);
        differentUser.setLogin("differentUser");
        differentUser.setPassword("123456");
        differentUser.setAge(29);

        sameUser.setId(3L);
        sameUser.setLogin("User");
        sameUser.setPassword("1234567");
        sameUser.setAge(18);
    }

    @Test
    void register_NullLogin_NotOk() {
        user.setLogin(null);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_NullInput_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_NullPassword_NotOk() {
        user.setPassword(null);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_NullAge_NotOk() {
        user.setAge(null);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_NullId_NotOk() {
        user.setId(null);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserNegativeId_NotOk() {
        user.setId(-11L);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_DifferentUsers_Ok() {
        boolean differentLogin = user.getLogin()
                .equals(differentUser.getLogin());
        assertFalse(differentLogin);

        storageDao.add(user);
        storageDao.add(differentUser);
        assertEquals(user,storageDao.get(user.getLogin()));
        assertEquals(differentUser, storageDao.get(differentUser.getLogin()));
    }

    @Test
    void register_TwoSameUsers_NotOk() {
        boolean sameLogin = user.getLogin().equals(sameUser.getLogin());
        assertTrue(sameLogin);
        storageDao.add(user);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(sameUser);
        });
        assertEquals(user,storageDao.get(user.getLogin()));
    }

    @Test
    void register_UserCorrectAge_Ok() {
        user.setAge(33);

        storageDao.add(user);
    }

    @Test
    void register_UserAgeLessThanNeed_NotOk() {
        user.setAge(17);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserAgeEqualsMinRequirement_Ok() {
        user.setAge(18);

        storageDao.add(user);
    }

    @Test
    void register_UserNegativeAge_NotOk() {
        user.setAge(-20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_PasswordLengthIncorrect_NotOk() {
        user.setPassword("1234");

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void checkTheSameUsersForEquals_Ok() {
        boolean actual = user.equals(sameUser);
        assertTrue(actual);
    }

    @Test
    void checkDifferentUsersForEquals_NotOk() {
        boolean actual = user.equals(differentUser);
        assertFalse(actual);
    }

    @Test
    void checkTheSameUsersForHashCode_Ok() {
        int userHashCode = user.hashCode();
        int sameUserHashCode = sameUser.hashCode();
        assertEquals(userHashCode, sameUserHashCode);
    }

    @Test
    void checkDifferenceUsersForHashCode_NotOk() {
        int userHashCode = user.hashCode();
        int differentUserHashCode = differentUser.hashCode();
        assertNotEquals(userHashCode, differentUserHashCode);
    }
}
