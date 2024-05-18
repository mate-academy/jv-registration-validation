package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import java.util.Random;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private static final int NUMBER_OF_USERS = 10;
    private static final int FIRST_USER_ID = 1;
    private static final int SECOND_USER_ID = 2;

    private static Random random;
    private static StorageDao storageDao;
    private static User userTest;
    private static RegistrationService registrationService;

    @BeforeAll
    public static void setUp() {
        random = new Random();
        userTest = new User();
        userTest.setAge(MIN_AGE);
        userTest.setId(30L);
        userTest.setLogin("CoolGuy");
        userTest.setPassword("Qwerty123_4");
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    /* Tests in class StorageDaoImpl */

    @Test
    public void test_addUser_ok() {
        User addedUser = storageDao.add(userTest);
        assertEquals(userTest, addedUser, "Added user is not equal to actual user");

        for (int i = 0; i < NUMBER_OF_USERS; i++) {
            User user = new User();
            user.setId(random.nextLong());
            user.setAge(random.nextInt(MAX_AGE));
            user.setLogin("MyLogin-" + i);
            user.setPassword("Password_" + i);
            User eachAddedUser = storageDao.add(user);

            assertEquals(eachAddedUser, user, "Not all users were added to the storage");
        }
    }

    @Test
    public void test_storageUser_containsUser() {
        User retrievedUser = storageDao.get("CoolGuy");
        assertEquals(userTest, retrievedUser, "Storage does not contain user");
    }

    /* Tests in class User */

    @Test
    public void test_equals_sameUser_ok() {
        User user1 = new User();
        user1.setLogin("user1");
        user1.setPassword("password");
        user1.setAge(MIN_AGE);

        User user2 = new User();
        user2.setLogin("user1");
        user2.setPassword("password");
        user2.setAge(MIN_AGE);

        assertEquals(user1, user2);
    }

    @Test
    public void test_equals_differentUsers_notOk() {
        User user1 = new User();
        user1.setLogin("user1");
        user1.setPassword("password");
        user1.setAge(MIN_AGE);

        User user2 = new User();
        user2.setLogin("user2");
        user2.setPassword("password");
        user2.setAge(MIN_AGE);

        assertNotEquals(user1, user2);
    }

    @Test
    public void test_hashCode_equalUsers_equalHashCodes() {
        User user1 = new User();
        user1.setLogin("user1");
        user1.setPassword("password");
        user1.setAge(MIN_AGE);

        User user2 = new User();
        user2.setLogin("user1");
        user2.setPassword("password");
        user2.setAge(MIN_AGE);

        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void test_hashCode_differentUsers_differentHashCodes() {
        User user1 = new User();
        user1.setLogin("user1");
        user1.setPassword("password");
        user1.setAge(MIN_AGE);

        User user2 = new User();
        user2.setLogin("user2");
        user2.setPassword("password");
        user2.setAge(MIN_AGE);

        assertNotEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void test_gettersAndSetters_workOk() {
        User user = new User();
        user.setLogin("user1");
        user.setPassword("password");
        user.setAge(MIN_AGE);
        user.setId(1L);

        assertEquals("user1", user.getLogin());
        assertEquals("password", user.getPassword());
        assertEquals(MIN_AGE, user.getAge());
        assertEquals(FIRST_USER_ID, user.getId());

        user.setLogin("newUser");
        user.setPassword("newPassword");
        user.setAge(MAX_AGE);
        user.setId(2L);

        assertEquals("newUser", user.getLogin());
        assertEquals("newPassword", user.getPassword());
        assertEquals(MAX_AGE, user.getAge());
        assertEquals(SECOND_USER_ID, user.getId());
    }

    /* Tests in class RegistrationServiceImpl */

    @Test
    public void register_existingUser_throwsException() {
        User user1 = new User();
        user1.setLogin("Recruit");
        user1.setPassword("password");
        user1.setAge(MIN_AGE);
        registrationService.register(user1);

        User user2 = new User();
        user2.setLogin("Recruit");
        user2.setPassword("KingBoss");
        user2.setAge(MIN_AGE);

        assertThrows(InvalidDataException.class, () -> registrationService.register(user2));
    }

    @Test
    public void register_loginLength_lessThan6_throwsException() {
        User user = new User();
        user.setLogin("PON");
        user.setPassword("MyCode");
        user.setAge(MIN_AGE);

        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_loginLength_6_isOK() {
        User user = new User();
        user.setLogin("Peters");
        user.setPassword("passCodes");
        user.setAge(MIN_AGE);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    public void register_loginLength_moreThan6_isOK() {
        User user = new User();
        user.setLogin("NancyHale");
        user.setPassword("passwords-1");
        user.setAge(MIN_AGE);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    public void register_passwordLength_lessThan6_throwsException() {
        User user = new User();
        user.setLogin("JonnyHale");
        user.setPassword("pas");
        user.setAge(MIN_AGE);

        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_passwordLength_6_isOk() {
        User user = new User();
        user.setLogin("AlexHale");
        user.setPassword("paSSco");
        user.setAge(MIN_AGE);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    public void register_passwordLength_moreThan6_isOk() {
        User user = new User();
        user.setLogin("ScottHale");
        user.setPassword("pass_code");
        user.setAge(MIN_AGE);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    public void register_age_lessThan18_throwsException() {
        User user = new User();
        user.setLogin("TaliaHale");
        user.setPassword("password");
        user.setAge(MIN_AGE - 1);

        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_age_18_isOk() {
        User user = new User();
        user.setLogin("DerekHale");
        user.setPassword("password-10");
        user.setAge(18);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    public void register_age_over18_ok() {
        User user = new User();
        user.setLogin("PeterHale");
        user.setPassword("password-1");
        user.setAge(MIN_AGE + 1);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    public void register_age_negative_notOk() {
        User user = new User();
        user.setLogin("PeterHale");
        user.setPassword("password-1");
        user.setAge(-MIN_AGE);

        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_loginIsNull_throwsException() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("password");
        user.setAge(MIN_AGE);

        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_passwordIsNull_throwsException() {
        User user = new User();
        user.setLogin("LexCorp");
        user.setPassword(null);
        user.setAge(MIN_AGE);

        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_ageIsNull_throwsException() {
        User user = new User();
        user.setLogin("LexCorp");
        user.setPassword("Lex123");
        user.setAge(null);

        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_validUser_registrationSuccessful() {
        User user = new User();
        user.setLogin("user123");
        user.setPassword("password");
        user.setAge(MIN_AGE);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser,
                "Registration failed, returned user is null");
        assertEquals(user.getLogin(), registeredUser.getLogin(),
                "Login mismatch after registration");
        assertEquals(user.getPassword(), registeredUser.getPassword(),
                "Password mismatch after registration");
        assertEquals(user.getAge(), registeredUser.getAge(),
                "Age mismatch after registration");
    }
}
