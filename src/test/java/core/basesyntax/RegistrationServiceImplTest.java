package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User bob;

    @BeforeClass
    public static void beforeClass() {
        registrationService = new RegistrationServiceImpl();
    }

    @Before
    public void setUp() {
        bob = new User();
        bob.setLogin("123");
        bob.setAge(19);
        bob.setPassword("1234567");
    }

    @After
    public void tearDown() {
        Storage.people.clear();
    }

    @Test
    public void register_userWithExistingLogin_notOk() {
        User alice = new User();
        alice.setLogin(bob.getLogin());
        registrationService.register(bob);
        assertThrows(RuntimeException.class, () -> {
           registrationService.register(alice);
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
    public void register_ageIs18_ok() {
        bob.setAge(18);
        int expected = 18;
        assertEquals(expected, (int) bob.getAge());
    }

    @Test
    public void register_ageOver18_ok() {
        bob.setAge(19);
        assertTrue(bob.getAge() > 18);
    }

    @Test
    public void register_negativeAge_notOk() {
        bob.setAge(-1);
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
    public void register_passwordIs6Characters_ok() {
        bob.setPassword("123456");
        registrationService.register(bob);
        int expected = 6;
        assertEquals(expected, bob.getPassword().length());
    }

    @Test
    public void register_passwordOver6Characters_ok() {
        registrationService.register(bob);
        assertTrue(bob.getPassword().length() > 6);
    }

    @Test
    public void register_nullPassword_notOk() {
        bob.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(bob);
        });
    }
}
