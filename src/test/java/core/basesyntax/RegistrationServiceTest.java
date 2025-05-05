package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static User validUser;
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeAll
    public static void setUp() {
        validUser = new User();
        validUser.setLogin("Login6");
        validUser.setAge(18);
        validUser.setPassword("123456");
    }

    @AfterEach
    public void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_validUser_Ok() {
        assertNotNull(validUser);
    }

    @Test
    void register_NullUser_NotOk() {
        User user = null;
        assertThrows(IllegalArgumentException.class, () -> registrationService.register(user));
    }

    @Test
    void register_existsUser_NotOk() {
        User user1 = new User();
        user1.setLogin("NewUser");
        user1.setAge(25);
        user1.setPassword("123456");

        User user2 = new User();
        user2.setLogin("NewUser");
        user2.setAge(45);
        user2.setPassword("password123");
        registrationService.register(user1);
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_lowAgeUser_notOk() {
        validUser.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));

        validUser.setAge(10);
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));

        validUser.setAge(-20);
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @Test
    void register_nullPassword_NotOk() {
        validUser.setPassword(null);

        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @Test
    void register_belowMinimumPassword_NotOk() {
        validUser.setPassword("12345");
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @Test
    void register_MinimumUserPasswordLength_Ok() {
        User user = new User();
        user.setLogin("UserMinPasswrd");
        user.setAge(24);
        user.setPassword("123456");

        assertEquals(user, registrationService.register(user));
    }
}
