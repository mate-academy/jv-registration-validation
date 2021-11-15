package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User testUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        testUser = new User();
        testUser.setPassword("valid_password");
        testUser.setLogin("valid_login");
        testUser.setAge(21);
    }

    @Test
    void registerUserWithNullValue_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void registerUserWithAgeNull_NotOk() {
        testUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser),
                "Null age shouldn't be accepted.");
    }

    @Test
    void registerUserWithLoginNull_NotOk() {
        testUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser),
                "Null login shouldn't be accepted.");
    }

    @Test
    void registerUserWithPasswordNull_NotOk() {
        testUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser),
                "Null password shouldn't be accepted.");
    }


    @Test
    void registerUserWithAllFieldsValueNull_NotOk() {
        testUser.setAge(null);
        testUser.setLogin(null);
        testUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser),
                "Null fields shouldn't be accepted.");
    }

    @Test
    void registerUserWithAgeLessThan18_NotOk() {
        testUser.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser),
                "Users shouldn't be added with the age less than 18.");
    }

    @Test
    void AgeEqualIntMaxValue_Ok() {
        testUser.setAge(Integer.MAX_VALUE);
        assertDoesNotThrow(() -> registrationService.register(testUser),
                "User adding must be possible with the age equal integer MAX_VALUE. ");
    }

    @Test
    void passwordIsLessThanMinValue_NotOk() {
        testUser.setPassword("12345");
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser),
                "Password shorter than 6 characters must not be accepted.");
    }

    @Test
    void userIsAlreadyExist_NotOk() {
        StorageDao storageDao = new StorageDaoImpl();
        storageDao.add(testUser);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser),
                "Users shouldn't be added with the same login.");
    }

    @Test
    void registerUserWithValidValues_Ok() {
        User addedUser = registrationService.register(testUser);
        assertEquals(testUser, addedUser,
                "Method register must return added user.");
        StorageDao storageDao = new StorageDaoImpl();
        String loginOfAddedUser = addedUser.getLogin();
        User actual = storageDao.get(loginOfAddedUser);
        assertEquals(testUser, actual,
                "User must be in the storage after adding.");
    }
}