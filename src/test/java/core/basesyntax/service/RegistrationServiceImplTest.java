package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.exception.CustomException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService service;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @Test
    void register_nullUser_notOk() {
        User user = null;
        try {
            service.register(user);
        } catch (CustomException e) {
            System.out.println("Custom exception was catched.");
            return;
        }
        fail();
    }

    @Test
    void register_nullFields_notOk() {
        User user = new User();
        assertThrows(CustomException.class, () -> service.register(user));
    }

    @Test
    void register_noValidLogin_notOk() {
        User user = new User();
        user.setId(1L);
        user.setAge(19);
        user.setLogin("login");
        user.setPassword("password");
        assertThrows(CustomException.class, () -> service.register(user));
    }

    @Test
    void register_loginMinimumLength_Ok() {
        User user = new User();
        user.setId(1L);
        user.setAge(19);
        user.setLogin("login1");
        user.setPassword("pass123");
        User register = service.register(user);
        assertEquals(user, register);
    }

    @Test
    void register_noValidPassword_notOk() {
        User user = new User();
        user.setId(1L);
        user.setAge(19);
        user.setLogin("goodlogin");
        user.setPassword("pass");
        assertThrows(CustomException.class, () -> service.register(user));
    }

    @Test
    void register_passwordMinimumLength_Ok() {
        User user = new User();
        user.setId(1L);
        user.setAge(19);
        user.setLogin("goodlogin");
        user.setPassword("pass12");
        User register = service.register(user);
        assertEquals(user, register);
    }

    @Test
    void register_negativeAge_notOk() {
        User user = new User();
        user.setId(1L);
        user.setAge(-8);
        user.setLogin("goodlogin");
        user.setPassword("password");
        assertThrows(CustomException.class, () -> service.register(user));
    }

    @Test
    void register_ageLessThan18_notOk() {
        User user = new User();
        user.setId(1L);
        user.setAge(14);
        user.setLogin("goodlogin");
        user.setPassword("password");
        assertThrows(CustomException.class, () -> service.register(user));
    }

    @Test
    void register_ValidData_ok() {
        User user = new User();
        user.setId(1L);
        user.setAge(19);
        user.setLogin("validlogin");
        user.setPassword("password");
        User register = service.register(user);
        assertEquals(user, register);
    }

    @Test
    void register_ExistingUser_notOk() {
        User user = new User();
        user.setId(1L);
        user.setAge(28);
        user.setLogin("greatuser12");
        user.setPassword("secretpassword");
        service.register(user);
        User anotherUser = new User();
        anotherUser.setId(2L);
        anotherUser.setAge(19);
        anotherUser.setLogin("greatuser12");
        anotherUser.setPassword("password12");
        assertThrows(CustomException.class, () -> service.register(anotherUser));
    }
}
