package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final String EXPECTED_EXCEPTION =
            ValidationException.class.getSimpleName();
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        Assertions.assertThrows(ValidationException.class,
                () -> registrationService.register(null),
                String.format("Should throw %s when user is null", EXPECTED_EXCEPTION));
    }

    @Test
    void register_positiveTest_Ok(){
        User user = new User();
        user.setAge(18);
        user.setLogin("User");
        user.setPassword("123456");
        storageDao.add(user);
    }

    @Test
    void register_newUser_Ok() {
        User user = new User();
        user.setLogin("User");
        user.setPassword("123456789");
        user.setAge(20);
        registrationService.register(user);
        User actual = storageDao.get(user.getLogin());
        Assertions.assertEquals(user, actual,
                "User should be add, if user doesn't exist");
    }

    @Test
    void register_login_not_Ok() {
        User user = new User();
        user.setAge(19);
        user.setPassword("1234567");
        user.setLogin(null);
        Assertions.assertThrows(ValidationException.class,
                () -> registrationService.register(user),
                String.format("Should throw %s when login is null", EXPECTED_EXCEPTION));
    }

    @Test
    void register_password_not_Ok() {
        User user = new User();
        user.setPassword("1234");
        user.setAge(19);
        user.setLogin("Name");
        Assertions.assertThrows(ValidationException.class,
                () -> registrationService.register(user),
                String.format("Should throw %s when password less than %d characters", EXPECTED_EXCEPTION, MIN_AGE));
    }
    @Test
    void register_passwordNull_not_Ok() {
        User user = new User();
        user.setPassword(null);
        user.setAge(19);
        user.setLogin("Name");
        Assertions.assertThrows(ValidationException.class,
                () -> registrationService.register(user),
                String.format("Should throw %s when password is null", EXPECTED_EXCEPTION));
    }

    @Test
    void register_age_not_OK() {
        User user = new User();
        user.setLogin("Name");
        user.setAge(17);
        user.setPassword("123456");
        Assertions.assertThrows(ValidationException.class,
                () -> registrationService.register(user),
                String.format("Should throw %s when age less than &d", EXPECTED_EXCEPTION, MIN_AGE));
    }
    @Test
    void register_Password_Ok() {
        User user = new User();
        user.setLogin("User");
        user.setPassword("12345678910");
        user.setAge(21);
        registrationService.register(user);
        User actual = storageDao.get(user.getLogin());
        Assertions.assertEquals(user, actual,
                "User should be added if user password is valid");
    }

}
