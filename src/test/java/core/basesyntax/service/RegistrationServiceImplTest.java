package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
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
        user = new User();
        user.setAge(18);
        user.setLogin("Abobus");
        user.setPassword("aboba1");
    }

    @Test
    void registerWithNullAge_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerWithNullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerWithNullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerValidUser_Ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
        assertTrue(Storage.people.contains(actual));

    }

    @Test
    void registerNullUser_NotOk() {
        user = null;
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void containsValidUser_Ok() {
        User actual = user;
        registrationService.register(user);
        assertTrue(Storage.people.contains(actual)); //storage - static
    }

    @Test
    void registerWithExistingLogin_NotOk() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerWithInvalidAge_NotOk() {
        user.setAge(5);
        assertNull(registrationService.register(user));
    }

    @Test
    void registerWithInvalidPassword_NotOk() {
        user.setPassword("abob");
        assertNull(registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
