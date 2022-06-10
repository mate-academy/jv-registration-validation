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

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static final String LOGIN_DEFAULT = "default";
    private static final String LOGIN_ANOTHER = "another";
    private static final int AGE_ALLOWED = 18;
    private static final int AGE_BIGGER_ALLOWED = 25;
    private static final int AGE_LESS_ALLOWED = 15;
    private static final int AGE_NEGATIVE = -10;
    private static final int AGE_ZERO = 0;

    private static final String PASSWORD_DEFAULT = "default";
    private static final String PASSWORD_SHORT = "short";
    private User user;

    User getDefaultUser() {
        User user = new User();
        user.setLogin(LOGIN_DEFAULT);
        user.setPassword(PASSWORD_DEFAULT);
        user.setAge(AGE_ALLOWED);
        return user;
    }

    void addUserDirectly(User user) {
        StorageDao storageDao = new StorageDaoImpl();
        storageDao.add(user);
    }

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = getDefaultUser();
    }

    @Test
    void register_loginNotUsed_Ok() {
        addUserDirectly(getDefaultUser());
        user.setLogin(LOGIN_ANOTHER);

        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_loginUsed_notOk() {
        addUserDirectly(getDefaultUser());

        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginNull_notOk() {
        user.setLogin(null);

        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginEmpty_notOk() {
        user.setLogin("");

        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageBiggerEighteen_Ok() {
        user.setAge(AGE_BIGGER_ALLOWED);

        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_ageEighteen_Ok() {
        user.setAge(AGE_ALLOWED);
        User actual = registrationService.register(user);

        assertEquals(user, actual);
    }

    @Test
    void register_ageLessEighteen_notOk() {
        user.setAge(AGE_LESS_ALLOWED);

        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageZero_notOk() {
        user.setAge(AGE_ZERO);

        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageNegative_notOk() {
        user.setAge(AGE_NEGATIVE);

        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordMoreOrEqualSixChars_Ok() {
        user.setPassword(PASSWORD_DEFAULT);

        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_passwordLessThanSixChars_NotOk() {
        user.setPassword(PASSWORD_SHORT);

        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordEmpty_notOk() {
        user.setPassword("");

        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordNull_notOk() {
        user.setPassword(null);

        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
