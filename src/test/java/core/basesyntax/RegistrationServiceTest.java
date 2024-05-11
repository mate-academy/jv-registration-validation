package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.CantAddUserException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static User user1;
    private static User user2;
    private static User user3;
    private static User user4;

    @BeforeAll
    public static void setUp() {
        user1 = new User();
        user2 = new User();
        user3 = new User();
        user4 = new User();
        user1.setAge(18);
        user2.setAge(17);
        user3.setAge(28);
        user4.setAge(34);
        user1.setLogin("asdf");
        user2.setLogin("qwerty");
        user3.setLogin("zxcvbn");
        user4.setLogin("mkinuuhf");
        user1.setPassword("123456");
        user2.setPassword("123456");
        user3.setPassword("1234");
        user4.setPassword("123456");
    }

    @Test
    public void registerUsers_SameUser() {
        RegistrationServiceImpl register = new RegistrationServiceImpl();
        try {
            register.register(user4);
            register.register(user4);
        } catch (CantAddUserException e) {
            assertEquals("User already exists in system", e.getMessage());
        }
    }

    @Test
    public void registerUsers_WrongLogin() {
        RegistrationServiceImpl register = new RegistrationServiceImpl();
        try {
            register.register(user1);
        } catch (CantAddUserException e) {
            assertEquals("Login too short", e.getMessage());
        }
    }

    @Test
    public void registerUsers_WrongAge() {
        RegistrationServiceImpl register = new RegistrationServiceImpl();
        try {
            register.register(user2);
        } catch (CantAddUserException e) {
            assertEquals("User's age under 18", e.getMessage());
        }
    }

    @Test
    public void registerUsers_WrongPassword() {
        RegistrationServiceImpl register = new RegistrationServiceImpl();
        try {
            register.register(user3);
        } catch (CantAddUserException e) {
            assertEquals("Password too short", e.getMessage());
        }
    }

    @Test
    public void registerUsers_registerNull() {
        RegistrationServiceImpl register = new RegistrationServiceImpl();
        assertThrows(NullPointerException.class, () -> register.register(null));
    }

    @Test
    public void registerUsers_Ok() throws CantAddUserException {
        RegistrationServiceImpl register = new RegistrationServiceImpl();
        assertEquals(user4, register.register(user4));
    }
}
