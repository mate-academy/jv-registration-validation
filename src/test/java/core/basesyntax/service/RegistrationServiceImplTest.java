package core.basesyntax.service;


import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegistrationServiceImplTest {

    private static RegistrationService registrationService;
    private User bob;

    @BeforeClass
    public static void beforeClass() {
        registrationService = new RegistrationServiceImpl();
    }

    @Before
    public void setUp() {
        Storage.people.clear();
        bob = new User();
        bob.setLogin("123");
        bob.setAge(19);
        bob.setPassword("1234567");
    }
    @Test
    public void register_userWithExistingLogin_notOk() {
        registrationService.register(bob);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    public void register_emptyLogin_notOk() {
        bob.setLogin("");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(bob);
        });
    }
    @Test
    public void register_nullLogin_notOk() {
        bob.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    public void register_ageUnder18_notOk() {
        bob.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(bob);
        });
    }
    @Test
    public void register_nullAge_notOk() {
        bob.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    public void register_passwordUnder6Characters_notOk() {
        bob.setPassword("12345");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    public void register_nullPassword_notOk() {
        bob.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    public void register_validUser_ok() {
        User expected = bob;
        User actual = registrationService.register(bob);
        assertEquals(expected, actual);
    }
}
