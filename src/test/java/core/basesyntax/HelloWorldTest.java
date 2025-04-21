package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Feel free to remove this class and create your own.
 */
public class HelloWorldTest {

    private final RegistrationServiceImpl registerService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();

    @Test
    void register_correctUser_ok() {
        User user = new User();
        user.setLogin("Igor123");
        user.setPassword("123456");
        user.setAge(19);

        registerService.register(user);
        User newUser = storageDao.get(user.getLogin());

        assertEquals(newUser.getLogin(), user.getLogin());
    }

    @Test
    void register_nullAge_NotOk() {
        User user = new User();
        user.setLogin("Igor123");
        user.setPassword("123456");
        user.setAge(null);

        assertThrows(NullPointerException.class, () -> registerService.register(user));
    }

    @Test
    void register_nullLogin_NotOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("123456");
        user.setAge(19);

        assertThrows(NullPointerException.class, () -> registerService.register(user));
    }

    @Test
    void register_nullPassword_NotOk() {
        User user = new User();
        user.setLogin("Igor123");
        user.setPassword(null);
        user.setAge(19);

        assertThrows(NullPointerException.class, () -> registerService.register(user));
    }

    @Test
    void register_sizePassword_notOk() {
        User user = new User();
        user.setLogin("Igor123");
        user.setPassword("1234");
        user.setAge(19);

        User actual = registerService.register(user);
        assertNotEquals(actual, user);
    }

    @Test
    void register_sizeLogin_notOk() {
        User user = new User();
        user.setLogin("Igor");
        user.setPassword("123456");
        user.setAge(19);

        User actual = registerService.register(user);
        assertNotEquals(actual, user);
    }

    @Test
    void register_sizeAge_notOk() {
        User user = new User();
        user.setLogin("Igor123");
        user.setPassword("123456");
        user.setAge(14);

        User actual = registerService.register(user);
        assertNotEquals(actual, user);
    }

    @Test
    void register_duplicateUser_NotOk() {
        User user = new User();
        user.setLogin("Igor123");
        user.setPassword("123456");
        user.setAge(19);

        registerService.register(user);

        assertThrows(IllegalArgumentException.class, () -> registerService.register(user));
    }

}
