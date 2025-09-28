package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final StorageDao storageDaoImpl = new StorageDaoImpl();
    private static final RegistrationService registrationServiceImpl
            = new RegistrationServiceImpl();

    @BeforeEach
    public void preparePeopleStorage() {
        Storage.people.clear();
        User user = new User();
        user.setLogin("Benson");
        user.setId(1234567890L);
        user.setAge(20);
        user.setPassword("123456qwe");
        Storage.people.add(user);
    }

    @Test
    public void register_nullUser_notOk() {
        assertThrows(InvalidateUserData.class, () -> {
            registrationServiceImpl.register(null);
        });
    }

    @Test void register_userAlreadyExists_notOk() {
        User user = new User();
        user.setLogin("Benson");
        user.setId(1234567890L);
        user.setAge(20);
        user.setPassword("123456qwe");
        assertThrows(InvalidateUserData.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test void register_userNotExists_ok() {
        User user = new User();
        user.setLogin("qwertyui");
        user.setId(1234567890L);
        user.setAge(20);
        user.setPassword("123456qwe");
        assertDoesNotThrow(() -> {
            registrationServiceImpl.register(user);
        });
        assertNotNull(storageDaoImpl.get(user.getLogin()));
    }

    @Test
    public void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setId(1234567890L);
        user.setAge(20);
        user.setPassword("123456qwe");
        assertThrows(InvalidateUserData.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    public void register_emptyLogin_notOk() {
        User user = new User();
        user.setLogin("");
        user.setId(1234567890L);
        user.setAge(20);
        user.setPassword("123456qwe");
        assertThrows(InvalidateUserData.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    public void register_5lengthLogin_notOk() {
        User user = new User();
        user.setLogin("qwert");
        user.setId(1234567890L);
        user.setAge(20);
        user.setPassword("123456qwe");
        assertThrows(InvalidateUserData.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    public void register_6lengthLogin_ok() {
        User user = new User();
        user.setLogin("qwerty");
        user.setId(1234567890L);
        user.setAge(20);
        user.setPassword("123456qwe");
        assertDoesNotThrow(() -> {
            registrationServiceImpl.register(user);
        });
        assertNotNull(storageDaoImpl.get(user.getLogin()));
    }

    @Test
    public void register_10lengthLogin_ok() {
        User user = new User();
        user.setLogin("qwertasdfg");
        user.setId(1234567890L);
        user.setAge(20);
        user.setPassword("123456qwe");
        assertDoesNotThrow(() -> {
            registrationServiceImpl.register(user);
        });
        assertNotNull(storageDaoImpl.get(user.getLogin()));
    }

    @Test
    public void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("qwertasdfg");
        user.setId(1234567890L);
        user.setAge(20);
        user.setPassword(null);
        assertThrows(InvalidateUserData.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    public void register_emptyPassword_notOk() {
        User user = new User();
        user.setLogin("qwertasdfg");
        user.setId(1234567890L);
        user.setAge(20);
        user.setPassword("");
        assertThrows(InvalidateUserData.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    public void register_5lengthPassword_notOk() {
        User user = new User();
        user.setLogin("qwertasdfg");
        user.setId(1234567890L);
        user.setAge(20);
        user.setPassword("q2w4t");
        assertThrows(InvalidateUserData.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    public void register_6lengthPassword_ok() {
        User user = new User();
        user.setLogin("qwertasdfg");
        user.setId(1234567890L);
        user.setAge(20);
        user.setPassword("qwe123");
        assertDoesNotThrow(() -> {
            registrationServiceImpl.register(user);
        });
        assertNotNull(storageDaoImpl.get(user.getLogin()));
    }

    @Test
    public void register_10lengthPassword_ok() {
        User user = new User();
        user.setLogin("qwertasdfg");
        user.setId(1234567890L);
        user.setAge(20);
        user.setPassword("qwe123f52z");
        assertDoesNotThrow(() -> {
            registrationServiceImpl.register(user);
        });
        assertNotNull(storageDaoImpl.get(user.getLogin()));
    }

    @Test
    public void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("qwertasdfg");
        user.setId(1234567890L);
        user.setAge(null);
        user.setPassword("123456qwe");
        assertThrows(InvalidateUserData.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    public void register_negativeAge_NoOk() {
        User user = new User();
        user.setLogin("qwertasdfg");
        user.setId(1234567890L);
        user.setAge(-15);
        user.setPassword("123456qwe");
        assertThrows(InvalidateUserData.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    public void register_zeroAge_NoOk() {
        User user = new User();
        user.setLogin("qwertasdfg");
        user.setId(1234567890L);
        user.setAge(0);
        user.setPassword("123456qwe");
        assertThrows(InvalidateUserData.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    public void register_below18Age_NoOk() {
        User user = new User();
        user.setLogin("qwertasdfg");
        user.setId(1234567890L);
        user.setAge(17);
        user.setPassword("123456qwe");
        assertThrows(InvalidateUserData.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    public void register_equal18_ok() {
        User user = new User();
        user.setLogin("qwertasdfg");
        user.setId(1234567890L);
        user.setAge(18);
        user.setPassword("123456qwe");
        assertDoesNotThrow(() -> {
            registrationServiceImpl.register(user);
        });
        assertNotNull(storageDaoImpl.get(user.getLogin()));
    }

    @Test
    public void register_above18Age_ok() {
        User user = new User();
        user.setLogin("qwertasdfg");
        user.setId(1234567890L);
        user.setAge(30);
        user.setPassword("123456qwe");
        assertDoesNotThrow(() -> {
            registrationServiceImpl.register(user);
        });
        assertNotNull(storageDaoImpl.get(user.getLogin()));
    }
}
