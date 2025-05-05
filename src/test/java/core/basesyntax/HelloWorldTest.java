package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
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
    public static void create() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    public void clearList() {
        Storage.people.clear();
    }

    @Test
    public void register_youngAge_NotOk() {
        User userLess18 = new User();
        userLess18.setLogin("UserLess18");
        userLess18.setPassword("UserLess18");
        userLess18.setAge(17);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userLess18),
                "Age has to be between 18 and 120");
    }

    @Test
    public void register_shortPassword_NotOk() {
        User user = new User();
        user.setLogin("User");
        user.setPassword("User");
        user.setAge(30);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user),
                "Password less 6 symbols is input");
    }

    @Test
    public void register_AlreadyExist_NotOk() {
        User user = new User();
        user.setLogin("User12345");
        user.setPassword("User@12345");
        user.setAge(25);
        new StorageDaoImpl().add(user);
        assertEquals(1, Storage.people.size(), "Size not equals");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user), "Same user was putted in storage");
    }

    @Test
    public void register_nullUser_NotOk() {
        assertThrows(RuntimeException.class,
                () -> registrationService.register(null), "Came null user");
    }

    @Test
    public void register_NullAge_NotOK() {
        User user = new User();
        user.setLogin("User12345");
        user.setPassword("User12345");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user), "User has null age");
    }

    @Test
    public void register_NullLogin_NotOK() {
        User user = new User();
        user.setPassword("User@12345");
        user.setAge(25);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user), "User has null or empty name");
    }

    @Test
    public void register_NullPassword_NotOK() {
        User user = new User();
        user.setLogin("User12345");
        user.setAge(25);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user), "User has null password");
    }

    @Test
    public void register_UnhapenedAge_NotOk() {
        User user = new User();
        user.setLogin("User12345");
        user.setPassword("User@12345");
        user.setAge(125);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user), "User with age. It's can't be");
    }

    @Test
    public void registration_PutCorrectDate_Ok() {
        User user = new User();
        user.setLogin("User111");
        user.setPassword("User@111");
        user.setAge(30);
        assertEquals(user, registrationService.register(user));
    }
}
