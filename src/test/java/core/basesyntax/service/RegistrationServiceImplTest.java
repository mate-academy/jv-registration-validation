package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.CurrentLoginIsExistsException;
import core.basesyntax.model.InvalidInputDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "programator12341";
    private static final String DEFAULT_PASSWORD = "passatic123";
    private static final int DEFAULT_AGE = 19;
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();
    private final User defaultUser = new User();

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        defaultUser.setAge(18);
        defaultUser.setLogin(DEFAULT_LOGIN);
        defaultUser.setPassword(DEFAULT_PASSWORD);
    }

    @Test
    void checkIfUserIsRegistered_Ok() {
        registrationService.register(defaultUser);
        assertEquals(defaultUser, storageDao.get(defaultUser.getLogin()));
    }

    @Test
    void register_existLogin_notOk() {
        registrationService.register(defaultUser);
        assertThrows(CurrentLoginIsExistsException.class, () ->
                        registrationService.register(defaultUser),
                "CurrentLoginIsExist must thrown if current login is located in storage.");
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidInputDataException.class, () ->
                        registrationService.register(null),
                "InvalidInputData must thrown if user is null or empty.");
    }

    @Test
    void register_nullLogin_notOk() {
        defaultUser.setLogin(null);
        assertThrows(InvalidInputDataException.class, () ->
                        registrationService.register(defaultUser),
                "InvalidInputData must thrown if Login is null or empty.");
    }

    @Test
    void register_nullPassword_notOk() {
        defaultUser.setPassword(null);
        assertThrows(InvalidInputDataException.class, () ->
                        registrationService.register(defaultUser),
                "InvalidInputData must thrown if Password is null or empty.");
    }

    @Test
    void register_shotPassword_notOk() {
        defaultUser.setPassword("short");
        assertThrows(InvalidInputDataException.class, () ->
                        registrationService.register(defaultUser),
                "InvalidInputData must thrown if Password is less than 10 elements.");
    }

    @Test
    void register_shorLogin_notOk() {
        defaultUser.setLogin("short");
        assertThrows(InvalidInputDataException.class, () ->
                        registrationService.register(defaultUser),
                "InvalidInputData must thrown if Login is less than 14 elements.");
    }

    @Test
    void register_missPatternLoginPassword_notOk() {
        defaultUser.setLogin("____&^&^*%^&*()");
        defaultUser.setPassword("&^*()*&^^*()*&(^");
        assertThrows(InvalidInputDataException.class, () ->
                        registrationService.register(defaultUser),
                "InvalidInputData must thrown Login or Password mismatch pattern [a-z-A-Z-0-9].");
    }
}
