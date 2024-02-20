package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static User Andy = new User();
    private static User Eva = new User();
    private static User Julia = new User();
    private static User Twin = new User();
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();

    @BeforeAll
    public static void setUp() {
        Andy.setLogin("Andy");
        Andy.setAge(23);
        Andy.setPassword("123456");
        Twin.setLogin("Andy");
        Twin.setAge(43);
        Twin.setPassword("654321");
        Julia.setLogin("Julia");
        Julia.setAge(19);
        Julia.setPassword("12345");
        Eva.setLogin("Eva");
        Eva.setAge(5);
        Eva.setPassword("eva23456");
    }

    @Test
    void nullValue_Ok() {
        User user = registrationService.register(null);
        assertNull(user);
    }

    @Test
    void userAlreadyExists_NotOk() {
        storageDao.add(registrationService.register(Andy));
        assertThrows(Exception.class, () -> registrationService.register(Twin));
    }

    @Test
    void userAge_NotOk() {
        assertThrows(IllegalArgumentException.class, () -> registrationService.register(Eva));
    }

    @Test
    void userPasswordLength_NotOk() {
        assertThrows(Exception.class, () -> registrationService.register(Julia));
    }
}
