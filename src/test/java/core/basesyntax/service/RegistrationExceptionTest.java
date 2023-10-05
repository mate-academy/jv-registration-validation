package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationExceptionTest {
    private static RegistrationService registrationService;
    private User user;
    private int localUserId;

    @BeforeAll
    public static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        localUserId = 0;
        user = getUniqueValidUser();
    }

    @Test
    void register_nullUser_notOk() {
        registrationExceptionHandler(null);
    }

    @Test
    void register_nullUserLogin_notOk() {
        user.setLogin(null);
        registrationExceptionHandler(user);
    }

    @Test
    void register_ExistedUserLogin_notOk() throws RegistrationException {
        User[] validUsers = new User[] {user, getUniqueValidUser()};
        for (User user : validUsers) {
            registrationService.register(user);
        }
        registrationExceptionHandler(user);
    }

    @Test
    void register_UserLoginLength_notOk() {
        String[] notOkLogins = new String[] {"", "a", "abc", "abcde"};
        for (String login : notOkLogins) {
            user.setLogin(login);
            registrationExceptionHandler(user);
        }
    }

    @Test
    void register_UserLogin_Ok() throws RegistrationException {
        String[] okLogins = new String[] {"abcdef", "abcdefg", "abcdefgh"};
        for (String login : okLogins) {
            user = getUniqueValidUser();
            user.setLogin(login);
            registrationService.register(user);
        }
    }

    @Test
    void register_nullUserPassword_notOk() {
        user.setPassword(null);
        registrationExceptionHandler(user);
    }

    @Test
    void register_UserPasswordLength_notOk() {
        String[] notOkPasswords = new String[] {"", "a", "abc", "abcde"};
        for (String password : notOkPasswords) {
            user.setPassword(password);
            registrationExceptionHandler(user);
        }
    }

    @Test
    void register_UserPassword_Ok() throws RegistrationException {
        String[] okPasswords = new String[] {"abcdef", "abcdefg", "abcdefgh"};
        for (String password : okPasswords) {
            user = getUniqueValidUser();
            user.setPassword(password);
            registrationService.register(user);
        }
    }

    @Test
    void register_nullUserAge_notOk() {
        user.setAge(null);
        registrationExceptionHandler(user);
    }

    @Test
    void register_UserAgeValue_notOk() {
        int[] notOkAges = new int[] {-67, -19, -18, -17, -1, 0, 1, 17};
        for (int age : notOkAges) {
            user.setAge(age);
            registrationExceptionHandler(user);
        }
    }

    @Test
    void register_UserAgeValue_Ok() throws RegistrationException {
        int[] okAges = new int[] {18, 19, 67, 109};
        for (int age : okAges) {
            user = getUniqueValidUser();
            user.setAge(age);
            registrationService.register(user);
        }
    }

    private User getUniqueValidUser() {
        User newUser = new User();
        newUser.setLogin("UserLogin " + localUserId);
        newUser.setPassword("UserPassword " + localUserId);
        newUser.setAge(18 + localUserId);
        localUserId++;
        return newUser;
    }

    private void registrationExceptionHandler(User user) {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}
