package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceException;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;

    private final User user = new User();
    private final StorageDao storageDao = new StorageDaoImpl();
    private final RegistrationServiceImpl registrationService
            = new RegistrationServiceImpl(storageDao);

    @BeforeEach
    public void setUpUser() {
        Storage.people.clear();
        user.setLogin("admin555");
        user.setPassword("qwert12345");
        user.setAge(33);
    }

    @Test
    public void register_nullUser_notOk() {
        RegistrationServiceException ex = assertThrows(
                RegistrationServiceException.class,
                () -> registrationService.register(null)
        );
        assertEquals("User should not be null!", ex.getMessage());
    }

    @Test
    public void register_nullLogin_notOk() {
        user.setLogin(null);
        RegistrationServiceException ex = assertThrows(
                RegistrationServiceException.class,
                () -> registrationService.register(user)
        );
        assertEquals("Login must not be null!", ex.getMessage());
    }

    @Test
    public void register_shortLogin_notOk() {
        user.setLogin("admin");
        RegistrationServiceException ex = assertThrows(
                RegistrationServiceException.class,
                () -> registrationService.register(user)
        );
        assertEquals("Your login length should be at least "
                + MIN_LOGIN_LENGTH + " characters!", ex.getMessage());
    }

    @Test
    public void register_borderLogin_ok() {
        user.setLogin("admin1");
        boolean actual = registrationService.register(user).getLogin().length() == MIN_LOGIN_LENGTH;
        assertTrue(actual, "Login should at least " + MIN_LOGIN_LENGTH + " characters!");
    }

    @Test
    public void register_nullPassword_notOk() {
        user.setPassword(null);
        RegistrationServiceException ex = assertThrows(
                RegistrationServiceException.class,
                () -> registrationService.register(user)
        );
        assertEquals("Password must not be null!", ex.getMessage());
    }

    @Test
    public void register_shortPassword_notOk() {
        user.setPassword("admin");
        RegistrationServiceException ex = assertThrows(
                RegistrationServiceException.class,
                () -> registrationService.register(user)
        );
        assertEquals("Your password must not be less than "
                + MIN_PASSWORD_LENGTH + " characters!", ex.getMessage());
    }

    @Test
    public void register_borderPassword_ok() {
        user.setPassword("qwert1");
        boolean actual = registrationService.register(user)
                .getPassword().length() == MIN_PASSWORD_LENGTH;
        assertTrue(actual, "Your password must be at least "
                + MIN_PASSWORD_LENGTH + " characters long.");
    }

    @Test
    public void register_nullAge_notOk() {
        user.setAge(null);
        RegistrationServiceException ex = assertThrows(
                RegistrationServiceException.class,
                () -> registrationService.register(user)
        );
        assertEquals("Age must not be null!", ex.getMessage());
    }

    @Test
    public void register_underAge_notOk() {
        user.setAge(17);
        RegistrationServiceException ex = assertThrows(
                RegistrationServiceException.class,
                () -> registrationService.register(user)
        );
        assertEquals("Your age is lower than " + MIN_AGE + ".", ex.getMessage());
    }

    @Test
    public void register_borderAge_ok() {
        user.setAge(18);
        boolean actual = registrationService.register(user).getAge() == MIN_AGE;
        assertTrue(actual, "Your age is lower than " + MIN_AGE + ".");
    }

    @Test
    public void register_duplicateLogin_notOk() {
        User user1 = new User();
        user1.setLogin("admin1");
        user1.setPassword("qwert1234");
        user1.setAge(31);
        registrationService.register(user1);
        user.setLogin("admin1");
        RegistrationServiceException ex = assertThrows(
                RegistrationServiceException.class,
                () -> registrationService.register(user)
        );
        assertEquals("Your login " + user.getLogin() + " already exists.", ex.getMessage());
    }

    @Test
    public void register_validUser_ok() {
        User user1 = new User();
        user1.setLogin("admin1");
        user1.setPassword("qwert1234");
        user1.setAge(23);
        registrationService.register(user1);
        boolean actual = storageDao.get(user1.getLogin()).equals(user1);
        assertTrue(actual, "Your login has been successfully registered!");
    }

    @Test
    public void register_emptyPassword_NotOk() {
        user.setPassword("");
        RegistrationServiceException ex = assertThrows(
                RegistrationServiceException.class,
                () -> registrationService.register(user)
        );
        assertEquals("Your password must not be less than "
                + MIN_PASSWORD_LENGTH + " characters!", ex.getMessage());
    }

    @Test
    public void register_emptyLogin_NotOk() {
        user.setLogin("");
        RegistrationServiceException ex = assertThrows(
                RegistrationServiceException.class,
                () -> registrationService.register(user)
        );
        assertEquals("Your login length should be at least "
                + MIN_PASSWORD_LENGTH + " characters!", ex.getMessage());
    }

    @Test
    public void register_negativeAge_NotOk() {
        user.setAge(-66);
        RegistrationServiceException ex = assertThrows(
                RegistrationServiceException.class,
                () -> registrationService.register(user)
        );
        assertEquals("Your age must not be negative!", ex.getMessage());
    }
}
