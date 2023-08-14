package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private List<User> users;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        users = new ArrayList<>();
        User validUser = new User(45534554L, "ValidUser", "123412346", 18);
        users.add(validUser);
    }

    @Test
    void register_validUser_ok() {
        User actual = users.get(0);
        actual.setLogin("validUser");
        assertEquals(actual, registrationService.register(actual));
    }

    @Test
    void register_storageContainsUser_notOk() {
        registrationService.register(users.get(0));
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(0));
        });
    }

    @Test
    void register_loginLessThanSixChars_notOk() {
        users.get(0).setLogin("asdas");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(0));
        });
    }

    @Test
    void register_passwordLessThanSixChars_notOk() {
        users.get(0).setPassword("asdas");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(0));
        });
    }

    @Test
    void register_ageLessThanEighteen_notOk() {
        users.get(0).setAge(16);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(0));
        });
    }

    @Test
    void register_nullLogin_notOk() {
        users.get(0).setLogin(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(0));
        });
    }

    @Test
    void register_nullPassword_notOk() {
        users.get(0).setPassword(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(0));
        });
    }
}
