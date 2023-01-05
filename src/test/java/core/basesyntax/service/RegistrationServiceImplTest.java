package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationServiceImpl;
    private static StorageDao storageDao;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationServiceImpl = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void beforeEach() {
        user = new User();
    }

    @Test
    void register_isOk() {
        user.setLogin("Yura");
        user.setPassword("qwerty1999");
        user.setAge(23);
        User registeredUser = registrationServiceImpl.register(user);
        assertSame(user, registeredUser);
    }

    @Test
    void register_userLoginIsNull_notOk() {
        user.setPassword("qwerty1999");
        user.setAge(23);
        Exception exception = assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(user));
        assertEquals("Login should not be null.", exception.getMessage());
    }

    @Test
    void register_userLoginIsBlank_notOk() {
        user.setLogin(" ");
        user.setPassword("qwerty1999");
        user.setAge(23);
        Exception exception = assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(user));
        assertEquals("Login should not be blank.", exception.getMessage());
    }

    @Test
    void register_userLoginIsEmpty_notOk() {
        user.setLogin("");
        user.setPassword("qwerty1999");
        user.setAge(23);
        Exception exception = assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(user));
        assertEquals("Login should not be empty.", exception.getMessage());
    }

    @Test
    void register_userLoginIsTooShort_notOk() {
        user.setLogin("Oi");
        user.setPassword("qwerty1999");
        user.setAge(23);
        Exception exception = assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(user));
        assertEquals("User login should be at least 3 characters.", exception.getMessage());
    }

    @Test
    void register_userLoginIsTooLong_notOk() {
        user.setLogin("21symbols21symbols21_");
        user.setPassword("qwerty1999");
        user.setAge(23);
        Exception exception = assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(user));
        assertEquals("User login should not be more than 20 characters.", exception.getMessage());
    }

    @Test
    void register_existingUserLogin_notOk() {
        user.setLogin("YuraDikalo");
        user.setPassword("qwerty1999");
        user.setAge(23);
        storageDao.add(user);
        User user2 = new User();
        user2.setLogin("YuraDikalo");
        user2.setPassword("qwerty2001");
        user2.setAge(21);
        Exception exception = assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(user2));
        assertEquals("User with login " + user.getLogin()
                + " is already registered.", exception.getMessage());
    }

    @Test
    void register_userPasswordIsNull_notOk() {
        user.setLogin("sweaeter");
        user.setAge(23);
        Exception exception = assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(user));
        assertEquals("Password should not be null.", exception.getMessage());
    }

    @Test
    void register_userPasswordIsBlank_notOk() {
        user.setLogin("sweaeter");
        user.setPassword(" ");
        user.setAge(23);
        Exception exception = assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(user));
        assertEquals("Password should not be blank.", exception.getMessage());
    }

    @Test
    void register_userPasswordIsEmpty_notOk() {
        user.setLogin("sweaeter");
        user.setPassword("");
        user.setAge(23);
        Exception exception = assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(user));
        assertEquals("Password should not be empty.", exception.getMessage());
    }

    @Test
    void register_userPasswordIsTooShort_notOk() {
        user.setLogin("sweaeter");
        user.setPassword("qwert");
        user.setAge(23);
        Exception exception = assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(user));
        assertEquals("Password should be at least 6 characters.", exception.getMessage());
    }

    @Test
    void register_userPasswordIsTooLong_notOk() {
        user.setLogin("sweaeter");
        user.setPassword("26symbols26symbols26symbol");
        user.setAge(23);
        Exception exception = assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(user));
        assertEquals("Password should not be more than 25 characters.", exception.getMessage());
    }

    @Test
    void register_userAgeIsNull_notOk() {
        user.setLogin("sweaeter");
        user.setPassword("qwerty1999");
        Exception exception = assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(user));
        assertEquals("User age should not be null.", exception.getMessage());
    }

    @Test
    void register_userAgeIsLessThanMinAge_notOk() {
        user.setLogin("sweaeter");
        user.setPassword("qwerty1999");
        user.setAge(17);
        Exception exception = assertThrows(InvalidUserDataException.class,
                () -> registrationServiceImpl.register(user));
        assertEquals("User age should not be less than min age.", exception.getMessage());
    }
}
