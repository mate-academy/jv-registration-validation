package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.DuplicateUserException;
import core.basesyntax.exceptions.InvalidUserException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static final int MIN_AGE = 18;
    private static final String VALID_LOGIN = "samwinchester1967";
    private static final String VALID_PASSWORD = "deanimpala67";
    private static final int INVALID_AGE = 15;
    private RegistrationService registrationService;
    private StorageDao storageDao;

    @BeforeEach
    public void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void register_nullUser_notOk() {
        assertThrows(InvalidUserException.class, () -> registrationService.register(null));
    }

    @Test
    public void register_ValidUser_Ok() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, MIN_AGE);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser);
        assertEquals(user.getLogin(), registeredUser.getLogin());
        assertEquals(user.getPassword(), registeredUser.getPassword());
        assertEquals(user.getAge(), registeredUser.getAge());
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    public void register_nullLogin_notOk() {
        User user = new User(null, VALID_PASSWORD, MIN_AGE);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_NullPassword_notOk() {
        User user = new User(VALID_LOGIN, null, MIN_AGE);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_zeroAge_notOk() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, INVALID_AGE);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_invalidLogin_notOk() {
        String invalidLogin = "mark";
        User user = new User(invalidLogin, VALID_PASSWORD, MIN_AGE);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_ShortPassword_notOk() {
        User user = new User(VALID_LOGIN, "ACDC", MIN_AGE);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_DuplicateLogin_ThrowsDuplicateUserException() {
        User user1 = new User(VALID_LOGIN, VALID_PASSWORD, MIN_AGE);
        User user2 = new User(VALID_LOGIN, "someNewPassword", MIN_AGE);
        storageDao.add(user1);
        assertThrows(DuplicateUserException.class, () -> registrationService.register(user2));
    }

}
