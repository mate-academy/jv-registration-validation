package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class HelloWorldTest {
    private RegistrationServiceImpl service = new RegistrationServiceImpl();
    private StorageDao storage = new StorageDaoImpl();
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void register_nullUser_notOK() {
        assertThrows(RuntimeException.class, () -> {
            service.register(null);
        });
    }

    @Test
    void register_nullAge_notOK() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_sameLogin_notOK() {
        user.setLogin("Bob");
        user.setAge(20);
        service.register(user);
        assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_userAge_notOK() {
        user.setAge(15);
        assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_userAge_OK() {
        User actual = new User();
        actual.setAge(19);
        actual.setLogin("Alice");
        User expected = service.register(actual);
        assertTrue(actual.equals(expected));
    }
}
