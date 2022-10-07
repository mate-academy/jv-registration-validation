package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService testRegistrationService;
    private User testUser;

    @BeforeAll
    static void atStartOnce() {        
        testRegistrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setAge(23);
        testUser.setLogin("SoulARC");
        testUser.setPassword("qwerty");
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void register_nonExistentUser_ok() {
        assertEquals(testUser, testRegistrationService.register(testUser));
    }

    @Test
    void register_nullUser_notOk() {
        testUser = null;
        assertThrows(RuntimeException.class, ()
                -> testRegistrationService.register(testUser));
    }

    @Test
    void register_existingUsername_notOk() {
        Storage.people.add(testUser);
        assertThrows(RuntimeException.class, ()
                -> testRegistrationService.register(testUser));
    }

    @Test
    void register_nullLogin_notOk() {
        testUser.setLogin(null);
        assertThrows(RuntimeException.class, ()
                -> testRegistrationService.register(testUser));
    }

    @Test
    void register_emptyLogin_notOk() {
        testUser.setLogin("");
        assertThrows(RuntimeException.class, ()
                -> testRegistrationService.register(testUser));
    }

    @Test
    void register_shortPassword_notOk() {
        testUser.setPassword("55555");
        assertThrows(RuntimeException.class, ()
                -> testRegistrationService.register(testUser));
    }

    @Test
    void register_emptyPassword_notOk() {
        testUser.setPassword("");
        assertThrows(RuntimeException.class, ()
                -> testRegistrationService.register(testUser));
    }

    @Test
    void register_nullAge_notOk() {
        assertThrows(RuntimeException.class, ()
                -> testRegistrationService.register(null));
    }

    @Test
    void register_lowAge_notOk() {
        testUser.setAge(17);
        assertThrows(RuntimeException.class, ()
                -> testRegistrationService.register(testUser));
    }
}
