package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    void register_userNotNull_Ok() {
        User userTest = new User();
        userTest.setId(12365L);
        userTest.setLogin("bondorol");
        userTest.setPassword("123456");
        userTest.setAge(18);
        User register = registrationService.register(userTest);
        assertNotNull(register);
    }

    @Test
    void register_userIsNull_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_passwordNull_notOk() {
        User userTest = new User();
        userTest.setId(12365L);
        userTest.setLogin("bondorol");
        userTest.setPassword(null);
        userTest.setAge(18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userTest);
        });
    }

    @Test
    void register_passwordIsLessThan6_notOk() {
        User userTest = new User();
        userTest.setId(12365L);
        userTest.setLogin("bondorol");
        userTest.setPassword("123");
        userTest.setAge(18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userTest);
        });
    }

    @Test
    void register_ageIs0_notOk() {
        User userTest = new User();
        userTest.setId(12435L);
        userTest.setLogin("123455");
        userTest.setPassword("123456");
        userTest.setAge(0);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userTest);
        });
    }

    @Test
    void register_ageLessThan18_notOk() {
        User userTest = new User();
        userTest.setId(12314L);
        userTest.setLogin("123456");
        userTest.setPassword("123456");
        userTest.setAge(12);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userTest);
        });
    }

    @Test
    void register_loginsEquals_notOk() {
        User user = new User();
        user.setId(123131344L);
        user.setLogin("bondorol");
        user.setPassword("121456136");
        user.setAge(19);
        StorageDao storageDao = new StorageDaoImpl();
        storageDao.add(user);

        User userTest = new User();
        userTest.setId(12314L);
        userTest.setLogin("bondorol");
        userTest.setPassword("123456");
        userTest.setAge(19);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userTest);
        });
    }
}

