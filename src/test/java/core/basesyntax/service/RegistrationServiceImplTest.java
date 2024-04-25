package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl service;

    @BeforeAll
    public static void setUp() {
        service = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_validUser_ok() {
        User user = initValidUser();
        assertEquals(service.register(user), user);
    }

    @Test
    void register_loginNull_notOk() {
        User user = initValidUser();
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_passwordNull_notOk() {
        User user = initValidUser();
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_ageNull_notOk() {
        User user = initValidUser();
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_userNull_notOk() {
        User user = null;
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_loginLengthIsMin_ok() {
        User user = initValidUser();
        user.setLogin("123456");
        assertEquals(service.register(user), user);
    }

    @Test
    void register_loginLengthLessThanMin_notOk() {
        User user = initValidUser();
        user.setLogin("12345");
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_loginLengthMoreThanMin_ok() {
        User user = initValidUser();
        user.setLogin("1234567");
        assertEquals(service.register(user), user);
    }

    @Test
    void register_passwordLengthMoreThanMin_ok() {
        User user = initValidUser();
        user.setPassword("1234567");
        assertEquals(service.register(user), user);
    }

    @Test
    void register_passwordLengthIsMin_ok() {
        User user = initValidUser();
        user.setPassword("123456");
        assertEquals(service.register(user), user);
    }

    @Test
    void register_passwordLengthLessThanMin_notOk() {
        User user = initValidUser();
        user.setPassword("12345");
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_loginLengthZero_notOk() {
        User user = initValidUser();
        user.setLogin("");
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_passwordLengthZero_notOk() {
        User user = initValidUser();
        user.setPassword("");
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_ageIsMinAge_ok() {
        User user = initValidUser();
        user.setAge(RegistrationServiceImpl.MIN_AGE);
        assertEquals(service.register(user), user);
    }

    @Test
    void register_ageIsNegativeNumber_notOk() {
        User user = initValidUser();
        user.setAge(-5);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_ageIsLessThanMinAge_notOk() {
        User user = initValidUser();
        user.setAge(RegistrationServiceImpl.MIN_AGE - 1);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_ageIsMoreThanMinAge_ok() {
        User user = initValidUser();
        user.setAge(RegistrationServiceImpl.MIN_AGE + 1);
        assertEquals(service.register(user), user);
    }

    @Test
    void register_ageIsZero_notOk() {
        User user = initValidUser();
        user.setAge(0);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void check_ifUserAdded_ok() {
        User user = initValidUser();
        service.register(user);
        assertEquals(Storage.people.get(0), user);
    }

    @Test
    void register_addExistedUser_notOk() {
        User user = initValidUser();
        user.setLogin("loginTest");
        service.register(user);
        final User sameUser = initValidUser();
        sameUser.setLogin("loginTest");
        assertThrows(RegistrationException.class, () -> service.register(sameUser));
    }

    private User initValidUser() {
        User newUser = new User();
        newUser.setId(1L);
        newUser.setAge(20);
        newUser.setLogin("testLogin");
        newUser.setPassword("testPassword");
        return newUser;
    }
}
