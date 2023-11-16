package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDaoImpl storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    void nullValue_notOk() {
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void emptyLogin_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(new User("", "password", 30));
        });
    }

    @Test
    void emptyPassword_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(new User("Germiona", "", 23));
        });
    }

    @Test
    void nullAge_notOk() {
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(new User("Germiona", "Grenger", null));
        });
    }

    @Test
    void emptyLoginAndPassword_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(new User("", "", 35));
        });
    }

    @Test
    void emptyLoginAndAge_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(new User("", "Grenger", null));
        });
    }

    @Test
    void emptyLoginAndPasswordWithNullAge_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(new User("", "", null));
        });
    }

    @Test
    void emptyPasswordAndAge_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(new User("Germiona", "", null));
        });
    }

    @Test
    void nullLogin_notOk() {
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(new User(null, "Grenger", 25));
        });
    }

    @Test
    void nullPassword_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(new User("Germiona", null, 25));
        });
    }

    @Test
    void nullLoginAndPassword_notOk() {
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(new User(null, null, 25));
        });
    }

    @Test
    void nullLoginAndAge_notOk() {
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(new User(null, "Grenger", null));
        });
    }

    @Test
    void nullPasswordAndAge_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(new User("Germiona", null, null));
        });
    }

    @Test
    void emptyUser_notOkay() {
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(new User());
        });
    }

    @Test
    void registerUserWithExistedLogin() {
        User existedUser = new User("Germiona", "Grenger", 23);
        storageDao.add(existedUser);

        User userWithExistedLogin = new User("Germiona", "Tayler", 30);

        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(userWithExistedLogin);
        });
    }

    @Test
    void registerUserWithShortLogin() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(new User("Fin", "Taylor", 25));
        });
    }

    @Test
    void registerUserWithShortPassword() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(new User("Germiona", "Bin", 32));
        });
    }

    @Test
    void registerUserWithSmallAge() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(new User("Germiona", "Grenger", 10));
        });
    }

    @Test
    void checkDoExistUser() {
        User userForRegister = new User("Germiona", "Grenger", 23);
        registrationService.register(userForRegister);

        User checkUser = storageDao.get("Germiona");
        assertEquals(userForRegister, checkUser);
    }
}
