package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static RegistrationService registrationService = new RegistrationServiceImpl();

    @AfterEach
    public void clearStorage() {
        Storage.people.clear();
    }

    @Test
    public void register_validUser_ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("123456");
        user.setAge(18);

        User result = registrationService.register(user);

        assertEquals(user, result);
        assertEquals(user, Storage.people.get(0));
        assertEquals(1, Storage.people.size());
    }

    @Test
    public void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("123456");
        user.setAge(null);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword(null);
        user.setAge(18);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("123456");
        user.setAge(18);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_underAge_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("123456");
        user.setAge(17);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("valid");
        user.setPassword("123456");
        user.setAge(18);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("12345");
        user.setAge(18);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_loginAlreadyExists_notOk() {
        User storedUser = new User();
        storedUser.setLogin("validLogin");
        storedUser.setPassword("123456");
        storedUser.setAge(18);
        Storage.people.add(storedUser);

        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("123456");
        user.setAge(18);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    public void register_emptyPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("");
        user.setAge(18);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_passwordLengthThree_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("123");
        user.setAge(18);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_passwordLengthSix_ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("123456");
        user.setAge(18);

        User result = registrationService.register(user);

        assertEquals(user, result);
        assertEquals(user, Storage.people.get(0));
        assertEquals(1, Storage.people.size());
    }

    @Test
    public void register_passwordLengthEight_ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("12345678");
        user.setAge(18);

        User result = registrationService.register(user);

        assertEquals(user, result);
        assertEquals(user, Storage.people.get(0));
        assertEquals(1, Storage.people.size());
    }

    @Test
    public void register_negativeAge_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("123456");
        user.setAge(-1);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_overMinAge_ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("123456");
        user.setAge(19);

        User result = registrationService.register(user);

        assertEquals(user, result);
        assertEquals(user, Storage.people.get(0));
        assertEquals(1, Storage.people.size());
    }
}
