package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int VALID_AGE = 18;
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(222585477L);
        user.setLogin("457hrl95d");
        user.setPassword("54761435");
        user.setAge(54);
    }

    @Test
    void register_validUser_Ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual, "Valid user must be registered");
        Storage.people.remove(user);
    }

    @Test
    void register_userAlreadyInStorage_notOk() {
        Storage.people.add(user);
        assertThrows(RegisterException.class, () ->
                    registrationService.register(user),
                "If login is occupied, user can't be registered");
        Storage.people.remove(user);
    }

    @Test
    void register_loginLengthIsZero_notOk() {
        user.setLogin("");
        assertThrows(RegisterException.class, () ->
                    registrationService.register(user),
                "User with login length less than valid can't be registered");
    }

    @Test
    void register_loginLengthIsLessThanValid_notOk() {
        user.setLogin("n6d");
        assertThrows(RegisterException.class, () ->
                    registrationService.register(user),
                "User with login length less than valid can't be registered");
    }

    @Test
    void register_loginLengthIsValidMinusOne_notOk() {
        user.setLogin("47gno");
        assertThrows(RegisterException.class, () ->
                    registrationService.register(user),
                "User with login length less than valid can't be registered");
    }

    @Test
    void register_loginLengthIsExactValid_Ok() {
        user.setLogin("nsp82h");
        User actual = registrationService.register(user);
        assertEquals(user, actual,
                "User with login length equal to valid must be registered");
        Storage.people.remove(user);
    }

    @Test
    void register_loginLengthIsGreaterThanMinValid_Ok() {
        user.setLogin("pfywnk96d");
        User actual = registrationService.register(user);
        assertEquals(user, actual,
                "User with login length greater than valid must be registered");
        Storage.people.remove(user);
    }

    @Test
    void register_passwordLengthIsZero_notOk() {
        user.setPassword("");
        assertThrows(RegisterException.class, () ->
                    registrationService.register(user),
                "User with password length less than valid can't be registered");
    }

    @Test
    void register_passwordLengthIsLessThanValid_notOk() {
        user.setPassword("kdy");
        assertThrows(RegisterException.class, () ->
                    registrationService.register(user),
                "User with password length less than valid can't be registered");
    }

    @Test
    void register_passwordLengthIsValidMinusOne_notOk() {
        user.setPassword("0jr42");
        assertThrows(RegisterException.class, () ->
                    registrationService.register(user),
                "User with password length less than valid can't be registered");
    }

    @Test
    void register_passwordLengthIsExactValid_Ok() {
        user.setPassword("mntr31");
        User actual = registrationService.register(user);
        assertEquals(user, actual,
                "User with password length equal to valid must be registered");
        Storage.people.remove(user);
    }

    @Test
    void register_passwordLengthIsGreaterThanMinValid_Ok() {
        user.setPassword("lt84v86ehg");
        User actual = registrationService.register(user);
        assertEquals(user, actual,
                "User with password length more than valid must be registered");
        Storage.people.remove(user);
    }

    @Test
    void register_ageIsNegative_notOk() {
        user.setAge(-10);
        assertThrows(RegisterException.class, () ->
                    registrationService.register(user),
                "User with negative age can't be registered");
    }

    @Test
    void register_ageLessThanValid_notOk() {
        user.setAge(8);
        assertThrows(RegisterException.class, () ->
                    registrationService.register(user),
                "User with age less than valid can't be registered");
    }

    @Test
    void register_ageIsEqualToValid_Ok() {
        user.setAge(VALID_AGE);
        User actual = registrationService.register(user);
        assertEquals(user, actual,
                "User with age equal to valid can't be registered");
        Storage.people.remove(user);
    }

    @Test
    void register_ageIsGreaterThanValid_Ok() {
        user.setAge(VALID_AGE);
        User actual = registrationService.register(user);
        assertEquals(user, actual,
                "User with age greater than valid can't be registered");
        Storage.people.remove(user);
    }

    @Test
    void register_userIsNull_notOk() {
        user = null;
        assertThrows(RegisterException.class, () ->
                    registrationService.register(user),
                "Null user can't be registered");
    }

    @Test
    void register_userIdIsNull_notOk() {
        user.setId(null);
        assertThrows(RegisterException.class, () ->
                    registrationService.register(user),
                "User with null Id can't be registered");
    }

    @Test
    void register_userLoginIsNull_notOk() {
        user.setLogin(null);
        assertThrows(RegisterException.class, () ->
                    registrationService.register(user),
                "User with null Login can't be registered");
    }

    @Test
    void register_userPasswordIsNull_notOk() {
        user.setPassword(null);
        assertThrows(RegisterException.class, () ->
                    registrationService.register(user),
                "User with null Password can't be registered");
    }

    @Test
    void register_userAgeIsNull_notOk() {
        user.setAge(null);
        assertThrows(RegisterException.class, () ->
                    registrationService.register(user),
                "User with null Age can't be registered");
    }

    @Test
    void register_userIdAndAgeAreNull_notOk() {
        user.setId(null);
        user.setAge(null);
        RegisterException exception = assertThrows(RegisterException.class, () ->
                registrationService.register(user),
                "User with null Id and Age can't be registered");
        assertEquals("Cannot register user, Id Age can't be null",
                exception.getMessage(),
                "The message should be: Cannot register user, Id Age can't be null");
    }

    @Test
    void register_userIdAndLoginAreNull_notOk() {
        user.setId(null);
        user.setLogin(null);
        RegisterException exception = assertThrows(RegisterException.class, () ->
                registrationService.register(user),
                "User with null Id and Age can't be registered");
        assertEquals("Cannot register user, Id Login can't be null",
                exception.getMessage(),
                "The message should be: Cannot register user, Id Login can't be null");
    }

    @Test
    void register_userPasswordAndAgeAreNull_notOk() {
        user.setPassword(null);
        user.setAge(null);
        RegisterException exception = assertThrows(RegisterException.class, () ->
                registrationService.register(user),
                "User with null Id and Age can't be registered");
        assertEquals("Cannot register user, Password Age can't be null",
                exception.getMessage(),
                "The message should be: Cannot register user, Password Age can't be null");
    }

    @Test
    void register_userIdPasswordLoginAgeAreNull_notOk() {
        user.setId(null);
        user.setLogin(null);
        user.setPassword(null);
        user.setAge(null);
        RegisterException exception = assertThrows(RegisterException.class, () ->
                registrationService.register(user),
                "User with null Id Login Password Age can't be registered");
        assertEquals("Cannot register user, Id Login Password Age can't be null",
                exception.getMessage(),
                "The message should be: Cannot register user, Id Login Password Age can't be null");
    }
}
