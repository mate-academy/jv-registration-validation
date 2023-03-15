package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.custom.exceptions.AgeNotValidException;
import core.basesyntax.custom.exceptions.LoginNotValidException;
import core.basesyntax.custom.exceptions.PasswordNotValidException;
import core.basesyntax.custom.exceptions.UserNotValidException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private static RegistrationService registrationService;
    private static final String DEFAULT_LOGIN = "Bob";
    private static final String DEFAULT_PASSWORD = "BobPas";
    private static final int DEFAULT_AGE = 20;
    private static final String WRONG_PASSWORD = "abc";
    private static final int WRONG_AGE_LESS = 16;
    private static final int WRONG_AGE_MORE = 131;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void beforeEach() {
        user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(DEFAULT_AGE);
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        user = null;
        assertThrows(UserNotValidException.class, () -> registrationService.register(user));
    }

    @Test
    void register_lessAge_notOk() {
        user.setAge(WRONG_AGE_LESS);
        assertThrows(AgeNotValidException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(AgeNotValidException.class, () -> registrationService.register(user));
    }

    @Test
    void register_greatAge_notOk() {
        user.setAge(WRONG_AGE_MORE);
        assertThrows(AgeNotValidException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(LoginNotValidException.class, () -> registrationService.register(user));
    }

    @Test
    void register_sameLogin_notOk() {
        StorageDao storageDao = new StorageDaoImpl();
        storageDao.add(user);
        assertThrows(LoginNotValidException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(PasswordNotValidException.class, () -> registrationService.register(user));
    }

    @Test
    void register_wrongLengthPassword_notOk() {
        user.setPassword(WRONG_PASSWORD);
        assertThrows(PasswordNotValidException.class, () -> registrationService.register(user));
    }

    @Test
    void register_successfulRegistration_Ok() {
        assertEquals(registrationService.register(user), user);
    }
}
