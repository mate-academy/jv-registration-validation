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
    private static final long ID = 123456789L;
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
    public void addUsers() {
        Storage.people.clear();
        firstUser = storageDao.add(new User(null,
                FIRST_LOGIN, FIRST_VALID_PASSWORD, FIRST_VALID_AGE));
        secondUser = storageDao.add(new User(null,
                SECOND_LOGIN, SECOND_VALID_PASSWORD, SECOND_VALID_AGE));
    }

    @Test
    public void register_addAgeGreaterThan18_ok() {
        User expected = new User(ID, THIRD_LOGIN, FIRST_VALID_PASSWORD, FIRST_VALID_AGE);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual,
                "User with age " + FIRST_VALID_AGE + " should be registered");
    }

    @Test
    public void register_addAge18_ok() {
        User expected = new User(ID, THIRD_LOGIN, FIRST_VALID_PASSWORD, SECOND_VALID_AGE);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual,
                "User with age " + SECOND_VALID_AGE + " should be registered");
    }

    @Test
    public void register_addInvalidUsersAge_NotOk() {
        User user = new User(ID, THIRD_LOGIN, FIRST_VALID_PASSWORD, INVALID_AGE);
        assertThrows(InvalidInputException.class, () -> registrationService.register(user),
                "Method should throw InvalidInputException for invalid age");

    }

    @Test
    public void register_addNullAge_NotOk() {
        User user = new User(ID, THIRD_LOGIN, FIRST_VALID_PASSWORD, null);
        assertThrows(InvalidInputException.class, () ->
                        registrationService.register(user),
                "Method should throw InvalidInputException for null age");
    }

    @Test
    public void register_addNullUser_NotOk() {
        assertThrows(InvalidInputException.class, () ->
                        registrationService.register(null),
                "Method should throw InvalidInputException for null user");
    }

    @Test
    public void register_addNullLogin_NotOk() {
        User user = new User(ID, null, FIRST_VALID_PASSWORD, FIRST_VALID_AGE);
        assertThrows(InvalidInputException.class, () ->
                        registrationService.register(user),
                "Method should throw InvalidInputException for null login");
    }

    @Test
    public void register_loginExists_NotOk() {
        User user = new User(ID, FIRST_LOGIN, FIRST_VALID_PASSWORD, FIRST_VALID_AGE);
        assertThrows(InvalidInputException.class, () -> registrationService.register(user),
                "Method should throw InvalidInputException if user with this login exists");
    }

    @Test
    public void register_passwordLengthIsGreaterThan6_Ok() {
        User expected = new User(ID, THIRD_LOGIN, FIRST_VALID_PASSWORD, FIRST_VALID_AGE);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual,
                "User with password " + FIRST_VALID_PASSWORD + " should be registered");
    }

    @Test
    public void register_passwordLengthIs6_Ok() {
        User expected = new User(ID, THIRD_LOGIN, SECOND_VALID_PASSWORD, FIRST_VALID_AGE);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual,
                "User with password " + SECOND_VALID_PASSWORD + " should be registered");
    }

    @Test
    public void register_addNullPassword_NotOk() {
        User user = new User(ID, THIRD_LOGIN, null, FIRST_VALID_AGE);
        assertThrows(InvalidInputException.class, () ->
                        registrationService.register(user),
                "Method should throw InvalidInputException for null password");
    }

    @Test
    public void register_passwordLengthIsLessThan6_NotOk() {
        User user = new User(ID, THIRD_LOGIN, INVALID_PASSWORD, FIRST_VALID_AGE);
        assertThrows(InvalidInputException.class, () -> registrationService.register(user),
                "Method should throw InvalidInputException for invalid password");
    }

    @Test
    public void getUser_Ok() {
        User firstExpected = new User(ID, THIRD_LOGIN, FIRST_VALID_PASSWORD, FIRST_VALID_AGE);
        registrationService.register(firstExpected);
        User firstActual = storageDao.get(THIRD_LOGIN);
        assertEquals(firstExpected, firstActual, "Method getUser() should return user by login");
    }

    @Test
    public void getUserNonExistLogin_Ok() {
        User actual = storageDao.get(FIRST_VALID_PASSWORD);
        assertNull(actual, "Method getUser() should return null for invalid login");
    }
}
