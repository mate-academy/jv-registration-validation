package core.basesyntax;

import core.basesyntax.exception.InvalidUserCredentialsException;
import core.basesyntax.exception.InvalidUserObjectException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    public void register_nullUser_should_throw_invalidUserException_negative() {
        User user = null;
        Assertions.assertThrows(InvalidUserObjectException.class,
                () -> registrationService.register(user));
    }

    @Test
    public void register_negativeUserAge_should_throw_invalidUserCredentialsException_negative() {
        User user = new User();
        user.setLogin("test-mail1@gmail.com");
        user.setPassword("test-password123");
        user.setAge(-2);
        Assertions.assertThrows(InvalidUserCredentialsException.class,
                () -> registrationService.register(user));
    }

    @Test
    public void register_lessThan18UserAge_should_throw_invalidUserCredentialsException_negative() {
        User user = new User();
        user.setLogin("test-mail1@gmail.com");
        user.setPassword("test-password123");
        user.setAge(17);
        Assertions.assertThrows(InvalidUserCredentialsException.class,
                () -> registrationService.register(user));
    }

    @Test
    public void register_positiveUserBoundaryAge_userObjects_should_be_equals_positive() {
        User user = new User();
        user.setLogin("test-mail1@gmail.com");
        user.setPassword("test-password123");
        user.setAge(18);
        Assertions.assertNotNull(registrationService.register(user));
        Assertions.assertEquals(user, registrationService.register(user));
    }

    @Test
    public void register_positiveUserBigAge_userObjects_should_be_equals_positive() {
        User user = new User();
        user.setLogin("test-mail1@gmail.com");
        user.setPassword("test-password123");
        user.setAge(1000);
        Assertions.assertNotNull(registrationService.register(user));
        Assertions.assertEquals(user, registrationService.register(user));
    }

    @Test
    public void register_emptyEmail_should_throw_invalidUserCredentialsException_negative() {
        User user = new User();
        user.setLogin("");
        user.setPassword("password123");
        user.setAge(22);
        Assertions.assertThrows(InvalidUserCredentialsException.class,
                () -> registrationService.register(user));
    }

    @Test
    public void register_nullEmail_should_throw_invalidUserCredentialsException_negative() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("password123");
        user.setAge(22);
        Assertions.assertThrows(InvalidUserCredentialsException.class,
                () -> registrationService.register(user));
    }

    @Test
    public void register_emailLengthLessThan6_should_throw_exception_negative() {
        User user = new User();
        user.setLogin("e@m.u");
        user.setPassword(null);
        user.setAge(22);
        Assertions.assertThrows(InvalidUserCredentialsException.class,
                () -> registrationService.register(user));
    }

    @Test
    public void register_emailLengthEquals6_userObjects_should_be_equals_positive() {
        User user = new User();
        user.setLogin("e@mail");
        user.setPassword("test-password123");
        user.setAge(20);
        Assertions.assertNotNull(registrationService.register(user));
        Assertions.assertEquals(user, registrationService.register(user));
    }

    @Test
    public void register_positiveUserEmail_userObjects_should_be_equals_positive() {
        User user = new User();
        user.setLogin("test-mail1@gmail.com");
        user.setPassword("test-password123");
        user.setAge(20);
        Assertions.assertNotNull(registrationService.register(user));
        Assertions.assertEquals(user, registrationService.register(user));
    }

    @Test
    public void register_nullPassword_should_throw_invalidUserCredentialsException_negative() {
        User user = new User();
        user.setLogin("test-mail1@gmail.com");
        user.setPassword(null);
        user.setAge(22);
        Assertions.assertThrows(InvalidUserCredentialsException.class,
                () -> registrationService.register(user));
    }

    @Test
    public void register_emptyPassword_should_throw_invalidUserCredentialsException_negative() {
        User user = new User();
        user.setLogin("test-mail1@gmail.com");
        user.setPassword("");
        user.setAge(22);
        Assertions.assertThrows(InvalidUserCredentialsException.class,
                () -> registrationService.register(user));
    }

    @Test
    public void register_passwordLengthLessThan6_should_throw_exception_negative() {
        User user = new User();
        user.setLogin("test-mail1@gmail.com");
        user.setPassword("123");
        user.setAge(22);
        Assertions.assertThrows(InvalidUserCredentialsException.class,
                () -> registrationService.register(user));
    }

    @Test
    public void register_passwordLengthEquals6_userObjects_should_be_equals_positive() {
        User user = new User();
        user.setLogin("test-mail1@gmail.com");
        user.setPassword("123123");
        user.setAge(20);
        Assertions.assertNotNull(registrationService.register(user));
        Assertions.assertEquals(user, registrationService.register(user));
    }

    @Test
    public void register_validPassword_userObjects_should_be_equals_positive() {
        User user = new User();
        user.setLogin("test-mail1@gmail.com");
        user.setPassword("password123");
        user.setAge(20);
        Assertions.assertNotNull(registrationService.register(user));
        Assertions.assertEquals(user, registrationService.register(user));
    }

}
