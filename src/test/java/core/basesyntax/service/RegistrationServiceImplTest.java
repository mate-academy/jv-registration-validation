package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService = new RegistrationServiceImpl();
    private static User user = new User();

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user.setId(1L);
        user.setLogin("test@gmail.com");
        user.setPassword("1234556");
        user.setAge(18);
    }

    @Test
    void register_ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_check_years_error() {
        user.setAge(15);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_check_pass_error() {
        user.setPassword("1");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void check_null_user() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void check_user_id_null() {
        user.setId(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_check_years_null() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_check_pass_null() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}