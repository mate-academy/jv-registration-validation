package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();

    @Test
    void register_alreadyExist_notOk() {
        User userFirst = new User();
        userFirst.setId(7575L);
        userFirst.setLogin("qwe");
        userFirst.setPassword("123456");
        userFirst.setAge(36);
        storageDao.add(userFirst);

        User userSecond = new User();
        userSecond.setId(789L);
        userSecond.setLogin("qwe");
        userSecond.setPassword("457627");
        userSecond.setAge(42);
        assertThrows(RuntimeException.class, () -> registrationService.register(userSecond));
    }

    @Test
    void register_validData_ok() {
        User user = new User();
        user.setId(123L);
        user.setLogin("sdhgs5s");
        user.setPassword("qwerds");
        user.setAge(28);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_ageNull_notOk() {
        User user = new User();
        user.setId(456L);
        user.setPassword("1234534346");
        user.setAge(null);
        user.setLogin("dsfdf");
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordNull_notOk() {
        User user = new User();
        user.setId(123L);
        user.setLogin("sdhgss");
        user.setPassword(null);
        user.setAge(45);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginNull_notOk() {
        User user = new User();
        user.setId(123L);
        user.setPassword("ghghgh");
        user.setAge(25);
        user.setLogin(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void register_youngAge_notOk() {
        User user = new User();
        user.setId(789L);
        user.setLogin("fhg");
        user.setPassword("789456123");
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setId(15L);
        user.setLogin("sdgfsdg");
        user.setPassword("qeeq");
        user.setAge(85);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}
