package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationServiceException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationServiceObject;
    private static StorageDao storageDaoObject;
    private User userForTest;

    @BeforeAll
    static void beforeAll() {
        registrationServiceObject = new RegistrationServiceImpl();
        storageDaoObject = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        userForTest = new User();
        userForTest.setLogin("fepe5002");
        userForTest.setPassword("admin123");
        userForTest.setAge(37);
    }

    @AfterEach
    void tearDown() {
        if (!Storage.people.isEmpty()) {
            Storage.people.clear();

        }
    }

    @Test
    void register_loginNull_notOk() {
        userForTest.setLogin(null);
        Assertions.assertThrows(RegistrationServiceException.class,
                () -> registrationServiceObject.register(userForTest));
    }

    @Test
    void register_loginIsEmpty_notOk() {
        userForTest.setLogin("");
        Assertions.assertThrows(RegistrationServiceException.class,
                () -> registrationServiceObject.register(userForTest));
    }

    @Test
    void register_loginFirstLetterNotSymbol_notOk() {
        userForTest.setLogin("$shabo4567");
        Assertions.assertThrows(RegistrationServiceException.class,
                () -> registrationServiceObject.register(userForTest));
    }

    @Test
    void register_passwordNull_notOk() {
        userForTest.setPassword(null);
        Assertions.assertThrows(RegistrationServiceException.class,
                () -> registrationServiceObject.register(userForTest));
    }

    @Test
    void register_passwordIsEmpty_notOk() {
        userForTest.setPassword("");
        Assertions.assertThrows(RegistrationServiceException.class,
                () -> registrationServiceObject.register(userForTest));
    }

    @Test
    void register_ageIsNull_notOk() {
        userForTest.setAge(null);
        Assertions.assertThrows(RegistrationServiceException.class,
                () -> registrationServiceObject.register(userForTest));
    }

    @Test
    void register_ageLessMinRegistrationAge_notOk() {
        userForTest.setAge(17);
        Assertions.assertThrows(RegistrationServiceException.class,
                () -> registrationServiceObject.register(userForTest));
    }

    @Test
    void register_ageLessZero_notOk() {
        userForTest.setAge(-17);
        Assertions.assertThrows(RegistrationServiceException.class,
                () -> registrationServiceObject.register(userForTest));
    }

    @Test
    void register_ageMoreMaxAge_notOk() {
        userForTest.setAge(101);
        Assertions.assertThrows(RegistrationServiceException.class,
                () -> registrationServiceObject.register(userForTest));
    }

    @Test
    void register_LoginLengthFirst_notOk() {
        userForTest.setLogin("kyk");
        Assertions.assertThrows(RegistrationServiceException.class,
                () -> registrationServiceObject.register(userForTest));
    }

    @Test
    void register_LoginLengthSecond_notOk() {
        userForTest.setLogin("k7tr");
        Assertions.assertThrows(RegistrationServiceException.class,
                () -> registrationServiceObject.register(userForTest));
    }

    @Test
    void register_PasswordLengthFirst_notOk() {
        userForTest.setPassword("74r");
        Assertions.assertThrows(RegistrationServiceException.class,
                () -> registrationServiceObject.register(userForTest));
    }

    @Test
    void register_PasswordLengthSecond_notOk() {
        userForTest.setPassword("Ger54");
        Assertions.assertThrows(RegistrationServiceException.class,
                () -> registrationServiceObject.register(userForTest));
    }

    @Test
    void register_AddUserWithSuchLogin_notOk() {
        storageDaoObject.add(userForTest);
        Assertions.assertThrows(RegistrationServiceException.class,
                () -> registrationServiceObject
                        .register(storageDaoObject.get(userForTest.getLogin())));
    }

    @Test
    void register_correctUser_ok() throws RegistrationServiceException {
        User userForTest1 = new User();
        userForTest1.setPassword("goverla2061");
        userForTest1.setLogin("userMate");
        userForTest1.setAge(45);

        registrationServiceObject.register(userForTest);
        registrationServiceObject.register(userForTest1);

        Assertions.assertEquals(2, Storage.people.size());
    }

    @Test
    void register_returnUser_ok() throws RegistrationServiceException {
        User userForTest1 = new User();
        userForTest1.setPassword("pipivan2028");
        userForTest1.setLogin("poxidnuk2678");
        userForTest1.setAge(23);

        registrationServiceObject.register(userForTest);
        registrationServiceObject.register(userForTest1);

        Assertions.assertNull(storageDaoObject.get("pokemon1234"));
    }
}
