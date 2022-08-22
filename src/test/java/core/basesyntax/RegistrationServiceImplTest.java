package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "BobBlink";
    private static final String HUGE_LOGIN = "BobBlinkBobBlinkBobBlink";
    private static final String NULL_VALUE = null;
    private static final int VALID_AGE = 25;
    private static final int LESS_AGE = -5;
    private static final int HIGH_AGE = 100;
    private static final String VALID_PASSWORD = "123456789";
    private static final String NOT_VALID_PASSWORD = "123";
    private static final User NULL_USER = null;
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

    @BeforeEach
    void beforeEach() {
        user.setLogin(VALID_LOGIN);
        user.setAge(VALID_AGE);
        user.setPassword(VALID_PASSWORD);
    }

    @Test
    void checkLoginIsNull_NotOk() {
        user.setLogin(NULL_VALUE);
        tryToRegisterException(user, "Login can't be NULL");
    }

    @Test
    void checkPasswordIsNull_NotOk() {
        user.setPassword(NULL_VALUE);
        tryToRegisterException(user, "Password can't be NULL");
    }

    @Test
    void checkAgeIsLessThanNormal_NotOk() {
        user.setAge(LESS_AGE);
        tryToRegisterException(user, "Age can't be less than 18");
    }

    @Test
    void checkAgeIsHigherThanNormal_NotOk() {
        user.setAge(HIGH_AGE);
        tryToRegisterException(user, "Age can't be older than 99");
    }

    @Test
    void checkLoginTheSame_NotOk() {
        registrationService.register(user);
        registrationService.register(user);
        assertEquals(1,Storage.people.size(), "The same login can't be able");
    }

    @Test
    void checkLoginHuge_NotOk() {
        user.setLogin(HUGE_LOGIN);
        tryToRegisterException(user, "Login can't be bigger than 20 characters");
    }

    @Test
    void checkPasswordSmall_NotOk() {
        user.setPassword(NOT_VALID_PASSWORD);
        tryToRegisterException(user, "Login can't be lower than 6 characters");
    }

    @Test
    void checkAddUser_Ok() {
        Storage.people.add(user);
        assertEquals(user,Storage.people.get(0), "User not added");
    }

    @Test
    void checkGetUser_Ok() {
        Storage.people.add(user);
        User getUser = Storage.people.get(0);
        assertEquals(user,getUser, "User not added");
    }

    @Test
    void checkAgeIsNull_NotOk() {
        user.setAge(null);
        tryToRegisterException(user, "Age can't be null");
    }

    @Test
    void checkUserIsNull_NotOk() {
        tryToRegisterException(NULL_USER, "User can't be NULL");
    }

    private void tryToRegisterException(User user, String message) {
        assertThrows(RuntimeException.class, () -> registrationService.register(user), message);
    }
}
