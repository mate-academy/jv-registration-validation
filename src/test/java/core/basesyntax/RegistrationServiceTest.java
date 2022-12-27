package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import core.basesyntax.service.UserRegistrationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class RegistrationServiceTest {
    private static RegistrationServiceImpl registrationService;
    private static StorageDao storageDao;
    private User user;

    @BeforeAll
    public static void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    public void setUp1() {
        user = new User();
    }

    @Test
    void register_UserAbove18Age_True() throws UserRegistrationException {
        user.setAge(29);
        user.setPassword("1234567890");
        user.setLogin("2434349");
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserUnder18Age_notOk() {
        user.setAge(17);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_User18Age_notOk() {
        user.setAge(18);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_PasswordUnder6Characters_notOk() {
        user.setPassword("1234");
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserNegativeAge_notOk() {
        user.setAge(-18);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_UserAlreadyPresent_notOk() throws UserRegistrationException {
        user.setAge(23);
        user.setLogin("23");
        storageDao.add(user);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ReturnUser_True() throws UserRegistrationException {
        user.setAge(88);
        user.setLogin("88");
        user.setPassword("1234567890");
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void register_ReturnUserId_Ok() throws UserRegistrationException {
        user.setAge(83);
        user.setLogin("83");
        user.setPassword("1234567890");
        Long actual = registrationService.register(user).getId();
        assertNotNull(actual);
    }

    @Test
    void register_ReturnLogin_Ok() throws UserRegistrationException {
        user.setAge(835);
        user.setLogin("835");
        user.setPassword("1234567890");
        String expected = user.getLogin();
        String actual = registrationService.register(user).getLogin();
        assertEquals(expected, actual);
    }
}
