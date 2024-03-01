package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "ReamFOX";
    private static final String VALID_PASSWORD = "razoqewrdsfe";
    private static final int VALID_AGE = 25;
    private StorageDao storageDao;
    private RegistrationService registrationService;
    private User testUser;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
        testUser = new User();
        testUser.setLogin(VALID_LOGIN);
        testUser.setPassword(VALID_PASSWORD);
        testUser.setAge(VALID_AGE);
        Storage.people.clear();
    }

    @Test
    void register_validUser_successfulRegistration() {
        Storage.people.add(testUser);
        assertEquals(storageDao.get(testUser.getLogin()), testUser);
    }

    @Test
    void register_existingUser_notOk() {
        registrationService.register(testUser);
        assertInvalidDataException(testUser,"User with login "
                + testUser.getLogin()
                + " already exists");
    }

    @Test
    void register_shortLogin_notOk() {
        String expectedMessage = "User login must be at least 6 characters";
        testUser.setLogin("Sasha");
        assertInvalidDataException(testUser,expectedMessage);
    }

    @Test
    void register_shortOrEmptyPassword_notOk() {
        String expectedMessage = "User password must be at least 6 characters";
        testUser.setPassword("pa");
        assertInvalidDataException(testUser,expectedMessage);
        testUser.setPassword("passw");
        assertInvalidDataException(testUser,expectedMessage);
        testUser.setPassword("");
        assertInvalidDataException(testUser,expectedMessage);
    }

    @Test
    void register_underageUser_notOk() {
        String expectedMessage = "User must be at least 18 years old or older";
        testUser.setAge(17);
        assertInvalidDataException(testUser,expectedMessage);
        testUser.setAge(13);
        assertInvalidDataException(testUser,expectedMessage);
    }

    @Test
    void register_NegativeAge_notOk() {
        testUser.setAge(-20);
        assertInvalidDataException(testUser,"User age cannot be less than 0");
    }

    @Test
    void register_nullPassword_notOk() {
        testUser.setPassword(null);
        assertInvalidDataException(testUser,"User password can`t be null");
    }

    private void assertInvalidDataException(User user, String expectedMessage) {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () ->
                registrationService.register(user));
        assertEquals(expectedMessage,exception.getMessage());
    }
}
