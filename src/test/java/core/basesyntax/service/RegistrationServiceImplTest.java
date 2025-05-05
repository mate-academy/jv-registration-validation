package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    @Test
    void register_checkNullUser_throwException() {
        //Given
        User user = null;
        Storage.people.add(user);
        //when
        //then
        Assertions.assertThrows(ValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nonExisted_Ok() {
        // Given
        User user = new User();
        user.setPassword("password1");
        user.setLogin("loginuser");
        user.setAge(30);
        // When
        User registeredUser = registrationService.register(user);
        //then
        Assertions.assertEquals(user.getAge(), registeredUser.getAge());
        Assertions.assertEquals(user.getPassword(), registeredUser.getPassword());
        Assertions.assertEquals(user.getLogin(), registeredUser.getLogin());
        Assertions.assertNotNull(registeredUser.getId());
    }

    @Test
    void register_alreadyExists_throwException() {
        // Given
        User userExistingUser = createUser(9L, 30, "userlogin1", "password999");
        Storage.people.add(userExistingUser);
        //when
        Assertions.assertThrows(ValidationException.class,
                () -> registrationService.register(userExistingUser));
    }

    @Test
    void register_checkLoginLength_throwException() {
        // Given
        User userExistingUser = createUser(98L, 30, "log", "password999");
        //when
        //then
        Assertions.assertThrows(ValidationException.class,
                () -> registrationService.register(userExistingUser));
    }

    @Test
    void register_checkLengthPassword_throwException() {
        // Given
        User userExistingUser = createUser(9L, 30, "logintest2", "pass");
        //when
        //then
        Assertions.assertThrows(ValidationException.class,
                () -> registrationService.register(userExistingUser));
    }

    @Test
    void register_checkEmptyPassword_throwException() {
        //Given
        User userExistingUser = createUser(9L, 30, "logintest2", "");
        //when
        //then
        Assertions.assertThrows(ValidationException.class,
                () -> registrationService.register(userExistingUser));
    }

    @Test
    void register_checkThreeLengthPassword_throwException() {
        //Given
        User userExistingUser = createUser(9L, 30, "logintest2", "abc");
        //when
        //then
        Assertions.assertThrows(ValidationException.class,
                () -> registrationService.register(userExistingUser));
    }

    @Test
    void register_checkFiveLengthPassword_throwException() {
        //Given
        User userExistingUser = createUser(9L, 30, "logintest", "abccd");
        //when
        //then
        Assertions.assertThrows(ValidationException.class,
                () -> registrationService.register(userExistingUser));
    }

    @Test
    void register_checkNegativeAge_throwException() {
        //Given
        User userExistingUser = createUser(2L, -1, "logintest2", "password123");
        //when
        //then
        Assertions.assertThrows(ValidationException.class,
                () -> registrationService.register(userExistingUser));
    }

    @Test
    void register_checkNullLogin_throwException() {
        //Given
        User userExistingUser = createUser(22L, 22, null, "password123");
        //when
        //then
        Assertions.assertThrows(ValidationException.class,
                () -> registrationService.register(userExistingUser));
    }

    @Test
    void register_checkNullPassword_throwException() {
        //Given
        User userExistingUser = createUser(22L, 22, "logintest2", null);
        //when
        //then
        Assertions.assertThrows(ValidationException.class,
                () -> registrationService.register(userExistingUser));
    }

    @ParameterizedTest
    @ValueSource(strings = {"login1", "login123", "login12345"})
    void register_checkLoginLength_OK(String login) {
        // Given
        User user = new User();
        user.setPassword("password1");
        user.setLogin(login);
        user.setAge(30);
        // When
        User registeredUser = registrationService.register(user);
        //then

        Assertions.assertNotNull(registeredUser.getId());
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, -5, 0, 5, 17})
    void register_checkUserUnderAge_throwException(int age) {
        //Given
        User userExistingUser = createUser(22L, age, "logintest2", "password123");
        //when
        //then
        Assertions.assertThrows(ValidationException.class,
                () -> registrationService.register(userExistingUser));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "123", "12345"})
    void register_checkLengthPassword1_throwException(String password) {
        // Given
        User userExistingUser = createUser(9L, 30, "logintest2", password);
        //when
        //then
        Assertions.assertThrows(ValidationException.class,
                () -> registrationService.register(userExistingUser));
    }

    @ParameterizedTest
    @ValueSource(ints = {18, 20, 25, 45, 80})
    void register_checkAge_OK(int age) {
        // Given
        User user = new User();
        user.setPassword("password1");
        user.setLogin("liginTest");
        user.setAge(age);
        // When
        User registeredUser = registrationService.register(user);
        //then
        Assertions.assertNotNull(registeredUser.getId());
    }

    private User createUser(long id, int age, String login, String password) {
        User user = new User();
        user.setId(id);
        user.setAge(age);
        user.setLogin(login);
        user.setPassword(password);
        return user;
    }
}
