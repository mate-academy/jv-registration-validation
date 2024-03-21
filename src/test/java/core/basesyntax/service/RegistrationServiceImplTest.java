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
    void register_userAlreadyExist_notOk() {
        Storage.people.add(user);
        assertThrows(RegistrationException.class, () ->
                    registrationService.register(user),
                "If login is occupied, user can't be registered");
        Storage.people.remove(user);
    }

    @Test
    void register_loginLengthIsZero_notOk() {
        user.setLogin("");
        assertThrows(RegistrationException.class, () ->
                    registrationService.register(user),
                "User with login length less than valid can't be registered");
    }

    @Test
    void register_loginLengthIsLessThanValid_notOk() {
        user.setLogin("n6d");
        assertThrows(RegistrationException.class, () ->
                    registrationService.register(user),
                "User with login length less than valid can't be registered");
    }

    @Test
    void register_loginLengthIsValidMinusOne_notOk() {
        user.setLogin("47gno");
        assertThrows(RegistrationException.class, () ->
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
    void register_passwordLengthIsZero_notOk() {
        user.setPassword("");
        assertThrows(RegistrationException.class, () ->
                    registrationService.register(user),
                "User with password length less than valid can't be registered");
    }

    @Test
    void register_passwordLengthIsLessThanValid_notOk() {
        user.setPassword("kdy");
        assertThrows(RegistrationException.class, () ->
                    registrationService.register(user),
                "User with password length less than valid can't be registered");
    }

    @Test
    void register_passwordLengthIsValidMinusOne_notOk() {
        user.setPassword("0jr42");
        assertThrows(RegistrationException.class, () ->
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
    void register_ageIsNegative_notOk() {
        user.setAge(-10);
        assertThrows(RegistrationException.class, () ->
                    registrationService.register(user),
                "User with negative age can't be registered");
    }

    @Test
    void register_ageLessThanValid_notOk() {
        user.setAge(8);
        assertThrows(RegistrationException.class, () ->
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
    void register_userIsNull_notOk() {
        user = null;
        assertThrows(RegistrationException.class, () ->
                    registrationService.register(user),
                "Null user can't be registered");
    }

    @Test
    void register_userIdIsNull_notOk() {
        user.setId(null);
        assertThrows(RegistrationException.class, () ->
                    registrationService.register(user),
                "User with null Id can't be registered");
    }

    @Test
    void register_userLoginIsNull_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () ->
                    registrationService.register(user),
                "User with null Login can't be registered");
    }

    @Test
    void register_userPasswordIsNull_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () ->
                    registrationService.register(user),
                "User with null Password can't be registered");
    }

    @Test
    void register_userAgeIsNull_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () ->
                    registrationService.register(user),
                "User with null Age can't be registered");
    }
}
