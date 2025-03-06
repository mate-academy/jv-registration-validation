package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();

    @Test
    void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("abc");
        user.setPassword("strongPas");
        user.setAge(25);

        Exception exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });

        assertEquals("Login must be at least 6 characters long", exception.getMessage());
    }

    @Test
    void register_shortPass_notOk() {
        User user = new User();
        user.setLogin("abcrqwrfqef");
        user.setPassword("str");
        user.setAge(25);

        Exception exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });

        assertEquals("Password is null or short", exception.getMessage());
    }

    @Test
    void register_smallAge_notOk() {
        User user = new User();
        user.setLogin("abcdegfwe");
        user.setPassword("strongPas");
        user.setAge(16);

        Exception exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });

        assertEquals("To small guy", exception.getMessage());
    }

    @Test
    void register_nullUser_notOk() {
        User user = null;

        Exception exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });

        assertEquals("User is null", exception.getMessage());
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setLogin("Gagara2");
        user.setPassword("GagaraPassword");
        user.setAge(20);

        registrationService.register(user);

        User storedUser = storageDao.get(user.getLogin());
        assertNotNull(storedUser);
    }

    @Test
    void register_existingLogin_notOk() {
        User user = new User();
        user.setLogin("Gagara2342");
        user.setPassword("GagaraPassword");
        user.setAge(20);
        registrationService.register(user);

        User user2 = new User();
        user2.setLogin("Gagara2342");
        user2.setPassword("Password124");
        user2.setAge(19);

        Exception exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user2);
        });

        assertEquals("Login is already taken", exception.getMessage());
    }
}
