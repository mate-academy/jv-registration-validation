package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    public static void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    public void register_validUser_Ok() {
        User validUser = createUser(2123123L, "Ryan Gosling",
                "JavaIsTheBestProgrammingLanguage", 41);
        assertEquals(validUser, registrationService.register(validUser));
        assertEquals(validUser, storageDao.get(validUser.getLogin()));
    }

    @Test
    public void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    public void register_nullIdUser_notOk() {
        User nullIdUser = createUser(null, "Not Ryan",
                "PleaseApproveMyPR", 24);
        assertThrows(RuntimeException.class, () -> registrationService.register(nullIdUser));
    }

    @Test
    public void register_nullLoginUser_notOk() {
        User nullLoginUser = createUser(12334L, null,
                "IHateMakingTestsSoMuch", 23);
        assertThrows(RuntimeException.class, () -> registrationService.register(nullLoginUser));
    }

    @Test
    public void register_nullPasswordUser_notOk() {
        User nullPasswordUser = createUser(2141L, "Also Not Ryan",
                null, 35);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(nullPasswordUser));

    }

    @Test
    public void register_nullAgeUser_notOk() {
        User nullAgeUser = createUser(21321L, "%3245##%@",
                "SoPleaseLetMeFree", null);
        assertThrows(RuntimeException.class, () -> registrationService.register(nullAgeUser));
    }

    @Test
    public void register_duplicateUser_notOk() {
        User anotherValidUser = createUser(2123123L, "Whaaaathup!",
                "ThisIsATestPassword", 23);
        registrationService.register(anotherValidUser);
        assertEquals(anotherValidUser, storageDao.get(anotherValidUser.getLogin()));
        assertThrows(RuntimeException.class,
                () -> registrationService.register(anotherValidUser));
    }

    @Test
    public void register_notAdultUser_notOk() {
        User notAdultUser = createUser(35252L, "Zorro",
                "Hazzard!", 17);
        assertThrows(RuntimeException.class, () -> registrationService.register(notAdultUser));
    }

    @Test void register_shortPasswordUser_notOk() {
        User shortPasswordUser = createUser(135211L, "Vendetta means",
                "12345", 42);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(shortPasswordUser));
    }

    private static User createUser(Long id, String login, String password, Integer age) {
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}
