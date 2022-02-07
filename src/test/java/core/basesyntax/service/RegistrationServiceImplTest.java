package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private StorageDaoImpl storageDao = new StorageDaoImpl();
    private User user = new User();
    private User userWithTheSameLogin1 = new User();
    private User userWithTheSameLogin2 = new User();

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
    void register_theSameLoginInStorage_notOk() {
        userWithTheSameLogin1.setLogin("login1");
        userWithTheSameLogin1.setPassword("somepassword");
        userWithTheSameLogin1.setAge(18);
        userWithTheSameLogin2.setLogin("login1");
        userWithTheSameLogin2.setPassword("anotherpassword");
        userWithTheSameLogin2.setAge(25);
        storageDao.add(userWithTheSameLogin1);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userWithTheSameLogin2);
        });
    }
}
