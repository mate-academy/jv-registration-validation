package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationTests {
    private static RegistrationService registrationService;
    private static User userTest;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        userTest = new User();

    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        userTest.setLogin("DefaultLogin");
        userTest.setPassword("dEfAu1tPa55w0rd");
        userTest.setAge(18);
    }

    @Test
    void register_addCorrect_user_ok() {
        User actual = registrationService.register(userTest);
        assertEquals(userTest, actual);
    }

    @Test
    void register_addUserWithSameLogin_notOK() {
        registrationService.register(userTest);
        User sameLogin = new User();
        sameLogin.setLogin("DefaultLogin");
        sameLogin.setAge(18);
        sameLogin.setPassword("dEfAu1tPa55w0rd");
        assertThrows(RuntimeException.class, () -> registrationService.register(sameLogin));
    }

    @Test
    void register_addUserTwice_notOK() {
        registrationService.register(userTest);
        assertThrows(RuntimeException.class, () -> registrationService.register(userTest));
    }

    @Test
    void register_addEmptyLogin_notOk() {
        userTest.setLogin("");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void register_loginContainsWhitespaces_notOk() {
        userTest.setLogin("L o g i n");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void register_addLoginIsNull_notOk() {
        userTest.setLogin(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void register_addPasswordWithoutDigits_notOk() {
        userTest.setPassword("passWord");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void register_addPasswordWhitespace_notOk() {
        userTest.setPassword("pass Word1");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void register_addPasswordUppercaseLetter_notOk() {
        userTest.setPassword("veryagressivehobbit");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void register_addShortPassword_notOk() {
        userTest.setPassword("java");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void register_addPasswordIsNull_notOk() {
        userTest.setPassword(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void register_addMinAge_notOk() {
        userTest.setAge(17);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void register_addAgeIsNull_notOk() {
        userTest.setLogin("NLO");
        userTest.setPassword("I_WANT_T0_BEL1VE");
        userTest.setAge(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userTest));
    }
}
