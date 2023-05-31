package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static User user;
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    static void setUp() {
        user = new User();
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    void userAdded_Ok() {
        user.setLogin("Jac");
        user.setAge(21);
        user.setId(15L);
        user.setPassword("Test123");
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void userIsNull_NotOk() {
        assertThrows(InvalidDataException.class, () -> registrationService.register(null));
    }

    @Test
    void userYoungerEighteen_NotOk() {
        user.setLogin("LilJac");
        user.setAge(15);
        user.setId(15L);
        user.setPassword("Test123");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        user.setLogin("Jac");
        user.setAge(17);
        user.setId(15L);
        user.setPassword("Test123");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void userNegativeAge_NotOk() {
        user.setLogin("NegativeJac");
        user.setAge(-15);
        user.setId(15L);
        user.setPassword("Test123");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void userAgeNull_NotOk() {
        user.setLogin("Jac");
        user.setId(15L);
        user.setPassword("Test123");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void userPasswordNull_NotOk() {
        user.setLogin("Jac");
        user.setAge(20);
        user.setId(15L);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void shortPassword_NotOk() {
        user.setLogin("Jac");
        user.setAge(20);
        user.setId(15L);
        user.setPassword("123");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        user.setLogin("Test");
        user.setAge(20);
        user.setId(15L);
        user.setPassword("12345");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void loginNull_NotOk() {
        user.setAge(20);
        user.setId(15L);
        user.setPassword("123456");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void userExist_NotOk() {
        user.setLogin("Test");
        user.setAge(21);
        user.setId(15L);
        user.setPassword("Test123");
        storageDao.add(user);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        User user1 = new User();
        user1.setLogin("Test");
        user1.setAge(45);
        user1.setId(19L);
        user1.setPassword("1234567");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user1));
    }
}
