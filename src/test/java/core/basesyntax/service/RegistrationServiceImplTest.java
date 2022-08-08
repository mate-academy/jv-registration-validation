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
    private final User secondUser = new User();
    private final User thirdUser = new User();
    private User defaultUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void beforeEach() {
        String loginDefault = "Kate";
        defaultUser = new User();
        defaultUser.setLogin(loginDefault);
        defaultUser.setAge(RegistrationServiceImpl.MIN_AGE_VALUE);
        String goodPassword = "123456";
        defaultUser.setPassword(goodPassword);
        String loginBruce = "Bruce";
        int validUser2Age = 100;
        String reverseGoodPassword = "654321";
        secondUser.setLogin(loginBruce);
        secondUser.setAge(validUser2Age);
        secondUser.setPassword(reverseGoodPassword);
    }

    @Test
    void register_sameLogin_notOk() {
        registrationService.register(defaultUser);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void register_nullLogin_notOk() {
        defaultUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void register_fewUsers_ok() {
        String loginAmy = "Amy";
        int validEighteenAge = 18;
        String anotherGoodPassword = "parol2";
        thirdUser.setLogin(loginAmy);
        thirdUser.setAge(validEighteenAge);
        thirdUser.setPassword(anotherGoodPassword);
        registrationService.register(defaultUser);
        registrationService.register(secondUser);
        registrationService.register(thirdUser);
        User user2Copy = secondUser;
        assertThrows(RuntimeException.class, () -> registrationService.register(user2Copy));
        assertEquals(3, Storage.people.size());
    }

    @Test
    void register_ageLessThanRequired_NotOk() {
        int invalidMinusAge = -5;
        defaultUser.setAge(invalidMinusAge);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
        defaultUser.setLogin(secondUser.getLogin());
        int invalidZeroAge = 0;
        defaultUser.setAge(invalidZeroAge);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void register_nullAge_notOk() {
        defaultUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void register_invalidPassword_notOk() {
        String invalidPassword = "H";
        defaultUser.setPassword(invalidPassword);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
        defaultUser.setLogin(secondUser.getLogin());
        String emptyPassword = "";
        defaultUser.setPassword(emptyPassword);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void register_nullPassword_notOk() {
        defaultUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void register_emptyUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(new User()));
    }

    @AfterEach
    void cleanStorage() {
        Storage.people.clear();
    }
}
