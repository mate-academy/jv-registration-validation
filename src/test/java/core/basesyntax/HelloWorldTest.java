package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.InvalidDataException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Test;

public class HelloWorldTest {

    private static final String SHORT_LOGIN = "test";
    private static final String NORMAL_LOGIN = "testlogin";
    private static final String shortPassword = "12332";
    private static final String NORMAL_PASSWORD = "123321";
    private static final int YEAR = 19;
    private static final int SMALL_YEAR = 17;
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private StorageDao storageDao = new StorageDaoImpl();

    @Test
    void register_shortPassword_notOK() {
        User user = new User();
        user.setPassword(shortPassword);
        user.setLogin(NORMAL_LOGIN);
        user.setAge(YEAR);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOK() {
        User user = new User();
        user.setPassword(NORMAL_PASSWORD);
        user.setLogin(SHORT_LOGIN);
        user.setAge(YEAR);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_smallAge_notOK() {
        User user = new User();
        user.setPassword(NORMAL_PASSWORD);
        user.setLogin(NORMAL_LOGIN);
        user.setAge(SMALL_YEAR);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOK() {
        User user = new User();
        user.setPassword(NORMAL_PASSWORD);
        user.setLogin(NORMAL_LOGIN);
        user.setAge(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setAge(YEAR);
        user.setPassword(null);
        user.setLogin(NORMAL_LOGIN);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_correctData_OK() {
        User user = new User();
        user.setPassword(NORMAL_PASSWORD);
        user.setAge(YEAR);
        user.setLogin(NORMAL_LOGIN);
        registrationService.register(user);
        assertEquals(user, storageDao.get(NORMAL_LOGIN));
    }

    @Test
    void register_nullLogin_notOK() {
        User user = new User();
        user.setAge(YEAR);
        user.setPassword(NORMAL_PASSWORD);
        user.setLogin(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_sameUser_notOK() {
        User user = new User();
        user.setAge(YEAR);
        user.setPassword(NORMAL_PASSWORD);
        user.setLogin(NORMAL_LOGIN);
        registrationService.register(user);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }
}
