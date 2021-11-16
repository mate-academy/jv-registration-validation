package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static StorageDao storage;
    private static RegistrationService registration;
    private User user;

    @BeforeAll
    static void beforeAll() {
        storage = new StorageDaoImpl();
        registration = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void registration_nullUser_NotOk() {
        assertThrows(RuntimeException.class, () ->
                registration.register(null)
        );
    }

    @Test
    void registration_userIsAlreadyInStorage_NotOk() {
        user.setLogin("arangutang1488");
        storage.add(user);
        assertThrows(RuntimeException.class, () ->
                registration.register(user)
        );
    }

    @Test
    void registration_userAgeIsLessThanEighteen_NotOk() {
        user.setLogin("wrongAge");
        user.setPassword("wrongAge");
        user.setAge(-228);
        assertThrows(RuntimeException.class, () ->
                registration.register(user)
        );
    }

    @Test
    void registration_passwordLengthIsLessThanSix_NotOk() {
        user.setLogin("wrongPassword");
        user.setPassword("12345");
        user.setAge(41);
        assertThrows(RuntimeException.class, () ->
                registration.register(user)
        );
    }

    @Test
    void registration_userWithNullLogin_NotOk() {
        user.setPassword("nullLogin");
        user.setAge(32);
        assertThrows(RuntimeException.class, () ->
                registration.register(user)
        );
    }

    @Test
    void registration_userWithNullPassword_NotOk() {
        user.setLogin("nullPassword");
        user.setAge(32);
        assertThrows(RuntimeException.class, () ->
                registration.register(user)
        );
    }

    @Test
    void registration_userWithNullAge_NotOk() {
        user.setLogin("nullAge");
        user.setPassword("nullAge");
        assertThrows(RuntimeException.class, () ->
                registration.register(user)
        );
    }

    @Test
    void registration_userWithEmptyLogin_NotOk() {
        user.setLogin("");
        user.setPassword("wrongLogin");
        user.setAge(30);
        assertThrows(RuntimeException.class, () ->
                registration.register(user)
        );
    }

    @Test
    void registration_checkByNormalUser_Ok() {
        user.setLogin("normalUser");
        user.setPassword("normalUser");
        user.setAge(33);
        assertEquals(user, registration.register(user));
    }
}
