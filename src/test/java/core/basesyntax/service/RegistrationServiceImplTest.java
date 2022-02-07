package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationServiceImpl;
    User userTest = new User();
    private StorageDaoImpl storageDao = new StorageDaoImpl();
    Storage storage = new Storage();

    @BeforeAll
    static void beforeAll() {
        registrationServiceImpl = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        User userTest = new User();
        userTest.setAge(20);
        userTest.setPassword("123456");
        userTest.setLogin("userTest");
        userTest.setId(9l);
    }

    @Test
    void register_userNull_notOk() {
        assertThrows(RuntimeException.class, () ->
                registrationServiceImpl.register(null));
    }

    @Test
    void register_loginNull_notOk() {
        userTest.setAge(20);
        userTest.setPassword("123456");
        userTest.setLogin(null);
        assertThrows(RuntimeException.class, () ->
                registrationServiceImpl.register(userTest));
    }

    @Test
    void register_loginEmpty_notOk() {
        userTest.setAge(20);
        userTest.setPassword("");
        userTest.setLogin(null);
        assertThrows(RuntimeException.class, () ->
                registrationServiceImpl.register(userTest));
    }

    @Test
    void register_loginNew_ok() {
        userTest.setAge(20);
        userTest.setPassword("123456");
        userTest.setLogin("userTest");
        User expectedUser = registrationServiceImpl.register(userTest);
        User actualUser = storageDao.get(userTest.getLogin());
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void register_loginExistsInStorage_notOk() {
        userTest.setAge(20);
        userTest.setPassword("123456");
        userTest.setLogin("userTest");
        registrationServiceImpl.register(userTest);
        assertThrows(RuntimeException.class, () ->
                registrationServiceImpl.register(userTest));
    }



    @Test
    void register_ageNull_notOk() {
        userTest.setAge(null);
        assertThrows(RuntimeException.class, () ->
                registrationServiceImpl.register(userTest));
    }

    @Test
    void register_agePositive_notOk() {
        userTest.setAge(10);
        assertThrows(RuntimeException.class, () ->
                registrationServiceImpl.register(userTest));
    }

    @Test
    void register_ageNegative_notOk() {
        userTest.setAge(-10);
        assertThrows(RuntimeException.class, () ->
                registrationServiceImpl.register(userTest));
    }

    @Test
    void register_passwordNull_notOk() {
        userTest.setLogin("userTest");
        userTest.setPassword(null);
        assertThrows(RuntimeException.class, () ->
                registrationServiceImpl.register(userTest));
    }

    @Test
    void register_passwordLessThanMin_notOk() {
        userTest.setLogin("userTest");
        userTest.setPassword("123");
        assertThrows(RuntimeException.class, () ->
                registrationServiceImpl.register(userTest));
    }




}