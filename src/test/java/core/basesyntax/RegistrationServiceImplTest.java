package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    //private Storage storage;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void trimDown() {
        Storage.PEOPLE.clear();
    }

    @Test
    void register_validUser_ok() {
        User userToRegistrate = new User("goodLogin", "goodPassword", 20);
        User registeredUser = registrationService.register(userToRegistrate);
        assertEquals(1, Storage.PEOPLE.size(), "User should be added to storage");
        assertEquals(userToRegistrate, registeredUser);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
        assertEquals(0, Storage.PEOPLE.size());
    }

    @Test
    void register_existingUsersLogin_notOk() {
        User user = new User("goodLogin", "goodPassword", 20);
        Storage.PEOPLE.add(user);
        User userToRegistrate = new User("goodLogin", "123456", 21);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userToRegistrate));
        assertEquals(1, Storage.PEOPLE.size(), "User shouldn't be added to storage");
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User(null, "goodPassword", 20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        assertEquals(0, Storage.PEOPLE.size());
    }

    @Test
    void register_invalidLoginLength_notOk() {
        User user1 = new User("bad", "goodPassword", 20);
        User user2 = new User("", "goodPassword", 20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        assertEquals(0, Storage.PEOPLE.size());
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User("goodLogin", null, 20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        assertEquals(0, Storage.PEOPLE.size());
    }

    @Test
    void register_invalidPasswordLength_notOk() {
        User user1 = new User("goodLogin", "passw", 20);
        User user2 = new User("goodLogin", "", 20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        assertEquals(0, Storage.PEOPLE.size());
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User("goodLogin", "goodPassword", null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        assertEquals(0, Storage.PEOPLE.size());
    }

    @Test
    void register_invalidAge_notOk() {
        User user1 = new User("goodLogin", "goodPassword", 0);
        User user2 = new User("goodLogin", "goodPassword", -20);
        User user3 = new User("goodLogin", "goodPassword", 17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        assertThrows(RegistrationException.class, () -> registrationService.register(user3));
        assertEquals(0, Storage.PEOPLE.size());
    }
}
