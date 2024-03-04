package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        setUp();
    }

    // It this method we add two users in storageDao
    private static void setUp() {
        User userBob = new User();
        userBob.setId(1L);
        userBob.setLogin("111111");
        userBob.setPassword("aaaaaa");
        userBob.setAge(20);

        User userJohn = new User();
        userJohn.setId(2L);
        userJohn.setLogin("222222");
        userJohn.setPassword("bbbbbb");
        userJohn.setAge(22);

        storageDao.add(userBob);
        storageDao.add(userJohn);
    }

    @Test
    void register_userIncorrectRegistration_NotOk() {
        User user = null;

        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userCorrectRegistration_Ok() {
        User user = new User();
        user.setId(3L);
        user.setLogin("333333");
        user.setPassword("cccccc");
        user.setAge(25);

        registrationService.register(user);
    }

    @Test
    void register_userExistLogin_throwException() {
        User user = new User();
        user.setId(4L);
        user.setLogin("111111"); // this login is already exist
        user.setPassword("dddddd");
        user.setAge(85);

        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userIncorrectLogin_throwException() {
        User user = new User();
        user.setId(4L);
        user.setLogin("1111"); // this login is incorrect
        user.setPassword("dddddd");
        user.setAge(55);

        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userIncorrectPassword_throwException() {
        User user = new User();
        user.setId(4L);
        user.setLogin("1111");
        user.setPassword("dddd"); // this password is incorrect
        user.setAge(85);

        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userIncorrectAge_throwException() {
        User user = new User();
        user.setId(4L);
        user.setLogin("555555");
        user.setPassword("dddddd");
        user.setAge(5); // age can't be less than 18

        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userNullLogin_throwException() {
        User user = new User();
        user.setId(4L);
        user.setLogin(null); // login can't be null
        user.setPassword("dddddd");
        user.setAge(5);

        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userNullPassword_throwException() {
        User user = new User();
        user.setId(4L);
        user.setLogin("555555");
        user.setPassword(null); // password can't be null
        user.setAge(5); // age can't be less than 18

        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }
}
