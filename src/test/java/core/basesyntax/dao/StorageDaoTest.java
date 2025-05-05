package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.User;
import java.util.Random;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageDaoTest {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private static final int NUMBER_OF_USERS = 10;

    private static Random random;
    private static StorageDao storageDao;
    private static User userTest;

    @BeforeAll
    public static void setUp() {
        random = new Random();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    public void createUserTest() {
        userTest = new User("CoolGuy", "Qwerty123_4", MIN_AGE);
    }

    @Test
    public void test_addUser_successful() {
        User addedUser = storageDao.add(userTest);
        assertEquals(userTest, addedUser, "Added user is not equal to actual user");

        for (int i = 0; i < NUMBER_OF_USERS; i++) {
            User user = new User("MyLogin-" + i, "Password_" + i, random.nextInt(MAX_AGE));
            User eachAddedUser = storageDao.add(user);

            assertEquals(eachAddedUser, user, "Not all users were added to the storage");
        }
    }

    @Test
    public void test_storageUser_containsUser() {
        User testUser = userTest;

        User retrievedUser = storageDao.get("CoolGuy");
        assertEquals(testUser, retrievedUser, "Storage does not contain user");
    }
}
