package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exeptionforservice.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */

public class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static User actual;

    @BeforeAll
    public static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUser() {
        actual = new User();
    }

    @Test
    public void checkedLessAgeInInput() {
        actual.setAge(10);
        actual.setLogin("odsvidni345");
        actual.setPassword("sdvnnbnwij3");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual));
    }

    @Test
    public void lessLetterInLogin() {
        actual.setLogin("Bob3654");
        actual.setPassword("mynameisbob748");
        actual.setAge(19);
        try {
            assertFalse(registrationService
                    .countCaracterInPasswordAndLogin(actual.getLogin()));
        } catch (RegistrationException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void leesLetterInPassword() {
        actual.setLogin("hellobob23");
        actual.setPassword("bob2311");
        actual.setAge(20);
        try {
            assertFalse(registrationService
                    .countCaracterInPasswordAndLogin(actual.getPassword()));
        } catch (RegistrationException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void checkNullInputInUserPassword() {
        actual.setPassword(null);
        actual.setAge(20);
        actual.setLogin("boblogin245");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual),
                "Invalid input, password is not null null. Try again!");
    }

    @Test
    public void checkNullInputLogin() {
        actual.setLogin(null);
        actual.setAge(29);
        actual.setPassword("bobpassword34u8ty");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual),"Login is not null!");
    }

    @Test
    public void checkIncorectData() {
        actual.setPassword("32264r512");
        actual.setAge(27);
        actual.setLogin("994u2y522");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual),"All data was correct");
    }

    @Test
    public void registrationUser() throws RegistrationException {
        actual.setLogin("mylogin245");
        actual.setPassword("mypassword234");
        actual.setAge(18);
        User current = new User();
        current.setAge(actual.getAge());
        current.setPassword(actual.getPassword());
        current.setLogin(actual.getLogin());
        assertEquals(current,registrationService.register(actual));
    }

    @Test
    public void registrationWithNull() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual));
    }
}
