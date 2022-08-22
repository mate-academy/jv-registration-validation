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
    private static User userTest = new User();

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        userTest = new User();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    @Test
    void register_addCorrect_user_ok() {
        userTest.setLogin("Hottabich");
        userTest.setPassword("LampIsSuck100%");
        userTest.setAge(19);
        assertEquals(userTest, registrationService.register(userTest));
    }

    @Test
    void register_addUserWithSameLogin_notOK() {
        userTest.setLogin("Login");
        userTest.setAge(180);
        userTest.setPassword("13@shitHappens");
        registrationService.register(userTest);
        User sameLogin = new User();
        sameLogin.setLogin("Login");
        sameLogin.setAge(180);
        sameLogin.setPassword("13@shitHappens");
        assertThrows(RuntimeException.class, () -> registrationService.register(sameLogin));
    }

    @Test
    void register_addUserTwice_notOK() {
        userTest.setLogin("Login");
        userTest.setAge(180);
        userTest.setPassword("13@shitHappens");
        registrationService.register(userTest);
        assertThrows(RuntimeException.class, () -> registrationService.register(userTest));
    }

    @Test
    void register_addAge_Ok() {
        userTest.setLogin("Login");
        userTest.setPassword("passWord1");
        userTest.setAge(18);
        assertEquals(userTest, registrationService.register(userTest));
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
        assertThrows(NullPointerException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void register_addPasswordWithoutDigits_notOk() {
        userTest.setLogin("Login");
        userTest.setPassword("passWord");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void register_addPasswordWhitespace_notOk() {
        userTest.setLogin("Login");
        userTest.setPassword("pass Word1");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void register_addPasswordUppercaseLetter_notOk() {
        userTest.setLogin("Belka");
        userTest.setPassword("strelka");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void register_addShortPassword_notOk() {
        userTest.setLogin("BestLenguage");
        userTest.setPassword("java");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void register_addPasswordIsNull_notOk() {
        userTest.setLogin("Login");
        userTest.setPassword(null);
        assertThrows(NullPointerException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void register_addMinAge_notOk() {
        userTest.setLogin("Maloletka");
        userTest.setPassword("MorgenshternKrut666");
        userTest.setAge(17);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void register_addAgeIsNull_notOk() {
        userTest.setLogin("NLO");
        userTest.setPassword("I_WANT_T0_BEL1VE");
        userTest.setAge(null);
        assertThrows(NullPointerException.class,
                () -> registrationService.register(userTest));
    }
}
