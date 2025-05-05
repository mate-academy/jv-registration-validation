package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_addUser_ok() {
        final User user1 = new User("login1", "111111", 18);
        final User user2 = new User("login22", "22222aB", 19);
        final User user3 = new User("login333", "Cd333333", 20);
        User firstAdd = registrationService.register(user1);
        assertEquals(user1, firstAdd);
        User secondAdd = registrationService.register(user2);
        assertEquals(user2, secondAdd);
        User thirdAdd = registrationService.register(user3);
        assertEquals(user3, thirdAdd);
        assertEquals(3, Storage.people.size());
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        final User userLoginNull = new User(null, "123456A", 23);
        assertThrows(RegistrationException.class, ()
                -> registrationService.register(userLoginNull));
    }

    @Test
    void register_shortLogin_notOk() {
        final User userLoginShort = new User("12345", "123abcD", 24);
        assertThrows(RegistrationException.class, ()
                -> registrationService.register(userLoginShort));
    }

    @Test
    void register_nullPassword_notOk() {
        final User userPasswordNull = new User("login21", null, 25);
        assertThrows(RegistrationException.class, ()
                -> registrationService.register(userPasswordNull));
    }

    @Test
    void register_shortPassword_notOk() {
        final User userPasswordShort = new User("login22", "abcdi", 26);
        assertThrows(RegistrationException.class, ()
                -> registrationService.register(userPasswordShort));
    }

    @Test
    void register_nullAge_notOk() {
        final User userAgeNull = new User("login23", "1234567", null);
        assertThrows(RegistrationException.class, () -> registrationService.register(userAgeNull));
    }

    @Test
    void register_minAge_notOk() {
        final User userMinAge = new User("login24", "abCDfi", 17);
        assertThrows(RegistrationException.class, () -> registrationService.register(userMinAge));
    }

    @Test
    void register_repeatUser_notOk() {
        final User userNew = new User("login25", "ABC123", 19);
        final User userRepeat = new User("login25", "fdc122", 18);
        User firstAdd = registrationService.register(userNew);
        assertEquals(userNew, firstAdd);
        assertEquals(1, Storage.people.size());
        assertThrows(RegistrationException.class, () -> registrationService.register(userRepeat));
    }
}
