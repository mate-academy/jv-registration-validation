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
    private User user;

    @BeforeAll
    public static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setLogin("userLogin");
        user.setPassword("userPassword");
        user.setAge(18);
    }

    @Test
    public void register_successfulRegistration_ok() {
        assertEquals(user, registrationService.register(user));
    }

    @Test
    public void register_nullUser_notOk() {
        user = null;
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    public void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_emptyLogin_notOk() {
        user.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_existingLogin_notOk() {
        registrationService.register(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_ageLessThenMinimal_notOk() {
        user.setAge(15);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_passwordShorterThenMinimal_notOk() {
        user.setPassword("userP");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @AfterEach
    public void tearDown() {
        Storage.people.clear();
    }
}
