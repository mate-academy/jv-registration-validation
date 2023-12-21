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
    private static final String EMPTY_STRING = "";
    private static final String ONE_CARECTER_STRING = "A";
    private static final String THRE_CARACTER_STRING = "F6t";
    private static final String FIVE_CARACTER_STRING = "t2f45";
    private static final String STARTS_WITH_NUMBER = "1KitPES";
    private static final int NEGATIVE_AGE = - 10;
    private static final int AGE_IS_ZERO = 0;
    private static final int AGE_IS_SEVENTEEN = 17;
    private Registration registrationService;
    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    void setup() {
        registrationService = new RegistrationImpl();
        user1 = new User("TomCat", "12345g", 44);
        user2 = new User("PesSirko","ye42te2", 18);
        user3 = new User("KitPes", "fgd284254567", 32);
    }

    @AfterEach
    void afterEach() {
        people.clear();
    }

    @Test
    void register_addThreeValidUsersToStorage_ok() {
        registrationService.register(user1);
        registrationService.register(user2);
        registrationService.register(user3);
        assertTrue(people.contains(user1));
        assertTrue(people.contains(user2));
        assertTrue(people.contains(user3));
        assertEquals(3, people.size());
    }

    @Test
    void register_returnCorrectUser_ok() {
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
    void register_addUserLoginStartsWithNumber_notOk() {
        user1.setLogin(STARTS_WITH_NUMBER);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user1);
        });
        assertEquals(0, people.size());
    }

    @Test
    void register_addUserPasswordIsNUll_notOk() {
        user2.setPassword(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user2);
        });
        assertEquals(0, people.size());
    }

    @Test
    void register_addUserWithSameLogin_notOk() {
        people.add(user1);
        people.add(user2);
        people.add(user3);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(new User(user1.getLogin(), "fd9fga9", 18));
        });
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(new User(user2.getLogin(), "hff7723", 30));
        });
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(new User(user3.getLogin(), "f12345h", 45));
        });
        assertEquals(3, people.size());
    }

    @Test
    void register_addUserPasswordIsShort_notOk() {
        user1.setPassword(EMPTY_STRING);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user1);
        });
        user1.setPassword(ONE_CARECTER_STRING);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user1);
        });
        user1.setPassword(THRE_CARACTER_STRING);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user1);
        });
        user1.setPassword(FIVE_CARACTER_STRING);
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
        user1.setLogin(ONE_CARECTER_STRING);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user1);
        });
        user1.setLogin(THRE_CARACTER_STRING);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user1);
        });
        user1.setLogin(FIVE_CARACTER_STRING);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user1);
        });
        assertEquals(0, people.size());
    }

    @Test
    void register_addUserInvalidAge_notOk() {
        user1.setAge(NEGATIVE_AGE);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user1);
        });
        user1.setAge(AGE_IS_ZERO);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user1);
        });
        user1.setAge(AGE_IS_SEVENTEEN);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user1);
        });
        assertEquals(0, people.size());
    }
}
