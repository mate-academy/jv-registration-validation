package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;


class RegistrationServiceTest {
    private static  RegistrationServiceImpl registrationService;
    private static StorageDaoImpl storageDao;
    private static final User user = new User();
    private static final User actual = new User();

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        user.setLogin("PerfectLogin");
        user.setPassword("Perfect!94?Password");
        user.setAge(19);
        storageDao.add(user);
    }

    @BeforeEach
    void setUp() {
        actual.setLogin("OtherLogin");
        actual.setPassword("OtherPassword");
        actual.setAge(20);
    }

    @Test
    public void register_User_Ok() {
        Assertions.assertNotNull(registrationService.register(actual));
        Storage.people.clear();
        storageDao.add(user);
    }

    @Test
    public void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullAge_notOk() {
        actual.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_nullLogin_notOk() {
        actual.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_nullPassword_notOk() {
        actual.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
    public void register_ageLessThatZero_notOk() {
        actual.setAge(-19);
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_invalidAge_notOk() {
        actual.setAge(10);
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_sameLoginRegister_notOk() {
        actual.setLogin("PerfectLogin");
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
    void register_tooShortPassword_notOk() {
        actual.setPassword("12345");
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

}
