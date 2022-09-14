package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    RegistrationService registrServ = new RegistrationServiceImpl();
    private static User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("userLogin");
        user.setPassword("userPassword123");
        user.setAge(19);
    }

    @Test
    void userIsNull_OK() {
        user = null;
        assertThrows(RuntimeException.class, () -> registrServ.register(user));
    }

    @Test
    void userAlreadyExist_OK() {
        registrServ.register(user);
        assertThrows(RuntimeException.class, () -> registrServ.register(user));
    }

    @Test
    void userLoginIsNull_OK() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrServ.register(user));
    }

    @Test
    void userAgeIs16_OK() {
        user.setAge(10);
        assertThrows(RuntimeException.class, () -> registrServ.register(user));
    }

    @Test
    void passwordLength2_OK() {
        user.setPassword("12");
        assertThrows(RuntimeException.class, () -> registrServ.register(user));
    }
}