package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("Login");
        user.setPassword("password");
        user.setAge(30);
    }

    @Test
    void register_userCorrectValue_Ok() {
        registrationService.register(user);
        User newUser = storageDao.get(user.getLogin());
        assertNotNull(storageDao.get(user.getLogin()),
                "Dao should return not null value");
        assertEquals(newUser.getLogin(), user.getLogin(),
                "Expected user login is " + user.getLogin()
                        + ", but was " + newUser.getLogin() + ".");
        assertEquals(newUser.getPassword(), user.getPassword(),
                "Expected user password is " + user.getPassword()
                        + ", but was " + newUser.getPassword() + ".");
        assertEquals(newUser.getAge(), user.getAge(),
                "Expected user age is " + user.getAge()
                        + ", but was " + newUser.getAge() + ".");
    }

    @Test
    void register_userNullValue_NotOk() {
        user = null;
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "A RegistrationException should be thrown if user == null.");
    }

    @Test
    void register_passwordNullValue_NotOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "A RegistrationException should be thrown if user password == null.");
    }

    @Test
    void register_loginNullValue_NotOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "A RegistrationException should be thrown if user login == null.");
    }

    @Test
    void register_ageNullValue_NotOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "A RegistrationException should be thrown if user age == null.");
    }

    @Test
    void register_loginDuplicate_NotOk() {
        user.setLogin("secondLogin");
        registrationService.register(user);
        User sameUser = storageDao.get(user.getLogin());
        assertThrows(RegistrationException.class, () -> registrationService.register(sameUser),
                "A RegistrationException should be thrown if user login already exist.");
    }

    @Test
    void register_ageTooLow_NotOk() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "A RegistrationException should be thrown if "
                + "user age less than 18, but was " + user.getAge() + ".");
    }

    @Test
    void register_passwordTooShort_NotOk() {
        user.setPassword("12345");
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "A RegistrationException should be thrown if user password"
                + " length less than 18, but was " + user.getPassword().length() + ".");
    }
}
