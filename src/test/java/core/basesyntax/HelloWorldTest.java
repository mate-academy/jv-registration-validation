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
    private static final int THREE_ELEMENTS = 3;
    private static final int ONE_ELEMENTS = 1;

    @BeforeAll
    static void create() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void nullList() {
        Storage.people.clear();
    }

    @Test
    void register_ageLess18_NotOk() {
        User userLess18 = new User();
        userLess18.setLogin("UserLess18");
        userLess18.setPassword("UserLess18");
        userLess18.setAge(17);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userLess18),
                "Age has to be between 18 and 120");
    }

    @Test
    void register_PasswordLess6Numbers_NotOk() {
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
        assertEquals(ONE_ELEMENTS, Storage.people.size(), "Size not equals");
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
        User user1 = new User();
        user1.setLogin("User111");
        user1.setPassword("User@111");
        user1.setAge(30);
        assertEquals(user1, registrationService.register(user1));
        User user2 = new User();
        user2.setLogin("User222");
        user2.setPassword("User@222");
        user2.setAge(45);
        assertEquals(user2, registrationService.register(user2));
        User user3 = new User();
        user3.setLogin("User333");
        user3.setPassword("User@333");
        user3.setAge(60);
        assertEquals(user3, registrationService.register(user3));
        assertEquals(THREE_ELEMENTS, Storage.people.size(), "Sizes not equals");
    }
}
