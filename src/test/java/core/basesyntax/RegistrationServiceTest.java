package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    public void clearStorage() {
        Storage.people.clear();
    }

    @Test
    public void register_loginAlreadyExists_notOk() {
        User user = new User();
        user.setAge(34);
        user.setLogin("mkinuuhf");
        user.setPassword("123456");
        Storage.people.add(user);
        User newUser = new User();
        newUser.setAge(29);
        newUser.setLogin("mkinuuhf");
        newUser.setPassword("123456967");
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    public void register_shortLogin_notOk() {
        User user = new User();
        user.setAge(18);
        user.setLogin("asdf");
        user.setPassword("123456");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_smallAge_notOk() {
        User user = new User();
        user.setAge(17);
        user.setLogin("qwerty");
        user.setPassword("123456");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_passwordEmpty_notOk() {
        User user = new User();
        user.setAge(28);
        user.setLogin("zxcvbn");
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_loginEmpty_notOk() {
        User user = new User();
        user.setAge(28);
        user.setLogin("");
        user.setPassword("1234567");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_smallPassword_notOk() {
        User user = new User();
        user.setAge(28);
        user.setLogin("zxcvbn");
        user.setPassword("12234");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_registerNull_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    public void register_passwordNull_notOk() {
        User user = new User();
        user.setAge(28);
        user.setLogin("zxcvbn");
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_loginNull_notOk() {
        User user = new User();
        user.setAge(28);
        user.setLogin(null);
        user.setPassword("1234567");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_ageNull_notOk() {
        User user = new User();
        user.setAge(null);
        user.setLogin("zxcvbn");
        user.setPassword("1234567");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_normalRegister_Ok() {
        User user = new User();
        user.setAge(34);
        user.setLogin("mkinuuhf");
        user.setPassword("123456");
        assertEquals(user, registrationService.register(user));
    }
}
