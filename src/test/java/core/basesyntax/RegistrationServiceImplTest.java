package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int DEFAULT_AGE = 27;
    private static final int OTHER_AGE = 22;
    private static final int WRONG_AGE1 = 0;
    private static final int WRONG_AGE2 = -10;
    private static final String DEFAULT_LOGIN = "login";
    private static final String DEFAULT_PASSWORD = "password";
    private static final String OTHER_PASSWORD = "another_Password";
    private static final String WRONG_PASSWORD = "1234";
    private static RegistrationServiceImpl regService;
    private User defaultUser;

    @BeforeAll
    static void beforeAll() {
        regService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        defaultUser = new User();
        defaultUser.setLogin(DEFAULT_LOGIN);
        defaultUser.setPassword(DEFAULT_PASSWORD);
        defaultUser.setAge(DEFAULT_AGE);
    }

    @Test
    void register_DefaultUser_OK() {
        User expected = defaultUser;
        User actual = regService.register(defaultUser);
        assertEquals(expected.getLogin(), actual.getLogin());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getAge(), actual.getAge());
    }

    @Test
    void register_nullUser_notOK() {
        assertThrows(RuntimeException.class, () -> {
            regService.register(null);
        });
    }

    @Test
    void register_idNotNull_notOK() {
        User userIdNotNull = defaultUser;
        userIdNotNull.setId(1L);
        assertThrows(RuntimeException.class, () -> {
            regService.register(userIdNotNull);
        });
    }

    @Test
    void register_NullLogin_notOK() {
        User userWithNullLogin = defaultUser;
        userWithNullLogin.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            regService.register(userWithNullLogin);
        });
    }

    @Test
    void register_NullPassword_notOK() {
        User userWithNullPassword = defaultUser;
        userWithNullPassword.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            regService.register(userWithNullPassword);
        });
    }

    @Test
    void register_NullAge_notOK() {
        User userWithNullAge = defaultUser;
        userWithNullAge.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            regService.register(userWithNullAge);
        });
    }

    @Test
    void register_WrongAge_notOK() {
        User userWrongAge = defaultUser;
        userWrongAge.setAge(WRONG_AGE1);
        assertThrows(RuntimeException.class, () -> {
            regService.register(userWrongAge);
        });
        userWrongAge.setAge(WRONG_AGE2);
        assertThrows(RuntimeException.class, () -> {
            regService.register(userWrongAge);
        });

    }

    @Test
    void register_LoginIsAlreadyInTheStorage_notOk() {
        regService.register(defaultUser);
        User sameLogin = defaultUser;
        sameLogin.setPassword(OTHER_PASSWORD);
        sameLogin.setAge(OTHER_AGE);
        assertThrows(RuntimeException.class, () -> {
            regService.register(sameLogin);
        });
    }

    @Test
    void register_shortPassword_notOK() {
        User userPasswordIsWrong = defaultUser;
        userPasswordIsWrong.setPassword(WRONG_PASSWORD);
        assertThrows(RuntimeException.class, () -> {
            regService.register(userPasswordIsWrong);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
