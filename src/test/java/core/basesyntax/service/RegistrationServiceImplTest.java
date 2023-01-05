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

    private static final String DEFAULT_LOGIN = "dimasmuk10@gmail.com";
    private static final int DEFAULT_AGE = 23;
    private static final String DEFAULT_PASSWORD = "1234qwerty";
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
        user.setLogin(DEFAULT_LOGIN);
        user.setAge(DEFAULT_AGE);
        user.setPassword(DEFAULT_PASSWORD);
    }

    @Test
    void register_userLoginOnNull_notOk() {
        user.setLogin(null);
        Exception exception = assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user), "Login can't be empty!");
    }

    @Test
    void register_userLoginIsEmpty_notOk() {
        user.setLogin("");
        Exception exception = assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user), "Login can't be empty!");
    }

    @Test
    void register_userLoginHasTheSameLogin_notOk() {
        storageDao.add(user);
        User user1 = new User();
        user1.setLogin(DEFAULT_LOGIN);
        user1.setAge(32);
        user1.setPassword("qws4123123");
        Exception exception = assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user1), "User with this login "
                        + user1.getLogin() + " is already registered!");
    }

    @Test
    void register_userPasswordIsNull_notOk() {
        user.setPassword(null);
        Exception exception = assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user), "Password can't be empty!");
    }

    @Test
    void register_userPasswordIsEmpty_notOk() {
        user.setPassword("");
        Exception exception = assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user), "Password can't be empty!");
    }

    @Test
    void register_userPasswordLengthLessMin_notOk() {
        user.setPassword("12");
        Exception exception = assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user), "Password length can't be"
                        + "less then 6!");
    }

    @Test
    void register_userAgeOnNull_notOk() {
        user.setAge(null);
        Exception exception = assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user), "Your age can't be null");
    }

    @Test
    void register_userAgeOlderThan18_notOk() {
        user.setAge(11);
        Exception exception = assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user), "Your age " + user.getAge() + " less"
                        + " then allowed: 18");
    }

    @Test
    void register_validUser_Ok() {
        assertEquals(user, registrationService.register(user),
                "User must be registered");
    }
}

