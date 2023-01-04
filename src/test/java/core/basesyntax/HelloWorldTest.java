package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HelloWorldTest {
    private static User user;
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void getDefaultUser() {
        user = new User();
        user.setAge(18);
        user.setPassword("123456");
        user.setLogin("test@gmail.com");
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void register_login_ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_login_noOk() {
        registrationService.register(user);
        User anotherUser = new User();
        anotherUser.setAge(23);
        anotherUser.setLogin("test@gmail.com");
        anotherUser.setPassword("778955833449");
        assertThrows(RuntimeException.class,() -> registrationService.register(anotherUser),
                "User login already exists");
    }

    @Test
    void register_nullLogin_noOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Login can't be null");
    }

    @Test
    void register_Age_Ok() {
        user.setAge(32);
        final User actual = registrationService.register(user);
        assertEquals(user,actual);
    }

    @Test
    void register_nonValidAge_noOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Not valid age");
    }

    @Test
    void register_UnhapenedAge_noOk() {
        user.setAge(102);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user), "Not valid age");
    }

    @Test
    void register_nullAge_noOK() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Age can't be null");
    }

    @Test
    void register_shortPassword_noOk() {
        user.setPassword("vika");
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Not valid password");
    }

    @Test
    void register_password_Ok() {
        user.setPassword("viktoriya");
        User actual = registrationService.register(user);
        assertEquals(user,actual);
    }

    @Test
    void register_nullPassword_noOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Password can't be null");
    }
}
