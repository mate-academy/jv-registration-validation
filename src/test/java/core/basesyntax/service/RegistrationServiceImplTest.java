package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exeption.RegistrationExeption;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user.setLogin("test@gmail.com");
        user.setPassword("1234556");
        user.setAge(18);
    }

    @Test
    void register_nullPassword_notOk() {
        user.setLogin("testUser");
        user.setPassword(null);
        user.setAge(20);
        assertThrows(RegistrationExeption.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        user.setPassword("123456");
        user.setAge(20);
        assertThrows(RegistrationExeption.class, () -> registrationService.register(user));
    }

    @Test
    void register_notValidAge_notOk() {
        user.setLogin("testUser");
        user.setPassword("123456");
        user.setAge(17);
        assertThrows(RegistrationExeption.class, () -> registrationService.register(user));
    }

    @Test
    void register_zeroAge_notOk() {
        user.setLogin("testUser");
        user.setPassword("123456");
        user.setAge(0);
        assertThrows(RegistrationExeption.class, () -> registrationService.register(user));
    }

    @Test
    void register_nagativeAge_notOk() {
        user.setLogin("testUser");
        user.setPassword("123456");
        user.setAge(-17);
        assertThrows(RegistrationExeption.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        user.setLogin("testUser");
        user.setPassword("123");
        user.setAge(18);
        assertThrows(RegistrationExeption.class, () -> registrationService.register(user));
    }

    @Test
    void register_addingExistingUser_notOk() {
        User register = registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(register));
    }

    @Test
    void register_addingValidUser_Ok() {
        user.setPassword("testUserPassword");
        user.setAge(30);
        user.setLogin("testUserLogin");
        User actual = registrationService.register(user);
        assertEquals("testUserPassword", actual.getPassword());
        assertEquals(30, actual.getAge());
        assertEquals("testUserLogin", actual.getLogin());
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

}
