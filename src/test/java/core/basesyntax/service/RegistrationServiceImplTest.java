package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int STANDARD_DB_SET_COUNTER = 3;
    private static final int ADDED_FIRST_CUSTOM_USER = 0;
    private static final int ADDED_SECOND_CUSTOM_USER = 1;
    private static final int ADDED_THIRD_CUSTOM_USER = 2;
    private static final User userBob = new User("user_bob", "qwerty1234", 34);
    private static final User userAnn = new User("user_ann", "12345678", 18);
    private static final User userRoma = new User("user_roma", "bobocode", 22);
    private RegistrationService registrationService;

    @BeforeEach
    void setRegistrationService() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void afterEach() {
        Storage.people.clear();
    }

    @Test
    @Order(1)
    void registrationUser_nullUser_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(null),
                "Expected, that a null pointer instead of"
                        + " User will call the RegistrationException.");
    }

    @Test
    @Order(2)
    void registrationUser_nullLogin_notOk() {
        User userNullLogin = new User(null, "fgjhdn1356nb7", 32);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userNullLogin),
                "Expected, that a user with a null login"
                        + " will call the RegistrationException.");
    }

    @Test
    @Order(3)
    void registrationUser_nullAge_notOk() {
        User userNullAge = new User("LoGiNoNe", "fgjhdnrt", null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userNullAge),
                "Expected, that a user with a null age "
                        + "will call the RegistrationException.");
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
        assertEquals(expectedFirst, actualFirst);
        assertEquals(expectedSecond, actualSecond);
        assertEquals(expectedThird, actualThird);
        assertEquals(Storage.people.get(ADDED_FIRST_CUSTOM_USER), actualFirst);
        assertEquals(Storage.people.get(ADDED_SECOND_CUSTOM_USER), actualSecond);
        assertEquals(Storage.people.get(ADDED_THIRD_CUSTOM_USER), actualThird);
    }

    @Test
    @Order(5)
    void registrationUser_existingUser_notOk() {
        Storage.people.addAll(List.of(userAnn, userBob, userRoma));
        assertNull(registrationService.register(userAnn),
                "Expected, that an existing user can't be added to the DB and must return null.");
        assertNull(registrationService.register(userBob),
                "Expected, that an existing user can't be added to the DB and must return null.");
        assertNull(registrationService.register(userRoma),
                "Expected, that an existing user can't be added to the DB and must return null.");
        assertEquals(Storage.people.size(), STANDARD_DB_SET_COUNTER,
                "Expected, that DB does not be changed.");
    }

    @Test
    @Order(6)
    void registrationUser_loginLength6_ok() {
        User expectedFirst = new User("123456", "password1", 23);
        User actualFirst = registrationService.register(expectedFirst);
        assertEquals(expectedFirst, actualFirst);
        assertEquals(Storage.people.get(ADDED_FIRST_CUSTOM_USER), actualFirst);
    }

    @Test
    @Order(7)
    void registrationUser_loginLength9_ok() {
        User expectedFirst = new User("123456789", "password1", 23);
        User actualFirst = registrationService.register(expectedFirst);
        assertEquals(expectedFirst, actualFirst);
        assertEquals(Storage.people.get(ADDED_FIRST_CUSTOM_USER), actualFirst);
    }

    @Test
    @Order(8)
    void registrationUser_loginLength5_notOk() {
        User userWithInvalidLoginFirst = new User("12345", "password13", 43);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidLoginFirst),
                "Should throw an exception");
    }

    @Test
    @Order(9)
    void registrationUser_loginLength3_notOk() {
        User userWithInvalidLoginFirst = new User("123", "password34", 28);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidLoginFirst),
                "Should throw an exception");
    }

    @Test
    @Order(10)
    void registrationUser_checkUserAge18_ok() {
        User userWithAge18First = new User("123345", "password5t", 18);
        User actualFirst = registrationService.register(userWithAge18First);
        assertEquals(userWithAge18First, actualFirst);
        assertEquals(Storage.people.get(ADDED_FIRST_CUSTOM_USER), actualFirst);
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
        assertEquals(userWithValidAgeFirst, actualFirst);
        assertEquals(userWithValidAgeSecond, actualSecond);
        assertEquals(userWithValidAgeThird, actualThird);
        assertEquals(Storage.people.get(ADDED_FIRST_CUSTOM_USER), actualFirst);
        assertEquals(Storage.people.get(ADDED_SECOND_CUSTOM_USER), actualSecond);
        assertEquals(Storage.people.get(ADDED_THIRD_CUSTOM_USER), actualThird);
    }

    @Test
    @Order(12)
    void registrationUser_checkUserNonPositiveAge_notOk() {
        User userWithInvalidAgeFirst = new User("123345", "password7u", -12);
        User userWithInvalidAgeSecond = new User("asd123", "password7i", 0);
        User userWithInvalidAgeThird = new User("1t5/]9", "password12", Integer.MIN_VALUE);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidAgeFirst),
                "Should throw an exception");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidAgeSecond),
                "Should throw an exception");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidAgeThird),
                "Should throw an exception");
    }

    @Test
    @Order(13)
    void registrationUser_checkUserPasswordSize6_ok() {
        User userWithValidPassFirst = new User("123345", "123456", 543);
        User actualFirst = registrationService.register(userWithValidPassFirst);
        assertEquals(userWithValidPassFirst, actualFirst);
        assertEquals(Storage.people.get(ADDED_FIRST_CUSTOM_USER), actualFirst);
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
        assertEquals(userWithValidPassFirst, actualFirst);
        assertEquals(userWithValidPassSecond, actualSecond);
        assertEquals(userWithValidPassThird, actualThird);
        assertEquals(Storage.people.get(ADDED_FIRST_CUSTOM_USER), actualFirst);
        assertEquals(Storage.people.get(ADDED_SECOND_CUSTOM_USER), actualSecond);
        assertEquals(Storage.people.get(ADDED_THIRD_CUSTOM_USER), actualThird);
    }

    @Test
    @Order(15)
    void registrationUser_checkUserPasswordSizeLess6_notOk() {
        User userWithInvalidAgeFirst = new User("123345", "3", 19);
        User userWithInvalidAgeSecond = new User("asd123", "", 22);
        User userWithInvalidAgeThird = new User("1t5/]9", "12345", 43);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidAgeFirst),
                "Should throw an exception");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidAgeSecond),
                "Should throw an exception");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidAgeThird),
                "Should throw an exception");
    }

    @Test
    @Order(16)
    void registrationUser_checkUserPasswordNull_notOk() {
        User userWithInvalidAge = new User("LogInMy", null, 19);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithInvalidAge),
                "Should throw an exception");
    }
}
