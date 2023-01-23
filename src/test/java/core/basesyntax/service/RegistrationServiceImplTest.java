package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
        user.setPassword("111111");
        user.setAge(20);
        user.setLogin("test_user@gmail.com");
    }

    @Test
    void register_userAlreadyExistInStorage() {
        User existingUser = new User();
        existingUser.setPassword("111111");
        existingUser.setAge(20);
        existingUser.setLogin("test_user@gmail.com");
        Storage.people.add(existingUser);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void ageIsNull_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void passwordIsNull_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void loginIsNull_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void ageIsLessThanMin_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void ageIsOverThanMax_notOk() {
        user.setAge(101);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void passwordIsShort_notOk() {
        user.setPassword("00000");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void passwordIsLong_notOk() {
        user.setPassword("123456789012345678901");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void passwordIsLongerThanMin_Ok() {
        user.setPassword("1234567");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void ageIsOverMinimal_Ok() {
        user.setAge(22);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void loginIsUnique_Ok() {
        user.setLogin("real_unique_email@gmail.com");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @AfterEach
    void cleanStorage() {
        Storage.people.clear();
    }
}
