package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int STANDARD_DB_SET_COUNTER = 3;
    private static final String EXPECTED_MESSAGE_ADDED_TO_DB_RETURN_USER =
            "Expected, that user will be return after added to the DB with correct data";
    private static final String EXPECTED_MESSAGE_ADDED_TO_DB =
            "Expected, that user will be added to the DB with correct data";
    private static final String EXPECTED_MESSAGE_NULL =
            "Expected, that existing user can't be added to the DB, must return null";
    private static final String EXPECTED_MESSAGE_NULL_USER =
            "Expected, that null pointer instead User have to call the RegistrationException";
    private static final String EXPECTED_MESSAGE_NULL_LOGIN_USER =
            "Expected, that user with null login call the RegistrationException";
    private static final String EXPECTED_MESSAGE_INVALID_LOGIN_USER =
            "Expected, that user with invalid login call yhe RegistrationException";
    private static final String EXPECTED_MESSAGE_NULL_AGE_USER =
            "Expected, that user with null age call yhe RegistrationException";
    private static final String EXPECTED_MESSAGE_INVALID_AGE_USER =
            "Expected, that user with invalid login call yhe RegistrationException";
    private static final String EXPECTED_MESSAGE_NULL_PASSWORD_USER =
            "Expected, that user with null password call yhe RegistrationException";
    private static final String EXPECTED_MESSAGE_INVALID_PASSWORD_USER =
            "Expected, that user with invalid password call yhe RegistrationException";
    private static final int ADDED_FIRST_CUSTOM_USER = 3;
    private static final int ADDED_SECOND_CUSTOM_USER = 4;
    private static final int ADDED_THIRD_CUSTOM_USER = 5;

    private static User userBob;
    private static User userAnn;
    private static User userRoma;
    private RegistrationService registrationService;

    @BeforeAll
    static void setDataBase() {
        userBob = new User("user_bob", "qwerty1234", 34);
        userAnn = new User("user_ann", "12345678", 18);
        userRoma = new User("user_roma", "bobocode", 22);
        Storage.people.addAll(List.of(userAnn, userBob, userRoma));
    }

    @BeforeEach
    void setRegistrationService() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void afterEach() {
        if (Storage.people.size() > STANDARD_DB_SET_COUNTER) {
            Storage.people.subList(STANDARD_DB_SET_COUNTER, Storage.people.size()).clear();
        }
    }

    @Test
    @Order(1)
    void registrationUser_nullUser_notOk() {
        User nullUser = null;
        assertThrows(RegistrationException.class,
                () -> registrationService.register(nullUser),
                EXPECTED_MESSAGE_NULL_USER);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(null),
                EXPECTED_MESSAGE_NULL_USER);
    }

    @Test
    @Order(2)
    void registrationUser_nullLogin_notOk() {
        User userNullLoginFirst = new User(null, "fgjhdn1356nb7", 32);
        User userNullLoginSecond = new User(null, "234vt7d124v1", 28);
        User userNullLoginThird = new User(null, "adsfrt4v41", 18);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userNullLoginFirst),
                EXPECTED_MESSAGE_NULL_LOGIN_USER);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userNullLoginSecond),
                EXPECTED_MESSAGE_NULL_LOGIN_USER);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userNullLoginThird),
                EXPECTED_MESSAGE_NULL_LOGIN_USER);
    }

    @Test
    @Order(3)
    void registrationUser_nullAge_notOk() {
        User userNullAgeFirst = new User("LoGiNoNe", "fgjhdnrt", null);
        User userNullAgeSecond = new User("NewLogin", "234vt756b", null);
        User userNullAgeThird = new User("my_Login", "adsfrtwert3", null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userNullAgeFirst),
                EXPECTED_MESSAGE_NULL_AGE_USER);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userNullAgeSecond),
                EXPECTED_MESSAGE_NULL_AGE_USER);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userNullAgeThird),
                EXPECTED_MESSAGE_NULL_AGE_USER);
    }

    @Test
    @Order(4)
    void registrationUser_validData_ok() {
        User expectedFirst = new User("LoGiNoNe1", "fgjhdngh", 32);
        User expectedSecond = new User("NewLogin2", "234vt7f1", 28);
        User expectedThird = new User("my_Login3", "adsfrtty", 18);
        User actualFirst = registrationService.register(expectedFirst);
        User actualSecond = registrationService.register(expectedSecond);
        User actualThird = registrationService.register(expectedThird);
        assertEquals(expectedFirst, actualFirst, EXPECTED_MESSAGE_ADDED_TO_DB_RETURN_USER);
        assertEquals(expectedSecond, actualSecond, EXPECTED_MESSAGE_ADDED_TO_DB_RETURN_USER);
        assertEquals(expectedThird, actualThird, EXPECTED_MESSAGE_ADDED_TO_DB_RETURN_USER);
        assertEquals(Storage.people.get(ADDED_FIRST_CUSTOM_USER), actualFirst,
                EXPECTED_MESSAGE_ADDED_TO_DB);
        assertEquals(Storage.people.get(ADDED_SECOND_CUSTOM_USER), actualSecond,
                EXPECTED_MESSAGE_ADDED_TO_DB);
        assertEquals(Storage.people.get(ADDED_THIRD_CUSTOM_USER), actualThird,
                EXPECTED_MESSAGE_ADDED_TO_DB);
    }

    @Test
    @Order(5)
    void registrationUser_existingUser_notOk() {
        assertNull(registrationService.register(userAnn), EXPECTED_MESSAGE_NULL);
        assertNull(registrationService.register(userBob), EXPECTED_MESSAGE_NULL);
        assertNull(registrationService.register(userRoma), EXPECTED_MESSAGE_NULL);
        assertEquals(Storage.people.size(), STANDARD_DB_SET_COUNTER,
                "Expected, that DB does not be changed");
    }

    @Test
    @Order(6)
    void registrationUser_loginLength6_ok() {
        User expectedFirst = new User("123456", "password1", 23);
        User expectedSecond = new User("asder54", "password2", 27);
        User expectedThird = new User("/56mlk", "password3", 25);
        User actualFirst = registrationService.register(expectedFirst);
        User actualSecond = registrationService.register(expectedSecond);
        User actualThird = registrationService.register(expectedThird);
        assertEquals(expectedFirst, actualFirst, EXPECTED_MESSAGE_ADDED_TO_DB_RETURN_USER);
        assertEquals(expectedSecond, actualSecond, EXPECTED_MESSAGE_ADDED_TO_DB_RETURN_USER);
        assertEquals(expectedThird, actualThird, EXPECTED_MESSAGE_ADDED_TO_DB_RETURN_USER);
        assertEquals(Storage.people.get(ADDED_FIRST_CUSTOM_USER), actualFirst,
                EXPECTED_MESSAGE_ADDED_TO_DB);
        assertEquals(Storage.people.get(ADDED_SECOND_CUSTOM_USER), actualSecond,
                EXPECTED_MESSAGE_ADDED_TO_DB);
        assertEquals(Storage.people.get(ADDED_THIRD_CUSTOM_USER), actualThird,
                EXPECTED_MESSAGE_ADDED_TO_DB);
    }

    @Test
    @Order(7)
    void registrationUser_loginLength9_ok() {
        User expectedFirst = new User("123456789", "password1", 23);
        User expectedSecond = new User("asder5478", "password2", 27);
        User expectedThird = new User("/56mlkhg5", "password3", 25);
        User actualFirst = registrationService.register(expectedFirst);
        User actualSecond = registrationService.register(expectedSecond);
        User actualThird = registrationService.register(expectedThird);
        assertEquals(expectedFirst, actualFirst, EXPECTED_MESSAGE_ADDED_TO_DB_RETURN_USER);
        assertEquals(expectedSecond, actualSecond, EXPECTED_MESSAGE_ADDED_TO_DB_RETURN_USER);
        assertEquals(expectedThird, actualThird, EXPECTED_MESSAGE_ADDED_TO_DB_RETURN_USER);
        assertEquals(Storage.people.get(ADDED_FIRST_CUSTOM_USER), actualFirst,
                EXPECTED_MESSAGE_ADDED_TO_DB);
        assertEquals(Storage.people.get(ADDED_SECOND_CUSTOM_USER), actualSecond,
                EXPECTED_MESSAGE_ADDED_TO_DB);
        assertEquals(Storage.people.get(ADDED_THIRD_CUSTOM_USER), actualThird,
                EXPECTED_MESSAGE_ADDED_TO_DB);
    }

    @Test
    @Order(8)
    void registrationUser_loginLength5_notOk() {
        User userWithInvalidLoginFirst = new User("12345", "password13", 43);
        User userWithInvalidLoginSecond = new User("asdfr", "password43", 21);
        User userWithInvalidLoginThird = new User("1ty45", "password34", 19);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidLoginFirst),
                EXPECTED_MESSAGE_INVALID_LOGIN_USER);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidLoginSecond),
                EXPECTED_MESSAGE_INVALID_LOGIN_USER);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidLoginThird),
                EXPECTED_MESSAGE_INVALID_LOGIN_USER);

    }

    @Test
    @Order(9)
    void registrationUser_loginLength3_notOk() {
        User userWithInvalidLoginFirst = new User("123", "password34", 28);
        User userWithInvalidLoginSecond = new User("asd", "passworde3", 33);
        User userWithInvalidLoginThird = new User("1t5", "passwordij", 45);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidLoginFirst),
                EXPECTED_MESSAGE_INVALID_LOGIN_USER);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidLoginSecond),
                EXPECTED_MESSAGE_INVALID_LOGIN_USER);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidLoginThird),
                EXPECTED_MESSAGE_INVALID_LOGIN_USER);

    }

    @Test
    @Order(10)
    void registrationUser_checkUserAge18_ok() {
        User userWithAge18First = new User("123345", "password5t", 18);
        User userWithAge18Second = new User("asd123", "password8i", 18);
        User userWithAge18Third = new User("1t5/]9", "password1v", 18);
        User actualFirst = registrationService.register(userWithAge18First);
        User actualSecond = registrationService.register(userWithAge18Second);
        User actualThird = registrationService.register(userWithAge18Third);
        assertEquals(userWithAge18First, actualFirst, EXPECTED_MESSAGE_ADDED_TO_DB_RETURN_USER);
        assertEquals(userWithAge18Second, actualSecond, EXPECTED_MESSAGE_ADDED_TO_DB_RETURN_USER);
        assertEquals(userWithAge18Third, actualThird, EXPECTED_MESSAGE_ADDED_TO_DB_RETURN_USER);
        assertEquals(Storage.people.get(ADDED_FIRST_CUSTOM_USER), actualFirst,
                EXPECTED_MESSAGE_ADDED_TO_DB);
        assertEquals(Storage.people.get(ADDED_SECOND_CUSTOM_USER), actualSecond,
                EXPECTED_MESSAGE_ADDED_TO_DB);
        assertEquals(Storage.people.get(ADDED_THIRD_CUSTOM_USER), actualThird,
                EXPECTED_MESSAGE_ADDED_TO_DB);
    }

    @Test
    @Order(11)
    void registrationUser_checkUserAgeMore18_ok() {
        User userWithValidAgeFirst = new User("123345", "password4dd", 543);
        User userWithValidAgeSecond = new User("asd123", "password12", Integer.MAX_VALUE);
        User userWithValidAgeThird = new User("1t5/]9", "passwordc4", 19);
        User actualFirst = registrationService.register(userWithValidAgeFirst);
        User actualSecond = registrationService.register(userWithValidAgeSecond);
        User actualThird = registrationService.register(userWithValidAgeThird);
        assertEquals(userWithValidAgeFirst, actualFirst,
                EXPECTED_MESSAGE_ADDED_TO_DB_RETURN_USER);
        assertEquals(userWithValidAgeSecond, actualSecond,
                EXPECTED_MESSAGE_ADDED_TO_DB_RETURN_USER);
        assertEquals(userWithValidAgeThird, actualThird,
                EXPECTED_MESSAGE_ADDED_TO_DB_RETURN_USER);
        assertEquals(Storage.people.get(ADDED_FIRST_CUSTOM_USER), actualFirst,
                EXPECTED_MESSAGE_ADDED_TO_DB);
        assertEquals(Storage.people.get(ADDED_SECOND_CUSTOM_USER), actualSecond,
                EXPECTED_MESSAGE_ADDED_TO_DB);
        assertEquals(Storage.people.get(ADDED_THIRD_CUSTOM_USER), actualThird,
                EXPECTED_MESSAGE_ADDED_TO_DB);
    }

    @Test
    @Order(12)
    void registrationUser_checkUserNonPositiveAge_notOk() {
        User userWithInvalidAgeFirst = new User("123345", "password7u", -12);
        User userWithInvalidAgeSecond = new User("asd123", "password7i", 0);
        User userWithInvalidAgeThird = new User("1t5/]9", "password12", Integer.MIN_VALUE);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidAgeFirst),
                EXPECTED_MESSAGE_INVALID_AGE_USER);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidAgeSecond),
                EXPECTED_MESSAGE_INVALID_AGE_USER);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidAgeThird),
                EXPECTED_MESSAGE_INVALID_AGE_USER);
    }

    @Test
    @Order(13)
    void registrationUser_checkUserPasswordSize6_ok() {
        User userWithValidPassFirst = new User("123345", "123456", 543);
        User userWithValidPassSecond = new User("asd123", "qwerty", Integer.MAX_VALUE);
        User userWithValidPassThird = new User("1t5/]9", "123qwe", 19);
        User actualFirst = registrationService.register(userWithValidPassFirst);
        User actualSecond = registrationService.register(userWithValidPassSecond);
        User actualThird = registrationService.register(userWithValidPassThird);
        assertEquals(userWithValidPassFirst, actualFirst,
                EXPECTED_MESSAGE_ADDED_TO_DB_RETURN_USER);
        assertEquals(userWithValidPassSecond, actualSecond,
                EXPECTED_MESSAGE_ADDED_TO_DB_RETURN_USER);
        assertEquals(userWithValidPassThird, actualThird,
                EXPECTED_MESSAGE_ADDED_TO_DB_RETURN_USER);
        assertEquals(Storage.people.get(ADDED_FIRST_CUSTOM_USER), actualFirst,
                EXPECTED_MESSAGE_ADDED_TO_DB);
        assertEquals(Storage.people.get(ADDED_SECOND_CUSTOM_USER), actualSecond,
                EXPECTED_MESSAGE_ADDED_TO_DB);
        assertEquals(Storage.people.get(ADDED_THIRD_CUSTOM_USER), actualThird,
                EXPECTED_MESSAGE_ADDED_TO_DB);
    }

    @Test
    @Order(14)
    void registrationUser_checkUserPasswordSizeMore6_ok() {
        User userWithValidPassFirst =
                new User("123345tfwke", "123456", 543);
        User userWithValidPassSecond =
                new User("asd123fobejwffeu", "qwerty", Integer.MAX_VALUE);
        User userWithValidPassThird =
                new User("1t5/]9", "123qwe26td3ghfe", 19);
        User actualFirst = registrationService.register(userWithValidPassFirst);
        User actualSecond = registrationService.register(userWithValidPassSecond);
        User actualThird = registrationService.register(userWithValidPassThird);
        assertEquals(userWithValidPassFirst, actualFirst,
                EXPECTED_MESSAGE_ADDED_TO_DB_RETURN_USER);
        assertEquals(userWithValidPassSecond, actualSecond,
                EXPECTED_MESSAGE_ADDED_TO_DB_RETURN_USER);
        assertEquals(userWithValidPassThird, actualThird,
                EXPECTED_MESSAGE_ADDED_TO_DB_RETURN_USER);
        assertEquals(Storage.people.get(ADDED_FIRST_CUSTOM_USER), actualFirst,
                EXPECTED_MESSAGE_ADDED_TO_DB);
        assertEquals(Storage.people.get(ADDED_SECOND_CUSTOM_USER), actualSecond,
                EXPECTED_MESSAGE_ADDED_TO_DB);
        assertEquals(Storage.people.get(ADDED_THIRD_CUSTOM_USER), actualThird,
                EXPECTED_MESSAGE_ADDED_TO_DB);
    }

    @Test
    @Order(15)
    void registrationUser_checkUserPasswordSizeLess6_notOk() {
        User userWithInvalidAgeFirst = new User("123345", "3", 19);
        User userWithInvalidAgeSecond = new User("asd123", "", 22);
        User userWithInvalidAgeThird = new User("1t5/]9", "12345", 43);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidAgeFirst),
                EXPECTED_MESSAGE_INVALID_PASSWORD_USER);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidAgeSecond),
                EXPECTED_MESSAGE_INVALID_PASSWORD_USER);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidAgeThird),
                EXPECTED_MESSAGE_INVALID_PASSWORD_USER);
    }

    @Test
    @Order(16)
    void registrationUser_checkUserPasswordNull_notOk() {
        User userWithInvalidAgeFirst = new User("LogInMy", null, 19);
        User userWithInvalidAgeSecond = new User("LolGeeen", null, 123);
        User userWithInvalidAgeThird = new User("HelloHello", null, 45);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidAgeFirst),
                EXPECTED_MESSAGE_NULL_PASSWORD_USER);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidAgeSecond),
                EXPECTED_MESSAGE_NULL_PASSWORD_USER);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidAgeThird),
                EXPECTED_MESSAGE_NULL_PASSWORD_USER);
    }
}
