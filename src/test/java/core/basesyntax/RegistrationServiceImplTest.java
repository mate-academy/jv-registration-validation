package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationFailedException;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final int MINIMAL_AGE = 18;
    private StorageDaoImpl storageDao;
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storageDao);
    }

    @Test
    void nullUser_NotOK() {
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void nullLogin_NotOK() {
        User user = buildUser(null, "123456", 19);
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void nullPassword_NotOK() {
        User user = buildUser("SampleLogin", null, 19);
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void nullAge_NotOK() {
        User user = buildUser("SampleLogin", "123456", null);
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void oneUser_OK() {
        User user = buildUser("SampleLogin11111", "123456", 22);
        registrationService.register(user);
        String login = user.getLogin();
        assertEquals(user, storageDao.get(login));
    }

    @Test
    void severalUsers_OK() {
        for (int i = 0; i < 10; ++i) {
            String login = "SampleLogin" + i;
            User user = buildUser(login, "123456", 19);
            registrationService.register(user);
            assertEquals(user, storageDao.get(user.getLogin()));
        }
    }

    @Test
    void sameUser_NotOK() {
        User user = buildUser("SampleLogin11", "123456", 19);
        registrationService.register(user);
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void loginLength0_NotOK() {
        User user = buildUser("", "123456", 19);
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void loginLength5_NotOK() {
        User user = buildUser("David", "123456", 19);
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void loginLength6_OK() {
        User user = buildUser("Normal", "123456", 19);
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void largeLogin_OK() {
        String login = "Lorem ipsum lorem ipsum lorem ipsum lorem";
        User user = buildUser(login, "123456", 19);
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void passwordLength0_NotOK() {
        User user = buildUser("SampleLogin", "", 19);
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void passwordLength5_NotOK() {
        User user = buildUser("SampleLogin", "12345", 19);
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void passwordLength6_OK() {
        User user = buildUser("SampleLogin222", "123456", 19);
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void passwordLength10_OK() {
        User user = buildUser("SampleUserLogin", "1234567890", 19);
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void smallAge_NotOK() {
        User user = buildUser("SampleLogin", "123456", 17);
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void ageZero_NotOK() {
        User user = buildUser("SampleLogin", "123456", 0);
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void ageNearlyMinimal_NotOK() {
        User user = buildUser("SampleLogin17", "123456", MINIMAL_AGE - 1);
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void ageMinimal_OK() {
        User user = buildUser("SampleLogin18", "123456", MINIMAL_AGE);
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
        //This test is needed since 18 is edge value: age should be >= 18, not > 18.
    }

    @Test
    void ageLarge_OK() {
        User user = buildUser("SampleLogin100", "123456", 100);
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void returnsUser_OK() {
        User user = buildUser("SampleLogin111", "123456", MINIMAL_AGE);
        assertEquals(user, registrationService.register(user));
    }

    private User buildUser(String login, String password, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}
