package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    static void unit() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void correctDateUser_Ok() {
        User alice = new User();
        alice.setPassword("qwerty");
        alice.setAge(25);
        alice.setLogin("alice123");
        User bob = new User();
        bob.setPassword("123456");
        bob.setAge(18);
        bob.setLogin("hamster18");
        User actualAlice = registrationService.register(alice);
        assertEquals(alice, actualAlice);
        User actualBob = registrationService.register(bob);
        assertEquals(bob, actualBob);
    }

    @Test
    void nullUser_NotOk() {
        User nullUser = null;
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(nullUser));
    }

    @Test
    void nullLogin_NotOk() {
        User nullLoginUser = new User();
        nullLoginUser.setPassword("123456");
        nullLoginUser.setAge(18);
        nullLoginUser.setLogin(null);
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(nullLoginUser));
    }

    @Test
    void nullAge_NotOk() {
        User nullAgeUser = new User();
        nullAgeUser.setPassword("123456");
        nullAgeUser.setAge(null);
        nullAgeUser.setLogin("bob25");
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(nullAgeUser));
    }

    @Test
    void nullPassword_NotOk() {
        User nullPasswordUser = new User();
        nullPasswordUser.setPassword(null);
        nullPasswordUser.setAge(18);
        nullPasswordUser.setLogin("bob25");
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(nullPasswordUser));
    }

    @Test
    void lengthPasswordLess_NotOk() {
        User lengthPasswordLess = new User();
        lengthPasswordLess.setPassword("12345");
        lengthPasswordLess.setAge(18);
        lengthPasswordLess.setLogin("bob25");
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(lengthPasswordLess));
    }

    @Test
    void valueAgeLess_NotOk() {
        User youngUser = new User();
        youngUser.setPassword("123456");
        youngUser.setAge(12);
        youngUser.setLogin("bob25");
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(youngUser));
    }

    @Test
    void loginAlreadyExists_NotOk() {
        User aliceUser = new User();
        aliceUser.setPassword("qwerty");
        aliceUser.setAge(25);
        aliceUser.setLogin("alice25");
        User bobUser = new User();
        bobUser.setPassword("123456");
        bobUser.setAge(18);
        bobUser.setLogin(aliceUser.getLogin());
        registrationService.register(aliceUser);
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(bobUser));
    }

    @Test
    void emptyPassword() {
        User emptyPassword = new User();
        emptyPassword.setPassword("");
        emptyPassword.setAge(18);
        emptyPassword.setLogin("emtyPass21");
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(emptyPassword));
    }
}
