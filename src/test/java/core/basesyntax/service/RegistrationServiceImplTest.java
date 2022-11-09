package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.*;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int USER_OVER_MAX_AGE = 125;
    private static final int USER_UNDER_MIN_AGE = 17;
    private static User user;
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        StorageDao storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
        user = new User();
        user.setId(3478700L);
        user.setLogin("newUserLogin");
        user.setPassword("userStrongPassword");
        user.setAge(20);
    }

    @Test
    void registerValidUser_Ok() {
        User actual = registrationService.register(user);
        User expected = user;
        assertEquals(expected, actual);
    }

    @Test
    void registerNullUser_NotOk() {
        User nullUser = new User();
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(nullUser);
        });
    }

    @Test
    void registerUserNullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerSameUserTwice_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
            registrationService.register(user);
        });
    }

    @Test
    void registerUserAgeUnder18_NotOk() {
        user.setAge(USER_UNDER_MIN_AGE);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerUserAgeUnder0_NotOk() {
        user.setAge(-15);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerUserAgeOver120_NotOk() {
        user.setAge(USER_OVER_MAX_AGE);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerUserAgeNull_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerUserShortPassword_NotOk() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerUserNullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }
}
