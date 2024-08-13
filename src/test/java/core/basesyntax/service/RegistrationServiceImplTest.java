package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User(2L, "my_login", "123456", 25);
        Storage.people.addAll(Arrays.asList(new User(5L, "petro34", "123679", 21),
                new User(4L, "oleksandr9", "5328409", 18),
                new User(8L, "semen56", "653hddxe", 19),
                new User(9L, "pavlo676", "50ujgda", 22),
                new User(7L, "my_login2", "705jpteq", 25)));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyPassword_notOk() {
        user.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_minimumLength_ofLogin_notOk() {
        user.setLogin("login");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_minimumLength_ofPassword_notOk() {
        user.setPassword("1234");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_belowAge_notOk() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shouldAddUserToStorage_isOk() {
        registrationService.register(user);
        User registeredUser = Storage.people.stream()
                .filter(u -> u.getLogin().equals(user.getLogin()))
                .findFirst()
                .orElse(null);
        assertTrue(Storage.people.contains(user));
        assertNotNull(registeredUser);
        assertEquals(registeredUser.getId(), user.getId());
        assertEquals(registeredUser.getLogin(), user.getLogin());
        assertEquals(registeredUser.getPassword(), user.getPassword());
        assertEquals(registeredUser.getAge(), user.getAge());
    }

    @Test
    void register_loginAlreadyExists_notOk() {
        User newUser = new User(5L, "my_login", "1234567", 19);
        Storage.people.add(newUser);
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
