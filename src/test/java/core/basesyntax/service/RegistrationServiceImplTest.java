package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.InvalidUserException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() throws InvalidUserException {
        registrationService = new RegistrationServiceImpl();
        user = new User();
        user.setId(1L);
        user.setLogin("newUser");
        user.setPassword("newUser");
        user.setAge(22);
    }

    @Test
    void validUserIsCreated_Ok() throws InvalidUserException {
        User newUser = new User();
        newUser.setId(1L);
        newUser.setLogin("HelloMate");
        newUser.setPassword("HelloMate");
        newUser.setAge(22);
        User actual = registrationService.register(newUser);
        assertTrue(actual.equals(newUser));
    }

    @Test
    void userIsNull_NotOk() throws InvalidUserException {
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void ageIsNull_NotOk() throws InvalidUserException {
        user.setAge(null);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void loginIsNull_NotOk() throws InvalidUserException { // implement
        user.setLogin(null);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void passwordIsNull_NotOk() throws InvalidUserException { // implement
        user.setPassword(null);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void userAlreadyExists_NotOk() throws InvalidUserException {
        StorageDao storageDao = new StorageDaoImpl();
        storageDao.add(user);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void tooShortLogin_NotOk() throws InvalidUserException {
        user.setLogin("mate");
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void tooShortPassword_NotOk() throws InvalidUserException {
        user.setPassword("mate");
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void tooYoungUser_NotOk() throws InvalidUserException {
        user.setAge(15);
        assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
    }
}
