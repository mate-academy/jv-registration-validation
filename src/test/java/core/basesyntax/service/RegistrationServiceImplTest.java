package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private User user = new User();

    @Test
    void registrationUser_PasswordLengthEdge_isOk() {
        user.setPassword("123456");
        user.setLogin("academy");
        user.setAge(18);
        registrationService.register(user);
        int expected = 1;
        int actual = Storage.people.size();
        assertEquals(expected, actual);
    }

    @Test
    void registrationUser_PasswordLength_notOk() {
        user.setPassword("12345");
        user.setLogin("123456");
        user.setAge(18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registrationUser_LoginLengthEdge_isOk() {
        user.setPassword("123456");
        user.setLogin("Developer");
        user.setAge(18);
        registrationService.register(user);
        int expected = 2;
        int actual = Storage.people.size();
        assertEquals(expected, actual);
    }

    @Test
    void registrationUser_LoginLength_notOk() {
        user.setPassword("123456");
        user.setLogin("12345");
        user.setAge(18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registrationUser_Age_isOk() {
        user.setPassword("123456");
        user.setLogin("Mate2024");
        user.setAge(18);
        registrationService.register(user);
        int expected = 3;
        int actual = Storage.people.size();
        assertEquals(expected, actual);
    }

    @Test
    void registrationUser_Age_notOk() {
        user.setPassword("123456");
        user.setLogin("Mate2024");
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registrationUser_PasswordIsNull_notOk() {
        user.setAge(18);
        user.setLogin("academy");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registrationUser_LoginIsNull_notOk() {
        user.setPassword("123456");
        user.setAge(18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registrationUser_LoginAlreadyExist_notOk() {
        user.setPassword("123456");
        user.setLogin("academy");
        user.setAge(18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}
