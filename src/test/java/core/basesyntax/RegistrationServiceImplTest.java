package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
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
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_Ok() {
        String login = "Alina";
        user.setLogin(login);
        user.setAge(20);
        user.setPassword("linkamarynka56");
        User register = registrationService.register(user);
        assertSame(storageDao.get(login), register);
    }

    @Test
    void register_exist_NotOk() {
        user.setLogin("Ali");
        user.setAge(20);
        user.setPassword("qwer123");
        storageDao.add(user);
        User user2 = new User();
        user2.setLogin("Ali");
        user2.setAge(20);
        user2.setPassword("12345678");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user2);
        });
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin("");
        user.setAge(20);
        user.setPassword("password18");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullLogin_NotOk() {
        user.setAge(20);
        user.setPassword("password18");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_NotOk() {
        user.setLogin("Alina");
        user.setPassword("password18");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageLessMinAge_notOk() {
        user.setLogin("Alina");
        user.setAge(17);
        user.setPassword("password1");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageNegativeValue_notOk() {
        user.setLogin("Alina");
        user.setAge(-21);
        user.setPassword("password1");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
     void register_nullPassword_NotOk() {
        user.setLogin("Alina");
        user.setAge(20);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordLessMinLength_notOk() {
        user.setLogin("Alina");
        user.setAge(30);
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }
}

