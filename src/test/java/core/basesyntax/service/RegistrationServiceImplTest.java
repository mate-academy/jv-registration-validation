package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl service;

    @BeforeAll
     static void init() {
        User user = new User();
        service = new RegistrationServiceImpl();
        user.setLogin("User");
        user.setPassword("345678");
        user.setAge(25);
        service.register(user);
    }

    @Test
    void register_NotValidAge_NotOk() {
        User userAge = new User();
        userAge.setLogin("minorUser");
        userAge.setPassword("123456");
        userAge.setAge(15);
        assertThrows(RuntimeException.class, () -> service.register(userAge));
        userAge.setAge(-1);
        assertThrows(RuntimeException.class, () -> service.register(userAge));
    }

    @Test
    void register_NotValidPassword_NotOk() {
        User userPassword = new User();
        userPassword.setLogin("InvalidPasswordUser");
        userPassword.setPassword("23457");
        userPassword.setAge(19);
        assertThrows(RuntimeException.class, () -> service.register(userPassword));
    }

    @Test
    void register_ExistingUser_NotOk() {
        User userCheck = new User();
        userCheck.setLogin("User");
        userCheck.setPassword("567890");
        userCheck.setAge(20);
        assertThrows(RuntimeException.class, () -> service.register(userCheck));
    }

    @Test
    void register_NullUser_NotOk() {
        assertThrows(RuntimeException.class, () -> service.register(null));

    }

    @Test
    void register_EmptyFieldsUser_NotOk() {
        assertThrows(RuntimeException.class, () -> service.register(new User()));

    }

    @Test
    void register_LoginIsTooLong_NotOk() {
        User userLongLogin = new User();
        userLongLogin.setLogin("userWithLongLogin12345");
        userLongLogin.setPassword("12345212");
        userLongLogin.setAge(28);
        assertThrows(RuntimeException.class, () -> service.register(userLongLogin));
    }

    @Test
    void register_Ok() {
        User newUser = new User();
        newUser.setLogin("newUser");
        newUser.setPassword("newUser123");
        newUser.setAge(18);
        service.register(newUser);
        int actual = Storage.people.size();
        assertEquals(2, actual);
    }
}
