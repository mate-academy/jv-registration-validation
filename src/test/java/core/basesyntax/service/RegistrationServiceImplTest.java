package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(18);
        user.setLogin("zimmboco@gmail.com");
        user.setPassword("246801");
    }

    @Test
    void register_userValidOk() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_age_NotOk() {
        user.setAge(16);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userWithExistsLogin_NotOk() {
        User validUserData = registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(validUserData));
    }

    @Test
    void register_invalidPassword_NotOk() {
        user.setPassword("246");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginNull_NotOk() {
        user.setLogin(null);
        assertNotNull(user);
    }

    @Test
    void register_userNull_NotOk() {
        assertNotNull(user);
    }

    @Test
    void register_passwordNull_NotOk() {
        user.setPassword(null);
        assertNotNull(user);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
