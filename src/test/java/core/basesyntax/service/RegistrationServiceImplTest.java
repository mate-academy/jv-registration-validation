package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        user = new User("Bober","Abracadabra",27);
    }

    @Test
    void register_newUser_Ok() {
        User newUser = registrationService.register(user);
        assertEquals(user,newUser);
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(-18);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_alreadyExistUser_notOk() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_validAge_Ok() {
        user.setAge(56);
        assertTrue(user.getAge() >= 18);
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_lowerCasePassword_notOk() {
        user.setPassword("qwerty");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_blankPassword_notOk() {
        user.setPassword("    ");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_blankLogin_OK() {
        user.setLogin("  ");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
