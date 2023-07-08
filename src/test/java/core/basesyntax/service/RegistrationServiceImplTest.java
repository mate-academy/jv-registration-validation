package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;
    private StorageDao storageDao;

    @BeforeEach
    public void setup() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
        storageDao = new StorageDaoImpl();
    }

    @Test
    void register_success_Ok() {
        User actual;
        try {
            actual = registrationService.register(user);
        } catch (ValidationException e) {
            return;
        }
        assertEquals(actual.getLogin(), storageDao.get(actual.getLogin()));
    }

    @Test
    void register_anExisting_Login_Not_Ok() {
        Storage.people.add(user);
        try {
            registrationService.register(user);
        } catch (ValidationException e) {
            return;
        }
        fail("Exception must be throw");
    }

    @Test
    void register_loginNull_NotOk() {
        user.setLogin(null);
        try {
            registrationService.register(user);
        } catch (ValidationException e) {
            return;
        }
        fail("Exception must be throw");
    }

    @Test
    void register_passwordNull_NotOK() {
        user.setPassword(null);
        try {
            registrationService.register(user);
        } catch (ValidationException e) {
            return;
        }
        fail("Exception must be throw");
    }

    @Test
    void register_underage_NotOk() {
        user.setAge(15);
        try {
            registrationService.register(user);
        } catch (ValidationException e) {
            return;
        }
        fail("Exception must be throw");
    }

    @Test
    void register_passwordToShort_NotOk() {
        user.setPassword("12345");
        try {
            registrationService.register(user);
        } catch (ValidationException e) {
            return;
        }
        fail("Exception must be throw");
    }

    @Test
    void register_loginToShort_NotOk() {
        user.setLogin("12345");
        try {
            registrationService.register(user);
        } catch (ValidationException e) {
            return;
        }
        fail("Exception must be throw");
    }
}
