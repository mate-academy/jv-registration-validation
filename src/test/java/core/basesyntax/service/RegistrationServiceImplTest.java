package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.UserRegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static StorageDaoImpl storageDao;
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    static void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void beforeEach() {
        storageDao = new StorageDaoImpl();
    }

    @AfterEach
    void afterEach() {
        storageDao = null;
    }

    @Test
    @DisplayName("Method register(User user) "
            + "throws UserRegistrationException if user parameter is null")
    void registerMethodThrowsExceptionIfUserParameterIsNull() {
        UserRegistrationException exception = assertThrows(
                UserRegistrationException.class,
                () -> registrationService.register(null));
        assertAll(() -> assertThrows(UserRegistrationException.class,
                        () -> registrationService.register(null)),
                () -> assertEquals("User can`t be null",
                        exception.getMessage())
        );
    }

    @Test
    @DisplayName("Method register(User user) "
            + "throws UserRegistrationException if user login is null")
    void registerMethodThrowsExceptionIfUserLoginIsNull() {
        User user = new User(1L, null, null, 17);
        UserRegistrationException exception = assertThrows(
                UserRegistrationException.class,
                () -> registrationService.register(user));
        assertAll(() -> assertThrows(UserRegistrationException.class,
                        () -> registrationService.register(user)),
                () -> assertEquals("User login can`t be null",
                        exception.getMessage())
        );
    }

    @Test
    @DisplayName("Method register(User user) "
            + "throws UserRegistrationException if user password is null")
    void registerMethodThrowsExceptionIfUserPasswordIsNull() {
        User user = new User(1L, "testLogin", null, 17);
        UserRegistrationException exception = assertThrows(
                UserRegistrationException.class,
                () -> registrationService.register(user));
        assertAll(() -> assertThrows(UserRegistrationException.class,
                        () -> registrationService.register(user)),
                () -> assertEquals("User password can`t be null",
                        exception.getMessage())
        );
    }

    @Test
    @DisplayName("Method register(User user) "
            + "throws UserRegistrationException if user login length less then 6")
    void registerMethodThrowsExceptionIfUserLoginLengthLessThenSixCharacters() {
        User user = new User(1L, "login", null, 17);
        UserRegistrationException exception = assertThrows(
                UserRegistrationException.class,
                () -> registrationService.register(user));
        assertAll(() -> assertThrows(UserRegistrationException.class,
                        () -> registrationService.register(user)),
                () -> assertEquals(String.format("User login %s length less then 6 characters",
                        user.getLogin()),
                        exception.getMessage())
        );
    }

    @Test
    @DisplayName("Method register(User user) "
            + "throws UserRegistrationException if user password length less then 6")
    void registerMethodThrowsExceptionIfUserPasswordLengthLessThenSixCharacters() {
        User user = new User(1L, "testLogin", "12345", 17);
        assertThrows(
                UserRegistrationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    @DisplayName("Method register(User user) "
            + "throws UserRegistrationException if user password is null")
    void registerMethodThrowsExceptionIfUserAlreadyExistsInStorage() {
        User existsUser = new User(1L, "testLogin", "testPassword", 17);
        storageDao.add(existsUser);
        User newUser = new User(1L, "testLogin", null, 17);
        UserRegistrationException exception = assertThrows(
                UserRegistrationException.class,
                () -> registrationService.register(newUser));
        assertAll(() -> assertThrows(UserRegistrationException.class,
                        () -> registrationService.register(newUser)),
                () -> assertEquals(String.format("User with login %s already exists",
                        newUser.getLogin()),
                        exception.getMessage())
        );
    }

    @Test
    @DisplayName("Method register(User user) "
            + "throws UserRegistrationException if user age less then 18")
    void registerMethodThrowsExceptionIfUserAgeLessThanEighteen() {
        User user = new User(1L, "testLogin", "testPassword", 17);
        UserRegistrationException exception = assertThrows(
                UserRegistrationException.class,
                () -> registrationService.register(user));
        assertAll(() -> assertThrows(
                UserRegistrationException.class,
                        () -> registrationService.register(user)),
                () -> assertEquals(String.format(String.format("User age %s less then 18 years",
                        user.getAge())),
                        exception.getMessage())
        );
    }

    @Test
    void registerMethodReturnsRegisteredUser() {
        User expectedUser = new User(1L, "testLogin", "testPassword", 18);
        User actualUser = new User(1L, "testLogin", "testPassword", 18);
        int expectedSize = Storage.people.size() + 1;
        assertAll(() -> assertEquals(expectedUser,
                        registrationService.register(actualUser)),
                () -> assertEquals(expectedSize,
                        Storage.people.size())
        );
    }

    @AfterAll
    static void tearDown() {
        storageDao = null;
        registrationService = null;
    }
}
