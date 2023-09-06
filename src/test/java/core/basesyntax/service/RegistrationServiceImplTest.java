package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.exception.InvalidInputDataException;
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
        user.setLogin("ExistsLogin");
        user.setPassword("ValidPassword");
        user.setAge(25);
        registrationService.register(user);
        assertThrows(UserAlreadyExistsException.class, () -> registrationService.register(user));
    }

    @Test
    void loginIsValid_nullLogin_notOk() {
        user.setLogin(null);
        user.setPassword("ValidPassword");
        user.setAge(25);
        assertThrows(InvalidInputDataException.class, () -> registrationService.register(user));
    }

    @Test
    void loginIsValid_emptyStringLogin_notOk() {
        user.setLogin("");
        user.setPassword("ValidPassword");
        user.setAge(25);
        assertThrows(InvalidInputDataException.class, () -> registrationService.register(user));
    }

    @Test
    void loginIsValid_lessThenSixCharactersLogin_notOk() {
        user.setLogin("Login");
        user.setPassword("ValidPassword");
        user.setAge(25);
        assertThrows(InvalidInputDataException.class, () -> registrationService.register(user));
    }

    @Test
    void loginIsValid_sixCharactersLogin_ok() {
        user.setLogin("Login6");
        user.setPassword("ValidPassword");
        user.setAge(25);
        assertTrue(registrationService.loginIsValid(user.getLogin()));
        assertEquals(user.getLogin(),registrationService.register(user).getLogin());
    }

    @Test
    void loginIsValid_moreThenSixCharactersLogin_ok() {
        user.setLogin("UniqueLogin");
        user.setPassword("ValidPassword");
        user.setAge(25);
        assertTrue(registrationService.loginIsValid(user.getLogin()));
        assertEquals(user.getLogin(),registrationService.register(user).getLogin());
    }

    @Test
    void passwordIsValid_nullPassword_notOk() {
        user.setLogin("NullPassLogin");
        user.setPassword(null);
        user.setAge(25);
        assertThrows(InvalidInputDataException.class, () -> registrationService.register(user));
    }

    @Test
    void passwordIsValid_emptyStringPassword_notOk() {
        user.setLogin("EmptyPasseLogin");
        user.setPassword("");
        user.setAge(25);
        assertThrows(InvalidInputDataException.class, () -> registrationService.register(user));
    }

    @Test
    void passwordIsValid_lessThenSixCharactersPassword_notOk() {
        user.setLogin("ShortPassLogin");
        user.setPassword("Pass");
        user.setAge(25);
        assertThrows(InvalidInputDataException.class, () -> registrationService.register(user));
    }

    @Test
    void passwordIsValid_sixCharactersPassword_ok() {
        user.setLogin("SixPassLogin");
        user.setPassword("ValPas");
        user.setAge(25);
        assertTrue(registrationService.passwordIsValid(user.getPassword()));
        assertEquals(user.getPassword(),registrationService.register(user).getPassword());
    }

    @Test
    void passwordIsValid_moreThenSixCharactersPassword_ok() {
        user.setLogin("LongPassLogin");
        user.setPassword("NewPassword");
        user.setAge(25);
        assertTrue(registrationService.passwordIsValid(user.getPassword()));
        assertEquals(user.getPassword(),registrationService.register(user).getPassword());
    }

    @Test
    void ageIsValid_nullAge_notOk() {
        user.setLogin("NullAgeLogin");
        user.setPassword("NewPassword");
        user.setAge(null);
        assertThrows(InvalidInputDataException.class, () -> registrationService.register(user));
    }

    @Test
    void ageIsValid_lessThenEighteenAge_notOk() {
        user.setLogin("YoungAgeLogin");
        user.setPassword("NewPassword");
        user.setAge(15);
        assertThrows(InvalidInputDataException.class, () -> registrationService.register(user));
    }

    @Test
    void ageIsValid_equalsEighteenAge_ok() {
        user.setLogin("Age18Login");
        user.setPassword("NewPassword");
        user.setAge(18);
        assertTrue(registrationService.ageIsValid(user.getAge()));
        assertEquals(user.getAge(),registrationService.register(user).getAge());
    }

    @Test
    void ageIsValid_moreThenEighteenAge_ok() {
        user.setLogin("Age18PlusLogin");
        user.setPassword("NewPassword");
        user.setAge(30);
        assertTrue(registrationService.ageIsValid(user.getAge()));
        assertEquals(user.getAge(),registrationService.register(user).getAge());
    }

    @Test
    void register_nullUser_notOk() {
        user = null;
        assertThrows(UserIsNullException.class, () -> registrationService.register(user));
    }

    @Test
    void register_addSuccessfulUser_ok() {
        user.setLogin("SuccessLogin");
        user.setPassword("ValidPassword");
        user.setAge(25);
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void register_addAndGetUserDetails_ok() {
        user.setLogin("AddGetLogin");
        user.setPassword("ValidPassword");
        user.setAge(25);
        registrationService.register(user);;
        assertEquals("AddGetLogin", storageDao.get(user.getLogin()).getLogin());
        assertEquals("ValidPassword", storageDao.get(user.getLogin()).getPassword());
        assertEquals(25, storageDao.get(user.getLogin()).getAge());
        assertEquals(user, storageDao.get(user.getLogin()));
    }
}
