package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.exception.RegisterException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDaoImpl storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    void register_userAlreadyExists_notOk() {
        User user = new User();
        user.setLogin("ExistsLogin");
        user.setPassword("ValidPassword");
        user.setAge(25);
        storageDao.add(user);
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void loginIsValid_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("ValidPassword");
        user.setAge(25);
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void loginIsValid_emptyStringLogin_notOk() {
        User user = new User();
        user.setLogin("");
        user.setPassword("ValidPassword");
        user.setAge(25);
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void loginIsValid_lessThenSixCharactersLogin_notOk() {
        User user = new User();
        user.setLogin("Login");
        user.setPassword("ValidPassword");
        user.setAge(25);
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void loginIsValid_sixCharactersLogin_ok() {
        User user = new User();
        user.setLogin("Login6");
        user.setPassword("ValidPassword");
        user.setAge(25);
        assertEquals(user.getLogin(),registrationService.register(user).getLogin());
    }

    @Test
    void loginIsValid_moreThenSixCharactersLogin_ok() {
        User user = new User();
        user.setLogin("UniqueLogin");
        user.setPassword("ValidPassword");
        user.setAge(25);
        assertEquals(user.getLogin(),registrationService.register(user).getLogin());
    }

    @Test
    void passwordIsValid_nullPassword_notOk() {
        User user = new User();
        user.setLogin("NullPassLogin");
        user.setPassword(null);
        user.setAge(25);
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void passwordIsValid_emptyStringPassword_notOk() {
        User user = new User();
        user.setLogin("EmptyPassLogin");
        user.setPassword("");
        user.setAge(25);
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void passwordIsValid_lessThenSixCharactersPassword_notOk() {
        User user = new User();
        user.setLogin("ShortPassLogin");
        user.setPassword("Pass");
        user.setAge(25);
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void passwordIsValid_sixCharactersPassword_ok() {
        User user = new User();
        user.setLogin("SixPassLogin");
        user.setPassword("ValPas");
        user.setAge(25);
        assertEquals(user.getPassword(),registrationService.register(user).getPassword());
    }

    @Test
    void passwordIsValid_moreThenSixCharactersPassword_ok() {
        User user = new User();
        user.setLogin("LongPassLogin");
        user.setPassword("NewPassword");
        user.setAge(25);
        assertEquals(user.getPassword(),registrationService.register(user).getPassword());
    }

    @Test
    void ageIsValid_nullAge_notOk() {
        User user = new User();
        user.setLogin("NullAgeLogin");
        user.setPassword("NewPassword");
        user.setAge(null);
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void ageIsValid_lessThenEighteenAge_notOk() {
        User user = new User();
        user.setLogin("YoungAgeLogin");
        user.setPassword("NewPassword");
        user.setAge(17);
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void ageIsValid_equalsEighteenAge_ok() {
        User user = new User();
        user.setLogin("Age18Login");
        user.setPassword("NewPassword");
        user.setAge(18);
        assertEquals(user.getAge(),registrationService.register(user).getAge());
    }

    @Test
    void ageIsValid_moreThenEighteenAge_ok() {
        User user = new User();
        user.setLogin("Age18PlusLogin");
        user.setPassword("NewPassword");
        user.setAge(30);
        assertEquals(user.getAge(),registrationService.register(user).getAge());
    }

    @Test
    void register_nullUser_notOk() {
        User user = null;
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void register_addSuccessfulUser_ok() {
        User user = new User();
        user.setLogin("SuccessLogin");
        user.setPassword("ValidPassword");
        user.setAge(25);
        User registeredUser = registrationService.register(user);
        User actual = storageDao.get("SuccessLogin");
        assertNotNull(registeredUser);
        assertEquals(user, actual);
    }

    @Test
    void register_addAndGetUserDetails_ok() {
        User user = new User();
        user.setLogin("AddGetLogin");
        user.setPassword("ValidPassword");
        user.setAge(25);
        registrationService.register(user);
        User actual = storageDao.get("AddGetLogin");
        assertEquals("AddGetLogin", actual.getLogin());
        assertEquals("ValidPassword", actual.getPassword());
        assertEquals(25, actual.getAge());
        assertEquals(user, actual);
    }
}
