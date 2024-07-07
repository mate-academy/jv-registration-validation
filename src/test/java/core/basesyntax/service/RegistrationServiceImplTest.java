package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();

    @Test
    @DisplayName("User register with null login")
    void register_nullLogin_notOk() {
        User userWithNullLogin = new User(null, "1234567", 45);
        assertThrows(RegistrationException.class,
                     () -> registrationService.register(userWithNullLogin));
    }

    @Test
    @DisplayName("User register with null password")
    void register_nullPassword_notOk() {
        User userWithNullPassword = new User("ivanivanov12@gmail.com", null, 45);
        assertThrows(RegistrationException.class,
                     () -> registrationService.register(userWithNullPassword));
    }

    @Test
    @DisplayName("User register with null age")
    void register_nullAge_notOk() {
        User userWithNullAge = new User("ivanivanov12@gmail.com", "1234567", null);
        assertThrows(RegistrationException.class,
                     () -> registrationService.register(userWithNullAge));
    }

    @Test
    @DisplayName("User register correct user")
    void register_correctUser_ok() {
        User user = new User("mykhailo777@gmail.com", "1234567", 23);
        registrationService.register(user);
        User expectedUser = storageDao.get(user.getLogin());
        assertEquals(user, expectedUser);
    }

    @Test
    @DisplayName("User login already exists register")
    void register_userLoginExist_notOk() {
        User user = new User("mykhailo88525@gmail.com", "1234567", 23);
        Storage.people.add(user);
        User newUser = new User("mykhailo88525@gmail.com", "1234567", 23);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(newUser));
    }

    @Test
    @DisplayName("User password length less than minimum")
    void register_userPasswordLessThanMinimum_notOK() {
        User userWithIncorrectPassword = new User("mykhailo12@gmail.com", "123", 82);
        assertThrows(RegistrationException.class,
                     () -> registrationService.register(userWithIncorrectPassword));
    }

    @Test
    @DisplayName("User login length less than minimum")
    void register_userLoginLessThanMinimum_notOk() {
        User userWithIncorrectLogin = new User("inc", "12345678", 21);
        assertThrows(RegistrationException.class,
                     () -> registrationService.register(userWithIncorrectLogin));
    }

    @Test
    @DisplayName("User age less than minimum")
    void register_userAgeLessThanMinimum_notOk() {
        User userWithIncorrectAge = new User("ivan16161", "12345667", 17);
        assertThrows(RegistrationException.class,
                     () -> registrationService.register(userWithIncorrectAge));
    }
}
