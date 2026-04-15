package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.UserValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
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

        registrationService.register(bob);
        registrationService.register(alice);
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setLogin("SomeUserName");
        user.setPassword("SomeUserPassword");
        user.setAge(30);

        User actual = registrationService.register(user);

        assertEquals(user, actual);
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
}
