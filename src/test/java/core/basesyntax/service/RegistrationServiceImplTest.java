package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.UserAgeException;
import core.basesyntax.exception.UserLoginExistsException;
import core.basesyntax.exception.UserLoginLengthException;
import core.basesyntax.exception.UserPasswordLengthException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();

    @Test
    @DisplayName("User register with null login")
    void register_nullLogin_throwNullPointerException() {
        User userWithNullLogin = new User(null, "1234567", 45);
        assertThrows(NullPointerException.class,
                     () -> registrationService.register(userWithNullLogin));
    }

    @Test
    @DisplayName("User register with null password")
    void register_nullPassword_throwNullPointerException() {
        User userWithNullPassword = new User("ivanivanov12@gmail.com", null, 45);
        assertThrows(NullPointerException.class,
                     () -> registrationService.register(userWithNullPassword));
    }

    @Test
    @DisplayName("User register with null age")
    void register_nullAge_throwNullPointerException() {
        User userWithNullAge = new User("ivanivanov12@gmail.com", "1234567", null);
        assertThrows(NullPointerException.class,
                     () -> registrationService.register(userWithNullAge));
    }

    @Test
    @DisplayName("User register correct user case #1")
    void register_correctUser_ok() {
        User user = new User("mykhailo777@gmail.com", "1234567", 23);
        User actualUser = registrationService.register(user);
        User expectedUser = storageDao.get(user.getLogin());
        assertEquals(actualUser, expectedUser);
    }

    @Test
    @DisplayName("User register correct user case #2")
    void register_correctUserSecondCase_ok() {
        User user = new User("ivangolybev@gmail.com", "1234567", 28);
        User actualUser = registrationService.register(user);
        User expectedUser = storageDao.get(user.getLogin());
        assertEquals(actualUser, expectedUser);
    }

    @Test
    @DisplayName("User register correct user case #3")
    void register_correctUserThirdCase_ok() {
        User user = new User("oleksiiivanov@gmail.com", "1234567", 33);
        User actualUser = registrationService.register(user);
        User expectedUser = storageDao.get(user.getLogin());
        assertEquals(actualUser, expectedUser);
    }

    @Test
    @DisplayName("User login already exists register")
    void register_userLoginExist_throwUserLoginExistException() {
        User user = new User("mykhailo88525@gmail.com", "1234567", 23);
        User actualUser = registrationService.register(user);
        assertThrows(UserLoginExistsException.class,
                     () -> registrationService.register(actualUser));
    }

    @Test
    @DisplayName("User password length less than minimum")
    void register_userPasswordLessThanMinimum_throwUserPasswordLengthException() {
        User userWithIncorrectPassword = new User("mykhailo12@gmail.com", "123", 82);
        assertThrows(UserPasswordLengthException.class,
                     () -> registrationService.register(userWithIncorrectPassword));
    }

    @Test
    @DisplayName("User login length less than minimum")
    void register_userLoginLessThanMinimum_throwUserLoginLengthException() {
        User userWithIncorrectLogin = new User("inc", "12345678", 21);
        assertThrows(UserLoginLengthException.class,
                     () -> registrationService.register(userWithIncorrectLogin));
    }

    @Test
    @DisplayName("User age less than minimum")
    void register_userAgeLessThanMinimum_throwUserPasswordLengthException() {
        User userWithIncorrectAge = new User("ivan16161", "12345667", 17);
        assertThrows(UserAgeException.class,
                     () -> registrationService.register(userWithIncorrectAge));
    }
}
