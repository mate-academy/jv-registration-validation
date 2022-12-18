package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exc.InvalidDataException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final User NULL_AGE_USER = new User();
    private static final User NULL_LOGIN_USER = new User();
    private static final User NULL_PASSWORD_USER = new User();
    private static final User CORRECT_USER1 = new User();
    private static final User CORRECT_USER2 = new User();
    private static final User INVALID_DATA_USER1 = new User();
    private static final User INVALID_DATA_USER2 = new User();
    private static final User INVALID_DATA_USER3 = new User();

    private RegistrationService regService;
    private StorageDao storageDao;

    @BeforeAll
    static void usersInitialization() {
        NULL_AGE_USER.setLogin("nulllogin1");
        NULL_AGE_USER.setPassword("nullpassword1");

        NULL_LOGIN_USER.setAge(29);
        NULL_LOGIN_USER.setPassword("nullpassword2");
        
        NULL_PASSWORD_USER.setAge(18);
        NULL_PASSWORD_USER.setLogin("nulllogin2");

        CORRECT_USER1.setAge(18);
        CORRECT_USER1.setLogin("login1");
        CORRECT_USER1.setPassword("pass12");

        CORRECT_USER2.setAge(34);
        CORRECT_USER2.setLogin("login2");
        CORRECT_USER2.setPassword("password1234");

        INVALID_DATA_USER1.setAge(-1);
        INVALID_DATA_USER1.setLogin("");;
        INVALID_DATA_USER1.setPassword("");  

        INVALID_DATA_USER2.setAge(19);
        INVALID_DATA_USER2.setLogin("login1");
        INVALID_DATA_USER2.setPassword("pass1");

        INVALID_DATA_USER3.setAge(19);
        INVALID_DATA_USER3.setLogin("");
        INVALID_DATA_USER3.setPassword("password3");
    }

    @BeforeEach
    void setUp() {
        regService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }
    
    @Test
    void register_null_notOk() {
        assertThrows(InvalidDataException.class, () -> regService.register(null));
    }

    @Test
    void register_invalidData_notOk() {
        assertThrows(InvalidDataException.class, () -> regService.register(NULL_AGE_USER));
        assertThrows(InvalidDataException.class, () -> regService.register(NULL_LOGIN_USER));
        assertThrows(InvalidDataException.class, () -> regService.register(NULL_PASSWORD_USER));
        assertThrows(InvalidDataException.class, () -> regService.register(INVALID_DATA_USER1));
        assertThrows(InvalidDataException.class, () -> regService.register(INVALID_DATA_USER2));
        assertThrows(InvalidDataException.class, () -> regService.register(INVALID_DATA_USER3));
    }

    @Test
    void register_checkAddingToDB_Ok() {
        regService.register(CORRECT_USER1);
        regService.register(CORRECT_USER2);
        User actualUser1 = storageDao.get(CORRECT_USER1.getLogin());
        User actualUser2 = storageDao.get(CORRECT_USER2.getLogin());

        assertEquals(CORRECT_USER1, actualUser1);
        assertEquals(CORRECT_USER2, actualUser2);
    }

    @Test
    void register_checkReturnedValue_Ok() {
        User actualUser1 = regService.register(CORRECT_USER1);
        User actualUser2 = regService.register(CORRECT_USER2);

        assertEquals(CORRECT_USER1, actualUser1);
        assertEquals(CORRECT_USER2, actualUser2);
    }

    @Test
    void register_addingAlreadyExistingUser_NotOk() {
        storageDao.add(CORRECT_USER2);
        assertThrows(InvalidDataException.class, () -> regService.register(CORRECT_USER2));
    }

    @AfterEach
    void storageCleanup() {
        Storage.people.clear();
    }
}
