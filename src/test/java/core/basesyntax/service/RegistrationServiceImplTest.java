package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.UserAddException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static User newUser;

    @BeforeEach
    void setUp() {
        newUser = new User();
        newUser.setId(1L);
        newUser.setLogin("Markus");
        newUser.setPassword("MarkPass");
        newUser.setAge(29);
    }

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    void register_existingUser_notOk() {
        storageDao.add(newUser);
        User existingUser = storageDao.get(newUser.getLogin());
        assertThrows(UserAddException.class, () ->
                registrationService.register(existingUser));
    }

    @Test
    void register_nullUser_notOk() {
        newUser = null;
        assertThrows(UserAddException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void register_nonExistingUser_Ok() {
        User actualUser = registrationService.register(newUser);
        User expectedUser = storageDao.get(newUser.getLogin());
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void register_userLoginShortThen6Symbols_notOk() {
        newUser.setLogin("Mark");
        assertThrows(UserAddException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void register_userLoginEmpty_notOk() {
        newUser.setLogin("");
        assertThrows(UserAddException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void register_userLogin3Symbols_notOk() {
        newUser.setLogin("Mar");
        assertThrows(UserAddException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void register_userLogin5Symbols_notOk() {
        newUser.setLogin("Marku");
        assertThrows(UserAddException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void register_userPasswordShortThen6Symbols_notOk() {
        newUser.setPassword("Mark");
        assertThrows(UserAddException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void register_userPasswordEmpty_notOk() {
        newUser.setPassword("");
        assertThrows(UserAddException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void register_userPassword3Symbols_notOk() {
        newUser.setPassword("Mar");
        assertThrows(UserAddException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void register_userPassword5Symbols_notOk() {
        newUser.setPassword("Marku");
        assertThrows(UserAddException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void register_userUnderage18_notOk() {
        newUser.setAge(17);
        assertThrows(UserAddException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void register_userNegativeAge_notOk() {
        newUser.setAge(-5);
        assertThrows(UserAddException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void register_userCorrectAge_Ok() {
        newUser.setAge(18);
        assertTrue(newUser.getAge() >= 18);
    }

    @Test
    void register_userLoginNull_notOk() {
        newUser.setLogin(null);
        assertThrows(UserAddException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void register_userPasswordNull_notOk() {
        newUser.setPassword(null);
        assertThrows(UserAddException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void register_userAgeNull_notOk() {
        newUser.setAge(null);
        assertThrows(UserAddException.class, () ->
                registrationService.register(newUser));
    }
}
