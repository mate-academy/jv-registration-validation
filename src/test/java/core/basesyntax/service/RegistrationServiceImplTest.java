
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
        user.setLogin("Evgenii");
        user.setAge(34);
        user.setPassword("1234567");
    }

    @AfterEach
    public void tearDown() {
        Storage.people.clear();
    }

    @Test
    public void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_blankLogin_notOk() {
        user.setLogin("   ");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void register_lessPasswordLength_notOk() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void register_lessAge_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void register_existLogin_notOk() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () ->
                    registrationService.register(user));
    }

    @Test
    public void register_userAll_ok() {
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }
}
