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
        User expected = new User();
        expected.setLogin("drapail");
        expected.setPassword("drapail123");
        expected.setAge(27);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    public void register_nullUser_notOk() {
        User user = null;
        String expectedErrorMessage = "User == null";
        Throwable nullUserException = assertThrows(RuntimeException.class, () -> {
            registrationService.register(user); });
        assertEquals(expectedErrorMessage, nullUserException.getMessage());
    }

    @Test
    public void register_duplicateLogin_notOk() {
        User user = new User();
        user.setLogin("drapail");
        user.setPassword("drapail123");
        user.setAge(27);
        User secondUser = new User();
        secondUser.setLogin("drapail");
        secondUser.setPassword("drapail123");
        secondUser.setAge(27);
        storage.add(user);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(secondUser);
        });
    }

    @Test
    void register_underageUser_notOk() {
        User user = new User();
        user.setLogin("drapail");
        user.setPassword("drapail123");
        user.setAge(4);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullUserAge_notOk() {
        User user = new User();
        user.setLogin("drapail");
        user.setPassword("drapail123");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullUserLogin_notOk() {
        User user = new User();
        user.setPassword("drapail123");
        user.setAge(27);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_weakPassword_notOk() {
        User user = new User();
        user.setLogin("drapail");
        user.setPassword("qwert");
        user.setAge(27);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("drapail");
        user.setAge(27);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
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
