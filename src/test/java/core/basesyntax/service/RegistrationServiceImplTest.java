package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("test-user");
        user.setPassword("test-password");
        user.setAge(RegistrationServiceImpl.AGE_MINIMUM_FOR_REGISTRATION);
    }

    @Test
    void register_loginNull_notOk() {
        user.setLogin(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginAlreadyExist_notOk() {
        registrationService.register(user);
        User newUser = new User();
        newUser.setLogin(user.getLogin());
        newUser.setPassword(user.getPassword());
        newUser.setAge(user.getAge());
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_loginEmpty_notOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_login_ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_loginMultiplyRegistration_ok() {
        int numberUsers = 5;
        for (int i = 0; i < numberUsers; i++) {
            User newUser = new User();
            newUser.setLogin("new-user-" + i);
            newUser.setPassword("test-password" + i);
            newUser.setAge(RegistrationServiceImpl.AGE_MINIMUM_FOR_REGISTRATION + i);
            registrationService.register(newUser);
        }
        assertEquals(numberUsers, Storage.people.size());
    }

    @Test
    void register_ageNull_notOk() {
        user.setAge(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageUnderRestriction_notOk() {
        user.setAge(RegistrationServiceImpl.AGE_MINIMUM_FOR_REGISTRATION - 1);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageNegative_notOk() {
        user.setAge(-1);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageRestrictionEqual_ok() {
        user.setAge(RegistrationServiceImpl.AGE_MINIMUM_FOR_REGISTRATION);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_ageAboveRestriction_ok() {
        user.setAge(RegistrationServiceImpl.AGE_MINIMUM_FOR_REGISTRATION + 1);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_passwordNull_notOk() {
        user.setPassword(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordEmpty_notOk() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordShort_notOk() {
        user.setPassword("*".repeat(RegistrationServiceImpl.PASSWORD_MINIMUM_LENGTH - 1));
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_password_ok() {
        user.setPassword("*".repeat(RegistrationServiceImpl.PASSWORD_MINIMUM_LENGTH));
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
