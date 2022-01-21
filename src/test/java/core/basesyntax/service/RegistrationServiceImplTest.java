package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

    @Test
    void register_NullUser_notOk() {
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        user.setLogin("Olga1509");
        user.setAge(19);
        user.setPassword(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        user.setAge(20);
        user.setPassword("12Qwerty");
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_emptyPassword_notOk() {
        user.setLogin("Alla1409");
        user.setAge(25);
        user.setPassword("");
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin("");
        user.setAge(34);
        user.setPassword("Qwerty19866");
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_notOk() {
        user.setLogin("Denis1765");
        user.setAge(0);
        user.setPassword("Qwerty1987");
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_negativeAge() {
        user.setLogin("Victor1698");
        user.setAge(-1);
        user.setPassword("Qwerty9898");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageLessThen_notOk() {
        user.setLogin("BoB212180");
        user.setAge(15);
        user.setPassword("qwerty99999");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordContainsLessThen_notOk() {
        user.setLogin("Den2029");
        user.setAge(30);
        user.setPassword("qwe");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_validDataUser_Ok() {
        User validDataUser = new User();
        validDataUser.setLogin("DEDE16081717");
        validDataUser.setAge(31);
        validDataUser.setPassword("Qwerty1234567");
        registrationService.register(validDataUser);
    }

    @Test
    void register_userIsExists_notOk() {
        User secondUser = new User();
        secondUser.setLogin("DEDE16081717");
        user.setAge(18);
        user.setPassword("1818Qwerty");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(secondUser);
        });
    }
}
