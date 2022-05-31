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
    void register_validUser_Ok() {
        User firstUser = new User();
        firstUser.setLogin("drapail");
        firstUser.setPassword("drapail123");
        firstUser.setAge(27);
        User actual = registrationService.register(firstUser);
        User expected = firstUser;
        assertEquals(expected, actual);
    }

    @Test
    public void register_duplicateLogin_notOk() {
        User firstUser = new User();
        firstUser.setLogin("drapail");
        firstUser.setPassword("drapail123");
        firstUser.setAge(27);
        User secondUser = new User();
        secondUser.setLogin("drapail");
        secondUser.setPassword("drapail123");
        secondUser.setAge(27);
        storage.add(firstUser);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(secondUser);
        });
    }

    @Test
    void register_underageUser_notOk() {
        User firstUser = new User();
        firstUser.setLogin("drapail");
        firstUser.setPassword("drapail123");
        firstUser.setAge(4);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(firstUser);
        });
    }

    @Test
    void register_nullAgeUser_notOk() {
        User firstUser = new User();
        firstUser.setLogin("drapail");
        firstUser.setPassword("drapail123");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(firstUser);
        });
    }

    @Test
    void register_nullLoginUser_notOk() {
        User firstUser = new User();
        firstUser.setPassword("drapail123");
        firstUser.setAge(27);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(firstUser);
        });
    }

    @Test
    void register_weakPassword_notOk() {
        User firstUser = new User();
        firstUser.setLogin("drapail");
        firstUser.setPassword("qwert");
        firstUser.setAge(27);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(firstUser);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        User firstUser = new User();
        firstUser.setLogin("drapail");
        firstUser.setAge(27);
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
