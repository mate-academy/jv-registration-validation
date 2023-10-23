package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HelloWorldTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static final long VALID_ID = 333;
    private static final String VALID_LOGIN = "mateuser777";
    private static final String VALID_PASSWORD = "validpassword";
    private static final int VALID_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;

    private final User validUser = new User();

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        validUser.setId(VALID_ID);
        validUser.setLogin(VALID_LOGIN);
        validUser.setPassword(VALID_PASSWORD);
        validUser.setAge(VALID_AGE);
    }

    @Test
    void userWithNullLogin_NotOk() {
        validUser.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @Test
    void userWithNullPassword_NotOk() {
        validUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @Test
    void userWithNullAge_NotOk() {
        validUser.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(validUser));
    }

    @Test
    void validLoginLength_Ok() {
        boolean expected = validUser.getLogin().length() >= MIN_LOGIN_LENGTH;
        assertTrue(expected);
    }

    @Test
    void validPasswordLength_Ok() {
        boolean expected = validUser.getPassword().length() >= MIN_PASSWORD_LENGTH;
        assertTrue(expected);
    }

    @Test
    void validAge_Ok() {
        boolean expected = validUser.getAge() >= VALID_AGE;
        assertTrue(expected);
    }

    @Test
    void register_AllValidValues_Ok() {
        User expected = storageDao.add(validUser);
        User actual = storageDao.get(validUser.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    void userInStorage_Ok() {
        boolean expected = storageDao.get(validUser.getLogin()) != null;
        assertFalse(expected);
    }

}
