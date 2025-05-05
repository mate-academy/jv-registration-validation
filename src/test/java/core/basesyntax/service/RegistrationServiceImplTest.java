package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private User user = new User();
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();

    @BeforeEach
     void beforeEach() {
        user.setAge(19);
        user.setLogin("ambasador");
        user.setPassword("ambasador1");
    }

    @Test
    void register_UserWasAdded_Ok() {
        registrationService.register(user);
        boolean actual = storageDao.get(user.getLogin()) != null;
        assertTrue(actual);
    }

    @Test
    void register_NegativeAge_NotOk() {
        user.setAge(-1);
        boolean actual = user.getAge() < 0;
        assertTrue(actual, "Age can not be less than 0");
    }

    @Test
    void register_NullAge_NotOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_NullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
    
    @Test
    void register_NullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_Login_isNotValid() {
        user.setLogin("qw2sq");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_Password_isNotValid() {
        user.setPassword("q1esq");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_Age_isNotValid() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_User_isNull() {
        user = null;
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}
