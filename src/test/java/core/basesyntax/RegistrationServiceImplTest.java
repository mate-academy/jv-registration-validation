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

public class RegistrationServiceImplTest {

    private static RegistrationServiceImpl registrationService;
    private static StorageDao storageDao;

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

        registrationService.register(validUser);
        assertNotNull(storageDao.get("loginValid"));
        assertEquals(validUser, storageDao.get("loginValid"));
    }

    @Test
    void register_existingLogin_notOk() {
        User firstUser = new User();
        firstUser.setLogin("validLogin");
        firstUser.setPassword("validPassword");
        firstUser.setAge(20);
        storageDao.add(firstUser);

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
        userWithShortPassword.setPassword("short");
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
    void register_emptyPassword_notOk() {
        User userWithEmptyPassword = new User();
        userWithEmptyPassword.setLogin("validLogin");
        userWithEmptyPassword.setPassword("");
        userWithEmptyPassword.setAge(20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithEmptyPassword);
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
    void register_minLengthLogin_ok() {
        User userWithMinLengthLogin = new User();
        String minLengthLogin = "MinLog";
        userWithMinLengthLogin.setLogin(minLengthLogin);
        userWithMinLengthLogin.setPassword("validPassword");
        userWithMinLengthLogin.setAge(20);

        registrationService.register(userWithMinLengthLogin);
        assertEquals(userWithMinLengthLogin, storageDao.get(minLengthLogin));
    }

    @Test
    void register_emptyLogin_notOk() {
        User userWithEmptyLogin = new User();
        userWithEmptyLogin.setLogin("");
        userWithEmptyLogin.setPassword("validPassword");
        userWithEmptyLogin.setAge(20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithEmptyLogin);
        });
    }

    @Test
    void register_ageIsZero_notOk() {
        User userWithZeroAge = new User();
        userWithZeroAge.setLogin("validLogin");
        userWithZeroAge.setPassword("validPassword");
        userWithZeroAge.setAge(0);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithZeroAge);
        });
    }

    @Test
    void register_fewYears_notOk() {
        User userWithSeventeenYears = new User();
        userWithSeventeenYears.setLogin("validLogin");
        userWithSeventeenYears.setPassword("validPassword");
        userWithSeventeenYears.setAge(17);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithSeventeenYears);
        });
    }

    @Test
    void register_minAge_ok() {
        User userWithMinAge = new User();
        userWithMinAge.setLogin("LoginV");
        userWithMinAge.setPassword("PasswordV");
        userWithMinAge.setAge(18);
        registrationService.register(userWithMinAge);
        assertEquals(userWithMinAge, storageDao.get("LoginV"));
    }
}
