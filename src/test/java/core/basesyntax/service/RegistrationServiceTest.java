package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {

    private LoginValidator loginValidator;

    @BeforeEach
    void setUp() {
        loginValidator = new LoginValidator();
        if (Storage.people != null) {
            Storage.people.clear();
        }
    }

    @Test
    public void register_noExistingUser_userAdded() {
        StorageDao storage = new StorageDaoImpl();
        RegistrationService service = new RegistrationServiceImpl(storage);
        User user = new User("validLogin", "validPass", 25);
        service.register(user);
        User fromStorage = storage.get(user.getLogin());
        assertNotNull(fromStorage, "User should be saved in storage after registration");
    }

    @Test
    public void register_loginIsRightSize() {
        assertTrue(loginValidator.isValid("Qwerty"));
    }

    @Test
    public void register_loginIsWrongSize() {
        assertFalse(loginValidator.isValid("Qwert"));
    }
}
