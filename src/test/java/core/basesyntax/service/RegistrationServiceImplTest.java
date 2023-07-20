package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        user.setId(21L);
        user.setLogin("bobius");
        user.setPassword("qwerty");
        user.setAge(23);
    }

    @Test
    void register_nullUser_notOk() {
        user.setId(null);
        user.setLogin(null);
        user.setPassword(null);
        user.setAge(null);
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageLessThanMinimum_notOk() {
        user.setAge(12);
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        user.setLogin("teddy");
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword("short");
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }

    @Test
    void register_addExistingUser_notOk() {
        Storage.people.add(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_existingUser_notOk() {
        Storage.people.add(user);
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }

    @Test
    void successfulRegistration() {
        User registeredUser = registrationService.register(user);
        assertEquals(user, registeredUser);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
