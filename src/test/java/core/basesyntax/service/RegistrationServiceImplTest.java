package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storage;
    private User newUser;

    @BeforeAll
    static void beforeAll() {
        storage = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
        User user = new User();
        user.setLogin("Henry");
        user.setPassword("3452345");
        user.setAge(19);
        registrationService.register(user);
    }

    @BeforeEach
    void setUp() {
        newUser = new User();
    }

    @Test
    void register_nullUser_notOk() {
        newUser = null;
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_nullLogin_notOk() {
        newUser.setPassword("123456");
        newUser.setAge(20);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_nullPassword_notOk() {
        newUser.setLogin("Tony");
        newUser.setAge(19);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_nullAge_notOk() {
        newUser.setLogin("Tony");
        newUser.setPassword("123456");
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_passwordLengthLessThenMin_notOk() {
        newUser.setLogin("Tony");
        newUser.setPassword("12345");
        newUser.setAge(18);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_ageLessThenMin_notOk() {
        newUser.setLogin("Tony");
        newUser.setPassword("123456");
        newUser.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_userWithAlreadyExistingLogin_notOk() {
        newUser.setLogin("Henry");
        newUser.setPassword("123456");
        newUser.setAge(22);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_ageValueIsNegative_notOk() {
        newUser.setLogin("John");
        newUser.setPassword("123456");
        newUser.setAge(-42);
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_validUserRegister_Ok() {
        newUser.setLogin("Bob");
        newUser.setPassword("123456");
        newUser.setAge(18);
        registrationService.register(newUser);
        assertEquals(newUser, storage.get(newUser.getLogin()));
    }
}
