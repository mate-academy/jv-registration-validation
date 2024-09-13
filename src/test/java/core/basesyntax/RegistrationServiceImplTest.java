package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final int MINIMAL_AGE = 18;
    private static StorageDaoImpl storageDao;
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    static void setUp(){
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storageDao);
    }

    @AfterEach
    void cleanUp(){
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storageDao);
    }

    @Test
    void nullUser_NotOK() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void nullLogin_NotOK() {
        User user = new User(null, "123456", 19);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void nullPassword_NotOK() {
        User user = new User("SampleLogin", null, 19);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void nullAge_NotOK() {
        User user = new User("SampleLogin", "123456", null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void oneUser_OK() {
        User user = new User("SampleLogin11111", "123456", 22);
        User actual = registrationService.register(user);
        assertEquals(actual, user);
    }

    @Test
    void sameUser_NotOK() {
        User user = new User("SampleLogin11", "123456", 19);
        registrationService.register(user);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void loginLength0_NotOK() {
        User user = new User("", "123456", 19);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void loginLength5_NotOK() {
        User user = new User("David", "123456", 19);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void loginLength6_OK() {
        User user = new User("Normal", "123456", 19);
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void largeLogin_OK() {
        String login = "Lorem ipsum lorem ipsum lorem ipsum lorem";
        User user = new User(login, "123456", 19);
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void passwordLength0_NotOK() {
        User user = new User("SampleLogin", "", 19);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void passwordLength5_NotOK() {
        User user = new User("SampleLogin", "12345", 19);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void passwordLength6_OK() {
        User user = new User("SampleLogin222", "123456", 19);
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void passwordLength10_OK() {
        User user = new User("SampleUserLogin", "1234567890", 19);
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void ageNegative_NotOK() {
        User user = new User("SampleLogin", "123456", -5);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void ageZero_NotOK() {
        User user = new User("SampleLogin", "123456", 0);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void ageNearlyMinimal_NotOK() {
        User user = new User("SampleLogin17", "123456", MINIMAL_AGE - 1);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void ageMinimal_OK() {
        User user = new User("SampleLogin18", "123456", MINIMAL_AGE);
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void ageLarge_OK() {
        User user = new User("SampleLogin100", "123456", 100);
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void returnsUser_OK() {
        User user = new User("SampleLogin111", "123456", MINIMAL_AGE);
        assertEquals(user, registrationService.register(user));
    }
}
