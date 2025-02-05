package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private final RegistrationServiceImpl service = new RegistrationServiceImpl();
    private User user;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user = new User();
        user.setLogin("test_test");
        user.setPassword("test_test");
        user.setAge(18);
    }

    @Test
    void register_passUserValidValue_Ok() {
        User actual = service.register(user);
        assertEquals(user, actual);
    }

    @Test
    public void register_passUserNullValue_TrowsException() {
        user = null;
        assertThrows(UserRegistrationExceptions.class, () -> service.register(user));
    }

    @Test
    void register_passThreeValidUsers_Ok() {
        User actual = service.register(user);
        assertEquals(user, actual);
        User user01 = new User();
        user01.setLogin("test_01");
        user01.setPassword("test_test");
        user01.setAge(18);
        User actual01 = service.register(user01);
        assertEquals(user01, actual01);
        User user02 = new User();
        user02.setLogin("test_02");
        user02.setPassword("test_test");
        user02.setAge(18);
        User actual02 = service.register(user02);
        assertEquals(user02, actual02);
    }

    @Test
    public void register_passSameUserTwice_TrowsException() {
        service.register(user);
        assertThrows(UserRegistrationExceptions.class, () -> service.register(user));
    }

    @Test
    public void register_passSameLoginUser_TrowsException() {
        service.register(user);
        user.setAge(21);
        user.setPassword("test_0001");
        assertThrows(UserRegistrationExceptions.class, () -> service.register(user));
    }

    @Test
    public void register_passUserInvalidLoginLength_TrowsException() {
        user.setLogin("test");
        assertThrows(UserRegistrationExceptions.class, () -> service.register(user));
    }

    @Test
    void register_passUserNullPassword_ThrowsException() {
        user.setPassword(null);
        assertThrows(UserRegistrationExceptions.class, () -> service.register(user));
    }

    @Test
    void register_passUserInvalidPassword_ThrowsException() {
        user.setPassword("test");
        assertThrows(UserRegistrationExceptions.class, () -> service.register(user));
    }

    @Test
    void register_passUserNullAge_ThrowsException() {
        user.setAge(null);
        assertThrows(UserRegistrationExceptions.class, () -> service.register(user));
    }

    @Test
    void register_passUserInvalidAge_ThrowsException() {
        user.setAge(-1);
        assertThrows(UserRegistrationExceptions.class, () -> service.register(user));
    }
}
