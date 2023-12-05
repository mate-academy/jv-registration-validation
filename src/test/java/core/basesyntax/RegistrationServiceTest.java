package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.InvalidUserDataException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private RegistrationServiceImpl registrationService;
    private StorageDaoImpl storageDao;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    void register_invalidAge_NotOk() {
        String validLogin = "misterBean";
        String validPassword = "misterBean";
        int negativeAge = -2;
        User testUser = new User(validLogin,
                validPassword, negativeAge);

        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(testUser));

        testUser.setAge(null);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(testUser));

        testUser.setAge(12);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(testUser));

        testUser.setAge(0);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(testUser));
    }

    @Test
    void register_InvalidUserLogin_NotOk() {
        String validPassword = "misterBean";
        int validAge = 23;
        String invalidLogin = "Bean";

        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(null));

        User testUser = new User(invalidLogin,
                validPassword, validAge);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(testUser));

        testUser.setLogin(null);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(testUser));

        testUser.setLogin("");
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(testUser));
    }

    @Test
    void register_InvalidUserPassword_NotOk() {
        String validLogin = "misterBean";
        int validAge = 23;
        String invalidPassword = "Bean";

        User testUser = new User(validLogin,
                invalidPassword, validAge);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(testUser));

        testUser.setPassword(null);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(testUser));

        testUser.setPassword("");
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(testUser));
    }

    @Test
    void register_ValidUser_Ok() {
        String validLogin = "misterBean";
        String validPassword = "misterBean";
        int validAge = 23;
        User testUser = new User(validLogin,
                validPassword, validAge);

        assertDoesNotThrow(() -> registrationService.register(testUser));

        testUser.setAge(18);
        assertDoesNotThrow(() -> registrationService.register(testUser));

        testUser.setAge(123);
        assertDoesNotThrow(() -> registrationService.register(testUser));
    }

    @Test
    void register_UserInDatabase_Ok() {
        String validLogin = "misterHolms";
        String validPassword = "misterWatson";
        int validAge = 23;
        User testUser = new User(validLogin, validPassword, validAge);

        registrationService.register(testUser);
        User actual = storageDao.get(testUser.getLogin());
        assertEquals(testUser, actual);
    }
}
