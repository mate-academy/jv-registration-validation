package core.basesyntax;

import core.basesyntax.exception.InvalidUserCredentialsException;
import core.basesyntax.exception.InvalidUserObjectException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
    public void register_userAlreadyExists_should_throw_invalidUserException_negative() {
        User user = new User();
        user.setLogin("test-mail1@gmail.com");
        user.setPassword("test-password1");
        user.setAge(22);
        registrationService.register(user);
        Assertions.assertThrows(InvalidUserObjectException.class,
                () -> registrationService.register(user));
    }

    @Test
    public void register_negativeUserAge_should_throw_invalidUserCredentialsException_negative() {
        User user = new User();
        user.setLogin("test-mail2@gmail.com");
        user.setPassword("test-password2");
        user.setAge(-2);
        Assertions.assertThrows(InvalidUserCredentialsException.class,
                () -> registrationService.register(user));
    }

    @Test
    public void register_lessThan18UserAge_should_throw_invalidUserCredentialsException_negative() {
        User user = new User();
        user.setLogin("test-mail3@gmail.com");
        user.setPassword("test-password3");
        user.setAge(17);
        Assertions.assertThrows(InvalidUserCredentialsException.class,
                () -> registrationService.register(user));
    }

    @Test
    public void register_positiveUserBoundaryAge_userObjects_should_be_equals_positive() {
        User user = new User();
        user.setLogin("test-mail4@gmail.com");
        user.setPassword("test-password4");
        user.setAge(18);
        User newUser = registrationService.register(user);
        Assertions.assertNotNull(newUser);
        Assertions.assertEquals(user, newUser);
    }

    @Test
    public void register_positiveUserBigAge_userObjects_should_be_equals_positive() {
        User user = new User();
        user.setLogin("test-mail5@gmail.com");
        user.setPassword("test-password5");
        user.setAge(1000);
        User newUser = registrationService.register(user);
        Assertions.assertNotNull(newUser);
        Assertions.assertEquals(user, newUser);
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
        User newUser = registrationService.register(user);
        Assertions.assertNotNull(newUser);
        Assertions.assertEquals(user, newUser);
    }

    @Test
    public void register_positiveUserEmail_userObjects_should_be_equals_positive() {
        User user = new User();
        user.setLogin("test-mail6@gmail.com");
        user.setPassword("test-password123");
        user.setAge(20);
        User newUser = registrationService.register(user);
        Assertions.assertNotNull(newUser);
        Assertions.assertEquals(user, newUser);
    }

    @Test
    public void register_nullPassword_should_throw_invalidUserCredentialsException_negative() {
        User user = new User();
        user.setLogin("test-mail7@gmail.com");
        user.setPassword(null);
        user.setAge(22);
        Assertions.assertThrows(InvalidUserCredentialsException.class,
                () -> registrationService.register(user));
    }

    @Test
    public void register_emptyPassword_should_throw_invalidUserCredentialsException_negative() {
        User user = new User();
        user.setLogin("test-mail8@gmail.com");
        user.setPassword("");
        user.setAge(22);
        Assertions.assertThrows(InvalidUserCredentialsException.class,
                () -> registrationService.register(user));
    }

    @Test
    public void register_passwordLengthLessThan6_should_throw_exception_negative() {
        User user = new User();
        user.setLogin("test-mail9@gmail.com");
        user.setPassword("123");
        user.setAge(22);
        Assertions.assertThrows(InvalidUserCredentialsException.class,
                () -> registrationService.register(user));
    }

    @Test
    public void register_passwordLengthEquals6_userObjects_should_be_equals_positive() {
        User user = new User();
        user.setLogin("test-mail10@gmail.com");
        user.setPassword("123123");
        user.setAge(20);
        User newUser = registrationService.register(user);
        Assertions.assertNotNull(newUser);
        Assertions.assertEquals(user, newUser);
    }

    @Test
    public void register_validPassword_userObjects_should_be_equals_positive() {
        User user = new User();
        user.setLogin("test-mail11@gmail.com");
        user.setPassword("password123");
        user.setAge(20);
        User newUser = registrationService.register(user);
        Assertions.assertNotNull(newUser);
        Assertions.assertEquals(user, newUser);
    }

}
