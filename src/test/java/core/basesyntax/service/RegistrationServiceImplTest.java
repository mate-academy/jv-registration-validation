package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.UserInvalidDataException;
import core.basesyntax.model.User;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RegistrationServiceImplTest {
    private static final int EXPECTED_ID = 1;
    private static final int MIN_CORRECT_AGE = 18;
    private static final int MAX_INCORRECT_AGE = 0;
    private static final int MIN_INCORRECT_AGE = -100;
    private static final int MIN_AGE_LESS_18 = 1;
    private static final int MAX_CORRECT_TESTING_AGE = 100;
    private static final int METHOD_EXECUTION_SEQUENCE_NUMBER = 1;
    private static final String CORRECT_LOGIN = "correctLogin";
    private static final String TEST_LOGIN_SECOND = "testLoginSecond";
    private static final String TEST_LOGIN_FOR_AGE = "testLoginForAge";
    private static final Map<String, String> passwordsByLogin = Map.of(
            "correctLogin", "correctPassword",
            "testLoginSecond", "testPasswordSecond"
    );
    private static final List<String> shortLogins = List.of(
            "", "t", "tt", "ttt", "tttt", "ttttt");
    private static final List<String> shortPasswords = List.of(
            "", "p", "pp", "ppp", "pppp", "ppppp");

    private static StorageDao storageDao;
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void initVariables() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUser() {
        user = new User();
        user.setLogin(CORRECT_LOGIN);
        user.setPassword(passwordsByLogin.get(CORRECT_LOGIN));
        user.setAge(MIN_CORRECT_AGE);
    }

    @Order(METHOD_EXECUTION_SEQUENCE_NUMBER)
    @Test
    void register_validUser_Ok() {
        User actualUser = registrationService.register(user);

        assertEquals(user, actualUser);
        assertEquals(EXPECTED_ID, actualUser.getId());
    }

    @Test
    void register_nullUser_notOk() {
        runAssertThrows(null);
    }

    @Test
    void register_loginPresentInStorage_notOk() {
        user.setLogin(TEST_LOGIN_SECOND);
        user.setPassword(passwordsByLogin.get(TEST_LOGIN_SECOND));
        storageDao.add(user);

        runAssertThrows(user);
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);

        runAssertThrows(user);
    }

    @Test
    void register_loginLengthLessThen6_notOk() {
        for (String shortLogin : shortLogins) {
            user.setLogin(shortLogin);

            runAssertThrows(user);
        }
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);

        runAssertThrows(user);
    }

    @Test
    void register_passwordLengthLessThen6_notOk() {
        for (String shortPassword : shortPasswords) {
            user.setPassword(shortPassword);

            runAssertThrows(user);
        }
    }

    @Test
    void register_ageLessThenOrZero_notOk() {
        for (int i = MAX_INCORRECT_AGE; i >= MIN_INCORRECT_AGE; i--) {
            user.setAge(i);

            runAssertThrows(user);
        }
    }

    @Test
    void register_ageLessThen18_notOk() {
        for (int i = MIN_AGE_LESS_18; i < MIN_CORRECT_AGE; i++) {
            user.setAge(i);

            runAssertThrows(user);
        }
    }

    @Test
    void register_ageOver18_Ok() {
        for (int i = MIN_CORRECT_AGE; i <= MAX_CORRECT_TESTING_AGE; i++) {
            setUser();
            user.setLogin(TEST_LOGIN_FOR_AGE + i);
            user.setAge(i);

            registrationService.register(user);
        }
    }

    private void runAssertThrows(User user) {
        assertThrows(UserInvalidDataException.class,
                () -> registrationService.register(user));
    }
}
