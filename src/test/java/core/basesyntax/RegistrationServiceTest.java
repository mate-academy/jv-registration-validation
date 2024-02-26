package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.PasswordLengthException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import core.basesyntax.service.UserAlreadyExistsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static User Andy = new User();
    private static User Eva = new User();
    private static User Julia = new User();
    private static User Twin = new User();
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();

    @BeforeAll
    public static void setUp() {
        Andy.setLogin("Andy");
        Andy.setAge(23);
        Andy.setPassword("123456");
        Twin.setLogin("Andy");
        Twin.setAge(43);
        Twin.setPassword("654321");
        Julia.setLogin("Julia");
        Julia.setAge(19);
        Julia.setPassword("12345");
        Eva.setLogin("Eva");
        Eva.setAge(5);
        Eva.setPassword("eva23456");
    }

    @AfterEach
    public void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_NullUser_ReturnsNull() {
        User user = registrationService.register(null);
        assertNull(user);
    }

    @Test
    void register_SuccessfulRegistration_ReturnsUserObject() {
        User registeredUser = registrationService.register(Andy);
        assertNotNull(registeredUser);
    }

    @Test
    void register_SuccessfulRegistration_UserStoredInDatabase() {
        User user = new User();
        user.setLogin("NewUser");
        user.setAge(25);
        user.setPassword("password123");

        registrationService.register(user);
        storageDao.add(user);
        User userFromDatabase = storageDao.get("NewUser");

        assertNotNull(userFromDatabase);
        assertEquals(user, userFromDatabase);
    }

    @Test
    void register_UserAlreadyExists_ThrowsUserAlreadyExistsException() {
        storageDao.add(registrationService.register(Andy));
        assertThrows(UserAlreadyExistsException.class, () -> registrationService.register(Twin));
    }

    @Test
    void register_UserAgeBelowMinimum_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> registrationService.register(Eva));
    }

    @Test
    void register_NullPassword_ThrowsPasswordLengthException() {
        Andy.setPassword("");

        assertThrows(PasswordLengthException.class, () -> registrationService.register(Andy));
    }

    @Test
    void register_UserPasswordLengthBelowMinimum_ThrowsPasswordLengthException() {
        assertThrows(PasswordLengthException.class, () -> registrationService.register(Julia));
    }

    @Test
    void register_MinimumAgeUser_ThrowsIllegalArgumentException() {
        Eva.setAge(6);

        assertThrows(IllegalArgumentException.class, () -> registrationService.register(Eva));
    }
}
