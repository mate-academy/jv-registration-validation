package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import core.basesyntax.service.UserHasNoLoginException;
import core.basesyntax.service.UserNullException;
import core.basesyntax.service.UserPasswordNullException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HelloWorldTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void initial() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @BeforeEach
     void createValidUser() {
        user = new User();
        user.setAge(42);
        user.setLogin("labudabudapta");
        user.setPassword("motorama");
    }

    @Test
    void register_addSomeUsersWithUniqueLoginsToDatabase_ok() {
        User user2 = new User();
        user2.setLogin("Petro");
        user2.setPassword("Petro1990");
        user2.setAge(32);
        User user3 = new User();
        user3.setLogin("Taras");
        user3.setPassword("Taras1998");
        user3.setAge(24);
        registrationService.register(user);
        registrationService.register(user2);
        registrationService.register(user3);
        assertEquals(3, Storage.people.size(),
                "After adding three users to database, the size must be 3"
                + ", but actual size is " + Storage.people.size());
    }

    @Test
    void register_addTwoUsersWithTheSamePasswordsAndDifferentLogins_ok() {
        User user2 = new User();
        user2.setLogin("Alex");
        user2.setPassword(user.getPassword());
        user2.setAge(35);
        registrationService.register(user);
        registrationService.register(user2);
        assertEquals(2, Storage.people.size(),
                "After adding two users with the same passwords but different "
                        + "logins to database, the size must be 2, but actual size is "
                        + Storage.people.size());
    }

    @Test
    void addUserWhichLoginIncludesDigits_ok() {
        user.setLogin("John2005");
        User user2 = new User();
        user2.setLogin("92Alexander08");
        user2.setPassword("jupiter");
        user2.setAge(30);
        registrationService.register(user);
        registrationService.register(user2);
        assertEquals(2, Storage.people.size(),
                "After adding two users which logins include digits to database "
                        + "the size must be 2, but actual size is "
                        + Storage.people.size());;
    }

    @Test
    void register_nullUser_notOk() {
        User userNull = null;
        assertThrows(UserNullException.class,
                () -> registrationService.register(userNull),
                "user is null\n");
    }

    @Test
    void register_userAgeLessThan18_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user),
                "condition break: user with age up to 18 was added\n");
    }

    @Test
    void register_negativeUserAge_notOk() {
        user.setAge(-1);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user),
                "condition break: user's age can't be negative\n");
    }

    @Test
    void register_userPasswordLessThanSixCharacters_notOk() {
        user.setPassword("Robin");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user),
                "too short password\n");
    }

    @Test
    void register_nullUserLogin_notOk() {
        user.setLogin(null);
        assertThrows(UserHasNoLoginException.class,
                () -> registrationService.register(user),
                "login might not be null\n");
    }

    @Test
    void register_invalidSymbolsInUserLogin_notOk() {
        user.setLogin("caramba#98");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user),
                "login shouldn't have any special symbol\n");
    }

    @Test
    void register_userWithDuplicateLoginMayBeAddToDatabase_notOk() {
        assertEquals(user, registrationService.register(user),
                "method register had to add user to the storage, but it didn't\n");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user),
                "you added user to the storage with duplicate login\n");
    }

    @Test
    void register_nullUserPassword_notOk() {
        user.setPassword(null);
        assertThrows(UserPasswordNullException.class,
                () -> registrationService.register(user),
                "User's password might not be null\n");
    }

    @Test
    void register_invalidSymbolsInPassword_notOk() {
        user.setPassword("$$$spirit$$$");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user),
                "Password shouldn't have any special symbol\n");
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }
}
