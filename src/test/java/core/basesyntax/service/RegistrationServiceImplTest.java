package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int USER_OVER_MAX_AGE = 125;
    private static final int USER_UNDER_MIN_AGE = 17;
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static User user;

    @BeforeAll
    public static void beforeAll() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    private User defaultUser2Supplier() {
        User user2 = new User();
        user2.setId(3478320L);
        user2.setLogin("newUser2Login");
        user2.setPassword("user2StrongPassword");
        user2.setAge(24);
        return user2;
    }

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(3478700L);
        user.setLogin("newUserLogin");
        user.setPassword("userStrongPassword");
        user.setAge(20);
    }

    @Test
    public void register_ValidUser_Ok() {
        User actual = registrationService.register(defaultUser2Supplier());
        User expected = defaultUser2Supplier();
        assertEquals(expected, actual);
    }

    @Test
    public void register_NullUser_NotOk() {
        User nullUser = new User();
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(nullUser);
        });
    }

    @Test
    public void register_UserNullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_SameUserTwice_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
            registrationService.register(user);
        });
    }

    @Test
    public void register_UserAgeUnderMinAge_NotOk() {
        user.setAge(USER_UNDER_MIN_AGE);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_UserWithNegativeAge_NotOk() {
        user.setAge(-15);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_UserAgeOverMaxAge_NotOk() {
        user.setAge(USER_OVER_MAX_AGE);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_UserAgeNull_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_UserShortPassword_NotOk() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_UserNullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }
}
