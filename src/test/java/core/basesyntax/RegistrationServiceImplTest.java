package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String LESS_THAN_SIX_CHARACTERS = "12345";
    private static RegistrationService registrationService;
    private static StorageDao storage;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storage = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User("123456", "password", 18);
        Storage.people.clear();
    }

    @Test
    void register_duplicateLogin_notOk() {
        registrationService.register(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginIsLessThanSixCharacters_notOk() {
        user.setLogin(LESS_THAN_SIX_CHARACTERS);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordIsLessThanSixCharacters_notOk() {
        user.setPassword(LESS_THAN_SIX_CHARACTERS);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userRegister_ok() {
        User actual = registrationService.register(user);
        User expected = storage.get(user.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    void register_ageIsLessThanEighteen_notOK() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_registrationServiceNullValue_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_loginNullValue_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordNullValue_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void storage_userIsNotExistInStorage() {
        User actual = storage.get("Nothing");
        User expected = null;
        assertEquals(expected, actual);
    }

    @Test
    void register_returnsProvidedUser_WhenSuccess() {
        User actual = registrationService.register(user);
        User expected = user;
        assertEquals(expected, actual);
    }
}
