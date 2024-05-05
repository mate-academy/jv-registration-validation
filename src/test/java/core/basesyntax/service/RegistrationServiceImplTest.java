package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setLogin("im_the_first");
        user1.setPassword("first_pass");
        user1.setAge(33);

        user2 = new User();
        user2.setLogin("im_the_second");
        user2.setPassword("second_pass");
        user2.setAge(18);

        user3 = new User();
        user3.setLogin("im_the_third");
        user3.setPassword("third_pass");
        user3.setAge(19);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void registration_nullUser_notOk() {
        user1 = null;
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertTrue(Storage.people.isEmpty());
    }

    @Test
    void registration_nullLogin_notOK() {
        user1.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertTrue(Storage.people.isEmpty());
    }

    @Test
    void registration_nullPassword_notOK() {
        user1.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertTrue(Storage.people.isEmpty());
    }

    @Test
    void registration_nullAge_notOk() {
        user1.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertTrue(Storage.people.isEmpty());
    }

    @Test
    void registration_negativeAge_notOK() {
        user1.setAge(-5);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        user2.setAge(-7);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        user3.setAge(-3);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertEquals(0, Storage.people.size());
    }

    @Test
    void registration_under_18Age_notOk() {
        user1.setAge(8);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        user2.setAge(9);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        user3.setAge(15);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertEquals(0, Storage.people.size());
    }

    @Test
    void registration_less_than6CharsLogin_notOk() {
        user1.setLogin("rr");
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        user2.setLogin("g");
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        user3.setLogin(" ");
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertEquals(0, Storage.people.size());
    }

    @Test
    void registration_less_than6CharsPassword_notOk() {
        user1.setLogin("123");
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        user2.setLogin("0000");
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        user3.setLogin("");
        assertEquals(0, Storage.people.size());
    }

    @Test
    void registration_password_6Chars_Ok() {
        user1.setPassword("123456");
        registrationService.register(user1);
        boolean actual = Storage.people.contains(user1);
        assertTrue(actual);
    }
}
