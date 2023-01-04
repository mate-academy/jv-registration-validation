package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
        user.setPassword("123456");
        user.setAge(18);
        user.setLogin("test@mail");
    }

    @Test
    void register_ageLessThenMinimal_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageIsNegative_notOk() {
        user.setAge(-17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageIsMoreThenMinimal_Ok() {
        user.setAge(19);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_ageIsMinimal_Ok() {
        user.setAge(18);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_ageIsNull_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordIsShort_notOk() {
        user.setAge(20);
        user.setPassword("123");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordIsCorrect_Ok() {
        user.setPassword("qwerty");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_passwordIsNull_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_login_IsNull_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userAlreadyInStorage() {
        User secondUser = new User();
        secondUser.setLogin("test@mail");
        secondUser.setPassword("123456");
        secondUser.setAge(18);
        Storage.people.add(secondUser);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @AfterEach
    void cleanStorage() {
        Storage.people.clear();
    }
}
