package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private final User secondValidUser = new User();
    private final User thirdUser = new User();
    private User defaultValidUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void beforeEach() {
        defaultValidUser = new User();
        defaultValidUser.setLogin("Kate");
        defaultValidUser.setAge(RegistrationServiceImpl.MIN_AGE_VALUE);
        defaultValidUser.setPassword("123456");
        secondValidUser.setLogin("Bruce");
        secondValidUser.setAge(100);
        secondValidUser.setPassword("654321");
    }

    @Test
    void register_sameLogin_notOk() {
        registrationService.register(defaultValidUser);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultValidUser));
    }

    @Test
    void register_nullLogin_notOk() {
        defaultValidUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultValidUser));
    }

    @Test
    void register_fewUsers_ok() {
        thirdUser.setLogin("Amy");
        thirdUser.setAge(18);
        thirdUser.setPassword("parol2");
        registrationService.register(defaultValidUser);
        registrationService.register(secondValidUser);
        registrationService.register(thirdUser);
        User user2Copy = secondValidUser;
        assertThrows(RuntimeException.class, () -> registrationService.register(user2Copy));
        assertEquals(3, Storage.people.size());
    }

    @Test
    void register_ageLessThanRequired_NotOk() {
        int invalidMinusAge = -5;
        defaultValidUser.setAge(invalidMinusAge);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultValidUser));
        defaultValidUser.setLogin(secondValidUser.getLogin());
        int invalidZeroAge = 0;
        defaultValidUser.setAge(invalidZeroAge);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultValidUser));
    }

    @Test
    void register_nullAge_notOk() {
        defaultValidUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultValidUser));
    }

    @Test
    void register_invalidPassword_notOk() {
        String invalidPassword = "H";
        defaultValidUser.setPassword(invalidPassword);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultValidUser));
        defaultValidUser.setLogin(secondValidUser.getLogin());
        String emptyPassword = "";
        defaultValidUser.setPassword(emptyPassword);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultValidUser));
    }

    @Test
    void register_nullPassword_notOk() {
        defaultValidUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultValidUser));
    }

    @Test
    void register_emptyUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(new User()));
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }
}
