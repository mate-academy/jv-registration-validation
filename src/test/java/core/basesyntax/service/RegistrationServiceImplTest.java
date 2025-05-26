package core.basesyntax.service;

import core.basesyntax.exception.NoValidUserException;
import core.basesyntax.model.User;
import java.util.Objects;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private final User user = new User();

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        user.setLogin("Login123");
        user.setPassword("Password");
        try {
            registrationService.register(user);
        } catch (NoValidUserException e) {
            return;
        }
        Assert.fail("Age of user must not be null.\n");
    }

    @Test
    void register_nullPassword_notOk() {
        user.setAge(21);
        user.setLogin("Login123");
        user.setPassword(null);
        try {
            registrationService.register(user);
        } catch (NoValidUserException e) {
            return;
        }
        Assert.fail("Password of user must not be null.\n");
    }

    @Test
    void register_nullLogin_notOk() {
        user.setAge(21);
        user.setLogin(null);
        user.setPassword("Password");
        try {
            registrationService.register(user);
        } catch (NoValidUserException e) {
            return;
        }
        Assert.fail("Login of user must not be null.\n");
    }

    @Test
    void register_smallAge_notOk() {
        user.setAge(15);
        user.setLogin("Login123");
        user.setPassword("Password");
        try {
            registrationService.register(user);
        } catch (NoValidUserException e) {
            return;
        }
        Assert.fail("Age of user must be more or equals 18.\n");
    }

    @Test
    void register_smallLogin_notOk() {
        user.setAge(21);
        user.setLogin("Login");
        user.setPassword("Password");
        try {
            registrationService.register(user);
        } catch (NoValidUserException e) {
            return;
        }
        Assert.fail("Login of user must be longer then 5 characters.\n");
    }

    @Test
    void register_smallPass_notOk() {
        user.setAge(21);
        user.setLogin("Login123");
        user.setPassword("Pass");
        try {
            registrationService.register(user);
        } catch (NoValidUserException e) {
            return;
        }
        Assert.fail("Password of user must be longer then 5 characters.\n");
    }

    @Test
    void register_userTween_notOk() {
        user.setAge(21);
        user.setLogin("Brother_Tween");
        user.setPassword("Password");
        try {
            registrationService.register(user);
            registrationService.register(user);
        } catch (NoValidUserException e) {
            return;
        }
        Assert.fail("Two users cannot have the same logins.\n");

    }

    @Test
    void register_normalUser_Ok() {
        user.setAge(21);
        user.setLogin("Brother");
        user.setPassword("Password");

        User userResult = registrationService.register(user);

        Assert.assertTrue("Users " + user + " and " + userResult + " must be equal.\n",
                Objects.equals(user, userResult));

    }
}
