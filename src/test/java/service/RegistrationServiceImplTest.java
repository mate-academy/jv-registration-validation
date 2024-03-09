package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    private User user1;
    private User user2;
    private User user3;

    @BeforeAll
    public static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUpEach() {
        user1 = new User();
        user1.setLogin("test_user");
        user1.setPassword("test_pwd1");
        user1.setAge(18);

        user2 = new User();
        user2.setLogin("another_user");
        user2.setPassword("test_pwd2");
        user2.setAge(19);

        user3 = new User();
        user3.setLogin("one_more_user");
        user3.setPassword("test_pwd3");
        user3.setAge(20);
    }

    @AfterEach
    public void tearDownEach() {
        Storage.people.clear();
    }

    @Test
    public void register_nullUser_notOk() {
        user1 = null;
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertTrue(Storage.people.isEmpty());
    }

    @Test
    public void register_nullLogin_notOk() {
        user1.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertTrue(Storage.people.isEmpty());
    }

    @Test
    public void register_nullPassword_notOk() {
        user1.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertTrue(Storage.people.isEmpty());
    }

    @Test
    public void register_nullAge_notOk() {
        user1.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertTrue(Storage.people.isEmpty());
    }

    @Test
    public void register_negativeAge_notOk() {
        user1.setAge(-18);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        user2.setAge(-99);
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        user3.setAge(-1);
        assertThrows(RegistrationException.class, () -> registrationService.register(user3));
        assertEquals(0, Storage.people.size());
    }

    @Test
    public void register_under18Age_notOk() {
        user1.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        user2.setAge(9);
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        user3.setAge(0);
        assertThrows(RegistrationException.class, () -> registrationService.register(user3));
        assertEquals(0, Storage.people.size());
    }

    @Test
    public void register_over18Age_ok() {
        user1.setAge(18);
        assertEquals(user1, registrationService.register(user1));
        user2.setAge(25);
        assertEquals(user2, registrationService.register(user2));
        user3.setAge(42);
        assertEquals(user3, registrationService.register(user3));
        assertEquals(3, Storage.people.size());
    }

    @Test
    public void register_less6CharsLogin_notOk() {
        user1.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        user2.setLogin("login");
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        user3.setLogin("123");
        assertThrows(RegistrationException.class, () -> registrationService.register(user3));
        assertEquals(0, Storage.people.size());
    }

    @Test
    public void register_more6CharsLogin_ok() {
        user1.setLogin("123456");
        assertEquals(user1, registrationService.register(user1));
        user2.setLogin("loginlogin");
        assertEquals(user2, registrationService.register(user2));
        user3.setLogin("user login %$#@*(5).,/;'");
        assertEquals(user3, registrationService.register(user3));
        assertEquals(3, Storage.people.size());
    }

    @Test
    public void register_less6CharsPassword_notOk() {
        user1.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        user2.setPassword("passw");
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        user3.setPassword("456");
        assertThrows(RegistrationException.class, () -> registrationService.register(user3));
        assertEquals(0, Storage.people.size());
    }

    @Test
    public void register_more6CharsPassword_ok() {
        user1.setPassword("qwerty");
        assertEquals(user1, registrationService.register(user1));
        user2.setPassword("password");
        assertEquals(user2, registrationService.register(user2));
        user3.setPassword("Q123[4]5+-$%&(*)$");
        assertEquals(user3, registrationService.register(user3));
        assertEquals(3, Storage.people.size());
    }

    @Test
    public void register_userAlreadyExists_notOk() {
        Storage.people.add(user1);
        Storage.people.add(user2);
        Storage.people.add(user3);
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        assertEquals(3, Storage.people.size());
    }

    @Test
    public void register_newUserAdd_ok() {
        assertNotNull(registrationService.register(user1));
        assertEquals(user1, Storage.people.get(0));
        assertDoesNotThrow(() -> registrationService.register(user2));
        assertEquals(user2, Storage.people.get(1));
        assertEquals(user3, registrationService.register(user3));
        assertEquals(user3, Storage.people.get(2));
    }
}
