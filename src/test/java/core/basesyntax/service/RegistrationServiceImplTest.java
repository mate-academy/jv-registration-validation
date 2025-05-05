package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl testRegistrationService;

    @BeforeEach
    void setUp() {
        testRegistrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullLogin_notOk() {
        User testUserNew = getNewUser();
        testUserNew.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            testRegistrationService.register(testUserNew);
        });
    }

    @Test
    void register_emptyLogin_notOk() {
        User testUserNew = getNewUser();
        testUserNew.setLogin("");
        assertThrows(RuntimeException.class, () -> {
            testRegistrationService.register(testUserNew);
        });
    }

    @Test
    void register_firstNumberInLogin_notOk() {
        User testUserNew = getNewUser();
        testUserNew.setLogin("1");
        assertThrows(RuntimeException.class, () -> {
            testRegistrationService.register(testUserNew);
        });
        testUserNew.setLogin("911");
        assertThrows(RuntimeException.class, () -> {
            testRegistrationService.register(testUserNew);
        });
    }

    @Test
    void register_userAddedToEmptyStorage_Ok() {
        User testUserNew = getNewUser();
        User actual = testRegistrationService.register(testUserNew);
        assertEquals(testUserNew, actual, "Unable to add user");
        assertEquals(1, Storage.people.size(), "Storage is empty");
        assertNotEquals(null, testUserNew.getId(), "Id no added after register");
        assertEquals(testUserNew,
                testRegistrationService.getStorageDao().get(testUserNew.getLogin()),
                "User not found in storage after add");
    }

    @Test
    void register_userAddedToFullStorage_Ok() {
        fillStorage();
        int oldStorageSize = Storage.people.size();
        User testUserNew = getNewUser();
        User actual = testRegistrationService.register(testUserNew);
        assertEquals(testUserNew, actual, "Unable to add user");
        assertEquals(oldStorageSize + 1, Storage.people.size(), "User not added to storage");
        assertNotEquals(null, testUserNew.getId(), "Id no added after register");
        assertEquals(testUserNew,
                testRegistrationService.getStorageDao().get(testUserNew.getLogin()),
                "User not found in storage after add");
    }

    @Test
    void register_userAlreadyExists_notOk() {
        fillStorage();
        User testUserExist = getExistUser();
        assertThrows(RuntimeException.class, () -> {
            testRegistrationService.register(testUserExist);
        });
        User testUser = getNewUser();
        testUser.setLogin(testUserExist.getLogin());
        assertThrows(RuntimeException.class, () -> {
            testRegistrationService.register(testUser);
        });
    }

    @Test
    void register_userOver18Age_Ok() {
        User testUserNew = getNewUser();
        testUserNew.setAge(18);
        User actual = testRegistrationService.register(testUserNew);
        assertEquals(testUserNew, actual, "Unable to add user 18 years old");
        testUserNew = getNewUser();
        testUserNew.setLogin("Grisha");
        testUserNew.setAge(38);
        actual = testRegistrationService.register(testUserNew);
        assertEquals(testUserNew, actual, "Unable to add user 38 years old");
    }

    @Test
    void register_userLeast18Age_NotOk() {
        User testUserNew = getNewUser();
        testUserNew.setAge(15);
        assertThrows(RuntimeException.class, () -> {
            testRegistrationService.register(testUserNew);
        });
        testUserNew.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            testRegistrationService.register(testUserNew);
        });
    }

    @Test
    void register_nullAge_notOk() {
        User testUserNew = getNewUser();
        testUserNew.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            testRegistrationService.register(testUserNew);
        });
    }

    @Test
    void register_userNegativeAge_NotOk() {
        User testUserNew = getNewUser();
        testUserNew.setAge(-18);
        assertThrows(RuntimeException.class, () -> {
            testRegistrationService.register(testUserNew);
        });
    }

    @Test
    void register_userZeroAge_NotOk() {
        User testUserNew = getNewUser();
        testUserNew.setAge(0);
        assertThrows(RuntimeException.class, () -> {
            testRegistrationService.register(testUserNew);
        });
    }

    @Test
    void register_passwordOver6Characters_Ok() {
        User testUserNew = getNewUser();
        testUserNew.setPassword("990990");
        User actual = testRegistrationService.register(testUserNew);
        assertEquals(testUserNew, actual, "Unable to add user 6 characters in password");
        testUserNew = getNewUser();
        testUserNew.setLogin("Grisha");
        testUserNew.setPassword("7990990");
        actual = testRegistrationService.register(testUserNew);
        assertEquals(testUserNew, actual, "Unable to add user 7 characters in password");
    }

    @Test
    void register_passwordLeast6Characters_NotOk() {
        User testUserNew = getNewUser();
        testUserNew.setPassword("39099");
        assertThrows(RuntimeException.class, () -> {
            testRegistrationService.register(testUserNew);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        User testUserNew = getNewUser();
        testUserNew.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            testRegistrationService.register(testUserNew);
        });
    }

    private void fillStorage() {
        StorageDao storageDao = testRegistrationService.getStorageDao();
        storageDao.add(getExistUser());
        User testUser2 = new User();
        testUser2.setAge(29);
        testUser2.setLogin("Luka199");
        testUser2.setPassword("2990990");
        storageDao.add(testUser2);
        User testUser3 = new User();
        testUser3.setAge(39);
        testUser3.setLogin("Kate");
        testUser3.setPassword("3990990");
        storageDao.add(testUser3);
    }

    private User getNewUser() {
        User testUserNew = new User();
        testUserNew.setAge(19);
        testUserNew.setLogin("Misha");
        testUserNew.setPassword("7990990");
        return testUserNew;
    }

    private User getExistUser() {
        User testUserExist = new User();
        testUserExist.setAge(19);
        testUserExist.setLogin("Yuli1");
        testUserExist.setPassword("1990990");
        return testUserExist;
    }
}
