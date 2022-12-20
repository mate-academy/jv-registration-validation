package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidInputException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final int FIRST_VALID_AGE = 19;
    private static final int SECOND_VALID_AGE = 18;
    private static final int INVALID_AGE = 17;
    private static final String FIRST_LOGIN = "firstValidLogin";
    private static final String SECOND_LOGIN = "secondValidLogin";
    private static final String THIRD_LOGIN = "thirdValidLogin";
    private static final String FIRST_VALID_PASSWORD = "firstValidPassword";
    private static final String SECOND_VALID_PASSWORD = "va_lid";
    private static final String INVALID_PASSWORD = "short";
    private static RegistrationServiceImpl registrationService;
    private static StorageDao storageDao;
    private User firstUser;
    private User secondUser;

    @BeforeAll
    public static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    public void addValidUsers() {
        Storage.people.clear();
        firstUser = storageDao.add(new User(null,
                FIRST_LOGIN, FIRST_VALID_PASSWORD, FIRST_VALID_AGE));
        secondUser = storageDao.add(new User(null,
                SECOND_LOGIN, SECOND_VALID_PASSWORD, SECOND_VALID_AGE));
    }

    @Test
    public void userWasAddedAndReturned_Ok() {
        User expected = new User(
                (long) FIRST_VALID_AGE, THIRD_LOGIN, FIRST_VALID_PASSWORD, FIRST_VALID_AGE);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual, "User should be returned");
    }

    @Test
    public void registerNullUser_NotOk() {
        assertThrows(InvalidInputException.class, () ->
                registrationService.register(null));
    }

    @Test
    public void registerNullAge_NotOk() {
        firstUser.setAge(null);
        assertThrows(InvalidInputException.class, () ->
                        registrationService.register(firstUser),
                "Method should throw InvalidInputException for null age");
    }

    @Test
    public void registerNullLogin_NotOk() {
        firstUser.setLogin(null);
        assertThrows(InvalidInputException.class, () ->
                        registrationService.register(firstUser),
                "Method should throw InvalidInputException for null login");
    }

    @Test
    public void registerNullPassword_NotOk() {
        firstUser.setPassword(null);
        assertThrows(InvalidInputException.class, () ->
                        registrationService.register(firstUser),
                "Method should throw InvalidInputException for null password");
    }

    @Test
    public void registerLoginExists_NotOk() {
        secondUser.setLogin(FIRST_LOGIN);
        assertThrows(InvalidInputException.class, () -> registrationService.register(secondUser),
                "Method should throw InvalidInputException if login exists");
    }

    @Test
    public void registerUserExists_NotOk() {
        assertThrows(InvalidInputException.class, () -> registrationService.register(firstUser),
                "Method should throw InvalidInputException if user exists");
    }

    @Test
    public void registerInvalidUsersAge_NotOk() {
        firstUser.setAge(INVALID_AGE);
        assertThrows(InvalidInputException.class, () -> registrationService.register(firstUser),
                "Method should throw InvalidInputException for invalid age");

    }

    @Test
    public void registerInvalidUsersPassword_NotOk() {
        firstUser.setPassword(INVALID_PASSWORD);
        assertThrows(InvalidInputException.class, () -> registrationService.register(firstUser),
                "Method should throw InvalidInputException for invalid password");
    }

    @Test
    public void getUser_Ok() {
        User actualFirst = registrationService.getUser(FIRST_LOGIN);
        User actualSecond = registrationService.getUser(SECOND_LOGIN);
        assertEquals(firstUser, actualFirst);
        assertEquals(secondUser, actualSecond);
    }

    @Test
    public void getUserNonExistLogin_Ok() {
        User actual = registrationService.getUser(FIRST_VALID_PASSWORD);
        assertNull(actual);
    }
}
