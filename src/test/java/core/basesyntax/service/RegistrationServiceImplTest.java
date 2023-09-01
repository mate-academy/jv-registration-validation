package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "ValidLogin";
    private static final String VALID_PASSWORD = "ValidPassword";
    private static final int VALID_AGE = 19;
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();

    }

    @Test
    void register_ifLoginIsAbsent_Ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_ifLoginIsPresent_NotOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword("AnyPassword");
        user.setAge(20);
        StorageDao storageDao = new StorageDaoImpl();
        storageDao.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "User with current login already exists in the storage. "
                        + "The RegistrationException should be thrown");
    }

    @Test
    void register_LoginValidLength_Ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_LoginMinValidLength_Ok() {
        user.setLogin("6chars");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_LoginMinEdgeValidLength_Ok() {
        user.setLogin("7 chars");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_LoginIsNull_NotOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Login can't be null. The RegistrationException should be thrown");
    }

    @Test
    void register_LoginIsEmpty_NotOk() {
        user.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Login is empty. The RegistrationException should be thrown");
    }

    @Test
    void register_LoginIsBlank_NotOk() {
        user.setLogin("      ");
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Login is blank. The RegistrationException should be thrown");
    }

    @Test
    void register_LoginInvalidLength_NotOk() {
        user.setLogin("less");
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Login has invalid length. The RegistrationException should be thrown");
    }

    @Test
    void register_LoginMinEdgeInvalidLength_NotOk() {
        user.setLogin("five5");
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Login has invalid length. The RegistrationException should be thrown");
    }

    @Test
    void register_PasswordValidLength_Ok() {
        User actual = registrationService.register(user);
        assertEquals(user.getPassword(), actual.getPassword());
    }

    @Test
    void register_PasswordMinValidLength_Ok() {
        user.setPassword("Valid6");
        User actual = registrationService.register(user);
        assertEquals(user.getPassword(), actual.getPassword());
    }

    @Test
    void register_PasswordMinEdgeValidLength_Ok() {
        user.setPassword("7 chars");
        User actual = registrationService.register(user);
        assertEquals(user.getPassword(), actual.getPassword());
    }

    @Test
    void register_PasswordIsNull_NotOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Password can't be null. The RegistrationException should be thrown");
    }

    @Test
    void register_PasswordIsEmpty_NotOk() {
        user.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Password is empty. The RegistrationException should be thrown");
    }

    @Test
    void register_PasswordIsBlank_NotOk() {
        user.setPassword("      ");
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Password is blank. The RegistrationException should be thrown");
    }

    @Test
    void register_PasswordInvalidLength_NotOk() {
        user.setPassword("less");
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Password has invalid length. The RegistrationException should be thrown");
    }

    @Test
    void register_PasswordMinEdgeInvalidLength_NotOk() {
        user.setPassword("five5");
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Password has invalid length. The RegistrationException should be thrown");
    }

    @Test
    void register_AgeValid_Ok() {
        User actual = registrationService.register(user);
        assertEquals(user.getAge(), actual.getAge());
    }

    @Test
    void register_AgeMinEdgeValid_Ok() {
        user.setAge(18);
        User actual = registrationService.register(user);
        assertEquals(user.getAge(), actual.getAge());
    }

    @Test
    void register_AgeInvalid_NotOk() {
        user.setAge(10);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Age is invalid.The RegistrationException should be thrown");
    }

    @Test
    void register_AgeMinEdgeInvalid_NotOk() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Age is invalid.The RegistrationException should be thrown");
    }

    @Test
    void register_AgeIsZero_NotOk() {
        user.setAge(0);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Age is invalid.The RegistrationException should be thrown");
    }

    @Test
    void register_AgeIsNegative_NotOk() {
        user.setAge(-18);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Age is invalid.The RegistrationException should be thrown");
    }
}
