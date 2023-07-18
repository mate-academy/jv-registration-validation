package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.UserNotValidException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String SHORTEST_VALID_LOGIN = "LoginN";
    private static final String INVALID_LOGIN = SHORTEST_VALID_LOGIN.substring(1);

    private static final String SHORTEST_VALID_PASSWORD = "123456";
    private static final String INVALID_PASSWORD = SHORTEST_VALID_PASSWORD.substring(1);

    private static final Integer MIN_VALID_AGE = 18;
    private static final Integer INVALID_AGE_ZERO = 0;
    private static final Integer INVALID_AGE_BELOW_ZERO = -18;

    private RegistrationServiceImpl registrationServiceImpl;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        registrationServiceImpl = new RegistrationServiceImpl();
    }

    @Test
    void nullUser_NotOk() {
        testInvalidUser(null, "Null user adding shouldn't work");
    }

    @Test
    void register_ValidUser_Ok() {
        User expected = createValidUser();
        User actual = registrationServiceImpl.register(expected);
        assertEquals(expected, actual, "User adding doesnt work");
        assertTrue(Storage.people.contains(expected), "User is not added to storage");
    }

    @Test
    void register_ExistedUserTest_NotOk() {
        User firstUser = createValidUser();
        User sameUser = createValidUser();

        Storage.people.add(firstUser);
        assertThrows(UserNotValidException.class, () ->
                        registrationServiceImpl.register(sameUser),
                "Adding user with existed login shouldn't work");

    }

    @Test
    void register_InvalidLoginTest_NotOk() {
        testInvalidLogin(null, "null");
        testInvalidLogin("", "empty String");
        testInvalidLogin(INVALID_LOGIN, "shorter than 6 symbols");
    }

    @Test
    void register_InvalidPasswordTest_NotOk() {
        testInvalidPassword(null, "null");
        testInvalidPassword("", "empty String");
        testInvalidPassword(INVALID_PASSWORD, "shorter than 6 symbols");
    }

    @Test
    void register_InvalidAgeTest_NotOk() {
        testInvalidAge(null,"null");
        testInvalidAge(MIN_VALID_AGE - 1,"< than 0");
        testInvalidAge(INVALID_AGE_ZERO,"0");
        testInvalidAge(INVALID_AGE_BELOW_ZERO,"< 0");
    }

    @Test
    void register_ValidAgeTest_Ok() {
        int testNumber = 0;

        Integer minAge = MIN_VALID_AGE;
        testValidAge(minAge, ++testNumber);
        testValidAge(minAge + 1, ++testNumber);
        testValidAge(minAge + 10, ++testNumber);
    }

    private void testValidAge(Integer age, int testNumber) {
        User expected = createValidUser(testNumber);

        expected.setAge(age);
        User actual = registrationServiceImpl.register(expected);
        assertEquals(expected, actual,
                "User adding with walid age doesnt work; Age = " + age);
    }

    private void testInvalidLogin(String login, String sortOffInvalidLogin) {
        User userWithInvalidLogin = createValidUser();
        String message = "Login " + sortOffInvalidLogin
                + " should throw UserNotValidException; Login is " + login;

        userWithInvalidLogin.setLogin(login);
        testInvalidUser(userWithInvalidLogin, message);
    }

    private void testInvalidPassword(String password, String sortOffInvalidPassword) {
        User userWithInvalidPassword = createValidUser();
        String message = "Password " + sortOffInvalidPassword
                + " should throw UserNotValidException; Password is " + password;

        userWithInvalidPassword.setPassword(password);
        testInvalidUser(userWithInvalidPassword, message);
    }

    private void testInvalidAge(Integer age, String sortOfInvalidAge) {
        User userWithInvalidAge = createValidUser();
        String message = "Age " + sortOfInvalidAge
                + " should throw UserNotValidException; Age = " + age;

        userWithInvalidAge.setAge(age);
        testInvalidUser(userWithInvalidAge, message);
    }

    private void testInvalidUser(User invalidUser, String message) {
        assertThrows(UserNotValidException.class, () ->
                registrationServiceImpl.register(invalidUser), message);
    }

    private User createValidUser(int indexOfUser) {
        User user = createValidUser();
        user.setLogin(user.getLogin() + indexOfUser);
        return user;
    }

    private User createValidUser() {
        User user = new User();
        user.setLogin(SHORTEST_VALID_LOGIN);
        user.setPassword(SHORTEST_VALID_PASSWORD);
        user.setAge(MIN_VALID_AGE);
        return user;
    }
}
