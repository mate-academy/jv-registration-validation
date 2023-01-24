package core.basesyntax;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    void CheckCountOfPrivateFieldsInRegistrationService() {
        List<Field> privateFields = new ArrayList<>();
        List<Field> allFields = Arrays.asList(RegistrationServiceImpl.class.getDeclaredFields());
        for (Field field : allFields) {
            if (Modifier.isPrivate(field.getModifiers())) {
                privateFields.add(field);
            }
        }
        assertEquals(allFields.size(), privateFields.size(), "You should have private fields only!\n");
        assertTrue(privateFields.size() >= allFields.size(), "You should have at least " + allFields.size() + " fields");
    }

    @Test
    void registerValidUsers_Ok() throws RegistrationException {
        List<User> actual = new ArrayList<>();
        for (User user : validUserData) {
            registrationService.register(user);
            actual.add(storageDaoImpl.get(user.getLogin()));
        }

        assertEquals(expectedStorageSize, Storage.people.size(),
                "Test failed! The size of the storage isn't correct. Expected " + expectedStorageSize + " but was " + Storage.people.size());

        assertTrue(actual.contains(new User("bob", "bobPassword27", 27)),
                "Test failed! The storage must contain a user with login: bob");
        assertTrue(actual.contains(new User("alice96", "qwerty", 21)),
                "Test failed! The storage must contain a user with login: alice96");
        assertTrue(actual.contains(new User("josh_goldberg", "sh43u#Idsh", 18)),
                "Test failed! The storage must contain a user with login: josh_goldberg");
        assertTrue(actual.contains(new User("marku$", "markus5", 60)),
                "Test failed! The storage must contain a user with login: marku$");
    }

    @Test
    void registerAndGetValidUsers_Ok() throws RegistrationException {
        for (User user : validUserData) {
            registrationService.register(user);
        }
        assertEquals(expectedStorageSize, Storage.people.size(),
                "The size of the storage isn't correct. Expected " + expectedStorageSize + " but was " + Storage.people.size());

        User actualBobUser = storageDaoImpl.get(bob.getLogin());
        User actualAliceUser = storageDaoImpl.get(alice.getLogin());
        User actualJohnUser = storageDaoImpl.get(john.getLogin());
        User actualMarkUser = storageDaoImpl.get(mark.getLogin());

        assertEquals(bob, actualBobUser,
                "Test failed! Storage expects to contain " + bob.getLogin() + ", "
                        + " but was " + actualBobUser);
        assertEquals(alice, actualAliceUser,
                "Test failed! Storage expects to contain " + alice.getLogin() + ", "
                        + " but was " + actualAliceUser);
        assertEquals(john, actualJohnUser,
                "Test failed! Storage expects to contain " + john.getLogin() + ", "
                        + " but was " + actualJohnUser);
        assertEquals(mark, actualMarkUser,
                "Test failed! Storage expects to contain " + mark.getLogin() + ", "
                        + " but was " + actualMarkUser);
    }

    @Test
    void registerNullUser_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null),
                "Test failed! The method should throw an exception if the user == null" + "\n");
    }

    @Test
    void registerSomeUser_NotOk() throws RegistrationException {
        registrationService.register(bob);
        registrationService.register(alice);
        assertEquals(2, Storage.people.size(),
                "Test failed! The size isn't correct. Expected " + 2 + " but was " + Storage.people.size());

        assertThrows(RegistrationException.class, () -> registrationService.register(bob),
                "Test failed! The method should throw an exception if the login are exist!" + "\n");
    }

    @Test
    void registerInvalidAge_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(new User("steve", "steveLongLongPasswordOfCourse", 15)),
                "Test failed! The method should throw an exception if the user's age does not meet the requirements!" + "\n");
    }

    @Test
    void registerInvalidPasswordLength_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(new User("steve", "short", 25)),
                "Test failed! The method should throw an exception if the password length does not meet the requirements!" + "\n");
    }

    @Test
    void registerNullPassword_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(new User("steve", null, 25)),
                "Test failed! The method should throw an exception if password == null!" + "\n");
    }

    @Test
    public void registerNullLogin_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(new User(null, "pasdasd325", 52)),
                "Test failed! The method should throw an exception if login == null!" + "\n");
    }

    @Test
    public void registerNullAge_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(new User("steve", "pasdas4d325", null)),
                "Test failed! The method should throw an exception if age == null!" + "\n");
    }

    @Test
    void getNull_Ok() throws RegistrationException {
        for (User user : validUserData) {
            registrationService.register(user);
        }
        assertNull(storageDaoImpl.get(null),
                "Test failed! The method must return null!"); // Хотя Немчинский через раз повторяет, что методы не должны возвращать null.  
    }

    @Test
    void getByNonExistUser() {
        assertNull(storageDaoImpl.get(alice.getLogin()),
                "Test failed! The method should have returned null with a non-existent login! " + alice.getLogin());
    }

    @Test
    void registerNegativeAge_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(new User("steve", "pasdas4d325", -16)),
                "Test failed! The method should throw an exception if age is a negative number!" + "\n");
    }

    @Test
    void changeIndexAfterAdd_Ok() throws RegistrationException {
        registrationService.register(bob);
        long bobIndex = bob.getId();
        registrationService.register(alice);
        long aliceIndex = alice.getId();
        assertNotEquals(bobIndex, aliceIndex,
                "Test failed! The index must increment when user registrations!" + "\n");
    }

    @Test
    void usersIsEquals_Ok() {
        assertEquals(bob, secondBob,
                "User objects do not equal. The equals() method doesn't work correctly!");
    }

    @Test
    void userNotEqualsWithNull_Ok() {
        assertNotEquals(bob, null);
    }

    @Test
    void usersHashCodeIsEquals_Ok() {
        assertEquals(bob.hashCode(), secondBob.hashCode(), "Users' hashCode does not equal. The hashCode() method doesn't work correctly!");
    }

    @Test
    void userSetters_Ok() {
        User tony = new User("tonyS", "sta1970rk", 50);
        tony.setId(10L);

        long oldId = tony.getId();
        String oldLogin = tony.getLogin();
        String oldPassword = tony.getPassword();
        int oldAge = tony.getAge();

        tony.setId(22L);
        assertNotEquals(oldId, tony.getId(),
                "Fields don't have to be equal. setId() method works incorrectly!" + '\n');

        tony.setLogin("STARK");
        assertNotEquals(oldLogin, tony.getLogin(),
                "Fields don't have to be equal. setLogin() method works incorrectly!" + '\n');

        tony.setPassword("qwertyasdfgzxcvb");
        assertNotEquals(oldPassword, tony.getPassword(),
                "Fields don't have to be equal. setPassword() method works incorrectly!" + '\n');

        tony.setAge(51);
        assertNotEquals(oldAge, tony.getAge(),
                "Fields don't have to be equal. setAge() method works incorrectly!" + '\n');
    }
}
