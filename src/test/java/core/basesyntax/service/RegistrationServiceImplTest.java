package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.UserRegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final StorageDaoImpl storageDao = new StorageDaoImpl();
    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private String expectedMessage;
    private User newUser;

    @BeforeEach
    void init() {
        newUser = new User();
        Storage.people.clear();
    }

    @AfterEach
    void teardown() {
        newUser = null;
    }

    @Test
    @DisplayName("Method register(User user) "
            + "throws UserRegistrationException if user parameter is null")
    void register_nullUser_notOk() {
        expectedMessage = "User can`t be null";
        UserRegistrationException expectedException = assertThrows(
                UserRegistrationException.class,
                () -> registrationService.register(null)
        );
        assertEquals(
                expectedMessage,
                expectedException.getMessage()
        );
    }

    @Test
    @DisplayName("Method register(User user) "
            + "throws UserRegistrationException if user login is null")
    void register_nullUserLogin_notOk() {
        expectedMessage = "User login can`t be null";
        UserRegistrationException expectedException = assertThrows(
                UserRegistrationException.class,
                () -> registrationService.register(newUser)
        );
        assertEquals(
                expectedMessage,
                expectedException.getMessage()
        );
    }

    @Test
    @DisplayName("Method register(User user) "
            + "throws UserRegistrationException if user password is null")
    void register_nullUserPassword_notOk() {
        newUser.setLogin("valid login");
        expectedMessage = "User password can`t be null";
        UserRegistrationException expectedException = assertThrows(
                UserRegistrationException.class,
                () -> registrationService.register(newUser)
        );
        assertEquals(
                expectedMessage,
                expectedException.getMessage()
        );
    }

    @Test
    @DisplayName("Method register(User user) "
            + "throws UserRegistrationException if user login length less then 6 characters")
    void register_UserLoginLengthLessThenSixCharacters_notOk() {
        newUser.setLogin("test");
        newUser.setPassword("valid password");
        expectedMessage = "User login %s length less then %d characters";
        UserRegistrationException expectedException = assertThrows(
                UserRegistrationException.class,
                () -> registrationService.register(newUser)
        );
        assertEquals(
                String.format(expectedMessage, newUser.getLogin(), 6),
                expectedException.getMessage()
        );
    }

    @Test
    @DisplayName("Method register(User user) "
            + "throws UserRegistrationException if user password length less then 6 characters")
    void register_UserPasswordLengthLessThenSixCharacters_notOk() {
        newUser.setLogin("valid login");
        newUser.setPassword("test");
        expectedMessage = "User password %s length less then %d characters";
        UserRegistrationException expectedException = assertThrows(
                UserRegistrationException.class,
                () -> registrationService.register(newUser)
        );
        assertEquals(
                String.format(expectedMessage, newUser.getPassword(), 6),
                expectedException.getMessage()
        );
    }

    @Test
    @DisplayName("Method register(User user) "
            + "throws UserRegistrationException if user age less then 18")
    void register_UserAgeLessThenEighteen_notOk() {
        newUser.setLogin("valid login");
        newUser.setPassword("valid password");
        newUser.setAge(17);
        expectedMessage = "User age %d less then %d years";
        UserRegistrationException expectedException = assertThrows(
                UserRegistrationException.class,
                () -> registrationService.register(newUser)
        );
        assertEquals(
                String.format(expectedMessage, newUser.getAge(), 18),
                expectedException.getMessage()
        );
    }

    @Test
    @DisplayName("Method register(User user) "
            + "throws UserRegistrationException if user with such login already exists")
    void register_UserAlreadyExists_notOk() {
        User oldUser = new User(1L, "valid login", "valid password", 18);
        storageDao.add(oldUser);
        newUser = new User(2L, "valid login", "other valid password", 18);
        expectedMessage = "User with login %s already exists";
        UserRegistrationException expectedException = assertThrows(
                UserRegistrationException.class,
                () -> registrationService.register(newUser)
        );
        assertEquals(
                String.format(expectedMessage, newUser.getLogin()),
                expectedException.getMessage()
        );
    }

    @Test
    void register_registerNewUser_ok() {
        User expectedUser = new User(1L, "test login", "test password", 18);
        User actualUser = new User(1L, "test login", "test password", 18);
        int expectedSize = Storage.people.size() + 1;
        assertAll(
                () -> assertEquals(expectedUser, registrationService.register(actualUser)),
                () -> assertEquals(expectedSize, Storage.people.size())
        );
    }
}
