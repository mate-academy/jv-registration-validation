package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private StorageDaoImpl storage;
    private RegistrationService registrationService;

    @BeforeEach
    public void setUp() {
        storage = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    public void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("password");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("user");
        user.setPassword(null);
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("user");
        user.setPassword("password");
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("user");
        user.setPassword("password");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin("username");
        user.setPassword("pass");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_underageUser_notOk() {
        User user = new User();
        user.setLogin("username");
        user.setPassword("password");
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_existingUser_notOk() {
        User user1 = new User();
        user1.setLogin("username");
        user1.setPassword("password");
        user1.setAge(20);
        registrationService.register(user1);
        User user2 = new User();
        user2.setLogin("username");
        user2.setPassword("newpassword");
        user2.setAge(21);
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
    }

    @Test
    public void register_validUser_ok() {
        User user = new User();
        user.setLogin("username1");
        user.setPassword("password");
        user.setAge(20);
        registrationService.register(user);
        assertNotNull(storage.get("username1"));
    }
}
