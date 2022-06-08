package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private int expectedSize;

    private static User userConstructor(long id, String login, String password, int age) {
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        expectedSize = 0;
    }

    @Test
    void register_newValidUsers_Ok() {
        int lowestValidAge = 18;
        int adultAge = 32;
        String lowestValidPass = "123456";
        User[] users = new User[3];
        users[0] = userConstructor(101L, "FirstUser", "password", adultAge);
        users[1] = userConstructor(102L, "SecondUser", lowestValidPass, adultAge + adultAge);
        users[2] = userConstructor(103L, "ThirdUser", "password", lowestValidAge);

        for (User user : users) {
            long oldId = user.getId();
            User actual = registrationService.register(user);
            assertNotNull(actual, "Returned Object must be not null");
            assertEquals(actual, user, "Registration method should return registered "
                    + "User Object");
            assertNotEquals(oldId, user.getId(), "User Id must should be changed "
                    + "due to registration");
            expectedSize++;
        }
        assertEquals(expectedSize, Storage.people.size(), "Storage size not changed properly");
    }

    @Test
    void register_nullAge_notOk() {
        User user = userConstructor(1L, "UserNullAge", "password", 0);
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "There must RuntimeException throw when NULL AGE field in "
                        + "User registration");
        assertEquals(expectedSize, Storage.people.size(), "Storage size not changed properly");
    }

    @Test
    void register_under18Age_notOk() {
        User user = userConstructor(1L, "UserYoungAge", "password", 12);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "There must RuntimeException throw when UNDER 18 AGE field in "
                        + "User registration");
        assertEquals(expectedSize, Storage.people.size(), "Storage size not changed properly");
    }

    @Test
    void register_negativeAge_notOk() {
        User user = userConstructor(1L, "UserNegativeAge", "password", 0);
        user.setAge(-1);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "There must RuntimeException throw when NEGATIVE AGE field in "
                        + "User registration");
        assertEquals(expectedSize, Storage.people.size(), "Storage size not changed properly");
    }

    @Test
    void register_existentUser_notOk() {
        User user = userConstructor(101L, "User", "password", 32);
        long oldId = user.getId();
        User actual = registrationService.register(user);
        assertNotNull(actual, "Returned Object must be not null");
        assertEquals(actual, user, "Registration method should return "
                + "registered User Object");
        assertNotEquals(oldId, user.getId(), "User Id must should be changed "
                + "due to registration");
        expectedSize++;

        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "There must RuntimeException throw when try register same User");

        assertEquals(expectedSize, Storage.people.size(), "Storage size not changed properly");
    }

    @Test
    void register_existentLoginUser_notOk() {
        User firstUser = userConstructor(101L, "User", "password", 32);
        User sameLoginUser = userConstructor(102L, "User", "password2", 43);
        long oldId = firstUser.getId();
        User actual = registrationService.register(firstUser);
        assertNotNull(actual, "Returned Object must be not null");
        assertEquals(actual, firstUser, "Registration method should return "
                + "registered User Object");
        assertNotEquals(oldId, firstUser.getId(), "User Id must should be changed "
                + "due to registration");
        expectedSize++;

        assertThrows(RuntimeException.class, () -> registrationService.register(sameLoginUser),
                "There must RuntimeException throw when try register User with same login");

        assertEquals(expectedSize, Storage.people.size(), "Storage size not changed properly");
    }

    @Test
    void register_emptyLoginUser_notOk() {
        User user = userConstructor(101L, "", "password", 32);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "There must RuntimeException throw when try register User with empty login");
        assertEquals(expectedSize, Storage.people.size(), "Storage size not changed properly");
    }

    @Test
    void register_nullFieldsUser_NotOk() {
        User nullFieldsUser = new User();
        assertThrows(RuntimeException.class, () -> registrationService.register(nullFieldsUser),
                "There must RuntimeException throw when null ALL fields User registration");
        assertEquals(expectedSize, Storage.people.size(), "Storage size not changed properly");
    }

    @Test
    void register_nullLoginUser_NotOk() {
        User nullLoginUser = userConstructor(1L, null, "password", 32);
        assertThrows(RuntimeException.class, () -> registrationService.register(nullLoginUser),
                "There must RuntimeException throw when null LOGIN field in User registration");
        assertEquals(expectedSize, Storage.people.size(), "Storage size not changed properly");
    }

    @Test
    void register_nullPasswordUser_NotOk() {
        User nullPasswordUser = userConstructor(1L, "NullPasswordUser", null, 32);
        assertThrows(RuntimeException.class, () -> registrationService.register(nullPasswordUser),
                "There must RuntimeException throw when null PASSWORD field in User registration");
        assertEquals(expectedSize, Storage.people.size(), "Storage size not changed properly");
    }

    @Test
    void register_emptyPassword_NotOk() {
        User emptyPasswordUser = userConstructor(1L, "EmptyPasswordUser", "", 32);
        assertThrows(RuntimeException.class, () -> registrationService.register(emptyPasswordUser),
                "There must RuntimeException throw when EMPTY PASSWORD field in "
                        + "User registration");
        assertEquals(expectedSize, Storage.people.size(), "Storage size not changed properly");
    }

    @Test
    void register_shortPassword_NotOk() {
        User notValidPassUser = userConstructor(1L, "NotValidPassUser", "12345", 32);
        assertThrows(RuntimeException.class, () -> registrationService.register(notValidPassUser),
                "There must RuntimeException throw when PASSWORD less then six symbols");
        assertEquals(expectedSize, Storage.people.size(), "Storage size not changed properly");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
