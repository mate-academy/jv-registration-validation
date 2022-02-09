package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static RegistrationServiceImpl registrationService;
    private static User testedUser;
    private static StorageDao storageDao;

    @BeforeEach
    void setUp() {
        testedUser = new User();
        Storage.people.clear();
        testedUser.setAge(27);
        testedUser.setLogin("thisIsUserLogin");
        testedUser.setPassword("password");
    }

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    public void register_user_Ok() {
        User user = registrationService.register(testedUser);
        Assertions.assertEquals(testedUser, user);
    }

    @Test
    void register_userExist_notOk() {
        storageDao.add(testedUser);
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_returnNull_notOk() {
        assertNotEquals(registrationService.register(testedUser), null);
    }

    @Test
    public void register_ageLessThan18_notOk() {
        testedUser.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    public void register_negativeAge_notOk() {
        testedUser.setAge(-5);
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_nullAge_notOk() {
        testedUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_nullPassword_notOk() {
        testedUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_passwordIsLessThan6_notOk() {
        testedUser.setPassword("passw");
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_passwordIsEmpty_notOk() {
        testedUser.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_nullLogin_notOk() {
        testedUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_loginIsEmpty_notOk() {
        testedUser.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_loginContainsSpace_notOk() {
        testedUser.setLogin("Login login");
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }
}
