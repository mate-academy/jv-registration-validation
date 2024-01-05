package core.basesyntax.service;

import static core.basesyntax.db.Storage.people;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static String EMPTY_STRING = "";
    private static String ONE_CHAR_STRING = "a";
    private static String THREE_CHAR_STRING = "abc";
    private static String FIVE_CHAR_STRING = "abcde";

    private static Integer NEGATIVE_AGE = -18;
    private static Integer AGE_ZERO = 0;
    private static Integer AGE_SEVENTEEN = 17;
    private RegistrationServiceImpl registrationService;
    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        user1 = new User("YanaBilyk", "ybn23ok!", 21);
        user2 = new User("MaxShevchenko", "max19sk!", 18);
        user3 = new User("OlgaPustova", "olg87sp!", 35);
    }

    @AfterEach
    void afterEach() {
        people.clear();
    }

    @Test
    void register_ValidUserToStorage_Ok() {
        registrationService.register(user1);
        registrationService.register(user2);
        registrationService.register(user3);
        assertTrue(people.contains(user1));
        assertTrue(people.contains(user2));
        assertTrue(people.contains(user3));
        assertEquals(3, people.size());
    }

    @Test
    void register_addUserIsNull_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
        assertEquals(0, people.size());
    }

    @Test
    void register_addUserLoginIsNull_notOk() {
        user1.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
        assertEquals(0, people.size());
    }

    @Test
    void register_addUserPasswordIsNull_notOk() {
        user1.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
        assertEquals(0, people.size());
    }

    @Test
    void register_addUserPasswordIsShort_notOk() {
        user1.setPassword(EMPTY_STRING);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
        user1.setPassword(ONE_CHAR_STRING);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
        user1.setPassword(THREE_CHAR_STRING);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
        user1.setPassword(FIVE_CHAR_STRING);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
        assertEquals(0, people.size());
    }

    @Test
    void register_addUserLoginIsShort_notOk() {
        user1.setLogin(EMPTY_STRING);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
        user1.setLogin(ONE_CHAR_STRING);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
        user1.setLogin(THREE_CHAR_STRING);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
        user1.setLogin(FIVE_CHAR_STRING);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
        assertEquals(0, people.size());
    }

    @Test
    void register_addUserWithSameLogin_notOK() {
        registrationService.register(user1);
        registrationService.register(user2);
        registrationService.register(user3);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User(user1.getLogin(),"djdofjs", 18));
        });
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User(user2.getLogin(),"dj10fjs", 19));
        });
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User(user3.getLogin(),"dj20fjs", 20));
        });
        assertEquals(3,people.size());
    }

    @Test
    void register_addUserWithInvalidAge_notOk() {
        user1.setAge(NEGATIVE_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
        user1.setAge(AGE_ZERO);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
        user1.setAge(AGE_SEVENTEEN);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
        user1.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
        assertEquals(0,people.size());
    }

}
