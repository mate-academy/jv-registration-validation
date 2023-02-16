package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.dao.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UserRegistrationTest {
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_loginIsAlreadyRegistered_notOk() {
        String login = "login";
        int ageValid = 19;
        String passwordValid = "asdfgh";
        User user = new User();
        user.setLogin(login);
        user.setAge(ageValid);
        user.setPassword(passwordValid);
        User userLoginIsAlreadyRegistered = new User();
        userLoginIsAlreadyRegistered.setLogin(login);
        userLoginIsAlreadyRegistered.setAge(ageValid);
        userLoginIsAlreadyRegistered.setPassword(passwordValid);
        registrationService.register(user);
        try {
            registrationService.register(userLoginIsAlreadyRegistered);
        } catch (RegistrationException e) {
            return;
        }
        fail("UsersDataIsNotValidException shout throw if login is already registered");
    }

    @Test
    void register_loginIsNewerRegistered_ok() {
        int ageValid = 19;
        String passwordValid = "asdfgh";
        User userOldLogin = new User();
        userOldLogin.setLogin("user");
        userOldLogin.setAge(ageValid);
        userOldLogin.setPassword(passwordValid);
        User userNewLogin = new User();
        userNewLogin.setLogin("new user");
        userNewLogin.setAge(ageValid);
        userNewLogin.setPassword(passwordValid);
        registrationService.register(userOldLogin);
        assertEquals(registrationService.register(userNewLogin), userNewLogin);
    }

    @Test
    void register_ageIsLessMinimalAge_notOk() {
        int ageNotValid = 16;
        String passwordValid = "asdfgh";
        User user = new User();
        user.setLogin("new login");
        user.setAge(ageNotValid);
        user.setPassword(passwordValid);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageMoreMinimalAge_ok() {
        int ageValid = 19;
        String passwordValid = "asdfgh";
        User user = new User();
        user.setLogin("minimal age");
        user.setAge(ageValid);
        user.setPassword(passwordValid);
        assertEquals(registrationService.register(user), user);
    }

    @Test
    void register_passwordIsLessMinimumCharacterSize_notOk() {
        int ageValid = 19;
        String pasWorldNotValid = "asd";
        User user = new User();
        user.setLogin("minimal password");
        user.setAge(ageValid);
        user.setPassword(pasWorldNotValid);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordIsLargeMinimumCharacterSize_Ok() {
        int ageValid = 19;
        String passwordValid = "asdfgh";
        User user = new User();
        user.setLogin("large password");
        user.setAge(ageValid);
        user.setPassword(passwordValid);
        assertEquals(registrationService.register(user), user);
    }

    @Test
    void register_nullLogin_notOk() {
        int ageValid = 19;
        String passwordValid = "asdfgh";
        User user = new User();
        user.setLogin(null);
        user.setAge(ageValid);
        user.setPassword(passwordValid);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_notOk() {
        String passwordValid = "asdfgh";
        User user = new User();
        user.setLogin("null age");
        user.setAge(null);
        user.setPassword(passwordValid);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        int ageValid = 19;
        User user = new User();
        user.setLogin("null password");
        user.setAge(ageValid);
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}
