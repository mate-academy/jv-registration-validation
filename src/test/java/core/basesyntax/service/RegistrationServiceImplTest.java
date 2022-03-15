package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final RegistrationService registrationService = new RegistrationServiceImpl();
    private static User validUser;
    private static User invalidUser;

    @BeforeEach
    void setUp() {
        validUser = new User();
        invalidUser = new User();
        validUser.setAge(35);
        validUser.setLogin("Chuchu");
        validUser.setPassword("43jk43jk43");
        Storage.people.clear();
    }

    @Test
    void userRegister_OK() {
        registrationService.register(validUser);
        assertTrue(Storage.people.contains(validUser));
    }

    @Test
    void register_nullAge_notOk() {
        invalidUser.setLogin("test");
        invalidUser.setPassword("jk43");
        assertThrows(RuntimeException.class,() -> registrationService.register(invalidUser));
    }

    @Test
    void register_userAgeLessThan_18_notOk() {
        invalidUser.setAge(2);
        invalidUser.setLogin("test");
        invalidUser.setPassword("jk4ygtr3");
        assertThrows(RuntimeException.class,() -> registrationService.register(invalidUser));
    }

    @Test
    void register_passwordLessThan_6_notOk() {
        invalidUser.setAge(20);
        invalidUser.setLogin("test");
        invalidUser.setPassword("jtr3");
        assertThrows(RuntimeException.class,() -> registrationService.register(invalidUser));
    }

    @Test
    void register_nullPassword_notOk() {
        invalidUser.setAge(40);
        invalidUser.setLogin("test");
        assertThrows(RuntimeException.class, () -> registrationService.register(invalidUser));
    }

    @Test
    void register_loginHasAlready_notOk() {
        invalidUser.setAge(22);
        invalidUser.setLogin("Chuchu");
        invalidUser.setPassword("343422rfr");
        registrationService.register(validUser);
        assertThrows(RuntimeException.class,() -> registrationService.register(invalidUser));
    }
}

