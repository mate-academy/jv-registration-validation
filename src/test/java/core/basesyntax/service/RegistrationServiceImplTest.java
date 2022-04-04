package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final RegistrationService REGISTRATION_SERVICE = new RegistrationServiceImpl();
    private static final StorageDao STORAGE_DAO = new StorageDaoImpl();
    private static final User VALID_USER = createUser(2123123L, "Ryan Gosling",
            "JavaIsTheBestProgrammingLanguage", 41);
    private static final User NULL_ID_USER = createUser(null, "Not Ryan",
            "PleaseApproveMyPR", 24);
    private static final User NULL_LOGIN_USER = createUser(12334L, null,
            "IHateMakingTestsSoMuch", 23);
    private static final User NULL_PASSWORD_USER = createUser(2141L, "Also Not Ryan",
            null, 35);
    private static final User NULL_AGE_USER = createUser(21321L, "%3245##%@",
            "SoPleaseLetMeFree", null);
    private static final User ANOTHER_VALID_USER = createUser(2123123L, "Whaaaathup!",
            "ThisIsATestPassword", 23);
    private static final User NOT_ADULT_USER = createUser(35252L, "Zorro",
            "Hazzard!", 14);
    private static final User SHORT_PASSWORD_USER = createUser(135211L, "Vendetta means",
            "V", 42);

    private static User createUser(Long id, String login, String password, Integer age) {
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }

    @Test
    public void register_validUser_Ok() {
        assertEquals(VALID_USER, REGISTRATION_SERVICE.register(VALID_USER));
        assertEquals(VALID_USER, STORAGE_DAO.get(VALID_USER.getLogin()));
    }

    @Test
    public void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> REGISTRATION_SERVICE.register(null));
    }

    @Test
    public void register_userWithNullParams_notOk() {
        assertThrows(RuntimeException.class, () -> REGISTRATION_SERVICE.register(NULL_ID_USER));
        assertThrows(RuntimeException.class, () -> REGISTRATION_SERVICE.register(NULL_LOGIN_USER));
        assertThrows(RuntimeException.class,
                () -> REGISTRATION_SERVICE.register(NULL_PASSWORD_USER));
        assertThrows(RuntimeException.class, () -> REGISTRATION_SERVICE.register(NULL_AGE_USER));
    }

    @Test
    public void register_duplicateUser_notOk() {
        REGISTRATION_SERVICE.register(ANOTHER_VALID_USER);
        assertEquals(ANOTHER_VALID_USER, STORAGE_DAO.get(ANOTHER_VALID_USER.getLogin()));
        assertThrows(RuntimeException.class,
                () -> REGISTRATION_SERVICE.register(ANOTHER_VALID_USER));
    }

    @Test
    public void register_notAdultUser_notOk() {
        assertThrows(RuntimeException.class, () -> REGISTRATION_SERVICE.register(NOT_ADULT_USER));
    }
}
