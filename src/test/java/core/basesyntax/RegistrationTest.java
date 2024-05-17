package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import java.util.Random;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationTest {
    private static Random random;
    private static StorageDao storageDao;
    private static User userTest;
    private static RegistrationService registrationService;

    @BeforeAll
    public static void setUp() {
        random = new Random();
        userTest = new User();
        userTest.setAge(18);
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

        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(random.nextLong());
            user.setAge(random.nextInt(100));
            user.setLogin("MyLogin-" + i);
            user.setPassword("Password_" + i);
            storageDao.add(user);
        }
        assertEquals(11, Storage.people.size() - 1, "Not all users were added to the storage");
    }

    @Test
    public void test_storageUser_containsUser() {
        User retrievedUser = storageDao.get("CoolGuy");
        assertEquals(userTest, retrievedUser, "Storage does not contain user");
    }

    @Test
    public void test_storageUser_returnNull_isOk() {
        User retrievedUser = storageDao.get("Coco123");
        assertNull(retrievedUser, "Storage should return null to un-existing user");
    }

    /* Tests in class User */

    @Test
    public void test_equals_sameUser_ok() {
        User user1 = new User();
        user1.setLogin("user1");
        user1.setPassword("password");
        user1.setAge(20);

        User user2 = new User();
        user2.setLogin("user1");
        user2.setPassword("password");
        user2.setAge(20);

        assertEquals(user1, user2);
    }

    @Test
    public void test_equals_differentUsers_notOK() {
        User user1 = new User();
        user1.setLogin("user1");
        user1.setPassword("password");
        user1.setAge(20);

        User user2 = new User();
        user2.setLogin("user2");
        user2.setPassword("password");
        user2.setAge(25);

        assertNotEquals(user1, user2);
    }

    @Test
    public void test_hashCode_equalUsers_equalHashCodes() {
        User user1 = new User();
        user1.setLogin("user1");
        user1.setPassword("password");
        user1.setAge(20);

        User user2 = new User();
        user2.setLogin("user1");
        user2.setPassword("password");
        user2.setAge(20);

        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void test_hashCode_differentUsers_differentHashCodes() {
        User user1 = new User();
        user1.setLogin("user1");
        user1.setPassword("password");
        user1.setAge(20);

        User user2 = new User();
        user2.setLogin("user2");
        user2.setPassword("password");
        user2.setAge(25);

        assertNotEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void test_gettersAndSetters_workOk() {
        User user = new User();
        user.setLogin("user1");
        user.setPassword("password");
        user.setAge(20);
        user.setId(1L);

        assertEquals("user1", user.getLogin());
        assertEquals("password", user.getPassword());
        assertEquals(20, user.getAge());
        assertEquals(1, user.getId());

        user.setLogin("newUser");
        user.setPassword("newPassword");
        user.setAge(25);
        user.setId(2L);

        assertEquals("newUser", user.getLogin());
        assertEquals("newPassword", user.getPassword());
        assertEquals(25, user.getAge());
        assertEquals(2, user.getId());
    }

    /* Tests in class RegistrationServiceImpl */

    @Test
    public void test_checking_user_OnExisting() {
        User user1 = new User();
        user1.setLogin("Recruit");
        user1.setPassword("password");
        user1.setAge(20);
        registrationService.register(user1);

        User user2 = new User();
        user2.setLogin("Recruit");
        user1.setPassword("KingBoss");
        user1.setAge(31);

        assertThrows(InvalidDataException.class, () -> registrationService.register(user2));
    }

    @Test
    public void test_loginLength_less6_notOk() {
        User user = new User();
        user.setLogin("PON");
        user.setPassword("pon");
        user.setAge(20);

        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void test_passwordLength_less6_notOk() {
        User user = new User();
        user.setLogin("PON");
        user.setPassword("pon");
        user.setAge(20);

        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    public void test_age_less18_notOk() {
        User user = new User();
        user.setLogin("PON");
        user.setPassword("pon");
        user.setAge(9);

        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }
}
