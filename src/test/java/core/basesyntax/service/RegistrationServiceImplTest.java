
package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final StorageDao storageDao = new StorageDaoImpl();
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    void password_Is_Ok() {
        String password = "hfjkghyr";
        boolean actual = registrationService.passwordIsValid(password);
        assertTrue(actual);
    }

    @Test
    void age_Is_Ok() {
        int age = 21;
        boolean actual = registrationService.ageIsValid(age);
        assertTrue(actual);
    }

    @Test
    void noLogin_Is_Ok() {
        String login = "Yevheniy";
        User actual = storageDao.get(login);
        assertNull(actual);
    }
}
