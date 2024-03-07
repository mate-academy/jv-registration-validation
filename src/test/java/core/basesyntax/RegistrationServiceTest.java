package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.InvalidAgeException;
import core.basesyntax.exeption.InvalidLoginException;
import core.basesyntax.exeption.InvalidPasswordException;
import core.basesyntax.exeption.InvalidUserException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static final String VALID_PASSWORD = "mAtE3sT7_&%$a8";
    private static final String VALID_LOGIN = "Bob1985";
    private static final int VALID_USER_AGE = 65;
    private static final int BELOW_THRESHOLD_AGE = 15;
    private static final String LOGIN_WITH_INCORRECT_LENGTH = "bob75superBOB135bestOF7the7BEST";
    private static final String PASSWORD_WITH_INCORRECT_LENGTH = "1";
    private static final String DUPLICATE_LOGIN = "bestOfTheBest";
    private StorageDaoImpl storageDao;
    private RegistrationServiceImpl service;
    private User userWithValidParameters;

    @BeforeEach
    public void fieldsInitialization() {
        userWithValidParameters = new User();
        userWithValidParameters.setPassword(VALID_PASSWORD);
        userWithValidParameters.setLogin(VALID_LOGIN);
        userWithValidParameters.setAge(VALID_USER_AGE);
        storageDao = new StorageDaoImpl();
        service = new RegistrationServiceImpl(storageDao);
    }

    @Test
    public void userRegistration_ok() {
        service.register(userWithValidParameters);
        assertEquals(userWithValidParameters, storageDao.get(userWithValidParameters.getLogin()));
    }

    @Test
    public void register_nullUser_notOk() {
        User user = null;
        Throwable exception = assertThrows(InvalidUserException.class,
                () -> service.register(user));
        assertEquals("This user is null", exception.getMessage());
    }

    @Test
    public void register_invalidAge_lowerLimit_notOk() {
        userWithValidParameters.setAge(BELOW_THRESHOLD_AGE);
        Throwable exception = assertThrows(InvalidAgeException.class,
                () -> service.register(userWithValidParameters));
        assertEquals("Registration age must be over 18", exception.getMessage());
    }

    @Test
    public void register_nullLogin_notOk() {
        userWithValidParameters.setLogin(null);
        Throwable exception = assertThrows(InvalidLoginException.class,
                () -> service.register(userWithValidParameters));
        assertEquals("Login cannot be null", exception.getMessage());
    }

    @Test
    public void register_invalidLoginLength_notOk() {
        userWithValidParameters.setLogin(LOGIN_WITH_INCORRECT_LENGTH);
        Throwable exception = assertThrows(InvalidLoginException.class,
                () -> service.register(userWithValidParameters));
        assertEquals("The login length must be at least 6 characters"
                        + " and no more than 20. Your length: "
                        + LOGIN_WITH_INCORRECT_LENGTH.length(),
                exception.getMessage());
    }

    @Test
    public void register_loginAlreadyExists_notOk() {
        userWithValidParameters.setLogin(DUPLICATE_LOGIN);
        storageDao.add(userWithValidParameters);
        User newUserWithDuplicateLogin = new User();
        newUserWithDuplicateLogin.setLogin(DUPLICATE_LOGIN);
        newUserWithDuplicateLogin.setPassword(VALID_PASSWORD);
        newUserWithDuplicateLogin.setAge(VALID_USER_AGE);
        Throwable exception = assertThrows(InvalidLoginException.class,
                () -> service.register(newUserWithDuplicateLogin));
        assertEquals("This login is already taken", exception.getMessage());
    }

    @Test
    public void register_nullPassword_notOk() {
        userWithValidParameters.setPassword(null);
        Throwable exception = assertThrows(InvalidPasswordException.class,
                () -> service.register(userWithValidParameters));
        assertEquals("Password cannot be null", exception.getMessage());
    }

    @Test
    public void register_invalidPasswordLength_notOk() {
        userWithValidParameters.setPassword(PASSWORD_WITH_INCORRECT_LENGTH);
        Throwable exception = assertThrows(InvalidPasswordException.class,
                () -> service.register(userWithValidParameters));
        assertEquals("The password length must be at least 6 characters"
                + " and no more than 20. Your length: "
                + PASSWORD_WITH_INCORRECT_LENGTH.length(), exception.getMessage());
    }
}
