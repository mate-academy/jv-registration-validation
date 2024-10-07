package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.UserUncheckedException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String LESS_THAN_SIX_CHARACTERS = "12345";
    private User user;
    private RegistrationService registrationService;
    private StorageDao storage;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        storage = new StorageDaoImpl();
        user = new User("123456", "password", 18);
        Storage.people.clear();
    }

    @Test
    void loginIsAlreadyContainInStorage_notOk() {
        registrationService.register(user);
        assertThrows(UserUncheckedException.class, () -> registrationService.register(user));
    }

    @Test
    void loginIsLessThanSixCharacters_notOk() {
        user.setLogin(LESS_THAN_SIX_CHARACTERS);
        assertThrows(UserUncheckedException.class, () -> registrationService.register(user));
    }

    @Test
    void passwordIsLessThanSixCharacters_notOk() {
        user.setPassword(LESS_THAN_SIX_CHARACTERS);
        assertThrows(UserUncheckedException.class, () -> registrationService.register(user));
    }

    @Test
    void userRegister_ok() {
        User actual = registrationService.register(user);
        User expected = storage.get(user.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    void ageIsLessThanEighteen_notOK() {
        user.setAge(17);
        assertThrows(UserUncheckedException.class, () -> registrationService.register(user));
    }

    @Test
    void registrationServiceNullValue_notOk() {
        assertThrows(NullPointerException.class, () -> registrationService.register(null));
    }

    @Test
    void loginNullValue_notOk() {
        user.setLogin(null);
        assertThrows(UserUncheckedException.class, () -> registrationService.register(user));
    }

    @Test
    void passwordNullValue_notOk() {
        user.setPassword(null);
        assertThrows(UserUncheckedException.class, () -> registrationService.register(user));
    }

    @Test
    void userNotFoundInStorage_ok() {
        User actual = storage.get("Nothing");
        User expected = null;
        assertEquals(expected, actual);
    }
}
