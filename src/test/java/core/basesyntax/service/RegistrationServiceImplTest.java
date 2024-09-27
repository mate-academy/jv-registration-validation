package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl service;
    private User user;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(474797L);
        user.setLogin("userLogin47");
        user.setPassword("password1111");
        user.setAge(25);
    }

    @Test
    void register_UserValid_Ok() {
        StorageDao storageDao = new StorageDaoImpl();
        assertNotNull(service.register(user));
        assertNotNull(storageDao.get(user.getLogin()));
    }

    @Test
    void register_UserNullValue_NotOk() {
        service.register(null);
        assertThrows(InvalidUserException.class, () -> service.register(null),
                "User must not be null");
    }

    @Test
    void register_LoginNotAvailable_NotOk() {
        StorageDao storageDao = new StorageDaoImpl();
        storageDao.add(user);
        assertNotNull(storageDao.get(user.getLogin()));
        assertThrows(InvalidUserException.class, () -> service.register(user),
                "A user with this login is already registered");
    }

    @Test
    void register_ShortLogin_NotOk() {
        user.setLogin("122");
        assertThrows(InvalidUserException.class, () -> service.register(user),
                "Login must contain at least 6 characters");
    }

    @Test
    void register_ShortPassword_NotOk() {
        user.setPassword("474");
        assertThrows(InvalidUserException.class, () -> service.register(user),
                "Password must contain at least 6 characters");
    }

    @Test
    void register_IncorrectAge_NotOk() {
        user.setAge(12);
        assertThrows(InvalidUserException.class, () -> service.register(user),
                "You are not have 18 years old");
    }

    @Test
    void register_LoginLimitValue_Ok() {
        user.setLogin("user11");
        assertNotNull(service.register(user));
    }

    @Test
    void register_PasswordLimitValue_Ok() {
        user.setPassword("user22");
        assertNotNull(service.register(user));
    }

    @Test
    void register_AgeLimitValue_Ok() {
        user.setAge(18);
        assertNotNull(service.register(user));
    }

    @Test
    void register_LoginNullValue_NotOk() {
        user.setLogin(null);
        assertThrows(InvalidUserException.class, () -> service.register(user),
                "Login cannot be empty");
    }

    @Test
    void register_PasswordNullValue_NotOk() {
        user.setPassword(null);
        assertThrows(InvalidUserException.class, () -> service.register(user),
                "Password cannot be empty");
    }

    @Test
    void register_AgeNullValue_NotOk() {
        user.setAge(null);
        assertThrows(InvalidUserException.class, () -> service.register(user),
                "Enter user age");
    }
}
