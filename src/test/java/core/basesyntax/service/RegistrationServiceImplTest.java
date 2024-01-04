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
    private static String STARTS_WITH_NUMBER = "1YanaBilyk";
    private static String STARTS_WITH_SYMBOL = "$YanaBilyk";
    private static String EMPTY_STRING = "";
    private static String ONE_CHAR_STRING = "a";
    private static String TWO_CHAR_STRING = "ab";
    private static String THREE_CHAR_STRING = "abc";
    private static String FOUR_CHAR_STRING = "abcd";
    private static String FIVE_CHAR_STRING = "abcde";

    private static Integer NEGATIVE_AGE = -18;
    private static Integer AGE_IS_ZERO = 0;
    private static Integer AGE_IS_SIXTEEN = 16;
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
    void return_CorrectUser_Ok() {
        User returnedUser1 = registrationService.register(user1);
        User returnedUser2 = registrationService.register(user2);
        User returnedUser3 = registrationService.register(user3);
        assertEquals(user1, returnedUser1);
        assertEquals(user2, returnedUser2);
        assertEquals(user3, returnedUser3);
    }

    @Test
    void register_addUserIsNull_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(null);
        });
        assertEquals(0, people.size());
    }

    @Test
    void register_addUserLoginIsNull_notOk() {
        user1.setLogin(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user1);
        });
        assertEquals(0, people.size());
    }

    @Test
    void register_addUserLoginStartWithNumber_notOk() {
        user1.setLogin(STARTS_WITH_NUMBER);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user1);
        });
        assertEquals(0, people.size());
    }

    @Test
    void register_addUserLogInStartWithSymbol_notOK() {
        user1.setLogin(STARTS_WITH_SYMBOL);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user1);
        });
        assertEquals(0, people.size());
    }

    @Test
    void register_addUserPasswordIsNull_notOk() {
        user1.setPassword(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user1);
        });
        assertEquals(0, people.size());
    }

    @Test
    void register_addUserPasswordIsShort_notOk() {
        user1.setPassword(EMPTY_STRING);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user1);
        });
        user1.setPassword(ONE_CHAR_STRING);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user1);
        });
        user1.setPassword(TWO_CHAR_STRING);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user1);
        });
        user1.setPassword(THREE_CHAR_STRING);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user1);
        });
        user1.setPassword(FOUR_CHAR_STRING);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user1);
        });
        user1.setPassword(FIVE_CHAR_STRING);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user1);
        });
        assertEquals(0, people.size());
    }

    @Test
    void register_addUserLoginIsShort_notOk() {
        user1.setLogin(EMPTY_STRING);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user1);
        });
        user1.setLogin(ONE_CHAR_STRING);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user1);
        });
        user1.setLogin(TWO_CHAR_STRING);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user1);
        });
        user1.setLogin(THREE_CHAR_STRING);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user1);
        });
        user1.setLogin(FOUR_CHAR_STRING);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user1);
        });
        user1.setLogin(FIVE_CHAR_STRING);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user1);
        });
        assertEquals(0, people.size());
    }

    @Test
    void register_addUserWithSameLogin_notOK() {
        registrationService.register(user1);
        registrationService.register(user2);
        registrationService.register(user3);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(new User(user1.getLogin(),"djdofjs", 18));
        });
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(new User(user2.getLogin(),"dj10fjs", 19));
        });
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(new User(user3.getLogin(),"dj20fjs", 20));
        });
        assertEquals(3,people.size());
    }

    @Test
    void register_addUserWithInvalidAge_notOk() {
        user1.setAge(NEGATIVE_AGE);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user1);
        });
        user1.setAge(AGE_IS_ZERO);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user1);
        });
        user1.setAge(AGE_IS_SIXTEEN);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user1);
        });
        user1.setAge(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user1);
        });
        assertEquals(0,people.size());
    }

}
