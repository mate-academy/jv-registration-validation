package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private User user;
    private final StorageDao storageDao = new StorageDaoImpl();

    @BeforeAll
    public static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void getDefaultUser() {
        user = new User();
        user.setId(1L);
        user.setPassword("123456");
        user.setAge(19);
    }

    @Test
    public void getRegistration_Ok() {
        user.setLogin("UserDao");
        storageDao.add(user);
        Assertions.assertEquals(user, storageDao.get("UserDao"));
    }

    @Test
    public void getRegistration_newLogin_Ok() {
        user.setLogin("UserTest");
        registrationService.register(user);
        Assertions.assertEquals("UserTest", storageDao.get("UserTest").getLogin());
    }

    @Test
    public void getRegistration_rightPassword_Ok() {
        user.setLogin("User");
        user.setPassword("123qwerty");
        registrationService.register(user);
        Assertions.assertEquals("123qwerty", storageDao.get("User").getPassword());
    }

    @Test
    public void getRegistration_wrongPasswordLessNormal_notOk() {
        user.setPassword("12");
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_wrongAgeLessNormal_notOk() {
        user.setAge(10);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_wrongAgeNegative_notOk() {
        user.setAge(-12);
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_wrongLogin_notOk() {
        user.setLogin("User");
        Assertions.assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}
