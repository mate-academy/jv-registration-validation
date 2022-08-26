package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @AfterEach
    public void clearStorage() {
        Storage.people.clear();
    }

    @Test
    public void register_nullUser_notOk() {
        var exception = assertThrows(RuntimeException.class,
                () -> registrationService.register(null),
                "Registration of null user must throw RuntimeException");
        assertEquals("Can't register user. Current user: null", exception.getMessage());
    }

    @Test
    public void register_userNullProperties_notOk() {
        User userWithNullLogin = new User(null, "12344321", 20);
        User userWithNullPassword = new User("Sam", null, 20);
        User userWithNullAge = new User("Mickey", "12344321", null);
        var nullLoginException = assertThrows(RuntimeException.class,
                () -> registrationService.register(userWithNullLogin),
                "Registration of user with null login must throw RuntimeException");
        var nullPasswordException = assertThrows(RuntimeException.class,
                () -> registrationService.register(userWithNullPassword),
                "Registration of user with null password login must throw RuntimeException");
        var nullAgeException = assertThrows(RuntimeException.class,
                () -> registrationService.register(userWithNullAge),
                "Registration of user with null age login must throw RuntimeException");
        assertEquals("Can't register user with login: null", nullLoginException.getMessage());
        assertEquals("Can't register user with password: null", nullPasswordException.getMessage());
        assertEquals("Can't register user with age: null", nullAgeException.getMessage());
    }

    @Test
    public void register_userEmptyLogin_notOk() {
        User userWithEmptyLogin = new User("", "12344321", 20);
        User userWithBlankLogin = new User("   ", "12344321", 20);
        var exceptionEmptyLogin = assertThrows(RuntimeException.class,
                () -> registrationService.register(userWithEmptyLogin),
                "Registration of user with empty login must throw RuntimeException");
        var exceptionBlankLogin = assertThrows(RuntimeException.class,
                () -> registrationService.register(userWithBlankLogin),
                "Registration of user with blank login must throw RuntimeException");
        assertEquals("Login is empty", exceptionEmptyLogin.getMessage());
        assertEquals("Login is empty", exceptionBlankLogin.getMessage());
    }

    @Test
    public void register_userWithExistingLogin_notOk() {
        User user = new User("User123", "qwerty", 30);
        Storage.people.add(user);
        var exception = assertThrows(RuntimeException.class,
                () -> registrationService.register(user),
                "Registration of existing user must throw RuntimeException");
        assertEquals("User with login - User123 exists", exception.getMessage());
    }

    @Test
    public void register_userWithInvalidPassword_notOk() {
        User userWithShortPassword = new User("John", "12345", 20);
        var exception = assertThrows(RuntimeException.class,
                () -> registrationService.register(userWithShortPassword),
                "Registration of user with password less than 6 characters"
                        + " must throw RuntimeException");
        assertEquals("Password length must be at least 6 characters. "
                + "Current password length: 5", exception.getMessage());
    }

    @Test
    public void register_userWithInvalidAge_notOk() {
        User userWthNegativeAge = new User("Sam1", "password123", -1);
        User userWthAgeUnder18 = new User("Sam12", "password123", 10);
        User userWthAgeAbove100 = new User("Sam123", "password123", 101);
        var exceptionNegativeAge = assertThrows(RuntimeException.class,
                () -> registrationService.register(userWthNegativeAge),
                "Registration of user with negative age must throw RuntimeException");
        var exceptionAgeUnder18 = assertThrows(RuntimeException.class,
                () -> registrationService.register(userWthAgeUnder18),
                "Registration of user with age under 18 must throw RuntimeException");
        var exceptionAgeAbove100 = assertThrows(RuntimeException.class,
                () -> registrationService.register(userWthAgeAbove100),
                "Registration of user with age above 100 must throw RuntimeException");
        assertEquals("Invalid age. Value can't be: -1", exceptionNegativeAge.getMessage());
        assertEquals("User must be at least 18 years old. Current age: 10",
                exceptionAgeUnder18.getMessage());
        assertEquals("Invalid age. Value can't be: 101", exceptionAgeAbove100.getMessage());
    }

    @Test
    public void resister_validUsers_ok() {
        List<User> validUsers = List.of(new User("James", "qwerty",20),
                new User("Anna", "password", 30),
                new User("Alice", "123456", 40));
        validUsers.forEach(user -> registrationService.register(user));
        for (User user : validUsers) {
            assertEquals(user, storageDao.get(user.getLogin()));
        }
    }
}
