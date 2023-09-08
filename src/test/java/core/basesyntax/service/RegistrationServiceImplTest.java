package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.exception.RegisterException;
import org.junit.jupiter.api.AfterEach;
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

    @AfterEach
    void afterEach() {
        Storage.PEOPLE.clear();
    }

    @Test
     void register_userAlreadyExists_notOk() {
        User user = new User();
        user.setLogin("UniqueLogin");
        user.setPassword("ValidPassword");
        user.setAge(25);
        storageDao.add(user);
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("ValidPassword");
        user.setAge(25);
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyStringLogin_notOk() {
        User user = new User();
        user.setLogin("");
        user.setPassword("ValidPassword");
        user.setAge(25);
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginLessThenMinLength_notOk() {
        User user = new User();
        user.setLogin("Login");
        user.setPassword("ValidPassword");
        user.setAge(25);
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void register_minLengthLogin_ok() {
        User user = new User();
        user.setLogin("UniqueLogin");
        user.setPassword("ValidPassword");
        user.setAge(25);
        User actualUser = registrationService.register(user);
        assertEquals(user.getLogin(), actualUser.getLogin());
    }

    @Test
    void register_moreThenMinLengthLogin_ok() {
        User user = new User();
        user.setLogin("UniqueLogin");
        user.setPassword("ValidPassword");
        user.setAge(25);
        User actualUser = registrationService.register(user);
        assertEquals(user.getLogin(), actualUser.getLogin());
    }

    @Test
    void pregister_nullPassword_notOk() {
        User user = new User();
        user.setLogin("UniqueLogin");
        user.setPassword(null);
        user.setAge(25);
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyStringPassword_notOk() {
        User user = new User();
        user.setLogin("UniqueLogin");
        user.setPassword("");
        user.setAge(25);
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void register_lessThenMinLengthPassword_notOk() {
        User user = new User();
        user.setLogin("UniqueLogin");
        user.setPassword("Pass");
        user.setAge(25);
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void register_minLengthPassword_ok() {
        User user = new User();
        user.setLogin("UniqueLogin");
        user.setPassword("ValPas");
        user.setAge(25);
        User actualUser = registrationService.register(user);
        assertEquals(user.getPassword(), actualUser.getPassword());
    }

    @Test
    void register_moreThenMinLengthPassword_ok() {
        User user = new User();
        user.setLogin("UniqueLogin");
        user.setPassword("NewPassword");
        user.setAge(25);
        User actualUser = registrationService.register(user);
        assertEquals(user.getPassword(), actualUser.getPassword());
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("UniqueLogin");
        user.setPassword("NewPassword");
        user.setAge(null);
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void register_lessThenMinAge_notOk() {
        User user = new User();
        user.setLogin("UniqueLogin");
        user.setPassword("NewPassword");
        user.setAge(17);
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void register_equalsMinAge_ok() {
        User user = new User();
        user.setLogin("UniqueLogin");
        user.setPassword("NewPassword");
        user.setAge(18);
        User actualUser = registrationService.register(user);
        assertEquals(user.getAge(), actualUser.getAge());
    }

    @Test
    void register_moreThenMinAge_ok() {
        User user = new User();
        user.setLogin("UniqueLogin");
        user.setPassword("NewPassword");
        user.setAge(30);
        User actualUser = registrationService.register(user);
        assertEquals(user.getAge(), actualUser.getAge());
    }

    @Test
    void register_nullUser_notOk() {
        User user = null;
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void register_addSuccessfulUser_ok() {
        User user = new User();
        user.setLogin("UniqueLogin");
        user.setPassword("ValidPassword");
        user.setAge(25);
        User registeredUser = registrationService.register(user);
        User actualUser = storageDao.get("UniqueLogin");
        assertNotNull(registeredUser);
        assertEquals(user, actualUser);
    }

    @Test
    void register_addAndGetUserDetails_ok() {
        User user = new User();
        user.setLogin("UniqueLogin");
        user.setPassword("ValidPassword");
        user.setAge(25);
        registrationService.register(user);
        User actualUser = storageDao.get("UniqueLogin");
        assertEquals("UniqueLogin", actualUser.getLogin());
        assertEquals("ValidPassword", actualUser.getPassword());
        assertEquals(25, actualUser.getAge());
        assertEquals(user, actualUser);
    }
}
