package core.basesyntax.service;

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
    private static User mickie = new User();
    private static User mouse = new User();

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        mickie.setLogin("Mickie");
        mickie.setPassword("123456");
        mickie.setAge(18);
        mouse.setLogin("Mouse");
        mouse.setPassword("123456");
        mouse.setAge(30);
    }

    @Test
    void register_invalidAge_NotOk() {
        mickie.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(mickie);
        });
        mouse.setAge(-19);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(mouse);
        });
    }

    @Test
    void register_invalidPassword_notOk() {
        mickie.setPassword("12345");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(mickie);
        });
    }

    @Test
    void register_theSameUserLogin_notOk() {
        registrationService.register(mickie);
        mouse.setLogin("Mickie");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(mouse);
        }, "Should throw an Exception: \"The user with such login already exists. "
                + "Please, enter another login.\n\"");
    }

    @Test
    void register_nullAge_NotOk() {
        mickie.setAge(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(mickie);
        });
    }

    @Test
    void register_nullPassword_NotOk() {
        mickie.setPassword(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(mickie);
        });
    }

    @Test
    void register_nullLogin_NotOk() {
        mickie.setLogin(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(mickie);
        });
    }

    @Test
    void register_validUser_Ok() {
        User actual1 = registrationService.register(mickie);
        assertTrue(Storage.people.contains(actual1));
        User actual2 = registrationService.register(mouse);
        assertTrue(Storage.people.contains(actual2));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
