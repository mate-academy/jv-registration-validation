package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_validData_Ok() {
        User expected = new User();
        expected.setLogin("abcdef123");
        expected.setPassword("tazbog123");
        expected.setAge(25);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
        assertEquals(expected, storageDao.get(expected.getLogin()));
    }

    @Test
    void register_loginDuplicate_notOk() {
        User user = new User();
        user.setLogin("duplicateLogin");
        user.setPassword("validPassword");
        user.setAge(25);
        storageDao.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_userAge_notOk() {
        User user = new User();
        user.setLogin("userLogin");
        user.setPassword("validPassword");
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAgeNull_notOk() {
        User user = new User();
        user.setLogin("userLogin");
        user.setPassword("validPassword");
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAgeNegative_notOk() {
        User user = new User();
        user.setLogin("userLogin");
        user.setPassword("validPassword");
        user.setAge(-59);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userMinAge_Ok() {
        User user = new User();
        user.setLogin("userLogin");
        user.setPassword("validPassword");
        user.setAge(18);
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_userValidLogin_Ok() {
        User user = new User();
        user.setLogin("userLogin");
        user.setPassword("validPassword");
        user.setAge(25);
        assertDoesNotThrow(() -> registrationService.register(user));
    }
}
