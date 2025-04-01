package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationFailedException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storageDao);
    }

    @Test
    void register_nullUserAge_notOk() {
        assertThrows(RegistrationFailedException.class, () -> registrationService
                .register(new User("JohnDoe1995", "john5405", null)));
    }

    @Test
    void register_userAge_notOk() {
        assertThrows(RegistrationFailedException.class, () -> registrationService
                .register(new User("JohnDoe1995", "john5405", 15)));
    }

    @Test
    void register_nullUserPassword_notOk() {
        assertThrows(RegistrationFailedException.class, () -> registrationService
                .register(new User("JohnDoe1995", null, 25)));
    }

    @Test
    void register_edgeUserPassword_notOk() {
        assertThrows(RegistrationFailedException.class, () -> registrationService
                .register(new User("JohnDoe1995", "gdf23", 25)));
    }

    @Test
    void register_nullUserLogin_notOk() {
        assertThrows(RegistrationFailedException.class, () -> registrationService
                .register(new User(null, "john5405", 25)));
    }

    @Test
    void register_edgeUserLogin_notOk() {
        assertThrows(RegistrationFailedException.class, () -> registrationService
                .register(new User("john1", "john5405", 25)));
    }

    @Test
    void register_userLoginDuplicate_notOk() {
        registrationService.register(new User("JohnDoe1995", "john5405", 25));
        assertThrows(RegistrationFailedException.class, () -> registrationService
                .register(new User("JohnDoe1995", "john5405", 25)));
    }

    @Test
    void register_userEdge_oK() {
        User user = new User("John19", "john19", 25);
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void register_user_oK() {
        User user = new User("John19Johnny", "john19re3532", 75);
        registrationService.register(user);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void register_multipleUsers_oK() {
        User userOne = new User("TestLogin1", "testPassword1", 21);
        User userTwo = new User("TestLogin2", "testPassword2", 75);
        User userThree = new User("TestLogin3", "testPassword3", 18);
        User userFour = new User("TestLogin4", "testPassword4", 53);
        User userFive = new User("TestLogin5", "testPassword5", 53);

        registrationService.register(userOne);
        registrationService.register(userTwo);
        registrationService.register(userThree);
        registrationService.register(userFour);
        registrationService.register(userFive);

        assertEquals(userOne, storageDao.get(userOne.getLogin()));
        assertEquals(userTwo, storageDao.get(userTwo.getLogin()));
        assertEquals(userThree, storageDao.get(userThree.getLogin()));
        assertEquals(userFour, storageDao.get(userFour.getLogin()));
        assertEquals(userFive, storageDao.get(userFive.getLogin()));
    }
}
