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

    private static final User bob = new User("bob", "bobPassword27", 27);
    private static final User alice = new User("alice96", "qwerty", 21);
    private static final User john = new User("josh_goldberg", "sh43u#Idsh", 18);
    private static final User mark = new User("marku$", "markus5", 60);

    private static final User secondBob = new User("bob", "bobPassword27", 27);

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDaoImpl = new StorageDaoImpl();
        expectedStorageSize = 4;
    }

    @BeforeEach
    void setUp() {
        validUserData = new ArrayList<>();
        validUserData.add(bob);
        validUserData.add(alice);
        validUserData.add(john);
        validUserData.add(mark);
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

        Assertions.assertTrue(actual.contains(new User("bob", "bobPassword27", 27)),
                "Test failed! The storage must contain a user with login: bob" + '\n');
        Assertions.assertTrue(actual.contains(new User("alice96", "qwerty", 21)),
                "Test failed! The storage must contain a user with login: alice96" + '\n');
        Assertions.assertTrue(actual.contains(new User("josh_goldberg", "sh43u#Idsh", 18)),
                "Test failed! The storage must contain a user with login: josh_goldberg" + '\n');
        Assertions.assertTrue(actual.contains(new User("marku$", "markus5", 60)),
                "Test failed! The storage must contain a user with login: marku$" + '\n');
    }

    @Test
    void registerAndGetValidUsers_Ok() throws RegistrationException {
        for (User user : validUserData) {
            registrationService.register(user);
        }
        Assertions.assertEquals(expectedStorageSize, Storage.people.size(),
                "The size of the storage isn't correct. Expected "
                        + expectedStorageSize + " but was " + Storage.people.size());

        User actualBobUser = storageDaoImpl.get(bob.getLogin());
        User actualAliceUser = storageDaoImpl.get(alice.getLogin());
        User actualJohnUser = storageDaoImpl.get(john.getLogin());
        User actualMarkUser = storageDaoImpl.get(mark.getLogin());

        Assertions.assertEquals(bob, actualBobUser,
                "Test failed! Storage expects to contain " + bob.getLogin() + ", "
                        + " but was " + actualBobUser + '\n');
        Assertions.assertEquals(alice, actualAliceUser,
                "Test failed! Storage expects to contain " + alice.getLogin() + ", "
                        + " but was " + actualAliceUser + '\n');
        Assertions.assertEquals(john, actualJohnUser,
                "Test failed! Storage expects to contain " + john.getLogin() + ", "
                        + " but was " + actualJohnUser + '\n');
        Assertions.assertEquals(mark, actualMarkUser,
                "Test failed! Storage expects to contain " + mark.getLogin() + ", "
                        + " but was " + actualMarkUser + '\n');
    }

    @Test
    void registerNullUser_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () ->
                        registrationService.register(null),
                "Test failed! The method should throw an exception if the user == null" + '\n');
    }

    @Test
    void registerSomeUser_NotOk() throws RegistrationException {
        registrationService.register(bob);
        registrationService.register(alice);
        Assertions.assertEquals(2, Storage.people.size(),
                "Test failed! The size isn't correct. Expected "
                        + 2 + " but was " + Storage.people.size() + '\n');

        Assertions.assertThrows(RegistrationException.class, () ->
                        registrationService.register(bob),
                "Test failed! The method should throw an exception if the login are exist!" + '\n');
    }

    @Test
    void registerInvalidAge_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () ->
                        registrationService.register(new User("steve", "steveLongPassword", 15)),
                "Test failed! The method should throw an exception if the user's age < 18!" + '\n');
    }

    @Test
    void registerInvalidPasswordLength_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () ->
                        registrationService.register(new User("steve", "short", 25)),
                "Test failed! The method should throw an exception if the password length < 6>" + '\n');
    }

    @Test
    void registerNullPassword_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () ->
                        registrationService.register(new User("steve", null, 25)),
                "Test failed! The method should throw an exception if password == null!" + '\n');
    }

    @Test
    public void registerNullLogin_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () -> registrationService.register(new User(null, "pasdasd325", 52)),
                "Test failed! The method should throw an exception if login == null!" + '\n');
    }

    @Test
    public void registerNullAge_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () -> registrationService.register(new User("steve", "pasdas4d325", null)),
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
        Assertions.assertNull(storageDaoImpl.get(alice.getLogin()),
                "Test failed! The method should have returned null with a non-existent login! " + alice.getLogin() + '\n');
    }

    @Test
    void registerNegativeAge_NotOk() {
        Assertions.assertThrows(RegistrationException.class, () -> registrationService.register(new User("steve", "pasdas4d325", -16)),
                "Test failed! The method should throw an exception if age is a negative number!" + '\n');
    }

    @Test
    void changeIndexAfterAdd_Ok() throws RegistrationException {
        registrationService.register(bob);
        long bobIndex = bob.getId();
        registrationService.register(alice);
        long aliceIndex = alice.getId();
        Assertions.assertNotEquals(bobIndex, aliceIndex,
                "Test failed! The index must increment when user registrations!" + '\n');
    }

    @Test
    void usersIsEquals_Ok() {
        Assertions.assertEquals(bob, secondBob,
                "Test failed! User objects do not equal. The equals() method doesn't work correctly!" + '\n');
    }

    @Test
    void userNotEqualsWithNull_Ok() {
        Assertions.assertNotEquals(bob, null,
                "Test failed! User and null can't be equals!" + '\n');
    }

    @Test
    void usersHashCodeIsEquals_Ok() {
        Assertions.assertEquals(bob.hashCode(), secondBob.hashCode(),
                "Test failed! Users' hashCode does not equal. The hashCode() method doesn't work correctly!" + '\n');
    }

    @Test
    void userSetters_Ok() {
        User tony = new User("tonyS", "sta1970rk", 50);
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
