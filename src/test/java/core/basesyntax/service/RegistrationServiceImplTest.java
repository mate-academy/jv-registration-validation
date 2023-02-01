package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

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
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(MyRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(MyRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(0);
        assertThrows(MyRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageLessThanEighteen_notOk() {
        user.setAge(17);
        assertThrows(MyRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(-1);
        assertThrows(MyRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordLengthLessThanSixSymbol_notOk() {
        user.setPassword("12345");
        assertThrows(MyRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_successfulRegistration_Ok() throws MyRegistrationException {
        user.setLogin("Jezza");
        user.setPassword("123456");
        user.setAge(62);
        storageDao.add(user);
        boolean actual = registrationService.register(user)
                        .equals(storageDao.get(user.getLogin()));
        assertTrue(actual);
    }
}
