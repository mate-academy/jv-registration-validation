package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.NotValidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void init() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.clear();
    }

    @Test
    void register_User_Ok() {
        User user = new User("User1234", "1234567", 19);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_NullUser_notOk() {
        assertThrows(NotValidDataException.class, () -> registrationService.register(null));
    }

    @Test
    void register_ExistsUser_notOk() {
        User user = new User("User123", "123456", 18);
        Storage.PEOPLE.add(user);
        User user2 = new User("User123", "2345643", 23);
        assertThrows(NotValidDataException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_NullLogin_notOk() {
        User user = new User(null, "123456", 18);
        assertThrows(NotValidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_IncorrectLogin_notOk() {
        User user = new User("user","123456",18);
        assertThrows(NotValidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullPassword_notOk() {
        User user = new User("User12", null, 19);
        assertThrows(NotValidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_IncorrectPassword_notOk() {
        User user = new User("User12", "12", 19);
        assertThrows(NotValidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullAge_notOk() {
        User user = new User("User12", "123456", null);
        assertThrows(NotValidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_IncorrectAge_notOk() {
        User user = new User("User12", "123456", 10);
        assertThrows(NotValidDataException.class, () -> registrationService.register(user));
    }
}
