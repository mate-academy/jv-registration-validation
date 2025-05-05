package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
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
        user.setLogin("TestUser");
        user.setId(1L);
        user.setPassword("123456");
        user.setAge(19);
    }

    @Test
    public void register_validUser_Ok() {
        user.setLogin("UserSome");
        registrationService.register(user);
        Assertions.assertEquals(user, storageDao.get("UserSome"));
    }

    @Test
    public void register_newLogin_Ok() {
        user.setLogin("UserTest");
        registrationService.register(user);
        Assertions.assertEquals("UserTest", storageDao.get("UserTest").getLogin());
    }

    @Test
    public void register_nullLogin_notOk() {
        user.setLogin(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    public void register_rightPassword_Ok() {
        user.setLogin("UserTestPassword");
        user.setPassword("123qwerty");
        registrationService.register(user);
        Assertions.assertEquals("123qwerty",
                storageDao.get("UserTestPassword").getPassword());
    }

    @Test
    public void register_shortPassword_notOk() {
        user.setPassword("12");
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    public void register_nullPassword_notOk() {
        user.setPassword(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    public void register_smallAge_notOk() {
        user.setAge(10);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    public void register_negativeAge_notOk() {
        user.setAge(-12);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    public void register_duplicateLogin_notOk() {
        user.setLogin("User");
        storageDao.add(user);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }
}
