package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationFailureException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class HelloWorldTest {
    private static final int VALID_AGE = 20;
    private static RegistrationService registrationService;
    private static User validUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void beforeEach() {
        validUser = new User();
        validUser.setLogin("ValidLogin");
        validUser.setPassword("password");
        validUser.setAge(VALID_AGE);
    }

    @Test
    void register_addUser_Ok() {
        validUser.setLogin("newLogin");
        User result = registrationService.register(validUser);
        assertEquals(validUser, result, "The valid user and result aren't the same.");
    }

    @Test
    void register_passwordEdges_Ok() {
        validUser.setLogin("Marco1");
        validUser.setPassword("123456");
        User result = registrationService.register(validUser);
        assertEquals(result, validUser, "User and added result aren't the same.");

        validUser = new User();
        validUser.setLogin("Parmezan");
        validUser.setPassword("12345678910");
        validUser.setAge(VALID_AGE);

        result = registrationService.register(validUser);
        assertEquals(result, validUser, "User and added result aren't the same.");
    }

    @Test
    void register_nullUser_notOk() {
        validUser = null;
        assertThrows(RegistrationFailureException.class,
                () -> registrationService.register(validUser),
                "Invalid result for null user.");
    }

    @Test
    void register_addIdenticalUsers_notOk() {
        validUser.setLogin("Antonio");
        Storage.people.add(validUser);
        assertThrows(RegistrationFailureException.class,
                () -> registrationService.register(validUser),
                "Added 2 identical users.");
    }

    @Test
    void register_addYoungUser_notOk() {
        validUser.setAge(15);
        assertThrows(RegistrationFailureException.class,
                () -> registrationService.register(validUser),
                "Added user that younger then 18 y.o.");

        validUser.setAge(0);
        assertThrows(RegistrationFailureException.class,
                () -> registrationService.register(validUser),
                "Added user that younger then 18 y.o.");
    }

    @Test
    void register_nullLogin_notOk() {
        validUser.setLogin(null);
        assertThrows(RegistrationFailureException.class,
                () -> registrationService.register(validUser),
                "Added user with null login.");
    }

    @Test
    void register_nullPassword_notOk() {
        validUser.setPassword(null);
        assertThrows(RegistrationFailureException.class,
                () -> registrationService.register(validUser),
                "Added user with null password.");
    }

    @Test
    void register_nullAge_notOk() {
        validUser.setAge(null);
        assertThrows(RegistrationFailureException.class,
                () -> registrationService.register(validUser),
                "Added user with null age.");
    }

    @Test
    void register_shortLogin_notOk() {
        validUser.setLogin("Login");
        assertThrows(RegistrationFailureException.class,
                () -> registrationService.register(validUser),
                "Added user with short login.");
    }

    @Test
    void register_shortPassword_notOk() {
        validUser.setPassword("1234");
        assertThrows(RegistrationFailureException.class,
                () -> registrationService.register(validUser),
                "Added user with short password");
    }
}
