package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static final int INCORRECT_AGE = 8;
    private static final int CORRECT_AGE = 20;
    private static final long ID = 2;
    private RegistrationService registrationService;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        addTestUsers();
    }

    private void addTestUsers() {
        User userBob = new User();
        userBob.setId(1L);
        userBob.setLogin("111111");
        userBob.setPassword("aaaaaa");
        userBob.setAge(CORRECT_AGE);

        User userJohn = new User();
        userJohn.setId(2L);
        userJohn.setLogin("222222");
        userJohn.setPassword("bbbbbb");
        userJohn.setAge(CORRECT_AGE);

        storageDao.add(userBob);
        storageDao.add(userJohn);
    }

    @Test
    void register_userIncorrectRegistration_NotOk() {
        User user = null;

        assertThrows(ValidationException.class, () -> registrationService.register(user),
                "User can't be null");
    }

    @Test
    void register_userCorrectRegistration_Ok() {
        User user = new User();
        user.setId(ID);
        user.setLogin("333333");
        user.setPassword("cccccc");
        user.setAge(CORRECT_AGE);

        registrationService.register(user);
    }

    @Test
    void register_userExistLogin_throwException() {
        User user = new User();
        user.setId(ID);
        user.setLogin("111111");
        user.setPassword("dddddd");
        user.setAge(CORRECT_AGE);

        assertThrows(ValidationException.class, () -> registrationService.register(user),
                "Such user is already exist");
    }

    @Test
    void register_userIncorrectLogin_throwException() {
        User user = new User();
        user.setId(ID);
        user.setLogin("1111");
        user.setPassword("ddd1dd");
        user.setAge(CORRECT_AGE);

        assertThrows(ValidationException.class, () -> registrationService.register(user),
                "Login can't be less than 6 symbols");
    }

    @Test
    void register_userIncorrectPassword_throwException() {
        User user = new User();
        user.setId(ID);
        user.setLogin("111122");
        user.setPassword("dddd");
        user.setAge(CORRECT_AGE);

        assertThrows(ValidationException.class, () -> registrationService.register(user),
                "Password can't be less than 6 symbols");
    }

    @Test
    void register_userIncorrectAge_throwException() {
        User user = new User();
        user.setId(ID);
        user.setLogin("555555");
        user.setPassword("dddddd");
        user.setAge(INCORRECT_AGE);

        assertThrows(ValidationException.class, () -> registrationService.register(user),
                "Age can't be less than 18");
    }

    @Test
    void register_userNullLogin_throwException() {
        User user = new User();
        user.setId(ID);
        user.setLogin(null);
        user.setPassword("dddddd");
        user.setAge(CORRECT_AGE);

        assertThrows(ValidationException.class, () -> registrationService.register(user),
                "Can't be null login");
    }

    @Test
    void register_userNullPassword_throwException() {
        User user = new User();
        user.setId(ID);
        user.setLogin("555555");
        user.setPassword(null);
        user.setAge(CORRECT_AGE);

        assertThrows(ValidationException.class, () -> registrationService.register(user),
                "Can't be null password");
    }
}
