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
    void register_validUser_Ok() {
        User actual = service.register(user);
        assertEquals(user, actual);
    }

    @Test
    public void register_nullUser_notOk() {
        user = null;
        assertThrows(UserRegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_threeValidUsers_Ok() {
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
    public void register_passSameUserTwice_notOk() {
        service.register(user);
        assertThrows(UserRegistrationException.class, () -> service.register(user));
    }

    @Test
    public void register_sameLoginUser_notOk() {
        service.register(user);
        user.setAge(21);
        user.setPassword("test_0001");
        assertThrows(UserRegistrationException.class, () -> service.register(user));
    }

    @Test
    public void register_userInvalidLoginLength_notOk() {
        user.setLogin("test");
        assertThrows(UserRegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_userNullPassword_notOk() {
        user.setPassword(null);
        assertThrows(UserRegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_userInvalidPassword_notOk() {
        user.setPassword("test");
        assertThrows(UserRegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_userNullAge_notOk() {
        user.setAge(null);
        assertThrows(UserRegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_userInvalidAge_notOk() {
        user.setAge(-1);
        assertThrows(UserRegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_userMinLoginLength_Ok() {
        user.setLogin("test_0");
        assertEquals(user, service.register(user));
    }

    @Test
    void register_userUnderMinLoginLength_Ok() {
        user.setLogin("test0");
        assertThrows(UserRegistrationException.class, () -> service.register(user));
    }
}
