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
    private static final String TWO_USERS_ERROR =
            "The list should contain 2 users.";
    private static final String ONLY_ONE_USER_ERROR =
            "There should be only 1 user in list.";
    private static final String CUSTOM_EXCEPTION_ERROR =
            "Custom Exception class should be thrown.";
    private static final String SIX_OR_MORE_CHARS_ERROR =
            "with 6 or more characters should be registered.";

    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void setUpAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();

        user.setLogin("uniqueLogin1");
        user.setPassword("validPassword1");
        user.setAge(25);

        Storage.people.clear();
    }

    @Test
    void register_nullableUser_notOk() {
        user = null;

        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                CUSTOM_EXCEPTION_ERROR);
        boolean containsNone = Storage.people.isEmpty();

        assertTrue(containsNone, "User was added despite being null.");
    }

    @Test
    void register_nullableLogin_notOk() {
        user.setLogin(null);

        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                CUSTOM_EXCEPTION_ERROR);
        boolean containsNone = Storage.people.isEmpty();

        assertTrue(containsNone, "User was added despite null login.");
    }

    @Test
    void register_nullablePassword_notOk() {
        user.setPassword(null);

        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                CUSTOM_EXCEPTION_ERROR);
        boolean containsNone = Storage.people.isEmpty();

        assertTrue(containsNone, "User was added despite null password.");
    }

    @Test
    void register_nullableAge_notOk() {
        user.setAge(null);

        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                CUSTOM_EXCEPTION_ERROR);
        boolean containsNone = Storage.people.isEmpty();

        assertTrue(containsNone, "User was added despite null age.");
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin("");

        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                CUSTOM_EXCEPTION_ERROR);
        user.setLogin("validLogin");
        registrationService.register(user);
        boolean containsOne = Storage.people.size() == 1;

        assertTrue(containsOne, ONLY_ONE_USER_ERROR);
        assertEquals(Storage.people.get(0).getLogin(), user.getLogin(),
                "User login in list should be: " + user.getLogin());
    }

    @Test
    void register_emptyPassword_notOk() {
        user.setPassword("");

        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                CUSTOM_EXCEPTION_ERROR);
        user.setPassword("validPassword");
        registrationService.register(user);
        boolean containsOne = Storage.people.size() == 1;

        assertTrue(containsOne, ONLY_ONE_USER_ERROR);
        assertEquals(Storage.people.get(0).getPassword(), user.getPassword(),
                "User password in list should be: " + user.getPassword());
    }

    @Test
    void register_tooShortLogin_notOk() {
        user.setLogin("abc");
        User edgeCaseUser = new User();
        edgeCaseUser.setLogin("distinctLogin");
        edgeCaseUser.setAge(60);
        edgeCaseUser.setLogin("abcde"); // edge case

        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                CUSTOM_EXCEPTION_ERROR);
        assertThrows(RegistrationException.class, () -> registrationService.register(edgeCaseUser),
                CUSTOM_EXCEPTION_ERROR);
        user.setLogin("validLogin");
        registrationService.register(user);
        boolean containsOne = Storage.people.size() == 1;

        assertTrue(containsOne, ONLY_ONE_USER_ERROR);
        assertEquals(Storage.people.get(0).getLogin(), user.getLogin(),
                "User login in list should be: " + user.getLogin());
    }

    @Test
    void register_tooShortPassword_notOk() {
        user.setPassword("abc");
        User edgeCaseUser = new User();
        edgeCaseUser.setLogin("distinctLogin");
        edgeCaseUser.setAge(60);
        edgeCaseUser.setPassword("abcde"); // edge case

        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                CUSTOM_EXCEPTION_ERROR);
        assertThrows(RegistrationException.class, () -> registrationService.register(edgeCaseUser),
                CUSTOM_EXCEPTION_ERROR);
        user.setPassword("validPassword");
        registrationService.register(user);
        boolean containsOne = Storage.people.size() == 1;

        assertTrue(containsOne, ONLY_ONE_USER_ERROR);
        assertEquals(Storage.people.get(0).getPassword(), user.getPassword(),
                "User password in list should be: " + user.getPassword());
    }

    @Test
    void register_negativeOrZeroAge_notOk() {
        user.setAge(-11);
        User zeroAgeUser = new User();
        zeroAgeUser.setLogin("distinctLogin");
        zeroAgeUser.setPassword("distinctPassword");
        zeroAgeUser.setAge(0);

        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                CUSTOM_EXCEPTION_ERROR);
        assertThrows(RegistrationException.class, () -> registrationService.register(zeroAgeUser),
                CUSTOM_EXCEPTION_ERROR);
        user.setAge(44);
        registrationService.register(user);
        boolean containsOne = Storage.people.size() == 1;

        assertTrue(containsOne, ONLY_ONE_USER_ERROR);
        assertEquals(Storage.people.get(0).getAge(), user.getAge(),
                "User password in list should be: " + user.getAge());
    }

    @Test
    void register_tooSmallAge_notOk() {
        user.setAge(1);
        User edgeCaseUser = new User();
        edgeCaseUser.setLogin("distinctLogin");
        edgeCaseUser.setPassword("distinctPassword");
        edgeCaseUser.setAge(17);

        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                CUSTOM_EXCEPTION_ERROR);
        assertThrows(RegistrationException.class, () -> registrationService.register(edgeCaseUser),
                CUSTOM_EXCEPTION_ERROR);
        user.setAge(44);
        registrationService.register(user);
        boolean containsOne = Storage.people.size() == 1;

        assertTrue(containsOne, ONLY_ONE_USER_ERROR);
        assertEquals(Storage.people.get(0).getAge(), user.getAge(),
                "User password in list should be: " + user.getAge());
    }

    @Test
    void register_loginTaken_notOk() {
        Storage.people.add(user);

        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                CUSTOM_EXCEPTION_ERROR);
        boolean containsOne = Storage.people.size() == 1;

        assertTrue(containsOne, "Added user despite having duplicate login.");
    }

    @Test
    void register_differentUserLogin_Ok() {
        Storage.people.add(user);
        User anotherUser = new User();
        anotherUser.setLogin("differentLogin");
        anotherUser.setPassword("differentPassword");
        anotherUser.setAge(25);

        registrationService.register(anotherUser);
        boolean containsTwo = Storage.people.size() == 2;

        assertTrue(containsTwo, TWO_USERS_ERROR);
        assertNotEquals(Storage.people.get(0), Storage.people.get(1),
                "There are at 2 identical Users in the list.");
    }

    @Test
    void register_loginLengthRequired_Ok() {
        user.setLogin("abcdef"); // edge case
        User anotherUser = new User();
        anotherUser.setLogin("abcdefabcdefabcdef");
        anotherUser.setPassword("someValidPassword");
        anotherUser.setAge(25);

        User actual = registrationService.register(user);
        User actual2 = registrationService.register(anotherUser);
        boolean containsTwo = Storage.people.size() == 2;

        assertEquals(user.getLogin(), actual.getLogin(), "Login "
                + SIX_OR_MORE_CHARS_ERROR);
        assertEquals(anotherUser.getLogin(), actual2.getLogin(), "Login "
                + SIX_OR_MORE_CHARS_ERROR);
        assertTrue(containsTwo, TWO_USERS_ERROR);
    }

    @Test
    void register_passwordLengthRequired_Ok() {
        user.setPassword("abcdef"); // edge case
        User anotherUser = new User();
        anotherUser.setLogin("someValidLogin");
        anotherUser.setPassword("abcdefabcdefabcdef");
        anotherUser.setAge(25);

        User actual = registrationService.register(user);
        User actual2 = registrationService.register(anotherUser);
        boolean containsTwo = Storage.people.size() == 2;

        assertEquals(user.getPassword(), actual.getPassword(), "Password "
                + SIX_OR_MORE_CHARS_ERROR);
        assertEquals(anotherUser.getPassword(), actual2.getPassword(), "Password "
                + SIX_OR_MORE_CHARS_ERROR);
        assertTrue(containsTwo, TWO_USERS_ERROR);
    }

    @Test
    void register_atLeast18Age_Ok() {
        user.setAge(18); // edge case
        User anotherUser = new User();
        anotherUser.setLogin("someValidLogin");
        anotherUser.setPassword("someValidPassword");
        anotherUser.setAge(100);

        User actual = registrationService.register(user);
        User actual2 = registrationService.register(anotherUser);
        boolean containsTwo = Storage.people.size() == 2;

        assertEquals(user.getAge(), actual.getAge(),
                "Age 18 or above should be on the list.");
        assertEquals(anotherUser.getAge(), actual2.getAge(),
                "Age 18 or above should be on the list.");
        assertTrue(containsTwo, TWO_USERS_ERROR);
    }
}
