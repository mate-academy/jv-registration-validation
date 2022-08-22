package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(PER_CLASS)
public class RegistrationServiceTest {
    private RegistrationServiceImpl registrationService;
    private StorageDaoImpl dao;
    private List<User> validUsers;

    @BeforeAll
    public void setup() {
        registrationService = new RegistrationServiceImpl();
        dao = new StorageDaoImpl();
        validUsers = List.of(new User("James", "qwerty",20),
                        new User("Anna", "password", 30),
                        new User("Alice", "123456", 40));
    }

    @AfterAll
    public void close(){
    }

    @Test
    public void register_nullUser_throwsNullPointer() {
        var exception = assertThrows(NullPointerException.class,
                () -> registrationService.register(null));
        assertEquals("User required", exception.getMessage());
    }

    @Test
    public void register_userNullProperties_throwsNullPointer() {
        User userWithNullLogin = new User(null, "12344321", 20);
        User userWithNullPassword = new User("Sam", null, 20);
        User userWithNullAge = new User("Mickey", "12344321", null);
        var nullLoginException = assertThrows(NullPointerException.class,
                () -> registrationService.register(userWithNullLogin));
        var nullPasswordException = assertThrows(NullPointerException.class,
                () -> registrationService.register(userWithNullPassword));
        var nullAgeException = assertThrows(NullPointerException.class,
                () -> registrationService.register(userWithNullAge));
        assertEquals("Login required", nullLoginException.getMessage());
        assertEquals("Password required", nullPasswordException.getMessage());
        assertEquals("Age required", nullAgeException.getMessage());
    }

    @Test
    public void register_userEmptyLogin_throwsRuntimeException() {
        User userWithEmptyLogin = new User("", "12344321", 20);
        User userWithBlankLogin = new User("   ", "12344321", 20);
        var exceptionEmptyLogin = assertThrows(RuntimeException.class,
                () -> registrationService.register(userWithEmptyLogin));
        var exceptionBlankLogin = assertThrows(RuntimeException.class,
                () -> registrationService.register(userWithBlankLogin));
        assertEquals("Login is empty", exceptionEmptyLogin.getMessage());
        assertEquals("Login is empty", exceptionBlankLogin.getMessage());
    }

    @Test
    public void register_userWithExistingLogin_throwsRuntimeException() {
        User user = new User("User123", "qwerty", 30);
        registrationService.register(user);
        var exception = assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
        assertEquals("User with login - User123 exists", exception.getMessage());
    }

    @Test
    public void register_userWithInvalidPassword_throwsRuntimeException() {
        User userWithShortPassword = new User("John", "12345", 20);
        var exception = assertThrows(RuntimeException.class,
                () -> registrationService.register(userWithShortPassword));
        assertEquals("Password length must be at least 6 characters. "
                + "Current password length: 5", exception.getMessage());
    }

    @Test
    public void register_userWithInvalidAge_throwsRuntimeException() {
        User userWthNegativeAge = new User("Sam1", "password123", -1);
        User userWthAgeUnder18 = new User("Sam12", "password123", 10);
        User userWthAgeAbove100 = new User("Sam123", "password123", 101);
        var exceptionNegativeAge = assertThrows(RuntimeException.class,
                () -> registrationService.register(userWthNegativeAge));
        var exceptionAgeUnder18 = assertThrows(RuntimeException.class,
                () -> registrationService.register(userWthAgeUnder18));
        var exceptionAgeAbove100 = assertThrows(RuntimeException.class,
                () -> registrationService.register(userWthAgeAbove100));
        assertEquals("Invalid age. Value can't be: -1", exceptionNegativeAge.getMessage());
        assertEquals("User must be at least 18 years old. Current age: 10",
                exceptionAgeUnder18.getMessage());
        assertEquals("Invalid age. Value can't be: 101", exceptionAgeAbove100.getMessage());
    }

    @Test
    public void resister_validUsers_ok() {
        validUsers.forEach(user -> registrationService.register(user));
        for (User user : validUsers) {
            assertEquals(user, dao.get(user.getLogin()));
        }
    }
}
