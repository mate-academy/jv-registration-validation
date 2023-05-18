package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private RegistrationService registrationService;
    private StorageDao storageDao;

    @BeforeEach
    public void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storageDao);
    }

    @Test
    public void register_validUser_registersSuccessfully() {
        User user = createUser("user12345", "qwerty123", 20);

        User registeredUser = registrationService.register(user);

        Assertions.assertEquals(user, registeredUser);
        Assertions.assertEquals(registeredUser, storageDao.get(registeredUser.getLogin()));
    }

    @Test
    public void register_nullUser_throwsInvalidUserException() {
        Assertions.assertThrows(InvalidUserException.class, () ->
                registrationService.register(null));
    }

    @Test
    public void register_nullLogin_throwsInvalidUserException() {
        User user = createUser(null, "qwerty123", 20);

        Assertions.assertThrows(InvalidUserException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void register_shortLogin_throwsInvalidUserException() {
        User user = createUser("user", "qwerty123", 20);

        Assertions.assertThrows(InvalidUserException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void register_nullPassword_throwsInvalidUserException() {
        User user = createUser("user12345", null, 20);

        Assertions.assertThrows(InvalidUserException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void register_shortPassword_throwsInvalidUserException() {
        User user = createUser("user12345", "pass", 20);

        Assertions.assertThrows(InvalidUserException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void register_negativeAge_throwsInvalidUserException() {
        User user = createUser("user12345", "password123", -25);

        Assertions.assertThrows(InvalidUserException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void register_nullAge_throwsInvalidUserException() {
        User user = createUser("user12345", "qwerty123", null);

        Assertions.assertThrows(InvalidUserException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void register_under18Age_throwsInvalidUserException() {
        User user = createUser("user12345", "password123", 17);

        Assertions.assertThrows(InvalidUserException.class, () ->
                registrationService.register(user));
    }

    private User createUser(String login, String password, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}
