package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_PASSWORD = "111111";
    private static final String VALID_LOGIN = "test_user@gmail.com";
    private static final int VALID_AGE = 20;
    private static RegistrationService registrationService;
    private static User user;


    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        user.setLogin(VALID_LOGIN);
    }

    @Test
    void register_userAlreadyExistInStorage_notOk() {
        User existingUser = new User();
        existingUser.setPassword(VALID_PASSWORD);
        existingUser.setAge(VALID_AGE);
        existingUser.setLogin(VALID_LOGIN);
        Storage.people.add(existingUser);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageIsNull_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordIsNull_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_loginIsNull_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageIsLessThanMin_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageIsOverThanMax_notOk() {
        user.setAge(101);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordIsShort_notOk() {
        user.setPassword("00000");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordIsLong_notOk() {
        user.setPassword("123456789012345678901");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordIsLongerThanMin_Ok() {
        user.setPassword("1234567");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_ageIsOverMinimal_Ok() {
        user.setAge(22);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_loginIsUnique_Ok() {
        user.setLogin("real_unique_email@gmail.com");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @AfterEach
    void register_cleanStorage() {
        Storage.people.clear();
    }
}
