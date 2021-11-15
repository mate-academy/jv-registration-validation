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
    private static RegistrationServiceImpl registrationService;
    private static StorageDaoImpl storageDao;
    private static User firstUser;
    private static User secondUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        firstUser = new User();
        firstUser.setLogin("PerfectLogin");
        firstUser.setPassword("Perfect!94?Password");
        firstUser.setAge(19);
        storageDao = new StorageDaoImpl();
        storageDao.add(firstUser);
        secondUser = new User();
    }

    @BeforeEach
    void setUp() {
        secondUser.setLogin("OtherLogin");
        secondUser.setPassword("OtherPassword");
        secondUser.setAge(20);
    }

    @Test
    public void register_User_Ok() {
        Assertions.assertNotNull(registrationService.register(secondUser));
        Storage.people.clear();
        storageDao.add(firstUser);
    }

    @Test
    public void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullAge_notOk() {
        secondUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(secondUser));
    }

    @Test
    void register_nullLogin_notOk() {
        secondUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(secondUser));
    }

    @Test
    void register_nullPassword_notOk() {
        secondUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(secondUser));
    }

    @Test
    public void register_ageLessThatZero_notOk() {
        secondUser.setAge(-19);
        assertThrows(RuntimeException.class, () -> registrationService.register(secondUser));
    }

    @Test
    void register_invalidAge_notOk() {
        secondUser.setAge(10);
        assertThrows(RuntimeException.class, () -> registrationService.register(secondUser));
    }

    @Test
    void register_sameLoginRegister_notOk() {
        secondUser.setLogin("PerfectLogin");
        assertThrows(RuntimeException.class, () -> registrationService.register(secondUser));
    }

    @Test
    void register_tooShortPassword_notOk() {
        secondUser.setPassword("12345");
        assertThrows(RuntimeException.class, () -> registrationService.register(secondUser));
    }

}
