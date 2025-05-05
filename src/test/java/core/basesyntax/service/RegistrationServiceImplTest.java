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
        User user = new User();
        user.setLogin("minorUser");
        user.setPassword("123456");
        user.setAge(15);
        assertThrows(RuntimeException.class, () -> service.register(user));
        user.setAge(-1);
        assertThrows(RuntimeException.class, () -> service.register(user));
    }

    @Test
    void register_NotValidPassword_NotOk() {
        User user = new User();
        user.setLogin("InvalidPasswordUser");
        user.setPassword("23457");
        user.setAge(19);
        assertThrows(RuntimeException.class, () -> service.register(user));
    }

    @Test
    void register_ExistingUser_NotOk() {
        User user = new User();
        user.setLogin("User");
        user.setPassword("567890");
        user.setAge(20);
        assertThrows(RuntimeException.class, () -> service.register(user));
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
        User user = new User();
        user.setLogin("userWithLongLogin12345");
        user.setPassword("12345212");
        user.setAge(28);
        assertThrows(RuntimeException.class, () -> service.register(user));
    }

    @Test
    void register_Ok() {
        User user = new User();
        user.setLogin("user");
        user.setPassword("newUser123");
        user.setAge(18);
        service.register(user);
        int actual = Storage.people.size();
        assertEquals(2, actual);
    }
}
