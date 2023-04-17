package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User testUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setLogin("testLogin");
        testUser.setPassword("testPassword");
        testUser.setAge(20);

    }

    @Test
    void register_userIs_Ok() {
        User registerUser = registrationService.register(testUser);
        assertSame(testUser, registerUser);
    }

    @Test
    void register_userIs_notOk() {
        User defaultUser = new User();
        defaultUser.setLogin("Bob");
        defaultUser.setPassword("000000");
        defaultUser.setAge(30);
        registrationService.register(defaultUser);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void register_nullLogin_notOk() {
        testUser.setLogin(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userAge_ok() {
        User actual = registrationService.register(testUser);
        assertEquals(testUser, actual);
    }

    @Test
    void register_userAge_notOk() {
        testUser.setAge(10);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void resister_nullAge_notOk() {
        testUser.setAge(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userPasswordLength_ok() {
        User actual = registrationService.register(testUser);
        assertEquals(testUser, actual);
    }

    @Test
    void register_nullPassword_notOk() {
        testUser.setPassword(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userPasswordLength_notOk() {
        testUser.setPassword("00000");
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }
}
