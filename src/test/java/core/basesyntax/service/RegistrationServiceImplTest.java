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
    private static final String OLEG_NAME = "oleg";
    private static final String STEPAN_NAME = "stepan";
    private static final String OLEKSIY_NAME = "oleksiy";
    private static final String PASS_LESS_THEN_MIN_LENGTH = "12345";
    private static final String PASS_MIN_LENGTH = "123456";
    private static final String PASS_MORE_THEN_MIN_LENGTH = "6543210";
    private static final int AGE_LESS_THEN_MIN = 17;
    private static final int AGE_MIN = 18;
    private static final int AGE_MORE_THEN_MIN = 19;
    private static RegistrationService service;
    private User testUser;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        final StorageDao storageDao = new StorageDaoImpl();

        User validUser1 = new User();
        validUser1.setLogin(OLEG_NAME);
        validUser1.setPassword(PASS_MIN_LENGTH);
        validUser1.setAge(AGE_MORE_THEN_MIN);

        User validUser2 = new User();
        validUser2.setLogin(STEPAN_NAME);
        validUser2.setPassword(PASS_MORE_THEN_MIN_LENGTH);
        validUser2.setAge(AGE_MIN);

        storageDao.add(validUser1);
        storageDao.add(validUser2);

        testUser = new User();
        testUser.setLogin(OLEKSIY_NAME);
        testUser.setPassword(PASS_MORE_THEN_MIN_LENGTH);
        testUser.setAge(AGE_MORE_THEN_MIN);
    }

    @Test
    void register_userValid_ok() {
        User actual = service.register(testUser);
        assertEquals(testUser, actual);
    }

    @Test
    void register_userNull_notOk() {
        testUser = null;
        assertThrows(RuntimeException.class, () -> service.register(testUser));
    }

    @Test
    void register_loginNull_notOk() {
        testUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> service.register(testUser));
    }

    @Test
    void register_loginAlreadyExists_notOk() {
        testUser.setLogin(OLEG_NAME);
        assertThrows(RuntimeException.class, () -> service.register(testUser));
    }

    @Test
    void register_passIsNull_notOk() {
        testUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> service.register(testUser));
    }

    @Test
    void register_passShorterThenMin_notOk() {
        testUser.setPassword(PASS_LESS_THEN_MIN_LENGTH);
        assertThrows(RuntimeException.class, () -> service.register(testUser));
    }

    @Test
    void register_minValidPassLength_ok() {
        testUser.setPassword(PASS_MIN_LENGTH);
        User actual = service.register(testUser);
        assertEquals(testUser, actual);
    }

    @Test
    void register_passLongerMoreThenMin_ok() {
        testUser.setPassword(PASS_MORE_THEN_MIN_LENGTH);
        User actual = service.register(testUser);
        assertEquals(testUser, actual);
    }

    @Test
    void register_ageIsNull_notOk() {
        testUser.setAge(null);
        assertThrows(RuntimeException.class, () -> service.register(testUser));
    }

    @Test
    void register_ageLessThenMin_notOk() {
        testUser.setAge(AGE_LESS_THEN_MIN);
        assertThrows(RuntimeException.class, () -> service.register(testUser));
    }

    @Test
    void register_minValidAge_ok() {
        testUser.setAge(AGE_MIN);
        User actual = service.register(testUser);
        assertEquals(testUser, actual);
    }

    @Test
    void register_ageMoreThenMin_ok() {
        testUser.setAge(AGE_MORE_THEN_MIN);
        User actual = service.register(testUser);
        assertEquals(testUser, actual);
    }

    @AfterEach
    void afterEach() {
        Storage.people.clear();
    }
}
