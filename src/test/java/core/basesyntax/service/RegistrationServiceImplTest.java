package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final String LOGIN_DEFAULT = "default";
    private static final String LOGIN_ANOTHER = "another";
    private static final int AGE_ALLOWED = 18;
    private static final int AGE_LESS_ALLOWED = 17;
    private static final String PASSWORD_DEFAULT = "default";
    private static final String PASSWORD_SHORT = "short";
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User user;

    private static void addDefaultUserToDao() {
        User user = new User();
        user.setLogin(LOGIN_DEFAULT);
        user.setPassword(PASSWORD_DEFAULT);
        user.setAge(AGE_ALLOWED);
        storageDao.add(user);
    }

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(LOGIN_DEFAULT);
        user.setPassword(PASSWORD_DEFAULT);
        user.setAge(AGE_ALLOWED);
    }

    @Test
    public void register_userNull_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    public void register_loginNull_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_ageNull_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_passwordNull_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_loginNotUsed_Ok() {
        addDefaultUserToDao();
        user.setLogin(LOGIN_ANOTHER);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    public void register_loginAlreadyUsed_notOk() {
        addDefaultUserToDao();
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_loginEmpty_notOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_loginOnlyWhitespaces_notOk() {
        user.setLogin("       ");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_ageEighteen_Ok() {
        user.setAge(AGE_ALLOWED);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    public void register_ageAboveEighteen_notOk() {
        user.setAge(AGE_LESS_ALLOWED);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_ageNegative_notOk() {
        Integer negativeAge = AGE_ALLOWED * -1;
        user.setAge(negativeAge);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_passwordAboveSixChars_NotOk() {
        user.setPassword(PASSWORD_SHORT);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_passwordSixChars_Ok() {
        user.setPassword(PASSWORD_DEFAULT);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    public void register_passwordEmpty_notOk() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_passwordOnlyWhitespaces_notOk() {
        user.setPassword("       ");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
