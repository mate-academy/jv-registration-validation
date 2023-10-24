package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final RegistrationService registrationService = new RegistrationServiceImpl();
    private static final User existingUser = new User();
    private static final User user = new User();

    @BeforeAll
    public static void setupUsers() {
        existingUser.setLogin("administrator");
        Storage.people.add(existingUser);
    }

    @AfterAll
    public static void teardown() {
        Storage.people.clear();
    }

    @Test
    public void register_validUser_ok() {
        user.setLogin("l".repeat(RegistrationServiceImpl.MIN_LOGIN_LENGTH));
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
    public void register_invalidUser_notOk() {
        user.setLogin("l".repeat(RegistrationServiceImpl.MIN_LOGIN_LENGTH - 1));
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
    public void register_nullLogin_notOk() {
        user.setLogin(null);
        user.setPassword("p".repeat(RegistrationServiceImpl.MIN_PASSWORD_LENGTH));
        user.setAge(RegistrationServiceImpl.MIN_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "Method should throw an Exception");
    }

    @Test
    public void register_invalidLogin_notOk() {
        user.setLogin("");
        user.setPassword("p".repeat(RegistrationServiceImpl.MIN_PASSWORD_LENGTH));
        user.setAge(RegistrationServiceImpl.MIN_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "Method should throw an Exception");
        user.setLogin("l".repeat(RegistrationServiceImpl.MIN_LOGIN_LENGTH - 1));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "Method should throw an Exception");
    }

    @Test
    public void register_nullPassword_notOk() {
        user.setLogin("l".repeat(RegistrationServiceImpl.MIN_LOGIN_LENGTH));
        user.setPassword(null);
        user.setAge(RegistrationServiceImpl.MIN_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "Method should throw an Exception");
    }

    @Test
    public void register_invalidPassword_notOk() {
        user.setLogin("l".repeat(RegistrationServiceImpl.MIN_LOGIN_LENGTH));
        user.setPassword("");
        user.setAge(RegistrationServiceImpl.MIN_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "Method should throw an Exception");
        user.setPassword("p".repeat(RegistrationServiceImpl.MIN_PASSWORD_LENGTH - 1));
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "Method should throw an Exception");
    }

    @Test
    public void register_nullAge_notOk() {
        user.setLogin("l".repeat(RegistrationServiceImpl.MIN_LOGIN_LENGTH));
        user.setPassword("p".repeat(RegistrationServiceImpl.MIN_PASSWORD_LENGTH));
        user.setAge(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "Method should throw an Exception");
    }

    @Test
    public void register_invalidAge_notOk() {
        user.setLogin("l".repeat(RegistrationServiceImpl.MIN_LOGIN_LENGTH));
        user.setPassword("p".repeat(RegistrationServiceImpl.MIN_PASSWORD_LENGTH));
        user.setAge(0);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "Method should throw an Exception");
        user.setAge(RegistrationServiceImpl.MIN_AGE - 1);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "Method should throw an Exception");
    }

    @Test
    public void register_nonUniqueLogin_notOk() {
        user.setLogin(existingUser.getLogin());
        user.setPassword("p".repeat(RegistrationServiceImpl.MIN_PASSWORD_LENGTH));
        user.setAge(RegistrationServiceImpl.MIN_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "Method should throw an Exception");
    }

    @Test
    public void register_null_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(null),
                "Method should not add null to the Storage");
    }
}
