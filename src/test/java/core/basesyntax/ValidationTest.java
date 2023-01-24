package core.basesyntax;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValidationTest {
    private static RegistrationServiceImpl registrationService;
    private static List<User> validUserData;
    private static StorageDaoImpl storageDaoImpl;
    private static int expectedStorageSize;

    private User validUserBob;
    private User validUserAlice;
    private User validUserJohn;
    private User validUserSecondBob;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDaoImpl = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        validUserBob = new User();
        validUserBob.setLogin("bob");
        validUserBob.setPassword("bobPassword27");
        validUserBob.setAge(27);

        validUserAlice = new User();
        validUserAlice.setLogin("alice96");
        validUserAlice.setPassword("qwerty");
        validUserAlice.setAge(21);

        validUserJohn = new User();
        validUserJohn.setLogin("josh_goldberg");
        validUserJohn.setPassword("sh43u#Idsh");
        validUserJohn.setAge(18);

        validUserSecondBob = new User();
        validUserSecondBob.setLogin("bob");
        validUserSecondBob.setPassword("bobPassword27");
        validUserSecondBob.setAge(27);

        validUserData = new ArrayList<>();
        validUserData.add(validUserBob);
        validUserData.add(validUserAlice);
        validUserData.add(validUserJohn);

        expectedStorageSize = validUserData.size();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void checkPrivateFieldsInRegistrationImpl_Ok() {
        List<Field> privateFields = new ArrayList<>();
        List<Field> allFields = Arrays.asList(RegistrationServiceImpl.class.getDeclaredFields());
        for (Field field : allFields) {
            if (Modifier.isPrivate(field.getModifiers())) {
                privateFields.add(field);
            }
        }
        Assertions.assertEquals(allFields.size(), privateFields.size(),
                "You should have private fields only!" + '\n');
        Assertions.assertTrue(privateFields.size() >= allFields.size(),
                "You should have at least " + allFields.size() + " fields" + '\n');
    }

    @Test
    void registerValidUsers_Ok() throws RegistrationException {
        List<User> actual = new ArrayList<>();
        for (User user : validUserData) {
            registrationService.register(user);
            actual.add(storageDaoImpl.get(user.getLogin()));
        }

        Assertions.assertEquals(expectedStorageSize, Storage.people.size(),
                "Test failed! The size of the storage isn't correct. Expected "
                        + expectedStorageSize + " but was " + Storage.people.size() + '\n');

        User newBob = new User();
        newBob.setLogin("bob");
        newBob.setPassword("bobPassword27");
        newBob.setAge(27);
        Assertions.assertTrue(actual.contains(newBob),
                "Test failed! The storage must contain a user with login: bob" + '\n');

        User newAlice = new User();
        newAlice.setLogin("alice96");
        newAlice.setPassword("qwerty");
        newAlice.setAge(21);
        Assertions.assertTrue(actual.contains(newAlice),
                "Test failed! The storage must contain a user with login: alice96" + '\n');

        User newJohn = new User();
        newJohn.setLogin("josh_goldberg");
        newJohn.setPassword("sh43u#Idsh");
        newJohn.setAge(18);
        Assertions.assertTrue(actual.contains(newJohn),
                "Test failed! The storage must contain a user with login: josh_goldberg" + '\n');
    }

    @Test
    void registerAndGetValidUsers_Ok() throws RegistrationException {
        for (User user : validUserData) {
            registrationService.register(user);
        }
        Assertions.assertEquals(expectedStorageSize, Storage.people.size(),
                "The size of the storage isn't correct. Expected "
                        + expectedStorageSize + " but was " + Storage.people.size());

        User actualBobUser = storageDaoImpl.get(validUserBob.getLogin());
        User actualAliceUser = storageDaoImpl.get(validUserAlice.getLogin());
        User actualJohnUser = storageDaoImpl.get(validUserJohn.getLogin());

        Assertions.assertEquals(validUserBob, actualBobUser,
                "Test failed! Storage expects to contain " + validUserBob.getLogin() + ", "
                        + " but was " + actualBobUser + '\n');
        Assertions.assertEquals(validUserAlice, actualAliceUser,
                "Test failed! Storage expects to contain " + validUserAlice.getLogin() + ", "
                        + " but was " + actualAliceUser + '\n');
        Assertions.assertEquals(validUserJohn, actualJohnUser,
                "Test failed! Storage expects to contain " + validUserJohn.getLogin() + ", "
                        + " but was " + actualJohnUser + '\n');
    }

    @Test
    void registerNullUser_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () ->
                        registrationService.register(null),
                "Test failed! The method should throw an exception if the user == null" + '\n');
    }

    @Test
    void registerSomeUser_NotOk() throws RegistrationException {
        registrationService.register(validUserBob);
        registrationService.register(validUserAlice);
        Assertions.assertEquals(2, Storage.people.size(),
                "Test failed! The size isn't correct. Expected "
                        + 2 + " but was " + Storage.people.size() + '\n');

        Assertions.assertThrows(RegistrationException.class, () ->
                        registrationService.register(validUserBob),
                "Test failed! The method should throw an exception if the login are exist!" + '\n');
    }

    @Test
    void registerInvalidAge_NotOk() {
        User invalidUserAge = new User();
        invalidUserAge.setLogin("steve");
        invalidUserAge.setPassword("steveLongPassword");
        invalidUserAge.setAge(15);

        Assertions.assertThrows(RegistrationException.class, () ->
                        registrationService.register(invalidUserAge),
                "Test failed! The method should throw an exception if the user's age < 18!" + '\n');
    }

    @Test
    void registerInvalidPasswordLength_NotOk() {
        User invalidUserPasswordLength = new User();
        invalidUserPasswordLength.setLogin("steve");
        invalidUserPasswordLength.setPassword("short");
        invalidUserPasswordLength.setAge(25);

        Assertions.assertThrows(RegistrationException.class, () ->
                        registrationService.register(invalidUserPasswordLength),
                "Test failed! The method should throw an exception if the password length < 6>"
                        + '\n');
    }

    @Test
    void registerNullPassword_NotOk() {
        User invalidUserPasswordIsNull = new User();
        invalidUserPasswordIsNull.setLogin("steve");
        invalidUserPasswordIsNull.setPassword(null);
        invalidUserPasswordIsNull.setAge(25);

        Assertions.assertThrows(RegistrationException.class, () ->
                        registrationService.register(invalidUserPasswordIsNull),
                "Test failed! The method should throw an exception if password == null!" + '\n');
    }

    @Test
    public void registerNullLogin_NotOk() {
        User invalidUserLoginIsNull = new User();
        invalidUserLoginIsNull.setLogin(null);
        invalidUserLoginIsNull.setPassword("pasdasd325");
        invalidUserLoginIsNull.setAge(52);

        Assertions.assertThrows(RegistrationException.class, () ->
                        registrationService.register(invalidUserLoginIsNull),
                "Test failed! The method should throw an exception if login == null!" + '\n');
    }

    @Test
    public void registerNullAge_NotOk() {
        User invalidUserAgeIsNull = new User();
        invalidUserAgeIsNull.setLogin("steve");
        invalidUserAgeIsNull.setPassword("pasdas4d325");
        invalidUserAgeIsNull.setAge(null);

        Assertions.assertThrows(RegistrationException.class, () ->
                        registrationService.register(invalidUserAgeIsNull),
                "Test failed! The method should throw an exception if age == null!" + '\n');
    }

    @Test
    void getNull_Ok() throws RegistrationException {
        for (User user : validUserData) {
            registrationService.register(user);
        }
        Assertions.assertNull(storageDaoImpl.get(null),
                "Test failed! The method must return null!" + '\n');
    }

    @Test
    void getByNonExistUser() {
        Assertions.assertNull(storageDaoImpl.get(validUserAlice.getLogin()),
                "Test failed! The method should have returned null with a non-existent login! "
                        + validUserAlice.getLogin() + '\n');
    }

    @Test
    void registerNegativeAge_NotOk() {
        User invalidUserNegativeAge = new User();
        invalidUserNegativeAge.setLogin("steve");
        invalidUserNegativeAge.setPassword("pasdas4d325");
        invalidUserNegativeAge.setAge(-16);

        Assertions.assertThrows(RegistrationException.class, () ->
                        registrationService.register(invalidUserNegativeAge),
                "Test failed! The method should throw an exception if age < 0>" + '\n');
    }

    @Test
    void changeIndexAfterAdd_Ok() throws RegistrationException {
        registrationService.register(validUserBob);
        long bobIndex = validUserBob.getId();
        registrationService.register(validUserAlice);
        long aliceIndex = validUserAlice.getId();
        Assertions.assertNotEquals(bobIndex, aliceIndex,
                "Test failed! The index must increment when user registrations!" + '\n');
    }

    @Test
    void usersIsEquals_Ok() {
        Assertions.assertEquals(validUserBob, validUserSecondBob,
                "Test failed! The equals() method doesn't work correctly!" + '\n');
    }

    @Test
    void userNotEqualsWithNull_Ok() {
        Assertions.assertNotEquals(validUserBob, null,
                "Test failed! User and null can't be equals!" + '\n');
    }

    @Test
    void usersHashCodeIsEquals_Ok() {
        Assertions.assertEquals(validUserBob.hashCode(),
                validUserSecondBob.hashCode(),
                "Test failed! The hashCode() method doesn't work correctly!" + '\n');
    }

    @Test
    void userSetters_Ok() {
        User tony = new User();
        tony.setLogin("tonyS");
        tony.setPassword("sta1970rk");
        tony.setAge(50);

        tony.setId(10L);

        long oldId = tony.getId();
        tony.setId(22L);
        Assertions.assertNotEquals(oldId, tony.getId(),
                "Fields don't have to be equal. setId() method works incorrectly!" + '\n');

        String oldLogin = tony.getLogin();
        tony.setLogin("STARK");
        Assertions.assertNotEquals(oldLogin, tony.getLogin(),
                "Fields don't have to be equal. setLogin() method works incorrectly!" + '\n');

        String oldPassword = tony.getPassword();
        tony.setPassword("qwertyasdfgzxcvb");
        Assertions.assertNotEquals(oldPassword, tony.getPassword(),
                "Fields don't have to be equal. setPassword() method works incorrectly!" + '\n');

        int oldAge = tony.getAge();
        tony.setAge(51);
        Assertions.assertNotEquals(oldAge, tony.getAge(),
                "Fields don't have to be equal. setAge() method works incorrectly!" + '\n');
    }
}
