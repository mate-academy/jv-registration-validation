package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.UserValidationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private int size;
    private final List<User> storage = Storage.people;
    private final User user1 = new User();
    private final User user2 = new User();
    private final User user3 = new User();

    @BeforeAll
    public static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void beforeTest() {
        user1.setLogin("Alex-25@gmail.com");
        user1.setPassword("15fg7t85");
        user1.setAge(25);
        user2.setLogin("smail_1982@ukr.net");
        user2.setPassword("15fg_7t85?7frt265c1%$5dvm69zas");
        user2.setAge(60);
        user3.setLogin("tornado.4avo@hotmail.com");
        user3.setPassword("1*5fg..7@t");
        user3.setAge(18);
    }

    @Test
    public void registerUser_nullUser_notOk() {
        assertEquals(0, size = storage.size(),"Test failed! Size of Storage should be "
                + 0 + " but it is " + size);
        assertThrows(UserValidationException.class, () -> registrationService.register(null));
        assertEquals(0, size = storage.size(),"Test failed! Size of Storage should be "
                + 0 + " but it is " + size);
    }

    @Test
    public void registerUser_nullLogin_notOk() {
        assertEquals(0, size = storage.size(),"Test failed! Size of Storage should be "
                + 0 + " but it is " + size);
        user1.setLogin(null);
        assertThrows(UserValidationException.class, () -> registrationService.register(user1));
        assertEquals(0, size = storage.size(),"Test failed! Size of Storage should be "
                + 0 + " but it is " + size);
    }

    @Test
    public void registerUser_groupedAssertions_uncorrectedUserLogin_notOk() {
        assertEquals(0, size = storage.size(),"Test failed! Size of Storage should be "
                + 0 + " but it is " + size);
        user1.setLogin("usernamedomain.com");
        user2.setLogin("user name@domain.com");
        user3.setLogin("username@domaincom");
        assertAll("Test failed! Login of User is incorrect",
                () -> assertThrows(UserValidationException.class,
                        () -> registrationService.register(user1)),
                () -> assertThrows(UserValidationException.class,
                        () -> registrationService.register(user2)),
                () -> assertThrows(UserValidationException.class,
                        () -> registrationService.register(user3))
        );
        assertEquals(0, size = storage.size(),"Test failed! Size of Storage should be "
                + 0 + " but it is " + size);
        user1.setLogin(".username@domain.com");
        user2.setLogin("username@domain.com.");
        user3.setLogin("username@domain..com");
        assertAll("Test failed! Login of User is incorrect",
                () -> assertThrows(UserValidationException.class,
                        () -> registrationService.register(user1)),
                () -> assertThrows(UserValidationException.class,
                        () -> registrationService.register(user2)),
                () -> assertThrows(UserValidationException.class,
                        () -> registrationService.register(user3))
        );
        assertEquals(0, size = storage.size(),"Test failed! Size of Storage should be "
                + 0 + " but it is " + size);
        user1.setLogin("usernameusernameusernameusernameusername"
                + "usernameusernameusername1234@domain.com");
        user2.setLogin("username@");
        user3.setLogin("t@m.c");
        assertAll("Test failed! Login of User is incorrect",
                () -> assertThrows(UserValidationException.class,
                        () -> registrationService.register(user1)),
                () -> assertThrows(UserValidationException.class,
                        () -> registrationService.register(user2)),
                () -> assertThrows(UserValidationException.class,
                        () -> registrationService.register(user3))
        );
        assertEquals(0, size = storage.size(),"Test failed! Size of Storage should be "
                + 0 + " but it is " + size);
    }

    @Test
    public void registerUser_correctedLogin_Ok() {
        assertEquals(0, size = storage.size(),"Test failed! Size of Storage should be "
                + 0 + " but it is " + size);
        user1.setLogin("user-N_a_m_e_@do-Main.com");
        User actual = registrationService.register(user1);
        assertEquals(user1, actual, "Test failed! Can't correctly add user1"
                + " to Storage. Login of User is incorrect.");
        assertEquals(1, size = storage.size(),"Test failed! Size of Storage should be "
                + 1 + " but it is " + size);
    }

    @Test
    public void registerUser_uncorrectedPassword_notOk() {
        assertEquals(0, size = storage.size(),"Test failed! Size of Storage should be "
                + 0 + " but it is " + size);
        user1.setPassword(null);
        assertThrows(UserValidationException.class, () -> registrationService.register(user1));
        user2.setPassword("e2^");
        assertThrows(UserValidationException.class, () -> registrationService.register(user2));
        user3.setPassword("f45t$");
        assertThrows(UserValidationException.class, () -> registrationService.register(user3));
        assertEquals(0, size = storage.size(),"Test failed! Size of Storage should be "
                + 0 + " but it is " + size);
    }

    @Test
    public void registerUser_correctedPassword_Ok() {
        assertEquals(0, size = storage.size(),"Test failed! Size of Storage should be "
                + 0 + " but it is " + size);
        user1.setPassword("123456");
        User actual = registrationService.register(user1);
        assertEquals(user1, actual,"Test failed! Can't correctly add user1"
                + " to Storage. Password of User is incorrect.");
        user2.setPassword("12345678");
        actual = registrationService.register(user2);
        assertEquals(user2, actual,"Test failed! Can't correctly add user2"
                + " to Storage. Password of User is incorrect.");
        user3.setPassword("f45t$1111111111111111111111111111111111111111111111111111111");
        actual = registrationService.register(user3);
        assertEquals(user3, actual,"Test failed! Can't correctly add user3"
                + " to Storage. Password of User is incorrect.");
        assertEquals(3, size = storage.size(),"Test failed! Size of Storage should be "
                + 3 + " but it is " + size);
    }

    @Test
    public void registerUser_uncorrectedAge_notOk() {
        assertEquals(0, size = storage.size(),"Test failed! Size of Storage should be "
                + 0 + " but it is " + size);
        user1.setAge(8);
        assertThrows(UserValidationException.class, () -> registrationService.register(user1));
        user2.setAge(null);
        assertThrows(UserValidationException.class, () -> registrationService.register(user2));
        user3.setAge(-3);
        assertThrows(UserValidationException.class, () -> registrationService.register(user3));
        assertEquals(0, size = storage.size(),"Test failed! Size of Storage should be "
                + 0 + " but it is " + size);
    }

    @Test
    public void checkIsNotEmpty_notOk() {
        registrationService.register(user1);
        registrationService.register(user2);
        registrationService.register(user3);
        assertFalse(Storage.people.isEmpty(), "Test failed! Storage shouldn't be empty");
    }

    @Test
    public void checkIsEmpty_Ok() {
        assertTrue(Storage.people.isEmpty(), "Test failed! Storage should be empty");
    }

    @Test
    public void checkingSizeInAddUser_Ok() {
        List<User> users = new ArrayList<>(Arrays.asList(user1, user2, user3));
        for (int i = 0; i < users.size(); i++) {
            registrationService.register(users.get(i));
            assertEquals(i + 1, size = storage.size(),"Test failed! Size of Storage should be "
                    + (i + 1) + " but it is " + size);
        }
    }

    @Test
    public void checkingAddUser_Ok() {
        List<User> users = new ArrayList<>(Arrays.asList(user1, user2, user3));
        for (User user : users) {
            registrationService.register(user);
        }
        for (int i = 0; i < users.size(); i++) {
            assertEquals(users.get(i), storage.get(i),"Test failed! Can't correctly add user"
                    + (i + 1) + " to Storage. Users isn`t equals.");
        }
    }

    @Test
    public void checkingAddIdenticalLogin_notOk() {
        assertEquals(0, size = storage.size(),"Test failed! Size of Storage should be "
                + 0 + " but it is " + size);
        storage.add(user1);
        assertTrue(storage.contains(user1));
        assertEquals(1, size = storage.size());
        user2.setLogin(user1.getLogin());
        User actual = registrationService.register(user2);
        assertNotEquals(user2, actual,"Test failed!"
                + " Users with identical login should not be added.");
        assertEquals(1, size = storage.size(),"Test failed! Size of Storage should be "
                + 1 + " but it is " + size);
    }

    @Test
    public void checkSetIdInAddUser_Ok() {
        StorageDao storageDao = new StorageDaoImpl();
        assertNull(user1.getId());
        storageDao.add(user1);
        assertNotNull(user1.getId(), "Test failed! Id should not be null "
                + "after added user to Storage.");
    }

    @AfterEach
    public void clear() {
        Storage.people.clear();
        size = 0;
    }
}
