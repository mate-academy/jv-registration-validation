package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl service;
    private static StorageDao storageDao;
    private static final String VALID_LOGIN = "AgroFix";
    private static final String VALID_PASSWORD = "qwerty23";
    private static final int VALID_AGE = 21;
    private static final String LESS_THEN_SIX_LOGIN = "Strii";
    private static final String LESS_THEN_SIX_PASSWORD = "four";
    private static final int LESS_THEN_EIGHTEEN_AGE = 4;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        service = new RegistrationServiceImpl(new StorageDaoImpl());
        assertNotNull(service);
    }

    @Test
    void registerLoginIsNull_isNotOk() {User user = new User();
        user.setLogin(null);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        assertThrows(InvalidUserDataException.class,
                () -> service.register(user));
    }

    @Test
    void registerPasswordIsNull_isNotOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(null);
        user.setAge(VALID_AGE);
        assertThrows(InvalidUserDataException.class,
                () -> service.register(user));
    }

    @Test
    void registerAgeIsNull_isNotOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(null);
        assertThrows(InvalidUserDataException.class,
                () -> service.register(user));
    }

    @Test
    void registerUserIsNull_isNotOk() {
        assertThrows(InvalidUserDataException.class,
                () -> service.register(null));
    }

    @Test
    void registerExistUser_isNotOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        storageDao.add(user);
        User DuplicateUser = new User();
        DuplicateUser.setLogin(VALID_LOGIN);
        DuplicateUser.setPassword(VALID_PASSWORD);
        DuplicateUser.setAge(23);
        assertThrows(InvalidUserDataException.class,
                () -> service.register(DuplicateUser));
    }

    @Test
    void registerLoginLessSix_isNotOk() {
        User user = new User();
        user.setLogin(LESS_THEN_SIX_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        assertThrows(InvalidUserDataException.class,
                () -> service.register(user));
    }

    @Test
    void registerPasswordLessSix_isNotOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(LESS_THEN_SIX_PASSWORD);
        user.setAge(VALID_AGE);
        assertThrows(InvalidUserDataException.class,
                () -> service.register(user));
    }

    @Test
    void registerAgeLessEighteen_isNotOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(LESS_THEN_EIGHTEEN_AGE);
        assertThrows(InvalidUserDataException.class,
                () -> service.register(user));
    }

    @Test
    void registerUserValid_isOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
    }

    @AfterAll
    static void afterAll() {
        Storage.people.clear();
    }
}
