package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final User user = new User();
    private static final User actual = new User();
    private static RegistrationServiceImpl registrationService;
    private static StorageDaoImpl storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        user.setLogin("FirstPerson");
        user.setPassword("thebestpassword");
        user.setAge(20);
        storageDao.add(user);
    }

    @BeforeEach
    void setup() {
        actual.setLogin("SecondPerson");
        actual.setLogin("anotherpass");
        actual.setAge(36);
    }

    @Test
    void register_nullPassword_NotOk() {
        actual.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_nullLogin_NotOk() {
        actual.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_nullAge_NotOk() {
        actual.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_negativAge_NotOk() {
        actual.setAge(-1);
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_smallAge_NotOk() {
        actual.setAge(12);
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_theSameLogin_NotOk() {
        actual.setLogin("FirstPerson");
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_shortPassword_NotOk() {
        actual.setPassword("01234");
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_Login_IsOk() {
        actual.setLogin(user.getLogin());
        assertTrue(storageDao.get(user.getLogin()) != null, " User isn`t add");
    }

    @Test
    void register_Age_IsOk() {
        actual.setAge(user.getAge());
        assertTrue(storageDao.get(user.getLogin()) != null, " User isn`t add");
    }

    @Test
    void register_Password_IsOk() {
        actual.setPassword(user.getPassword());
        assertTrue(storageDao.get(user.getLogin()) != null, " User isn`t add");
    }
}
