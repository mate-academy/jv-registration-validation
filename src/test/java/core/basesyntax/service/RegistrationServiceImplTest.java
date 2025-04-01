package core.basesyntax.service;

import core.basesyntax.exceptions.RegistrationFailedException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullUserAge_notOk() {
        assertThrows(RegistrationFailedException.class, () -> registrationService
                .register(new User("JohnDoe1995", "john5405", null)));
    }

    @Test
    void register_userAge_notOk() {
        assertThrows(RegistrationFailedException.class, () -> registrationService
                .register(new User("JohnDoe1995", "john5405", 15)));
    }

    @Test
    void register_nullUserPassword_notOk() {
        assertThrows(RegistrationFailedException.class, () -> registrationService
                .register(new User("JohnDoe1995", null, 25)));
    }

    @Test
    void register_userPassword_notOk() {
        assertThrows(RegistrationFailedException.class, () -> registrationService
                .register(new User("JohnDoe1995", "gdf3", 25)));
    }

    @Test
    void register_nullUserLogin_notOk() {
        assertThrows(RegistrationFailedException.class, () -> registrationService
                .register(new User(null, "john5405", 25)));
    }

    @Test
    void register_userLogin_notOk() {
        assertThrows(RegistrationFailedException.class, () -> registrationService
                .register(new User("john", "john5405", 25)));
    }

    @Test
    void register_userLoginDuplicate_notOk() {
        registrationService.register(new User("JaneDoe24", "john5405", 25));
        assertThrows(RegistrationFailedException.class, () -> registrationService
                .register(new User("JaneDoe24", "john5405", 25)));
    }

    @Test
    void register_user_oK() {
        User actual = registrationService
                .register(new User("JohnDoe1995", "john5405", 25));
        User expected = new User("JohnDoe1995", "john5405", 25);
        assertEquals(expected, actual);
    }

    @Test
    void register_multipleUsers_oK() {
        registrationService.register(new User("TestLogin1", "testPassword1", 21));
        registrationService.register(new User("TestLogin2", "testPassword1", 75));
        registrationService.register(new User("TestLogin3", "testPassword1", 18));
        registrationService.register(new User("TestLogin4", "testPassword1", 53));
        User actual = registrationService.register(new User("TestLogin5", "testPassword1", 75));
        User expected = new User("TestLogin5", "testPassword1", 75);
        assertEquals(expected, actual);
    }
}