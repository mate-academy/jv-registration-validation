package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidDataForRegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User user;
    private static StorageDao storageDao;
    private static User anotherUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        anotherUser = new User();
        anotherUser.setLogin("bobik");
        anotherUser.setAge(30);
        anotherUser.setPassword("idk1488");
        user = new User();
        user.setPassword("popbob1337");
        user.setAge(20);
        user.setLogin("bobcarlson");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(InvalidDataForRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageIsLessThan18_notOk() {
        user.setAge(15);
        assertThrows(InvalidDataForRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(InvalidDataForRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_loginIsEmpty_notOk() {
        user.setLogin("");
        assertThrows(InvalidDataForRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordIsLessThanSixCharacters_notOk() {
        user.setPassword("12js");
        assertThrows(InvalidDataForRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordIsNull_notOk() {
        user.setPassword(null);
        assertThrows(InvalidDataForRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageIsNegative_notOk() {
        user.setAge(-20);
        assertThrows(InvalidDataForRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageIsGreaterThan130_notOk() {
        user.setAge(500);
        assertThrows(InvalidDataForRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_loginAlreadyExistsInTheStorage_notOk() {
        User storageUser = new User();
        storageUser.setLogin(user.getLogin());
        storageUser.setAge(user.getAge());
        storageUser.setPassword(user.getPassword());
        Storage.people.add(storageUser);
        assertThrows(InvalidDataForRegistrationException.class, () -> {
            registrationService.register(user);
        }, InvalidDataForRegistrationException.class.getName()
                + ", method does not allow login duplicates");
    }
}
