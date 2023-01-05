package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidUserDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static StorageDao storageDao;
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();

    }

    @BeforeEach
    void beforeEach() {
        user = new User();
    }

    @Test
    void register_userLoginOnNull_notOk() {
        user.setAge(23);
        user.setPassword("qwer1234");
        Exception exception = assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user));
        assertEquals("Login can't be empty!", exception.getMessage());
    }

    @Test
    void register_userLoginIsEmpty_notOk() {
        user.setLogin("");
        user.setAge(23);
        user.setPassword("qwer1234");
        Exception exception = assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user));
        assertEquals("Login can't be empty!", exception.getMessage());
    }

    @Test
    void register_userLoginHasTheSameLogin_notOk() {
        user.setLogin("dima");
        user.setAge(23);
        user.setPassword("qwer1234");
        storageDao.add(user);
        User user1 = new User();
        user1.setLogin("dima");
        user1.setAge(32);
        user1.setPassword("qws4123123");
        Exception exception = assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user1));
        assertEquals("User with this login "
                + user1.getLogin() + " is already registered!", exception.getMessage());
    }

    @Test
    void register_userPasswordIsNull_notOk() {
        user.setLogin("dima");
        user.setAge(25);
        Exception exception = assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user));
        assertEquals("Password can't be empty!", exception.getMessage());
    }

    @Test
    void register_userPasswordIsEmpty_notOk() {
        user.setLogin("dimasa");
        user.setPassword("");
        user.setAge(25);
        Exception exception = assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user));
        assertEquals("Password can't be empty!", exception.getMessage());
    }

    @Test
    void register_userPasswordLengthLessMin_notOk() {
        user.setLogin("dima");
        user.setPassword("12");
        user.setAge(25);
        Exception exception = assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user));
        assertEquals("Password length can't be"
                + "less then 6!", exception.getMessage());
    }

    @Test
    void register_userAgeOnNull_notOk() {
        user.setLogin("dima");
        user.setPassword("qwer1234");
        Exception exception = assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user));
        assertEquals("Your age can't be null", exception.getMessage());
    }

    @Test
    void register_userAgeOlderThan18_notOk() {
        user.setLogin("dima");
        user.setAge(11);
        user.setPassword("qwer1234");
        Exception exception = assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user));
        assertEquals("Your age " + user.getAge() + " less"
                + " then allowed: 18", exception.getMessage());
    }

    @Test
    void register_validUser_Ok() {
        user.setLogin("dimamk");
        user.setAge(64);
        user.setPassword("qwerty213");
        assertEquals(user, registrationService.register(user),
                "User must be registered");
    }
}

