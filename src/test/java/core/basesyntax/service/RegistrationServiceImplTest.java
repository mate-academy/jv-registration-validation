package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        user.setLogin("user");
        user.setPassword("psswrd");
        user.setAge(18);
    }

    @Test
    void register_allDataCorrect_ok() {
        User testUser = registrationService.register(user);
        assertEquals(user, testUser);
    }

    @Test
    void register_existingUser_notOk() {
        Storage.people.add(user);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(NullPointerException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword("");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(NullPointerException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(NullPointerException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_less18YearsAge_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_addUserToStorage_ok() {
        int oldSize = Storage.people.size();
        registrationService.register(user);
        assertEquals(oldSize + 1, Storage.people.size());
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
