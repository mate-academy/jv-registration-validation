package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationTests {
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final User userTest = new User();

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    @Test
    void correct_user_ok() {
        userTest.setLogin("Hottabich");
        userTest.setPassword("LampIsSuck100%");
        userTest.setAge(19);
        assertEquals(userTest, registrationService.register(userTest));
    }

    @Test
    void incorrectLogin_notOK() {
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
    void register_moreThanMinAge_Ok() {
        userTest.setLogin("Login");
        userTest.setPassword("passWord1");
        userTest.setAge(18);
        assertEquals(userTest, registrationService.register(userTest));
    }

    @Test
    void loginIsEmpty_notOk() {
        userTest.setLogin("");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void loginContainsWhitespaces_notOk() {
        userTest.setLogin("L o g i n");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void loginIsNull_notOk() {
        userTest.setLogin(null);
        assertThrows(NullPointerException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void passwordWithoutDigits_notOk() {
        userTest.setLogin("Login");
        userTest.setPassword("passWord");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void passwordWithWhitespace_notOk() {
        userTest.setLogin("Login");
        userTest.setPassword("pass Word1");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void passwordWithoutUppercaseLetter_notOk() {
        userTest.setLogin("Belka");
        userTest.setPassword("strelka");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void shortPassword_notOk() {
        userTest.setLogin("BestLenguage");
        userTest.setPassword("java");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void passwordIsNull_notOk() {
        userTest.setLogin("Login");
        userTest.setPassword(null);
        assertThrows(NullPointerException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void lessThanMinAge_notOk() {
        userTest.setLogin("Maloletka");
        userTest.setPassword("MorgenshternKrut666");
        userTest.setAge(17);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void ageIsNull_notOk() {
        userTest.setLogin("NLO");
        userTest.setPassword("I_WANT_T0_BEL1VE");
        userTest.setAge(null);
        assertThrows(NullPointerException.class,
                () -> registrationService.register(userTest));
    }
}
