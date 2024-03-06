package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.InvalidDataException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Test;

public class HelloWorldTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private Storage storage = new Storage();
    private StorageDao storageDao = new StorageDaoImpl();

    @Test
    void register_smallPassword_notOK() {
        User user = new User();
        user.setPassword("12332");
        user.setLogin("testlog");
        user.setAge(19);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_smallLogin_notOK() {
        User user = new User();
        user.setPassword("123321");
        user.setLogin("test");
        user.setAge(19);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_smallAge_notOK() {
        User user = new User();
        user.setPassword("123321");
        user.setLogin("testlog");
        user.setAge(2);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOK() {
        User user = new User();
        user.setPassword("123321");
        user.setLogin("testlog");
        user.setAge(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setAge(19);
        user.setPassword(null);
        user.setLogin("testlog");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_correctData_OK() {
        User user = new User();
        String login = "testlog";
        user.setPassword("123321");
        user.setAge(19);
        user.setLogin(login);
        registrationService.register(user);
        assertEquals(user, storageDao.get(login));
    }

    @Test
    void register_nullLogin_notOK() {
        User user = new User();
        user.setAge(19);
        user.setPassword("123321");
        user.setLogin(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }
}

