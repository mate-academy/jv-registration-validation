package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "ValidLogin";
    private static final String VALID_PASSWORD = "password";
    private static final String INVALID_LOGIN = "invL";
    private static final String INVALID_PASSWORD = "invP";
    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();
    private final User nullLoginPasswordUser = new User();

    @BeforeEach
    void setUp() {
        nullLoginPasswordUser.setAge(20);
        nullLoginPasswordUser.setLogin(null);
        nullLoginPasswordUser.setPassword(null);
    }

    @Test
    void register_valid_ok() {
        User simpleValidUser = new User();
        simpleValidUser.setAge(20);
        simpleValidUser.setLogin(VALID_LOGIN);
        simpleValidUser.setPassword(VALID_PASSWORD);
        registrationService.register(simpleValidUser);
        assertNotNull(storageDao.get(simpleValidUser.getLogin()));
    }

    @Test
    void register_invalidPassword_notOk() {
        User invalidPasswordUser = new User();
        invalidPasswordUser.setAge(20);
        invalidPasswordUser.setLogin(VALID_LOGIN);
        invalidPasswordUser.setPassword(INVALID_PASSWORD);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(invalidPasswordUser));
    }

    @Test
    void register_invalidLogin_notOk() {
        User invalidLoginUser = new User();
        invalidLoginUser.setAge(20);
        invalidLoginUser.setLogin(INVALID_LOGIN);
        invalidLoginUser.setPassword(VALID_PASSWORD);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(invalidLoginUser));
    }

    @Test
    void register_invalidAge_notOk() {
        User invalidAgeUser = new User();
        invalidAgeUser.setAge(16);
        invalidAgeUser.setLogin(VALID_LOGIN);
        invalidAgeUser.setPassword(VALID_PASSWORD);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(invalidAgeUser));
    }

    @Test
    void register_existedUser_notOk() {
        User existedUser = new User();
        existedUser.setAge(20);
        existedUser.setLogin(VALID_LOGIN);
        existedUser.setPassword(VALID_PASSWORD);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(existedUser));
    }

    @Test
    void register_nullLogin_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(nullLoginPasswordUser));
    }

    @Test
    void register_nullPassword_notOk() {
        nullLoginPasswordUser.setLogin(VALID_LOGIN);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(nullLoginPasswordUser));
    }
}
