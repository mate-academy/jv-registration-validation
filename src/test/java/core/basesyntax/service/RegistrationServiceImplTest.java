package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    void register_userIsNull_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_loginIsNull_notOk() {
        User userTest = new User();
        userTest.setId(0L);
        userTest.setLogin(null);
        userTest.setPassword("123456");
        userTest.setAge(18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userTest);
        });
    }

    @Test
    void register_ageLessThanMinAge_notOk() {
        User userTest = new User();
        userTest.setId(0L);
        userTest.setLogin("bondorol");
        userTest.setPassword("123456");
        userTest.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userTest);
        });
    }

    @Test
    void register_passwordNull_notOk() {
        User userTest = new User();
        userTest.setId(0L);
        userTest.setLogin("bondorol");
        userTest.setPassword(null);
        userTest.setAge(18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userTest);
        });
    }

    @Test
    void register_passwordLengthLessThanMin_notOk() {
        User userTest = new User();
        userTest.setId(0L);
        userTest.setLogin("bondorol");
        userTest.setPassword("12345");
        userTest.setAge(18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userTest);
        });
    }

    @Test
    void register_loginsAlreadyExist_notOk() {
        User user = new User();
        user.setId(0L);
        user.setLogin("bondorol");
        user.setPassword("1214566");
        user.setAge(18);
        StorageDao storageDao = new StorageDaoImpl();
        storageDao.add(user);

        User userTest = new User();
        userTest.setId(0L);
        userTest.setLogin("bondorol");
        userTest.setPassword("123456");
        userTest.setAge(19);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userTest);
        });
    }
}

