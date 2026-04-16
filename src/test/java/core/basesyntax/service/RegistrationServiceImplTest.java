package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.UserValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        Storage.people.clear();

        registrationService = new RegistrationServiceImpl();

        User bob = new User();
        bob.setLogin("bobbob");
        bob.setPassword("bbbooobbb");
        bob.setAge(23);

        User alice = new User();
        alice.setLogin("alicealice");
        alice.setPassword("aaaaliiii");
        alice.setAge(22);

        Storage.people.add(bob);
        Storage.people.add(alice);
    }

    @Test
    void register_validUser_ok() {
        User user = new User();

        user.setLogin("SomeUserName");
        user.setPassword("SomeUserPassword");
        user.setAge(30);

        User actual = registrationService.register(user);

        assertEquals(user, actual);
        assertEquals(3, Storage.people.size());
        assertTrue(Storage.people.contains(actual));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(UserValidationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_duplicateUser_notOk() {
        User bob = new User();

        bob.setLogin("bobbob");
        bob.setPassword("bbbooobbb");
        bob.setAge(23);

        assertThrows(UserValidationException.class, () -> registrationService.register(bob));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();

        user.setLogin("SomeUserName");
        user.setPassword("aa");
        user.setAge(30);

        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User();

        user.setLogin("aa");
        user.setPassword("SomeUserPassword");
        user.setAge(30);

        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidAge_notOk() {
        User user = new User();

        user.setLogin("SomeUserName");
        user.setPassword("SomeUserPassword");
        user.setAge(17);

        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();

        user.setLogin(null);
        user.setPassword("SomeUserPassword");
        user.setAge(30);

        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();

        user.setLogin("SomeUserName");
        user.setPassword(null);
        user.setAge(30);

        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();

        user.setLogin("SomeUserName");
        user.setPassword("SomeUserPassword");
        user.setAge(null);

        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        User user = new User();

        user.setLogin("SomeUserName");
        user.setPassword("SomeUserPassword");
        user.setAge(-1);

        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_zeroLengthPassword_notOk() {
        User user = new User();

        user.setLogin("SomeUserName");
        user.setPassword("");
        user.setAge(30);

        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_justEnoughLengthPassword_ok() {
        User user = new User();

        user.setLogin("SomeUserName");
        user.setPassword("abcdef");
        user.setAge(30);

        User actual= registrationService.register(user);

        assertEquals(user, actual);
        assertEquals(3, Storage.people.size());
        assertTrue(Storage.people.contains(actual));
    }

    @Test
    void register_zeroAge_notOk() {
        User user = new User();

        user.setLogin("SomeUserName");
        user.setPassword("SomeUserPassword");
        user.setAge(0);

        assertThrows(UserValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_18Age_ok() {
        User user = new User();

        user.setLogin("SomeUserName");
        user.setPassword("SomeUserPassword");
        user.setAge(18);

        User actual = registrationService.register(user);

        assertEquals(user, actual);
        assertEquals(3, Storage.people.size());
        assertTrue(Storage.people.contains(actual));
    }
}
