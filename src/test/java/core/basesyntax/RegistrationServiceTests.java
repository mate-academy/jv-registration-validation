package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class RegistrationServiceTests {
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private StorageDao storageDao = new StorageDaoImpl();

    @Test
    public void add_user_OK() {
        User newUser = new User();
        newUser.setLogin("Bob679");
        newUser.setAge(18);
        newUser.setPassword("0987654321");
        registrationService.register(newUser);
        User expectedUser = storageDao.get(newUser.getLogin());
        assertEquals(expectedUser.getLogin(), newUser.getLogin());
        assertEquals(expectedUser.getPassword(), newUser.getPassword());
        assertEquals(expectedUser.getAge(), newUser.getAge());
        assertEquals(storageDao.size(), 1);
    }

    @Test
    public void add_user_with_incorrect_login() {
        User newUser = new User();
        newUser.setLogin("Mary");
        newUser.setAge(18);
        newUser.setPassword("666666666");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    public void add_user_with_existing_login() {
        User newUser = new User();
        newUser.setLogin("Bob679");
        newUser.setAge(22);
        newUser.setPassword("1230988686");
        assertEquals(storageDao.size(), 1);
    }

    @Test
    public void add_user_with_null_login() {
        User newUser = new User();
        newUser.setLogin(null);
        newUser.setAge(35);
        newUser.setPassword("853895378");
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    public void add_user_with_empty_login() {
        User newUser = new User();
        newUser.setLogin("");
        newUser.setAge(35);
        newUser.setPassword("59494866489640");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    public void add_user_with_incorrect_password() {
        User newUser = new User();
        newUser.setLogin("Alice12");
        newUser.setAge(44);
        newUser.setPassword("123");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    public void add_user_with_null_password() {
        User newUser = new User();
        newUser.setLogin("Olha906784");
        newUser.setAge(35);
        newUser.setPassword(null);
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    public void add_user_with_empty_password() {
        User newUser = new User();
        newUser.setLogin("Anton69504");
        newUser.setAge(35);
        newUser.setPassword("");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    public void add_user_with_incorrect_age() {
        User newUser = new User();
        newUser.setLogin("Mary67890");
        newUser.setAge(8);
        newUser.setPassword("098765543211111");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    public void add_user_with_empty_age() {
        User newUser = new User();
        newUser.setLogin("Mary67890");
        newUser.setAge(0000);
        newUser.setPassword("098765543211111");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    public void add_user_with_borderline_value_age() {
        User newUser = new User();
        newUser.setLogin("Mary67890533");
        newUser.setAge(17);
        newUser.setPassword("098765543211111");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(newUser);
        });
    }
}
