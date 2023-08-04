package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private User validUser = new User();

    @BeforeEach
    void setUp() {
        validUser.setLogin("Mutaborrr");
        validUser.setAge(43);
        validUser.setPassword("19800527");
    }

    @AfterEach
    void disconnect() {
        Storage.people.clear();
    }

    @Test
    void nullUser_NotOk() {
        validUser = null;
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void nullPassword_NotOk() {
        validUser.setPassword(null);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void nullLogin_NotOk() {
        validUser.setLogin(null);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void nullAge_NotOk() {
        validUser.setAge(null);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }

        @Test
        void registrationSuccess() {
            User actual = registrationService.register(validUser);
            assertNotNull(actual);
            StorageDao storageDao = new StorageDaoImpl();
            String actualLogin = storageDao.get(validUser.getLogin()).getLogin();
            assertEquals(validUser.getLogin(), actualLogin);
        }

    @Test
    void userAlreadyExists_NotOk() {
        User userWithSameLogin = new User();
        userWithSameLogin.setLogin("Mutaborrr");
        userWithSameLogin.setAge(43);
        userWithSameLogin.setPassword("19800527");
        Storage.people.add(userWithSameLogin);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }
        @Test
        void userLoginLength_NotOk() {
            validUser.setLogin("");
            assertThrows(UserRegistrationException.class, () -> {
                registrationService.register(validUser);
            });
            validUser.setLogin("Mut");
            assertThrows(UserRegistrationException.class, () -> {
                registrationService.register(validUser);
            });
            validUser.setLogin("Mut12");
            assertThrows(UserRegistrationException.class, () -> {
                registrationService.register(validUser);
            });
        }

        @Test
        void userLoginLength_Ok() {
            validUser.setLogin("Mutab1");
            User actual = registrationService.register(validUser);
            assertNotNull(actual);
            Storage.people.clear();
            validUser.setLogin("Mutaborrrrr");
            actual = registrationService.register(validUser);
            assertNotNull(actual);
        }

    @Test
    void userPasswordLength_Ok() {
        validUser.setPassword("272727");
        User actual = registrationService.register(validUser);
        assertNotNull(actual);
        Storage.people.clear();
        validUser.setPassword("2727272727272727");
        actual = registrationService.register(validUser);
        assertNotNull(actual);
    }

    @Test
    void userPasswordLength_NotOk() {
        validUser.setPassword("");
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(validUser);
        });
        validUser.setPassword("27");
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(validUser);
        });
        validUser.setPassword("27272");
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void notOldEnoughToAccess_NotOk() {
        validUser.setAge(0);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(validUser);
        });
        validUser.setAge(10);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(validUser);
        });

        validUser.setAge(17);
        assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void OldEnoughToAccess_Ok() {
        validUser.setAge(43);
        User actual = registrationService.register(validUser);
        assertNotNull(actual);
    }
}