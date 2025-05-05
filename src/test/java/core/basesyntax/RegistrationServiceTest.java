package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static final String CORRECT_LOGIN = "Valid User Login";
    private static final String IDENTICAL_LOGIN = "Identical User Login";
    private static final String IDENTICAL_LOGIN_NEGATIVE_AGE
            = "Identical User Login With Negative Age";
    private static final String CORRECT_PASSWORD = "Valid User Password";
    private static final String INVALID_LENGTH_5 = "55555";
    private static final Integer CORRECT_AGE = 20;

    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null),
                "User should not be null.");
    }

    @Test
    public void register_validUser_ok() {
        User validUser = new User();
        validUser.setLogin(CORRECT_LOGIN);
        validUser.setPassword(CORRECT_PASSWORD);
        validUser.setAge(CORRECT_AGE);

        User actual = registrationService.register(validUser);
        assertEquals(validUser, actual);
    }

    @Test
    public void register_nullLogin_notOk() {
        User nullLogin = new User();
        nullLogin.setLogin(null);
        nullLogin.setPassword(CORRECT_PASSWORD);
        nullLogin.setAge(CORRECT_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(nullLogin),
                "Login should not be null.");
    }

    @Test
    public void register_emptyLogin_noOk() {
        User emptyLogin = new User();
        emptyLogin.setLogin("");
        emptyLogin.setPassword(CORRECT_PASSWORD);
        emptyLogin.setAge(CORRECT_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(emptyLogin),
                "Length 0. Login can`t be less than 6 characters.");
    }

    @Test
    public void register_invalidLogin_notOk() {
        User fiveLength = new User();
        fiveLength.setLogin(INVALID_LENGTH_5);
        fiveLength.setPassword(CORRECT_PASSWORD);
        fiveLength.setAge(CORRECT_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(fiveLength),
                "Length 5. Login can`t be less than 6 characters.");
    }

    @Test
    public void register_existedLogin_notOk() {
        User validUser = new User();
        validUser.setLogin("Identical User Login");
        validUser.setPassword("password valid user");
        validUser.setAge(25);
        registrationService.register(validUser);

        final User identicalLogin = new User();
        validUser.setLogin(IDENTICAL_LOGIN);
        validUser.setPassword("password exist login");
        validUser.setAge(20);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(identicalLogin),
                "Login already exists.");
    }

    @Test
    public void register_nullPassword_notOk() {
        User nullPass = new User();
        nullPass.setLogin(CORRECT_LOGIN);
        nullPass.setPassword(null);
        nullPass.setAge(CORRECT_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(nullPass),
                "Password should not be null.");
    }

    @Test
    public void register_emptyPassword_notOk() {
        User emptyPass = new User();
        emptyPass.setLogin("User Login 1");
        emptyPass.setPassword("");
        emptyPass.setAge(CORRECT_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(emptyPass),
                "Length 0. Login can`t be less then 6 characters.");
    }

    @Test
    public void register_invalidPassword_notOk() {
        User fiveLength = new User();
        fiveLength.setLogin("User Login 2");
        fiveLength.setPassword(INVALID_LENGTH_5);
        fiveLength.setAge(CORRECT_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(fiveLength),
                "Length 5. Login can`t be less then 6 characters.");
    }

    @Test
    public void register_nullAge_notOk() {
        User nullAge = new User();
        nullAge.setLogin(CORRECT_LOGIN);
        nullAge.setPassword(CORRECT_PASSWORD);
        nullAge.setAge(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(nullAge),
                "Age should be not null.");
    }

    @Test
    public void register_invalidAge0_notOk() {
        User age0 = new User();
        age0.setLogin("User Login 3");
        age0.setPassword(CORRECT_PASSWORD);
        age0.setAge(0);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(age0),
                "Age 0. Age can`t less than 18 characters.");
    }

    @Test
    public void register_invalidAge17_notOk() {
        User age17 = new User();
        age17.setLogin("User Login 4");
        age17.setPassword(CORRECT_PASSWORD);
        age17.setAge(17);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(age17),
                "Age 17. Age can`t less than 18 characters.");
    }

    @Test
    public void register_existedLoginAndNegativeAge_notOk() {
        User validUser = new User();
        validUser.setLogin("Identical User Login With Negative Age");
        validUser.setPassword("password valid user");
        validUser.setAge(25);
        registrationService.register(validUser);

        final User identicalLoginAndNegativeAge = new User();
        validUser.setLogin(IDENTICAL_LOGIN_NEGATIVE_AGE);
        validUser.setPassword("password exist login");
        validUser.setAge(-25);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(identicalLoginAndNegativeAge),
                "Login already exists and age is negative.");
    }
}
