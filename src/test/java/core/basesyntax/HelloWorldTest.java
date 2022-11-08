package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
/**
 Feel free to remove this class and create your own.
 */

public class HelloWorldTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void create() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void clearList() {
        Storage.people.clear();
    }

    @Test
    void register_youngAge_NotOk() {
        User userLess18 = new User();
        userLess18.setLogin("UserLess18");
        userLess18.setPassword("UserLess18");
        userLess18.setAge(17);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userLess18),
                "Age has to be between 18 and 120");
    }

    @Test
    void register_shortPassword_NotOk() {
        User user = new User();
        user.setLogin("User");
        user.setPassword("User");
        user.setAge(30);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user),
                "Password less 6 symbols is input");
    }

    @Test
    void register_IfExist_NotOk() {
        User user = new User();
        user.setLogin("User12345");
        user.setPassword("User@12345");
        user.setAge(25);
        registrationService.register(user);
        assertEquals(1, Storage.people.size(), "Size not equals");
        User user2 = new User();
        user2.setLogin("User12345");
        user2.setPassword("User@12345");
        user2.setAge(25);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user2), "Same user was putted in storage");
    }

    @Test
    void register_EmptyUser_NotOk() {
        assertThrows(RuntimeException.class,
                () -> registrationService.register(null), "Came null user");
    }

    @Test
    void register_NullFields_NotOK() {
        User user = new User();
        user.setPassword("User@12345");
        user.setAge(25);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user), "User has null or empty name");
        user.setLogin("User12345");
        user.setPassword(null);
        user.setAge(25);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user), "User has null password");
        user.setLogin("User12345");
        user.setPassword("User12345");
        user.setAge(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user), "User has null age");
    }

    @Test
    void register_UnhapenedAge_NotOk() {
        User user = new User();
        user.setLogin("User12345");
        user.setPassword("User@12345");
        user.setAge(125);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user), "User with age. It's can't be");
    }

    @Test
    void registration_PutCorrectDate_Ok() {
        User user = new User();
        user.setLogin("User111");
        user.setPassword("User@111");
        user.setAge(30);
        assertEquals(user, registrationService.register(user));
    }
}
