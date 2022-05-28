package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private StorageDao storage = new StorageDaoImpl();
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_addToStorage_Ok() {
        User firstUser = new User();
        firstUser.setLogin("drapail");
        User actual = storage.add(firstUser);
        User expected = firstUser;
        assertEquals(expected, actual);
    }

    @Test
    void register_getExistingUserFromStorage_Ok() {
        User firstUser = new User();
        firstUser.setLogin("drapail");
        storage.add(firstUser);
        User actual = storage.get("drapail");
        User expected = firstUser;
        assertEquals(expected, actual);
    }

    @Test
    public void register_duplicateLogin_notOk() {
        User firstUser = new User();
        User secondUser = new User();
        firstUser.setLogin("drapail");
        secondUser.setLogin("drapail");
        storage.add(firstUser);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(secondUser);
        });
    }

    @Test
    void register_underageUser_notOk() {
        User firstUser = new User();
        firstUser.setLogin("drapail");
        firstUser.setAge(4);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(firstUser);
        });
    }

    @Test
    void register_nullAgeUser_notOk() {
        User firstUser = new User();
        firstUser.setLogin("drapail");
        firstUser.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(firstUser);
        });
    }

    @Test
    void register_nullLoginUser_notOk() {
        User firstUser = new User();
        firstUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(firstUser);
        });
    }

    @Test
    void register_weakPassword_notOk() {
        User firstUser = new User();
        firstUser.setLogin("drapail");
        firstUser.setPassword("qwert");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(firstUser);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        User firstUser = new User();
        firstUser.setLogin("drapail");
        firstUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(firstUser);
        });
    }

    @Test
    void register_addManyUsers_Ok() {
        for (int i = 0; i < 10_000; i++) {
            User user = new User();
            user.setLogin("USER_" + i);
            user.setPassword("QWERTY_" + i);
            user.setAge(22);
            registrationService.register(user);
        }
        assertEquals(10_000, Storage.people.size());
    }
}
