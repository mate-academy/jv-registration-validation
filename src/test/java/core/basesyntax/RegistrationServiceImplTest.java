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
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final User CORRECT_USER = new User();

    private static RegistrationService regService;
    private static StorageDao storageDao;

    private static void resetUserToDefault() {
        CORRECT_USER.setAge(20);
        CORRECT_USER.setLogin("userlogin");
        CORRECT_USER.setPassword("userpassword123");
    }

    @BeforeAll
    static void usersInitialization() {
        regService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        resetUserToDefault();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidDataException.class, () -> regService.register(null));
    }

    @Test
    void register_nullAge_notOk() {
        CORRECT_USER.setAge(null);
        assertThrows(InvalidDataException.class, () -> regService.register(CORRECT_USER));
    }

    @Test
    void register_nullLogin_notOk() {
        CORRECT_USER.setLogin(null);
        assertThrows(InvalidDataException.class, () -> regService.register(CORRECT_USER));
    }

    @Test
    void register_nullPassword_notOk() {
        CORRECT_USER.setPassword(null);
        assertThrows(InvalidDataException.class, () -> regService.register(CORRECT_USER));
    }

    @Test
    void register_negativeAge_notOk() {
        CORRECT_USER.setAge(-1);
        assertThrows(InvalidDataException.class, () -> regService.register(CORRECT_USER));
    }

    @Test
    void register_shortPassword_notOk() {
        CORRECT_USER.setPassword("abcd");
        assertThrows(InvalidDataException.class, () -> regService.register(CORRECT_USER));
    }

    @Test
    void register_emptyLogin_notOk() {
        CORRECT_USER.setLogin("");
        assertThrows(InvalidDataException.class, () -> regService.register(CORRECT_USER));
    }

    @Test
    void register_checkAddingToDB_Ok() {
        regService.register(CORRECT_USER);
        User actualUser = storageDao.get(CORRECT_USER.getLogin());
        assertEquals(CORRECT_USER, actualUser);
    }

    @Test
    void register_checkReturnedValue_Ok() {
        User actualUser = regService.register(CORRECT_USER);
        assertEquals(CORRECT_USER, actualUser);
    }

    @Test
    void register_addingAlreadyExistingUser_NotOk() {
        storageDao.add(CORRECT_USER);
        assertThrows(InvalidDataException.class, () -> regService.register(CORRECT_USER));
    }

    @AfterEach
    void setDefaults() {
        Storage.people.clear();
        resetUserToDefault();
    }
}
