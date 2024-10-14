package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HelloWorldTest {

    private RegistrationServiceImpl registrationService;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    void register_validUser() {
        User validUser = new User();
        validUser.setAge(20);
        validUser.setPassword("passwordValid");
        validUser.setLogin("loginValid");

        User result = registrationService.register(validUser);
        assertNotNull(result);
        assertEquals(validUser, storageDao.get("loginValid"));
    }

    @Test
    void register_existingLogin_notOk() {
        User firstUser = new User();
        firstUser.setLogin("validLogin");
        firstUser.setPassword("validPassword");
        firstUser.setAge(20);
        registrationService.register(firstUser);

        User secondUserWithSameLogin = new User();
        secondUserWithSameLogin.setLogin("validLogin");
        secondUserWithSameLogin.setPassword("anotherPassword");
        secondUserWithSameLogin.setAge(25);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(secondUserWithSameLogin);
        });
    }

    @Test
    void register_shortPassword_notOk() {
        User userWithShortPassword = new User();
        userWithShortPassword.setLogin("validLogin");
        userWithShortPassword.setPassword("pwd");
        userWithShortPassword.setAge(20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithShortPassword);
        });
    }

    @Test
    void register_passwordIsNull_notOk() {
        User userWithNullPassword = new User();
        userWithNullPassword.setLogin("validLogin");
        userWithNullPassword.setPassword(null);
        userWithNullPassword.setAge(20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithNullPassword);
        });
    }

    @Test
    void register_shortLogin_notOk() {
        User userWithShortLogin = new User();
        userWithShortLogin.setLogin("log");
        userWithShortLogin.setPassword("validPassword");
        userWithShortLogin.setAge(20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithShortLogin);
        });
    }

    @Test
    void register_loginIsNull_notOk() {
        User userWithNullLogin = new User();
        userWithNullLogin.setLogin(null);
        userWithNullLogin.setPassword("validPassword");
        userWithNullLogin.setAge(20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithNullLogin);
        });
    }

    @Test
    void register_ageIsNull_notOk() {
        User userWithNullAge = new User();
        userWithNullAge.setLogin("validLogin");
        userWithNullAge.setPassword("validPassword");
        userWithNullAge.setAge(0);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithNullAge);
        });
    }

    @Test
    void register_fewYears_notOk() {
        User userWithFewYears = new User();
        userWithFewYears.setLogin("validLogin");
        userWithFewYears.setPassword("validPassword");
        userWithFewYears.setAge(16);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithFewYears);
        });
    }
}
