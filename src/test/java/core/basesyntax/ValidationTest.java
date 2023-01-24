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
    private static final String EXCEPTION_MESSAGE = RegistrationException.class.toString();

    private static final String VALID_LOGIN_FIRST = "firstDefaultLogin";
    private static final String VALID_LOGIN_SECOND = "secondDefaultLogin";
    private static final String VALID_LOGIN_THIRD = "thirdDefaultLogin";

    private static final String VALID_PASSWORD_FIRST = "123456";
    private static final String VALID_PASSWORD_SECOND = "LOOOOOOOOOOOOONG";
    private static final String VALID_PASSWORD_THIRD = "qwe123ZXC$%^";

    private static final int VALID_AGE_FIRST = 18;
    private static final int VALID_AGE_SECOND = 19;
    private static final int VALID_AGE_THIRD = 91;

    private static RegistrationServiceImpl registrationService;
    private static List<User> validUserData;
    private static StorageDaoImpl storageDaoImpl;

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
        validUserBob.setLogin(VALID_LOGIN_FIRST);
        validUserBob.setPassword(VALID_PASSWORD_FIRST);
        validUserBob.setAge(VALID_AGE_FIRST);

        validUserSecondBob = new User();
        validUserSecondBob.setLogin(validUserBob.getLogin());
        validUserSecondBob.setPassword(validUserBob.getPassword());
        validUserSecondBob.setAge(validUserBob.getAge());

        validUserAlice = new User();
        validUserAlice.setLogin(VALID_LOGIN_SECOND);
        validUserAlice.setPassword(VALID_PASSWORD_SECOND);
        validUserAlice.setAge(VALID_AGE_SECOND);

        validUserJohn = new User();
        validUserJohn.setLogin(VALID_LOGIN_THIRD);
        validUserJohn.setPassword(VALID_PASSWORD_THIRD);
        validUserJohn.setAge(VALID_AGE_THIRD);

        validUserData = new ArrayList<>();
        validUserData.add(validUserBob);
        validUserData.add(validUserAlice);
        validUserData.add(validUserJohn);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_checkPrivateFields_Ok() {
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
    void register_ValidUsers_Ok() throws RegistrationException {
        for (User user : validUserData) {
            registrationService.register(user);
        }

        Assertions.assertEquals(validUserData.size(), Storage.people.size(),
                "Test failed! The size of the storage isn't correct. Expected "
                        + validUserData.size() + " but was " + Storage.people.size() + '\n');
        Assertions.assertTrue(Storage.people.contains(validUserBob),
                "Test failed! The storage must contain a user with login: "
                        + validUserBob.getLogin() + '\n');
        Assertions.assertTrue(Storage.people.contains(validUserAlice),
                "Test failed! The storage must contain a user with login: "
                        + validUserAlice.getLogin() + '\n');
        Assertions.assertTrue(Storage.people.contains(validUserJohn),
                "Test failed! The storage must contain a user with login: "
                        + validUserJohn.getLogin() + '\n');
    }

    @Test
    void register_AndGetValidUsers_Ok() throws RegistrationException {
        for (User user : validUserData) {
            registrationService.register(user);
        }
        Assertions.assertEquals(validUserData.size(), Storage.people.size(),
                "The size of the storage isn't correct. Expected "
                        + validUserData.size() + " but was " + Storage.people.size());

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
    void register_NullUser_NotOk() {
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(null),
                String.format("%S should be thrown for: User == null" + '\n', EXCEPTION_MESSAGE));
    }

    @Test
    void register_SomeUser_NotOk() throws RegistrationException {
        registrationService.register(validUserBob);
        registrationService.register(validUserAlice);
        int expectedStorageSize = 2;
        Assertions.assertEquals(expectedStorageSize, Storage.people.size(),
                "Test failed! The size isn't correct. Expected "
                        + expectedStorageSize + " but was " + Storage.people.size() + '\n');

        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(validUserBob),
                String.format("%S should be thrown for: already existing login: "
                        + validUserBob.getLogin() + '\n', EXCEPTION_MESSAGE));
    }

    @Test
    public void register_NullLogin_NotOk() {
        validUserBob.setLogin(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(validUserBob),
                String.format("%S should be thrown for: login == null" + '\n', EXCEPTION_MESSAGE));
    }

    @Test
    void register_EmptyLogin_NotOk() {
        validUserBob.setLogin("");
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(validUserBob),
                String.format("%S should be thrown for: login is empty" + '\n', EXCEPTION_MESSAGE));
    }

    @Test
    void register_InvalidPasswordLength_NotOk() {
        validUserBob.setPassword("12345");
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(validUserBob),
                String.format("%S should be thrown for: not enough length for password: "
                        + validUserBob.getPassword() + '\n', EXCEPTION_MESSAGE));
    }

    @Test
    void register_NullPassword_NotOk() {
        validUserBob.setPassword(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(validUserBob),
                String.format("%S should be thrown for: password == null"
                        + '\n', EXCEPTION_MESSAGE));
    }

    @Test
    void register_NullAge_NotOk() {
        validUserBob.setAge(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(validUserBob),
                String.format("%S should be thrown for: age == null" + '\n', EXCEPTION_MESSAGE));
    }

    @Test
    void register_InvalidAge_NotOk() {
        validUserBob.setAge(17);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(validUserBob),
                String.format("%S should be thrown for: user's age < 18!"
                        + '\n', EXCEPTION_MESSAGE));

        validUserBob.setAge(-19);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(validUserBob),
                String.format("%S should be thrown for: user's age < 18!"
                        + '\n', EXCEPTION_MESSAGE));
    }

    @Test
    void getNull_Ok() throws RegistrationException {
        for (User user : validUserData) {
            registrationService.register(user);
        }
        Assertions.assertNull(storageDaoImpl.get(null),
                "Test failed! The method must return null" + '\n');
    }

    @Test
    void getByNonExistUser() {
        Assertions.assertNull(storageDaoImpl.get(validUserAlice.getLogin()),
                "Test failed! The method should have returned null with a non-existent login. "
                        + validUserAlice.getLogin() + '\n');
    }

    @Test
    void changeIndexAfterAdd_Ok() throws RegistrationException {
        registrationService.register(validUserBob);
        long previousIndex = validUserBob.getId();
        registrationService.register(validUserAlice);
        long actualIndex = validUserAlice.getId();
        Assertions.assertNotEquals(previousIndex, actualIndex,
                "Test failed! The index must increment when user registrations" + '\n');
    }

    @Test
    void usersIsEquals_Ok() {
        Assertions.assertEquals(validUserBob, validUserSecondBob,
                "Test failed! The equals() method doesn't work correctly" + '\n');
    }

    @Test
    void userNotEqualsWithNull_Ok() {
        Assertions.assertNotEquals(validUserBob, null,
                "Test failed! User and null can't be equals" + '\n');
    }

    @Test
    void usersHashCodeIsEquals_Ok() {
        Assertions.assertEquals(validUserBob.hashCode(),
                validUserSecondBob.hashCode(),
                "Test failed! The hashCode() method doesn't work correctly" + '\n');
    }
}
