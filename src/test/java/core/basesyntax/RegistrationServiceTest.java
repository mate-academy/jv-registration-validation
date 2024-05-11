package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.CantAddUserException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static User user;

    @BeforeAll
    public static void setUp() {
        user = new User();
    }

    @BeforeEach
    public void clearStorage() {
        Storage.people.clear();
    }

    @Test
    public void register_sameUser_exception() {
        RegistrationServiceImpl register = new RegistrationServiceImpl();
        try {
            user.setAge(34);
            user.setLogin("mkinuuhf");
            user.setPassword("123456");
            register.register(user);
            register.register(user);
        } catch (CantAddUserException e) {
            assertEquals("User already exists in system", e.getMessage());
        }
    }

    @Test
    public void register_wrongLogin_exception() {
        RegistrationServiceImpl register = new RegistrationServiceImpl();
        try {
            user.setAge(18);
            user.setLogin("asdf");
            user.setPassword("123456");
            register.register(user);
        } catch (CantAddUserException e) {
            assertEquals("Login too short", e.getMessage());
        }
    }

    @Test
    public void register_wrongAge_exception() {
        RegistrationServiceImpl register = new RegistrationServiceImpl();
        try {
            user.setAge(17);
            user.setLogin("qwerty");
            user.setPassword("123456");
            register.register(user);
        } catch (CantAddUserException e) {
            assertEquals("User's age under 18", e.getMessage());
        }
    }

    @Test
    public void register_wrongPassword_exception() {
        RegistrationServiceImpl register = new RegistrationServiceImpl();
        try {
            user.setAge(28);
            user.setLogin("zxcvbn");
            user.setPassword("12234");
            register.register(user);
        } catch (CantAddUserException e) {
            assertEquals("Password too short", e.getMessage());
        }
    }

    @Test
    public void register_registerNull_nullException() {
        RegistrationServiceImpl register = new RegistrationServiceImpl();
        assertThrows(NullPointerException.class, () -> register.register(null));
    }

    @Test
    public void register_normalRegister_Ok() throws CantAddUserException {
        user.setAge(34);
        user.setLogin("mkinuuhf");
        user.setPassword("123456");
        RegistrationServiceImpl register = new RegistrationServiceImpl();
        assertEquals(user, register.register(user));
    }
}
