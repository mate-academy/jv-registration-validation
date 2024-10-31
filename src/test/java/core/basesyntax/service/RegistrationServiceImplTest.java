package core.basesyntax.service;

import core.basesyntax.exeptions.InvalidUserDataException;
import core.basesyntax.exeptions.NullUserException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final RegistrationServiceImpl registrationService
            = new RegistrationServiceImpl();

    @Test
    void register_validUser_ok() {
        User validUser = new User("Default", "Default", 18);
        User outputUser;
        try {
            outputUser = registrationService.register(validUser);
            Assertions.assertEquals(validUser, outputUser, "Register returned wrong User");
        } catch (InvalidUserDataException e) {
            Assertions.fail("Wrong data validation");
        }
    }

    @Test
    void register_invalidLoginUser_notOk() {
        User invalidUser = new User("tiny", "Default", 18);
        Assertions.assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(invalidUser),
                "An InvalidUserDataException was expected "
                        + "for user with login less than 6 characters");

    }

    @Test
    void register_invalidPasswordUser_notOk() {
        User invalidUser = new User("Default", "tiny", 18);
        Assertions.assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(invalidUser),
                "An InvalidUserDataException was expected for "
                + "user with password less than 6 characters");

    }

    @Test
    void register_invalidAgeUser_notOk() {
        User invalidUser = new User("Default", "Default", 16);
        Assertions.assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(invalidUser),
                "An InvalidUserDataException was expected for user with age less than 18");

    }

    @Test
    void register_sameLoginUser_notOk() {
        User validUser = new User("RomanLoiev", "RomanLoiev", 19);
        registrationService.register(validUser);
        User invalidUser = new User("RomanLoiev", "RomanLoiev", 19);
        Assertions.assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(invalidUser),
                "An InvalidUserDataException was expected for user with already exist login");
    }

    @Test
    void register_nullUser_notOk() {
        User nullUser = null;
        Assertions.assertThrows(NullUserException.class,
                () -> registrationService.register(nullUser),
                "An NullUserException was expected for null user");
    }
}
