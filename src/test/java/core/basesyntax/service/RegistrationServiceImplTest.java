package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.AuthenticationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService = new RegistrationServiceImpl();
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("Bob");
        user.setAge(19);
        user.setId(191919191L);
        user.setPassword("crazyTrain");
    }

    @Test
    void register_validInput_ok() {
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_differentUsers_ok() {
        user.setLogin("firstUser");
        registrationService.register(user);
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_underAge_notOk() {
        user.setLogin("secondUser");
        user.setAge(10);
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordLessThanSixChar_notOk() {
        user.setLogin("thirdUser");
        user.setPassword("Ozzy");
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageIs18_Ok() {
        user.setLogin("fourthUser");
        user.setAge(18);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_passwordLengthIs6_ok() {
        user.setLogin("fifthUser");
        user.setPassword("qwerty");
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void userIsNull_notOk() {
        user = null;
        assertThrows(AuthenticationException.class, () -> {
            registrationService.register(user);
        });
    }
}
