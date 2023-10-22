package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeAll
    public static void setupUsers() {
        Storage.people.add(new User());
        Storage.people.get(0).setLogin("administrator");
    }

    @Test
    public void register_validUser_returnsSameUser() {
        User user = new User();
        user.setLogin("0".repeat(RegistrationServiceImpl.MIN_LOGIN_LENGTH));
        user.setPassword("p".repeat(RegistrationServiceImpl.MIN_PASSWORD_LENGTH));
        user.setAge(RegistrationServiceImpl.MIN_AGE);
        int initialStorageSize = Storage.people.size();
        User addedUser = registrationService.register(user);
        assertSame(user,
                addedUser,
                "Method should return the same User");
        assertEquals(Storage.people.size(),
                initialStorageSize + 1,
                "Method should add the User to the Storage");
    }

    @Test
    public void register_invalidUser_throwsException() {
        User user = new User();
        user.setLogin("1".repeat(RegistrationServiceImpl.MIN_LOGIN_LENGTH - 1));
        user.setPassword("p".repeat(RegistrationServiceImpl.MIN_PASSWORD_LENGTH - 1));
        user.setAge(RegistrationServiceImpl.MIN_AGE - 1);
        int initialStorageSize = Storage.people.size();
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "Method should throw an Exception");
        assertEquals(Storage.people.size(),
                initialStorageSize,
                "Method should not add the User to the Storage");
    }

    @Test
    public void register_nullLoginUser_throwsException() {
        User user = new User();
        user.setLogin(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "Method should throw an Exception");
    }

    @Test
    public void register_invalidLoginUser_throwsException() {
        User user1 = new User();
        user1.setLogin("");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user1),
                "Method should throw an Exception");
        User user2 = new User();
        user2.setLogin("l".repeat(RegistrationServiceImpl.MIN_LOGIN_LENGTH - 1));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user2),
                "Method should throw an Exception");
    }

    @Test
    public void register_nullPasswordUser_throwsException() {
        User user = new User();
        user.setLogin("l".repeat(RegistrationServiceImpl.MIN_LOGIN_LENGTH));
        user.setPassword(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "Method should throw an Exception");
    }

    @Test
    public void register_invalidPasswordUser_throwsException() {
        User user1 = new User();
        user1.setLogin("l".repeat(RegistrationServiceImpl.MIN_LOGIN_LENGTH));
        user1.setPassword("");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user1),
                "Method should throw an Exception");
        User user2 = new User();
        user2.setLogin("l".repeat(RegistrationServiceImpl.MIN_LOGIN_LENGTH));
        user2.setPassword("p".repeat(RegistrationServiceImpl.MIN_PASSWORD_LENGTH - 1));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user2),
                "Method should throw an Exception");
    }

    @Test
    public void register_nullAgeUser_throwsException() {
        User user = new User();
        user.setLogin("l".repeat(RegistrationServiceImpl.MIN_LOGIN_LENGTH));
        user.setPassword("p".repeat(RegistrationServiceImpl.MIN_PASSWORD_LENGTH));
        user.setAge(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "Method should throw an Exception");
    }

    @Test
    public void register_invalidAgeUser_throwsException() {
        User user1 = new User();
        user1.setLogin("l".repeat(RegistrationServiceImpl.MIN_LOGIN_LENGTH));
        user1.setPassword("p".repeat(RegistrationServiceImpl.MIN_PASSWORD_LENGTH));
        user1.setAge(0);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user1),
                "Method should throw an Exception");
        User user2 = new User();
        user2.setLogin("l".repeat(RegistrationServiceImpl.MIN_LOGIN_LENGTH));
        user2.setPassword("p".repeat(RegistrationServiceImpl.MIN_PASSWORD_LENGTH));
        user2.setAge(RegistrationServiceImpl.MIN_AGE - 1);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user2),
                "Method should throw an Exception");
    }

    @Test
    public void register_nonUniqueLoginUser_throwsException() {
        User user = new User();
        user.setLogin("administrator");
        user.setPassword("i_am_the_law");
        user.setAge(42);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "Method should throw an Exception");
    }

    @Test
    public void register_nullUser_returnsNull() {
        User user = null;
        int initialStorageSize = Storage.people.size();
        User addedUser = registrationService.register(user);
        assertNull(addedUser, "Method should return a null");
        assertEquals(Storage.people.size(),
                initialStorageSize,
                "Method should not add null to the Storage");
    }
}
