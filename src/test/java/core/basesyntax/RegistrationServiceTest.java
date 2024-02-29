package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exeption.UserNullException;
import core.basesyntax.exeption.age.InvalidAgeException;
import core.basesyntax.exeption.login.InvalidLoginCharacterException;
import core.basesyntax.exeption.login.InvalidLoginLengthException;
import core.basesyntax.exeption.login.LoginAlreadyExistsException;
import core.basesyntax.exeption.login.NullLoginException;
import core.basesyntax.exeption.password.InvalidPasswordCharacterException;
import core.basesyntax.exeption.password.InvalidPasswordLengthException;
import core.basesyntax.exeption.password.NullPasswordException;
import core.basesyntax.exeption.password.SequentialPatternException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static RegistrationServiceImpl service = new RegistrationServiceImpl();

    @BeforeAll
    public static void addValidUser() {
        User user = new User();
        user.setAge(75);
        user.setLogin("bestOfTheBest");
        user.setPassword("bob1965bob");
        service.register(user);
    }

    @Test
    public void register_userNullExceptionThrown() {
        User user = null;
        assertThrows(UserNullException.class, () -> service.register(user));
    }

    @Test
    public void register_invalidAgeExceptionThrown_lowerLimit() {
        User user = new User();
        user.setAge(15);
        user.setLogin("lol");
        user.setPassword("1111111");
        assertThrows(InvalidAgeException.class, () -> service.register(user));
    }

    @Test
    public void register_invalidAgeExceptionThrown_upperLimit() {
        User user = new User();
        user.setAge(150);
        user.setLogin("lol");
        user.setPassword("1111111");
        assertThrows(InvalidAgeException.class, () -> service.register(user));
    }

    @Test
    public void register_nullLoginExceptionThrown() {
        User user = new User();
        user.setAge(25);
        user.setLogin(null);
        user.setPassword("fselkv851");
        assertThrows(NullLoginException.class, () -> service.register(user));
    }

    @Test
    public void register_invalidLoginLengthExceptionThrown() {
        User user = new User();
        user.setAge(85);
        user.setLogin("nullnullnullnullnullnullnullnullnullnull");
        user.setPassword("fselkv851");
        assertThrows(InvalidLoginLengthException.class, () -> service.register(user));
    }

    @Test
    public void register_loginAlreadyExistsExceptionThrown() {
        User user = new User();
        user.setAge(35);
        user.setLogin("bestOfTheBest");
        user.setPassword("$Alexandr*777");
        assertThrows(LoginAlreadyExistsException.class, () -> service.register(user));
    }

    @Test
    public void register_invalidLoginCharacterExceptionThrown() {
        User user = new User();
        user.setAge(45);
        user.setLogin("%Big$$$Super->Boss");
        user.setPassword("password");
        assertThrows(InvalidLoginCharacterException.class, () -> service.register(user));
    }

    @Test
    public void register_nullPasswordExceptionThrown() {
        User user = new User();
        user.setAge(33);
        user.setLogin("simple");
        user.setPassword(null);
        assertThrows(NullPasswordException.class, () -> service.register(user));
    }

    @Test
    public void register_invalidPasswordLengthExceptionThrown() {
        User user = new User();
        user.setAge(33);
        user.setLogin("simple");
        user.setPassword("1");
        assertThrows(InvalidPasswordLengthException.class, () -> service.register(user));
    }

    @Test
    public void register_invalidPasswordCharacterExceptionThrown() {
        User user = new User();
        user.setAge(33);
        user.setLogin("simple");
        user.setPassword("太阳天空地球海森林海洋");
        assertThrows(InvalidPasswordCharacterException.class, () -> service.register(user));
    }

    @Test
    public void register_sequentialPatternExceptionThrown_IncreasingSymbols() {
        User user = new User();
        user.setAge(33);
        user.setLogin("simple");
        user.setPassword("safari1234dog");
        assertThrows(SequentialPatternException.class, () -> service.register(user));
    }

    @Test
    public void register_sequentialPatternExceptionThrown_DecreaseSequentially() {
        User user = new User();
        user.setAge(33);
        user.setLogin("simple");
        user.setPassword("bibaqponboba");
        assertThrows(SequentialPatternException.class, () -> service.register(user));
    }

    @Test
    public void register_sequentialPatternExceptionThrown_RepeatedCharacters() {
        User user = new User();
        user.setAge(33);
        user.setLogin("simple");
        user.setPassword("dog$$$$cat");
        assertThrows(SequentialPatternException.class, () -> service.register(user));
    }
}
