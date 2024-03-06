package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.CustomException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class HelloWorldTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private Storage storage = new Storage();

    @Test
    void isSmallPasswordThrowsException_OK() {
        User user = new User();
        user.setPassword("12332");
        user.setLogin("testlog");
        user.setAge(19);
        assertThrows(CustomException.class, () -> registrationService.register(user));
    }

    @Test
    void isSmallLoginLengthThrowsException_OK() {
        User user = new User();
        user.setPassword("123321");
        user.setLogin("test");
        user.setAge(19);
        assertThrows(CustomException.class, () -> registrationService.register(user));
    }

    @Test
    void isSmallAgeThrowsException_OK() {
        User user = new User();
        user.setPassword("123321");
        user.setLogin("testlog");
        user.setAge(2);
        assertThrows(CustomException.class, () -> registrationService.register(user));
    }

    @Test
    void nullAge_notOK() {
        User user = new User();
        user.setPassword("123321");
        user.setLogin("testlog");
        user.setAge(null);
        assertThrows(CustomException.class, () -> registrationService.register(user));
    }

    @Test
    void nullPasswordAndLogin_notOk() {
        User user = new User();
        user.setPassword("123321");
        user.setLogin(null);
        user.setAge(19);
        assertThrows(CustomException.class, () -> registrationService.register(user));
        user.setPassword(null);
        user.setLogin("testlog");
        assertThrows(CustomException.class, () -> registrationService.register(user));
    }

    @Test
    void correctDataNotThrowsException_OK() {
        User user = new User();
        user.setPassword("123321");
        user.setLogin("testlog");
        user.setAge(19);
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void correctDataAddsToList_OK() {
        User user = new User();
        user.setPassword("123321");
        user.setLogin("testlog");
        user.setAge(19);
        for (int i = 0; i < storage.people.size(); i++) {
            assertEquals(user, storage.people.get(i));
        }
    }
}

