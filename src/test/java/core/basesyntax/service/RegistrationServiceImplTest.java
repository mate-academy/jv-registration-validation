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
    private static User user2 = new User();
    private static User user3 = new User();
    private static RegistrationServiceImpl registrationService;
    private User defaultUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        String loginBruce = "Bruce";
        int validUser2Age = 100;
        String reverseGoodPassword = "654321";
        user2.setLogin(loginBruce);
        user2.setAge(validUser2Age);
        user2.setPassword(reverseGoodPassword);
        String loginAmy = "Amy";
        int validEighteenAge = 18;
        String goodPassword = "parol2";
        user3.setLogin(loginAmy);
        user3.setAge(validEighteenAge);
        user3.setPassword(goodPassword);
    }

    @BeforeEach
    void beforeEach() {
        int validDefaultAge = 18;
        String loginDefault = "Kate";
        defaultUser = new User();
        defaultUser.setLogin(loginDefault);
        defaultUser.setAge(validDefaultAge);
        String goodPassword = "123456";
        defaultUser.setPassword(goodPassword);
    }

    @Test
    void addUser_SameLogin_NotOk() {
        registrationService.register(defaultUser);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void addUser_NullLogin_NotOk() {
        defaultUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void addUsers_FewUsers_Ok() {
        registrationService.register(defaultUser);
        registrationService.register(user2);
        registrationService.register(user3);
        User user2Copy = user2;
        assertThrows(RuntimeException.class, () -> registrationService.register(user2Copy));
        assertEquals(3, Storage.people.size());
    }

    @Test
    void register_ageLessThanRequired_NotOk() {
        int invalidMinusAge = -5;
        defaultUser.setAge(invalidMinusAge);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
        defaultUser.setLogin(user2.getLogin());
        int invalidZeroAge = 0;
        defaultUser.setAge(invalidZeroAge);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void addUser_NullAge_NotOk() {
        defaultUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void addUser_InvalidPassword_NotOk() {
        String invalidPassword = "H";
        defaultUser.setPassword(invalidPassword);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
        defaultUser.setLogin(user2.getLogin());
        String emptyPassword = "";
        defaultUser.setPassword(emptyPassword);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void addUser_NullPassword_NotOk() {
        defaultUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void registerNullUser_NotOk() {
        User emptyUser = new User();
        assertThrows(RuntimeException.class, () -> registrationService.register(emptyUser));
    }

    @AfterEach
    void cleanStorage() {
        Storage.people.clear();
    }
}
