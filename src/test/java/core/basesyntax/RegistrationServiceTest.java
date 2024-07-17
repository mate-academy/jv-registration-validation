package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static final String CORRECT_LOGIN = "Valid User Login";
    private static final String IDENTICAL_LOGIN = "Identical User Login";
    private static final String CORRECT_PASSWORD = "Valid User Password";
    private static final String INVALID_LENGTH_5 = "55555";
    private static final Integer CORRECT_AGE = 20;

    private StorageDaoImpl storageDao;
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null),
                "User should not be null");
    }

    @Test
    public void register_validUser_ok() {
        User validUser = new User();
        validUser.setLogin(CORRECT_LOGIN);
        validUser.setPassword(CORRECT_PASSWORD);
        validUser.setAge(CORRECT_AGE);

        registrationService.register(validUser);
        assertNotNull(storageDao.get(CORRECT_LOGIN));
    }

    @Test
    public void register_invalidLogin_notOk() {
        User validUser = new User();
        validUser.setLogin(IDENTICAL_LOGIN);
        validUser.setPassword(CORRECT_PASSWORD);
        validUser.setAge(CORRECT_AGE);
        registrationService.register(validUser);

        final User identicalLogin = new User();
        validUser.setLogin(IDENTICAL_LOGIN);
        validUser.setPassword(CORRECT_PASSWORD);
        validUser.setAge(CORRECT_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(identicalLogin),
                "Login already exists.");

        User zeroLogin = new User();
        zeroLogin.setLogin("");
        zeroLogin.setPassword(CORRECT_PASSWORD);
        zeroLogin.setAge(CORRECT_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(zeroLogin),
                "Length 0. Login can`t be less than 6 characters.");

        User fiveLength = new User();
        fiveLength.setLogin(INVALID_LENGTH_5);
        fiveLength.setPassword(CORRECT_PASSWORD);
        fiveLength.setAge(CORRECT_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(fiveLength),
                "Length 5. Login can`t be less than 6 characters.");
    }

    @Test
    public void register_invalidPassword_notOk() {
        User zeroPass = new User();
        zeroPass.setLogin("User Login 1");
        zeroPass.setPassword("");
        zeroPass.setAge(CORRECT_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(zeroPass),
                "Length 0. Login can`t be less then 6 characters.");

        User fiveLength = new User();
        fiveLength.setLogin("User Login 2");
        fiveLength.setPassword(INVALID_LENGTH_5);
        fiveLength.setAge(CORRECT_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(fiveLength),
                "Length 5. Login can`t be less then 6 characters.");
    }

    @Test
    public void register_invalidAge_notOk() {
        User age0 = new User();
        age0.setLogin("User Login 3");
        age0.setPassword(CORRECT_PASSWORD);
        age0.setAge(0);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(age0),
                "Age 0. Age can`t less than 18 characters.");

        User age17 = new User();
        age17.setLogin("User Login 4");
        age17.setPassword(CORRECT_PASSWORD);
        age17.setAge(17);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(age17),
                "Age 17. Age can`t less than 18 characters.");
    }
}
