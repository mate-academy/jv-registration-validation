package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exeptions.UserValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService regService;
    private static final String GENERAL_LOGIN = "login";
    private static final String GENERAL_PASSWORD = "123456";
    private static final Integer GENERAL_AGE = 25;

    @BeforeAll
    static void beforeAll() {
        regService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_userIsNull_notOk() {
        assertThrows(UserValidationException.class, () -> regService.register(null));
    }

    @Test
    void register_loginIsNull_notOk() {
        User userWithLoginIsNull = createUser(null, GENERAL_PASSWORD, GENERAL_AGE);
        assertThrows(
                UserValidationException.class, () -> regService.register(userWithLoginIsNull)
        );
    }

    @Test
    void register_loginIsEmpty_notOk() {
        User userWithEmptyLogin = createUser("", GENERAL_PASSWORD, GENERAL_AGE);
        assertThrows(
                UserValidationException.class, () -> regService.register(userWithEmptyLogin)
        );
    }

    @Test
    void register_passwordIsNull_notOk() {
        User userWithPasswordIsNull = createUser(GENERAL_LOGIN, null, GENERAL_AGE);
        assertThrows(
                UserValidationException.class, () -> regService.register(userWithPasswordIsNull)
        );
    }

    @Test
    void register_passwordIsShort_notOk() {
        User userWithShortPassword = createUser(GENERAL_LOGIN, "12345", GENERAL_AGE);
        assertThrows(
                UserValidationException.class, () -> regService.register(userWithShortPassword)
        );
    }

    @Test
    void register_ageIsNull_notOk() {
        User userWithAgeIsNull = createUser(GENERAL_LOGIN, GENERAL_PASSWORD, null);
        assertThrows(UserValidationException.class, () -> regService.register(userWithAgeIsNull));
    }

    @Test
    void register_ageIsSmall_notOk() {
        User userWithSmallAge = createUser(GENERAL_LOGIN, GENERAL_PASSWORD, 17);
        assertThrows(UserValidationException.class, () -> regService.register(userWithSmallAge));
    }

    @Test
    void register_ageIsHuge_notOk() {
        User userWithInvalidHugeAge = createUser(GENERAL_LOGIN, GENERAL_PASSWORD, 151);
        assertThrows(
                UserValidationException.class, () -> regService.register(userWithInvalidHugeAge)
        );
    }

    @Test
    void register_ageIsNegative_notOk() {
        User userWithNegativeAge = createUser(GENERAL_LOGIN, GENERAL_PASSWORD, -5);
        assertThrows(UserValidationException.class, () -> regService.register(userWithNegativeAge));
    }

    @Test
    void register_userWithSameLogin_notOk() {
        User userWithValidSmallAge = createUser(GENERAL_LOGIN, GENERAL_PASSWORD, 18);
        User userWithSameLogin = createUser(GENERAL_LOGIN, "654321", 50);
        regService.register(userWithValidSmallAge);
        assertEquals(1, Storage.people.size());
        assertThrows(UserValidationException.class, () -> regService.register(userWithSameLogin));
    }

    @Test
    void register_validUserList_ok() {
        User firstUser = createUser(GENERAL_LOGIN + '1', GENERAL_PASSWORD, 18);
        User secondUser = createUser(GENERAL_LOGIN + '2', "1234567", GENERAL_AGE);
        User thirdUser = createUser(GENERAL_LOGIN + '3', "12345678", 150);
        regService.register(firstUser);
        regService.register(secondUser);
        regService.register(thirdUser);
        assertEquals(3, Storage.people.size());
    }

    private User createUser(String login, String password, Integer age) {
        User user = new User();
        user.setAge(age);
        user.setLogin(login);
        user.setPassword(password);

        return user;
    }
}
