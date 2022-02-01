package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private StorageDaoImpl storageDao = new StorageDaoImpl();
    private User user = new User();

    @BeforeEach
    void setUp() {
        user.setLogin("login");
        user.setPassword("password");
        user.setAge(18);
    }

    @Test
    void register_lessThanSixCharactersInPassword_notOk() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullInPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_lessThanEighteenAge_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(-18);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_moreThanHundredAge_notOk() {
        user.setAge(101);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullInLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_theSameUserInStorage_notOk() {
        storageDao.add(user);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_theSameLoginInStorage_notOk() {
        User user1 = new User();
        user1.setLogin("login1");
        user1.setPassword("somepassword");
        user1.setAge(18);
        storageDao.add(user1);
        User user2 = new User();
        user2.setLogin("login1");
        user2.setPassword("anotherpassword");
        user2.setAge(25);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user2);
        });
    }
}