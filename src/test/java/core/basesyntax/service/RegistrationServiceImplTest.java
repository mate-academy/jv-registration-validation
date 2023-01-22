package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exeptions.UserValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService regService;

    @BeforeEach
    void setUp() {
        regService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_UserIsNull_NotOk() {
        assertThrows(UserValidationException.class, () -> regService.register(null));
    }

    @Test
    void register_loginIsNull_NotOk() {
        User userWithLoginIsNull = createUser(null, "123456", 25);
        assertThrows(
                UserValidationException.class, () -> regService.register(userWithLoginIsNull)
        );
    }

    @Test
    void register_loginIsEmpty_NotOk() {
        User userWithEmptyLogin = createUser("", "123456", 25);
        assertThrows(
                UserValidationException.class, () -> regService.register(userWithEmptyLogin)
        );
    }

    @Test
    void register_PasswordIsNull_NotOk() {
        User userWithPasswordIsNull = createUser("login", null, 25);
        assertThrows(
                UserValidationException.class, () -> regService.register(userWithPasswordIsNull)
        );
    }

    @Test
    void register_PasswordIsShort_NotOk() {
        User userWithShortPassword = createUser("login", "12345", 25);
        assertThrows(
                UserValidationException.class, () -> regService.register(userWithShortPassword)
        );
    }

    @Test
    void register_AgeIsNull_NotOk() {
        User userWithAgeIsNull = createUser("login", "123456", null);
        assertThrows(UserValidationException.class, () -> regService.register(userWithAgeIsNull));
    }

    @Test
    void register_AgeIsSmall_NotOk() {
        User userWithSmallAge = createUser("login", "123456", 17);
        assertThrows(UserValidationException.class, () -> regService.register(userWithSmallAge));
    }

    @Test
    void register_AgeIsHuge_NotOk() {
        User userWithInvalidHugeAge = createUser("login", "123456", 151);
        assertThrows(
                UserValidationException.class, () -> regService.register(userWithInvalidHugeAge)
        );
    }

    @Test
    void register_AgeIsNegative_NotOk() {
        User userWithNegativeAge = createUser("login", "123456", -5);
        assertThrows(UserValidationException.class, () -> regService.register(userWithNegativeAge));
    }

    @Test
    void register_userWithSameLogin_NotOk() {
        User userWithValidSmallAge = createUser("login", "123456", 18);
        User userWithSameLogin = createUser("login", "654321", 50);
        regService.register(userWithValidSmallAge);
        assertEquals(1, Storage.people.size());
        assertThrows(UserValidationException.class, () -> regService.register(userWithSameLogin));
    }

    @Test
    void register_validUserList_Ok() {
        User firstUser = createUser("login1", "123456", 18);
        User secondUser = createUser("login2", "1234567", 70);
        User thirdUser = createUser("login3", "12345678", 150);
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
