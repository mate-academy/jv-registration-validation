package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static final String ALICE_PASSWORD = "123Alice";
    private static final String ALICE_LOGIN = "Alice";
    private static final int ALICE_AGE = 18;
    private static final String BOB_PASSWORD = "123Bob";
    private static final String BOB_PASSWORD_NOTOK = "abcda";
    private static final String BOB_LOGIN = "Bob";
    private static final int BOB_AGE = 22;
    private User alice = new User();
    private User bob = new User();

    @BeforeAll
    public static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        alice.setLogin(ALICE_LOGIN);
        alice.setPassword(ALICE_PASSWORD);
        alice.setAge(ALICE_AGE);

        bob.setLogin(BOB_LOGIN);
        bob.setPassword(BOB_PASSWORD);
        bob.setAge(BOB_AGE);
    }

    @Test
    void register_ageLessThanMinAge_notOk() {
        alice.setAge(15);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(alice));
        bob.setAge(-1);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(bob));
    }

    @Test
    void register_nullAge_notOk() {
        bob.setAge(null);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(bob));
    }

    @Test
    void register_nullPassword_notOk() {
        bob.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(bob);
        }, "The password can not be null!");
    }

    @Test
    void register_passwordLeastMinNumbersOfCharacters_notOk() {
        bob.setPassword(BOB_PASSWORD_NOTOK);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(bob));
    }

    @Test
    void register_theSameLogin_notOk() {
        registrationService.register(alice);
        bob.setLogin("Alice");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(bob);
        }, "The same login! Exception!");
    }

    @Test
    void register_userName_Ok() {
        User aliceActual = registrationService.register(alice);
        assertTrue(Storage.people.contains(aliceActual));
        User bobActual = registrationService.register(bob);
        assertTrue(Storage.people.contains(bobActual));
    }

    @AfterEach
    void cleanStorage() {
        Storage.people.clear();
    }
}

