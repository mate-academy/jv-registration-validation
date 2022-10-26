package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String USER_NAME_1 = "Bob";
    private static final String USER_PASSWORD_1 = "123456";
    private static final int USER_AGE_1 = 18;
    private static final String EMPTY_STRING = "";
    private static final String WHITESPACE_CHARACTERS_STRING = "  \t\n";
    private static final String INVALID_PASSWORD_ABCDE = "abcde";
    private static final int INVALID_AGE = 17;
    private static final int INVALID_AGE_2 = Integer.MIN_VALUE;
    private static final int INVALID_AGE_3 = 121;
    private static final int INVALID_AGE_4 = Integer.MAX_VALUE;
    private static final String USER_LOGIN_2 = "Rick";
    private static final String USER_PASSWORD_2 = "pickle";
    private static final int USER_AGE_2 = 65;
    private static final String USER_LOGIN_3 = "Rudeus";
    private static final String USER_PASSWORD_3 = "quagmire";
    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private User actualUser;

    @BeforeEach
    void setUp() {
        actualUser = new User();
        actualUser.setLogin(USER_NAME_1);
        actualUser.setPassword(USER_PASSWORD_1);
        actualUser.setAge(USER_AGE_1);
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        actualUser = null;
        runAssertThrows(actualUser);
    }

    @Test
    void register_nullUserLogin_notOk() {
        actualUser.setLogin(null);
        runAssertThrows(actualUser);
    }

    @Test
    void register_emptyUserLoginString_notOk() {
        actualUser.setLogin(EMPTY_STRING);
        runAssertThrows(actualUser);
    }

    @Test
    void register_whitespaceOnlyUserLoginString_notOk() {
        actualUser.setLogin(WHITESPACE_CHARACTERS_STRING);
        runAssertThrows(actualUser);
    }

    @Test
    void register_sameUserAlreadyExistAndRegistered_notOk() {
        Storage.people.add(actualUser);
        runAssertThrows(actualUser);
    }

    @Test
    void register_nullPassword_notOk() {
        actualUser.setPassword(null);
        runAssertThrows(actualUser);
    }

    @Test
    void register_passwordMinLength_notOk() {
        actualUser.setPassword(INVALID_PASSWORD_ABCDE);
        runAssertThrows(actualUser);
    }

    @Test
    void register_minAge_notOk() {
        actualUser.setAge(INVALID_AGE);
        runAssertThrows(actualUser);
        actualUser.setAge(INVALID_AGE_2);
        runAssertThrows(actualUser);
    }

    @Test
    void register_maxAge_notOk() {
        actualUser.setAge(INVALID_AGE_3);
        runAssertThrows(actualUser);
        actualUser.setAge(INVALID_AGE_4);
        runAssertThrows(actualUser);
    }

    @Test
    void register_oneRegisteredUser_isReturnedSameAsPassed_ok() {
        assertEquals(actualUser, registrationService.register(actualUser));
    }

    @Test
    void register_coupleOfRegisteredUsers_areReturnedSameAsPassed_ok() {
        User validUser = new User();
        validUser.setLogin(USER_LOGIN_2);
        validUser.setPassword(USER_PASSWORD_2);
        validUser.setAge(USER_AGE_2);
        Storage.people.add(validUser);
        assertEquals(actualUser, registrationService.register(actualUser));
    }

    @Test
    void register_oneUserStoredCorrectly_ok() {
        Storage.people.add(actualUser);
        assertTrue(Storage.people.contains(actualUser));
        assertEquals(actualUser, Storage.people.get(0));
    }

    @Test
    void register_coupleOfUsersStoredCorrectly_ok() {
        User validUser1 = new User();
        validUser1.setLogin(USER_LOGIN_2);
        validUser1.setPassword(USER_PASSWORD_2);
        validUser1.setAge(USER_AGE_2);
        Storage.people.add(validUser1);
        Storage.people.add(actualUser);
        User validUser2 = new User();
        validUser1.setLogin(USER_LOGIN_3);
        validUser1.setPassword(USER_PASSWORD_3);
        Storage.people.add(validUser2);
        assertTrue(Storage.people.contains(actualUser));
        assertEquals(actualUser, Storage.people.get(1));
    }

    @Test
    void register_everything_isOk() {
        assertDoesNotThrow(() -> registrationService.register(actualUser));
    }

    private void runAssertThrows(User user) {
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}
