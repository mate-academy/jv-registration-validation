package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user1;
    private User user2;
    private User user3;

    @BeforeAll
    static void setUpAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        // Technically valid users.
        // Three of them for testing edge cases, because duplicates are not allowed to store.
        // Clearing static storage list before each test.
        user1 = new User();
        user1.setLogin("uniqueLogin1");
        user1.setPassword("password");
        user1.setAge(20);

        user2 = new User();
        user2.setLogin("uniqueLogin2");
        user2.setPassword("password");
        user2.setAge(20);

        user3 = new User();
        user3.setLogin("uniqueLogin3");
        user3.setPassword("password");
        user3.setAge(20);

        Storage.people.clear();
    }

    @Test
    void register_nullLogin_notOk() {
        user1.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));

        boolean containsNone = Storage.people.isEmpty();
        assertTrue(containsNone, "Error. The User was added to the list despite NULL login.");
    }

    @Test
    void register_nullPassword_notOk() {
        user1.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));

        boolean containsNone = Storage.people.isEmpty();
        assertTrue(containsNone, "Error. The User was added to the list despite NULL password.");
    }

    @Test
    void register_nullAge_notOk() {
        user1.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));

        boolean containsNone = Storage.people.isEmpty();
        assertTrue(containsNone, "Error. The User was added to the list despite NULL age.");
    }

    @Test
    void register_tooShortLogin_notOk() {
        user1.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));

        user2.setLogin("abc");
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));

        user3.setLogin("abcde");
        assertThrows(RegistrationException.class, () -> registrationService.register(user3));

        boolean containsNone = Storage.people.isEmpty();
        assertTrue(containsNone, "Error. At least one User was added despite "
                + "login being too short");
    }

    @Test
    void register_tooShortPassword_notOk() {
        user1.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));

        user2.setLogin("abc");
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));

        user3.setLogin("abcde");
        assertThrows(RegistrationException.class, () -> registrationService.register(user3));

        boolean containsNone = Storage.people.isEmpty();
        assertTrue(containsNone, "Error. At least one User was added despite "
                + "password being too short");
    }

    @Test
    void register_negativeAge_notOk() {
        user1.setAge(-134);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));

        user2.setAge(-1);
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));

        boolean containsNone = Storage.people.isEmpty();
        assertTrue(containsNone, "Error. At least one User was added despite "
                + "the negative input age");
    }

    @Test
    void register_tooSmallAge_notOk() {
        user1.setAge(0);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));

        user2.setAge(10);
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));

        user3.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user3));

        boolean containsNone = Storage.people.isEmpty();
        assertTrue(containsNone, "Error. At least one User was added despite "
                + "age being below 18");
    }

    @Test
    void register_sameUserLogin_notOk() {
        Storage.people.add(user1);

        assertThrows(RegistrationException.class, () -> registrationService.register(user1));

        boolean containsOnlyOne = Storage.people.size() == 1;
        assertTrue(containsOnlyOne, "Error. User with same login was added");
    }

    @Test
    void register_differentUserLogin_Ok() {
        Storage.people.add(user1);
        registrationService.register(user2);
        registrationService.register(user3);

        boolean containsThree = Storage.people.size() == 3;
        assertTrue(containsThree, "Error. The list should contain 3 users.");

        assertNotEquals(Storage.people.get(0), Storage.people.get(1), "Test failed! "
                + "There are at least 2 identical Users in the list.");
        assertNotEquals(Storage.people.get(0), Storage.people.get(2), "Test failed! "
                + "There are at least 2 identical Users in the list.");
        assertNotEquals(Storage.people.get(1), Storage.people.get(2), "Test failed! "
                + "There are at least 2 identical Users in the list.");
    }

    @Test
    void register_loginLengthRequired_Ok() {
        user1.setLogin("abcdef");
        User actual = registrationService.register(user1);
        assertEquals(user1.getLogin(), actual.getLogin(), "Test failed! "
                + "The user with login that is >= 6 characters should be registered.");

        user2.setLogin("abcdefghijk");
        actual = registrationService.register(user2);
        assertEquals(user2.getLogin(), actual.getLogin(), "Test failed! "
                + "The user with login that is >= 6 characters should be registered.");

        user3.setLogin("abcdefghijkabcdefghijkabcdefghijk");
        actual = registrationService.register(user3);
        assertEquals(user3.getLogin(), actual.getLogin(), "Test failed! "
                + "The user with login that is >= 6 characters should be registered.");

        boolean containsThree = Storage.people.size() == 3;
        assertTrue(containsThree, "Error. The list should contain 3 users.");
    }

    @Test
    void register_passwordLengthRequired_Ok() {
        user1.setPassword("abcdef");
        User actual = registrationService.register(user1);
        assertEquals(user1.getPassword(), actual.getPassword(), "Test failed! "
                + "The user with password that is >= 6 characters should be registered.");

        user2.setPassword("abcdefghijk");
        actual = registrationService.register(user2);
        assertEquals(user2.getPassword(), actual.getPassword(), "Test failed! "
                + "The user with password that is >= 6 characters should be registered.");

        user3.setPassword("abcdefghijkabcdefghijkabcdefghijk");
        actual = registrationService.register(user3);
        assertEquals(user3.getPassword(), actual.getPassword(), "Test failed! "
                + "The user with password that is >= 6 characters should be registered.");

        boolean containsThree = Storage.people.size() == 3;
        assertTrue(containsThree, "Error. The list should contain 3 users.");
    }

    @Test
    void register_atLeast18Age_Ok() {
        user1.setAge(18);
        User actual = registrationService.register(user1);
        assertEquals(user1.getAge(), actual.getAge(), "Test failed! "
                + "The user which is 18 years or more should be registered.");

        user2.setAge(58);
        actual = registrationService.register(user2);
        assertEquals(user2.getAge(), actual.getAge(), "Test failed! "
                + "The user which is 18 years or more should be registered.");

        user3.setAge(101);
        actual = registrationService.register(user3);
        assertEquals(user3.getAge(), actual.getAge(), "Test failed! "
                + "The user which is 18 years or more should be registered.");

        boolean containsThree = Storage.people.size() == 3;
        assertTrue(containsThree, "Error. The list should contain 3 users.");
    }
}
