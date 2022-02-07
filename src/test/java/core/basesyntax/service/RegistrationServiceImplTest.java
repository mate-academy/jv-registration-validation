package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationServiceImpl;
    private static StorageDaoImpl storageDao = new StorageDaoImpl();
    private User userTest;

    @BeforeAll
    static void beforeAll() {
        registrationServiceImpl = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        userTest = new User();
        userTest.setLogin("userTest");
        userTest.setAge(20);
        userTest.setPassword("123456");
    }

    @Test
    void register_userNull_notOk() {
        assertThrows(RuntimeException.class, () ->
                registrationServiceImpl.register(null));
    }

    @Test
    void register_loginNull_notOk() {
        User currentUser = userTest;
        currentUser.setLogin(null);
        assertThrows(RuntimeException.class, () ->
                registrationServiceImpl.register(currentUser));
    }

    @Test
    void register_loginEmpty_notOk() {
        User currentUser = userTest;
        currentUser.setLogin("");
        assertThrows(RuntimeException.class, () ->
                registrationServiceImpl.register(currentUser));
    }

    @Test
    void register_loginExistsInStorage_notOk() {
        User currentUser = userTest;
        currentUser.setLogin("098765");
        registrationServiceImpl.register(currentUser);
        assertThrows(RuntimeException.class, () ->
                registrationServiceImpl.register(currentUser));
    }

    @Test
    void register_loginNew_ok() {
        User expectedUser = registrationServiceImpl.register(userTest);
        User actualUser = storageDao.get(userTest.getLogin());
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void register_ageNull_notOk() {
        User currentUser = userTest;
        currentUser.setAge(null);
        assertThrows(RuntimeException.class, () ->
                registrationServiceImpl.register(currentUser));
    }

    @Test
    void register_agePositive_notOk() {
        User currentUser = userTest;
        currentUser.setAge(10);
        assertThrows(RuntimeException.class, () ->
                registrationServiceImpl.register(currentUser));
    }

    @Test
    void register_ageNegative_notOk() {
        User currentUser = userTest;
        currentUser.setAge(-10);
        assertThrows(RuntimeException.class, () ->
                registrationServiceImpl.register(currentUser));
    }

    @Test
    void register_passwordNull_notOk() {
        User currentUser = userTest;
        currentUser.setPassword(null);
        assertThrows(RuntimeException.class, () ->
                registrationServiceImpl.register(currentUser));
    }

    @Test
    void register_passwordToShort_notOk() {
        User currentUser = userTest;
        currentUser.setPassword("12345");
        assertThrows(RuntimeException.class, () ->
                registrationServiceImpl.register(currentUser));
    }
}