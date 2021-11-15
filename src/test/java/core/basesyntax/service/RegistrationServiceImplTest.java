package core.basesyntax.service;

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
    void register_nullValue_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_userWithNullAge_NotOk() {
        testUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser),
                "Null age shouldn't be accepted.");
    }

    @Test
    void register_userWithNullLogin_NotOk() {
        testUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser),
                "Null login shouldn't be accepted.");
    }

    @Test
    void register_userWithNullPassword_NotOk() {
        testUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser),
                "Null password shouldn't be accepted.");
    }


    @Test
    void register_userWithNullValues_NotOk() {
        testUser.setAge(null);
        testUser.setLogin(null);
        testUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser),
                "Null fields shouldn't be accepted.");
    }

    @Test
    void register_userWithAgeLessThan18_NotOk() {
        testUser.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser),
                "Users shouldn't be added with the age less than 18.");
    }

    @Test
    void register_ageEqualIntMaxValue_Ok() {
        testUser.setAge(Integer.MAX_VALUE);
        assertDoesNotThrow(() -> registrationService.register(testUser),
                "User adding must be possible with the age equal integer MAX_VALUE. ");
    }

    @Test
    void register_userWithPasswordLessThanMinValue_NotOk() {
        testUser.setPassword("12345");
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser),
                "Password shorter than 6 characters must not be accepted.");
    }

    @Test
    void register_userIsAlreadyExist_NotOk() {
        Storage.people.add(testUser);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser),
                "Users shouldn't be added with the same login.");
    }

    @Test
    void register_userWithValidValues_Ok() {
        User addedUser = registrationService.register(testUser);
        assertEquals(testUser, addedUser,
                "Method register must return added user.");
        int storageActualSize = Storage.people.size();
        assertEquals(1, storageActualSize,
                "Storage size must be increased after adding new User.");
        User actual = Storage.people.get(0);
        assertEquals(testUser, actual,
                "User must be in the storage after adding.");
    }
}
