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
        user.setAge(-228);
        assertThrows(RuntimeException.class, () ->
                registration.register(user)
        );
    }

    @Test
    void registration_passwordLengthIsLessThanSix_NotOk() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () ->
                registration.register(user)
        );
    }

    @Test
    void registration_userWithNullStates_NotOk() {
        user.setLogin("titan");
        user.setPassword("brotherfromanothermother");
        assertThrows(RuntimeException.class, () ->
                registration.register(user)
        );
    }

    @Test
    void registration_checkByNormalUser_Ok() {
        user.setId(1L);
        user.setLogin("normalUser");
        user.setPassword("normalUser");
        user.setAge(33);
        assertEquals(user, registration.register(user));
    }
}