package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();
    private final User validTestUser = new User();
    private final User testUserWithInvalidLogin = new User();
    private final User testUserWithInvalidAge = new User();
    private final User testUserWithInvalidPassword = new User();
    private final Long validTestId = 1L;
    private final Integer validTestAge = 20;
    private final Integer invalidTestAge = 10;
    private final String validTestLogin = "testLogin";
    private final String uniqueTestLogin = validTestLogin + "unique";
    private final String invalidTestLogin = "testLogin";
    private final String validTestPassword = "testPassword";
    private final String invalidTestPassword = "test";

    @BeforeEach
    public void initializeUsers() {
        validTestUser.setId(validTestId);
        validTestUser.setAge(validTestAge);
        validTestUser.setLogin(uniqueTestLogin);
        validTestUser.setPassword(validTestPassword);

        testUserWithInvalidLogin.setId(validTestId + 1);
        testUserWithInvalidLogin.setLogin(invalidTestLogin);
        testUserWithInvalidLogin.setAge(validTestAge);
        testUserWithInvalidLogin.setPassword(validTestPassword);

        testUserWithInvalidAge.setId(validTestId + 2);
        testUserWithInvalidAge.setAge(invalidTestAge);
        testUserWithInvalidAge.setLogin(validTestLogin + "b");
        testUserWithInvalidAge.setPassword(validTestPassword);

        testUserWithInvalidPassword.setId(validTestId + 3);
        testUserWithInvalidPassword.setAge(validTestAge);
        testUserWithInvalidPassword.setLogin(validTestLogin);
        testUserWithInvalidPassword.setPassword(invalidTestPassword);

    }

    @Test
    public void testRegisterValidUserIsOk() {
        registrationService.register(validTestUser);
        User user = storageDao.get(uniqueTestLogin);
        Assertions.assertNotNull(user);
    }

    @Test
    public void testRegisterWithInvalidAgeFails() {
        Assertions.assertThrows(RuntimeException.class,
                () -> registrationService.register(testUserWithInvalidAge));
    }

    @Test
    public void testRegisterWithInvalidPasswordFails() {
        Assertions.assertThrows(RuntimeException.class,
                () -> registrationService.register(testUserWithInvalidPassword));
    }

    @Test
    public void testRegisterWithInvalidLoginFails() {
        final String notUniqueLogin = "notUniqueLogin";
        User testUser = validTestUser;
        testUser.setLogin(notUniqueLogin);
        storageDao.add(testUser);
        testUserWithInvalidLogin.setLogin(notUniqueLogin);
        Assertions.assertThrows(RuntimeException.class,
                () -> registrationService.register(testUserWithInvalidLogin));
    }

    @Test
    public void testRegisterWithNullAgeFails() {
        testUserWithInvalidAge.setAge(null);
        Assertions.assertThrows(RuntimeException.class,
                () -> registrationService.register(testUserWithInvalidAge));
    }

    @Test
    public void testRegisterWithNullPasswordFails() {
        testUserWithInvalidPassword.setPassword(null);
        Assertions.assertThrows(RuntimeException.class,
                () -> registrationService.register(testUserWithInvalidPassword));
    }

    @Test
    public void testRegisterWithNullLoginFails() {
        testUserWithInvalidLogin.setLogin(null);
        Assertions.assertThrows(RuntimeException.class,
                () -> registrationService.register(testUserWithInvalidLogin));
    }

    @Test
    public void testRegisterUserIsNullFails() {
        Assertions.assertThrows(RuntimeException.class,
                () -> registrationService.register(null));
    }
}
