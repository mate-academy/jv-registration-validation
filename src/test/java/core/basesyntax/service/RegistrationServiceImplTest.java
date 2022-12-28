package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
                "firstValidLogin", "firstValidPassword", 63));
        secondUser = storageDao.add(new User(null,
                "secondValidLogin", "va_lid", 18));
    }

    @Test
    public void register_addAgeGreaterThanMinAge_ok() {
        int validAge = 19;
        User expected = new User(123L, "thirdValidLogin",
                "firstValidPassword", validAge);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual,
                "User with age " + validAge + " should be registered");
    }

    @Test
    public void register_addAdultUser_ok() {
        int validAge = 18;
        User expected = new User(123L, "thirdValidLogin",
                "firstValidPassword", validAge);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual,
                "User with age " + validAge + " should be registered");
    }

    @Test
    public void register_addInvalidUsersAge_NotOk() {
        User user = new User(123L, "thirdValidLogin",
                "firstValidPassword", 17);
        assertThrows(InvalidInputException.class, () -> registrationService.register(user),
                "Method should throw InvalidInputException for invalid age");

    }

    @Test
    public void register_addNullAge_NotOk() {
        User user = new User(123L, "thirdValidLogin",
                "firstValidPassword", null);
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
        User user = new User(123L, null, "firstValidPassword", 19);
        assertThrows(InvalidInputException.class, () ->
                        registrationService.register(user),
                "Method should throw InvalidInputException for null login");
    }

    @Test
    public void register_loginExists_NotOk() {
        User user = new User(123L, "firstValidLogin",
                "firstValidPassword", 28);
        assertThrows(InvalidInputException.class, () -> registrationService.register(user),
                "Method should throw InvalidInputException if user with this login exists");
    }

    @Test
    public void register_passwordLengthIsGreaterThanMinLength_Ok() {
        String validPassword = "firstValidPassword";
        User expected = new User(123L, "thirdValidLogin", validPassword, 41);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual,
                "User with password " + validPassword + " should be registered");
    }

    @Test
    public void register_passwordLengthIs6_Ok() {
        String validPassword = "va_lid";
        User expected = new User(123L, "thirdValidLogin", validPassword, 19);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual,
                "User with password " + validPassword + " should be registered");
    }

    @Test
    public void register_addNullPassword_NotOk() {
        User user = new User(123L, "thirdValidLogin", null, 19);
        assertThrows(InvalidInputException.class, () ->
                        registrationService.register(user),
                "Method should throw InvalidInputException for null password");
    }

    @Test
    public void register_passwordLengthIsLessThanMinLength_Ok() {
        User user = new User(123L, "thirdValidLogin", "short", 25);
        assertThrows(InvalidInputException.class, () -> registrationService.register(user),
                "Method should throw InvalidInputException for invalid password");
    }
}
