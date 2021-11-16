package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StorageDaoImplTest {
    private static RegistrationService registrationServise;
    private static User user1;
    private static User user2;

    @BeforeAll
    static void beforeAll() {
        registrationServise = new RegistrationServiceImpl();
        user1 = new User();
        user2 = new User();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user1.setLogin("user");
        user1.setPassword("password");
        user1.setAge(21);
        user2.setLogin("1abcdef");
        user2.setPassword("password");
        user2.setAge(20);
    }

    @Test
    void register_correctUser_isOk() {
        registrationServise.register(user1);
        assertTrue(Storage.people.contains(user1));
        assertEquals(Storage.people.get(0), user1);
    }

    @Test
    void register_loginStartWithNumeric_notOk() {
        assertThrows(RuntimeException.class,
                () -> registrationServise.register(user2));
    }

    @Test
    void register_loginLengthLessThenThree_notOk() {
        user2.setLogin("al");
        assertThrows(RuntimeException.class,
                () -> registrationServise.register(user2));
    }

    @Test
    void register_nullAge_notOk() {
        user1.setAge(null);
        assertThrows(NullPointerException.class,
                () -> registrationServise.register(user1));
    }

    @Test
    void register_nullLogin_notOk() {
        user1.setLogin(null);
        assertThrows(NullPointerException.class,
                () -> registrationServise.register(user1));
    }

    @Test
    void register_nullPassword_notOk() {
        user1.setPassword(null);
        assertThrows(NullPointerException.class,
                () -> registrationServise.register(user1));
    }

    @Test
    void register_ageLessThanEighteen_notOk() {
        user1.setAge(17);
        assertThrows(RuntimeException.class,
                () -> registrationServise.register(user1));
    }

    @Test
    void register_passwordLengthLessThanSix_notOk() {
        user1.setPassword("abcd");
        assertThrows(RuntimeException.class,
                () -> registrationServise.register(user1));
    }

    @Test
    void register_theSameUser_notOk() {
        registrationServise.register(user1);
        assertThrows(RuntimeException.class,
                () -> registrationServise.register(user1));
    }

    @Test
    void register_UserWithExistingLogin_notOk() {
        registrationServise.register(user1);
        user2.setLogin("user");
        assertThrows(RuntimeException.class,
                () -> registrationServise.register(user2));
    }
}
