package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static RegistrationService service;
    private static List<User> usersList;
    private User alice;
    private User bob;
    private User david;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
        usersList = new ArrayList<>();
    }

    @BeforeEach
    void setUp() {
        alice = new User("user230j", "de^wdki", 18);
        bob = new User("user22d0j", "de^wdkHBIUi", 19);
        david = new User("userDavid", "dewiuydki", 21);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
        usersList.clear();
    }

    @Test
    void register_validUserCase_Ok() {
        User actualAlice = service.register(alice);
        User actualBob = service.register(bob);
        User actualDavid = service.register(david);
        assertEquals(alice, actualAlice,
                "actual should be " + alice + " , but was: " + actualAlice);
        assertEquals(bob, actualBob,
                "actual should be " + bob + " , but was: " + actualBob);
        assertEquals(david, actualDavid,
                "actual should be " + david + " , but was: " + actualDavid);
        int actualSize = Storage.people.size();
        assertEquals(3, actualSize, "Expected size should be "
                + 3 + " but was: " + actualSize);
    }

    @Test
    void register_twoUsersWithTheSameLogin_notOk() {
        service.register(alice);
        service.register(bob);
        User userWithDuplicatedLogin = new User(bob.getLogin(), "dUI789*i", 18);
        assertThrows(RuntimeException.class,() -> service.register(userWithDuplicatedLogin),
                "Register should have thrown RuntimeException"
                        + " if the user with this login already exists");
        int actualSize = Storage.people.size();
        assertEquals(2, actualSize, "Expected size should be "
                + 2 + " but was: " + actualSize);
    }

    @Test
    void register_userLessThanMinAge_notOk() {
        service.register(bob);
        service.register(david);
        User userWithAgeLessThanMinAge = new User("user30ewj", "&*giuKdki", 16);
        assertThrows(RuntimeException.class, () -> service.register(userWithAgeLessThanMinAge),
                "Register should have thrown RuntimeException if users age is less than min age");
        int actualSize = Storage.people.size();
        assertEquals(2, actualSize, "Expected size should be "
                + 2 + " but was: " + actualSize);
    }

    @Test
    void register_userWithNotLongEnoughPassword_notOk() {
        User userWithSmallPassword = new User("userVikaj", "Kdki", 34);
        service.register(bob);
        assertThrows(RuntimeException.class, () -> service.register(userWithSmallPassword),
                "Register should have thrown RuntimeException"
                        + " if user's password is not long enough");
        int actualSize = Storage.people.size();
        assertEquals(1, actualSize, "Expected size should be "
                + 1 + " but was: " + actualSize);
    }

    @Test
    void register_userWithAllNullInputs_notOk() {
        User userWithAllFieldsNull = new User();
        assertThrows(RuntimeException.class, () -> service.register(userWithAllFieldsNull),
                "Register should have thrown RuntimeException if one of the inputs is null");
    }

    @Test
    void register_nullUser_notOk() {
        User nullUser = null;
        assertThrows(RuntimeException.class, () -> service.register(nullUser),
                "Register should have thrown RuntimeException if user is null");
    }

    @Test
    void register_userWithNullAge_notOk() {
        User userWithNullAge = new User("user332k", "wdwdw8uiK", null);
        assertThrows(RuntimeException.class, () -> service.register(userWithNullAge),
                "Register should have thrown RuntimeException if age is null");
    }

    @Test
    void register_userWithNullPassword_notOk() {
        User userWithNullPassword = new User("usr32k", null, 32);
        assertThrows(RuntimeException.class, () -> service.register(userWithNullPassword),
                "Register should have thrown RuntimeException if password is null");
    }

    @Test
    void register_userWithNullLogin_notOk() {
        User userWithNullLogin = new User(null, "wwwUw^R&82", 28);
        assertThrows(RuntimeException.class, () -> service.register(userWithNullLogin),
                "Register should have thrown RuntimeException if login is null");
    }

    @Test
    void register_userWithAgeMoreThanMaxAge_notOk() {
        User userWithAgeMoreThanMaxAge = new User("userQ", "dcesiYhgv6", 1221);
        assertThrows(RuntimeException.class, () -> service.register(userWithAgeMoreThanMaxAge),
                "Register should have thrown RuntimeException if age more than 100");
    }
}
