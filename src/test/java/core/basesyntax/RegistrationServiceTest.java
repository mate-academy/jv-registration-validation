package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class RegistrationServiceTest {
    private static RegistrationService service;
    private static StorageDao storageDao;
    private User alice;
    private User bob;
    private User david;
    private User userWithDuplicatedLogin;
    private User userWithAgeUnder18;
    private User userWithSmallPassword;
    private User userWithNullAge;
    private User userWithNullLogin;
    private User userWithNullPassword;
    private User userWithAllFieldsNull;
    private User nullUser;
    private User userWithAgeMoreThan100;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        alice = new User("user230j", "de^wdki", 18);
        bob = new User("user22d0j", "de^wdkHBIUi", 19);
        david = new User("userDavid", "dewiuydki", 21);
    }

    @Test
    void register_validUserCase_Ok() {
        service.register(alice);
        service.register(bob);
        service.register(david);

        int actualSize = Storage.people.size();
        assertEquals(3, actualSize, "Expected size should be 3, but was: " + actualSize);
        User actual = storageDao.get(alice.getLogin());
        assertEquals(alice, actual, "actual should be " + alice + " , but was: " + actual);
        actual = storageDao.get(bob.getLogin());
        assertEquals(bob, actual, "actual should be " + bob + " , but was: " + actual);
        actual = storageDao.get(david.getLogin());
        assertEquals(david, actual, "actual should be " + david + " , but was: " + actual);
        Storage.people.clear();
    }

    @Test
    void register_twoUsersWithTheSameLogin_notOk() {
        userWithDuplicatedLogin = new User("user22d0j", "dUI789*i", 18);
        service.register(alice);
        service.register(bob);

        assertThrows(RuntimeException.class,() -> service.register(userWithDuplicatedLogin),
                "Register should have thrown RuntimeException"
                        + " if the user with this login already exists");
        int actualSize = Storage.people.size();
        assertEquals(2, actualSize, "Expected size should be 2, but was: " + actualSize);
        Storage.people.clear();
    }

    @Test
    void register_userLessThan18YearsOld_notOk() {
        userWithAgeUnder18 = new User("user30ewj", "&*giuKdki", 16);
        service.register(bob);
        service.register(david);

        assertThrows(RuntimeException.class, () -> service.register(userWithAgeUnder18),
                "Register should have thrown RuntimeException if users age is under 18");
        int actualSize = Storage.people.size();
        assertEquals(2, actualSize, "Expected size should be 2, but was: " + actualSize);
        Storage.people.clear();
    }

    @Test
    void register_userWithPasswordLessThan6Characters_notOk() {
        userWithSmallPassword = new User("userVikaj", "Kdki", 34);
        service.register(bob);

        assertThrows(RuntimeException.class, () -> service.register(userWithSmallPassword),
                "Register should have thrown RuntimeException"
                        + " if users password is less than 6 characters");
        int actualSize = Storage.people.size();
        assertEquals(1, actualSize, "Expected size should be 1, but was: " + actualSize);
        Storage.people.clear();
    }

    @Test
    void register_userWithNullInputs_notOk() {
        userWithNullAge = new User("user332k", "wdwdw8uiK", null);
        userWithNullPassword = new User("usr32k", null, 32);
        userWithNullLogin = new User(null, "wwwUw^R&82", 28);
        userWithAllFieldsNull = new User();
        nullUser = null;

        assertThrows(RuntimeException.class, () -> service.register(userWithNullLogin),
                "Register should have thrown RuntimeException if one of th inputs is null");
        assertThrows(RuntimeException.class, () -> service.register(userWithNullAge),
                "Register should have thrown RuntimeException if one of th inputs is null");
        assertThrows(RuntimeException.class, () -> service.register(userWithNullPassword),
                "Register should have thrown RuntimeException if one of th inputs is null");
        assertThrows(RuntimeException.class, () -> service.register(nullUser),
                "Register should have thrown RuntimeException if one of th inputs is null");
        assertThrows(RuntimeException.class, () -> service.register(userWithAllFieldsNull),
                "Register should have thrown RuntimeException if one of th inputs is null");
        int actualSize = Storage.people.size();
        assertEquals(0, actualSize, "Expected size should be 0, but was: " + actualSize);
        Storage.people.clear();
    }

    @Test
    void register_userWithAgeMoreThan100_notOk() {
        userWithAgeMoreThan100 = new User("userQ", "dcesiYhgv6", 121);

        assertThrows(RuntimeException.class, () -> service.register(userWithAgeMoreThan100),
                "Register should have thrown RuntimeException if age more than 100");
        int actualSize = Storage.people.size();
        assertEquals(0, actualSize, "Expected size should be 0, but was: " + actualSize);
        Storage.people.clear();

    }

    @Test
    void register_plantyOfUsers_Ok() {
        StringBuilder login = new StringBuilder("user");
        StringBuilder password = new StringBuilder("password");
        int firstLoginLength = login.length();
        int firstPasswordLength = password.length();
        int age = 18;
        for (int i = 0; i < 10000; i++) {
            login.append(i);
            password.append(i);
            service.register(new User(login.toString(), password.toString(), age));
            login.delete(firstLoginLength, login.length());
            password.delete(firstPasswordLength, password.length());
            if (age == 50) {
                age = 18;
            }
            age++;
        }

        int actualSize = Storage.people.size();
        assertEquals(10000, actualSize, "Expected size should be "
                + 10000 + " , but was: " + actualSize);
        Storage.people.clear();

    }
}
