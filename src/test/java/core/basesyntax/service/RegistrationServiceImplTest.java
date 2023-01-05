package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User user;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
        storageDao = new StorageDaoImpl();
    }

    @Test
    void register_ageIsIncorrect_NotOk() {
        user.setAge(12);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userLoginIsNull_NotOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userPasswordLessThenSixCharacters_NotOk() {
        user.setPassword("3728");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userPasswordIsNull_NotOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userExistInSuchStorage_NotOk() {
        storageDao.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
