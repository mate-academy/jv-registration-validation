package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.exception.UserAlreadyExistsException;
import core.basesyntax.service.exception.UserIsNullException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService;
    private StorageDaoImpl storageDao;
    private User user;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        user = new User();
    }

    @Test
    void userExists_true_ok() {
        user.setLogin("FirstLogin");
        storageDao.add(user);
        assertTrue(registrationService.userExists("FirstLogin"));
    }

    @Test
    void loginIsValid_nullLogin_notOk() {
        user.setLogin(null);
        assertFalse(registrationService.loginIsValid(user.getLogin()));
    }

    @Test
    void loginIsValid_emptyStringLogin_notOk() {
        user.setLogin("");
        assertFalse(registrationService.loginIsValid(user.getLogin()));
    }

    @Test
    void loginIsValid_sixCharactersLogin_ok() {
        user.setLogin("UniLog");
        assertTrue(registrationService.loginIsValid(user.getLogin()));
    }

    @Test
    void loginIsValid_moreThenSixCharactersLogin_ok() {
        user.setLogin("UniqueLogin");
        assertTrue(registrationService.loginIsValid(user.getLogin()));
    }

    @Test
    void passwordIsValid_nullPassword_notOk() {
        user.setPassword(null);
        assertFalse(registrationService.passwordIsValid(user.getPassword()));
    }

    @Test
    void passwordIsValid_emptyStringPassword_notOk() {
        user.setPassword("");
        assertFalse(registrationService.passwordIsValid(user.getPassword()));
    }

    @Test
    void passwordIsValid_sixCharactersPassword_ok() {
        user.setPassword("NewPass");
        assertTrue(registrationService.passwordIsValid(user.getPassword()));
    }

    @Test
    void passwordIsValid_moreThenSixCharactersPassword_ok() {
        user.setPassword("NewPassword");
        assertTrue(registrationService.passwordIsValid(user.getPassword()));
    }

    @Test
    void ageIsValid_nullAge_notOk() {
        user.setAge(null);
        assertFalse(registrationService.ageIsValid(user.getAge()));
    }

    @Test
    void ageIsValid_negativeNumberAge_notOk() {
        user.setAge(-1);
        assertFalse(registrationService.ageIsValid(user.getAge()));
    }

    @Test
    void ageIsValid_lessThenEighteenAge_notOk() {
        user.setAge(15);
        assertFalse(registrationService.ageIsValid(user.getAge()));
    }

    @Test
    void ageIsValid_equalsEighteenAge_ok() {
        user.setAge(18);
        assertTrue(registrationService.ageIsValid(user.getAge()));
    }

    @Test
    void ageIsValid_moreThenEighteenAge_ok() {
        user.setAge(30);
        assertTrue(registrationService.ageIsValid(user.getAge()));
    }

    @Test
    void register_nullUser_notOk() {
        user = null;
        assertThrows(UserIsNullException.class, () -> registrationService.register(user));
    }

    @Test
    void register_addUser_ok() {
        user.setLogin("UniLogin");
        user.setPassword("ValidPassword");
        user.setAge(25);
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void register_addAndGetUser_ok() {
        user.setLogin("UniqueLogin");
        user.setPassword("ValidPassword");
        user.setAge(25);
        registrationService.register(user);;
        assertEquals("UniqueLogin", storageDao.get(user.getLogin()).getLogin());
        assertEquals("ValidPassword", storageDao.get(user.getLogin()).getPassword());
        assertEquals(25, storageDao.get(user.getLogin()).getAge());
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void register_userNotRegisteredAgeNotValid_ok() {
        user.setLogin("NewUniqueLogin");
        user.setPassword("ValidPassword");
        user.setAge(15);
        assertEquals(null, registrationService.register(user));
    }

    @Test
    void register_userNotRegisteredLoginNotValid_ok() {
        user.setLogin("Short");
        user.setPassword("ValidPassword");
        user.setAge(25);
        assertEquals(null, registrationService.register(user));
    }

    @Test
    void register_userNotRegisteredPasswordNotValid_ok() {
        user.setLogin("SecondLogin");
        user.setPassword("Short");
        user.setAge(25);
        assertEquals(null, registrationService.register(user));
    }

    @Test
    void register_userNotRegisteredUserExists_ok() {
        user.setLogin("FirstLogin");
        user.setPassword("ValidPassword");
        user.setAge(25);
        storageDao.add(user);
        assertThrows(UserAlreadyExistsException.class,
                () -> registrationService.register(user));
    }
}
