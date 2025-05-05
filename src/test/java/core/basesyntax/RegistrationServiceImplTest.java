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
    private static RegistrationService regService;
    private static StorageDao storageDao;
    private User correctUser = new User();

    @BeforeAll
    static void initialization() {
        regService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void resetUserToDefault() {
        correctUser.setAge(20);
        correctUser.setLogin("userlogin");
        correctUser.setPassword("userpassword123");
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidDataException.class, () -> regService.register(null));
    }

    @Test
    void register_nullAge_notOk() {
        correctUser.setAge(null);
        assertThrows(InvalidDataException.class, () -> regService.register(correctUser));
    }

    @Test
    void register_nullLogin_notOk() {
        correctUser.setLogin(null);
        assertThrows(InvalidDataException.class, () -> regService.register(correctUser));
    }

    @Test
    void register_nullPassword_notOk() {
        correctUser.setPassword(null);
        assertThrows(InvalidDataException.class, () -> regService.register(correctUser));
    }

    @Test
    void register_negativeAge_notOk() {
        correctUser.setAge(-1);
        assertThrows(InvalidDataException.class, () -> regService.register(correctUser));
    }

    @Test
    void register_shortPassword_notOk() {
        correctUser.setPassword("abcd");
        assertThrows(InvalidDataException.class, () -> regService.register(correctUser));
    }

    @Test
    void register_emptyLogin_notOk() {
        correctUser.setLogin("");
        assertThrows(InvalidDataException.class, () -> regService.register(correctUser));
    }

    @Test
    void register_checkAddingToDB_Ok() {
        regService.register(correctUser);
        User actualUser = storageDao.get(correctUser.getLogin());
        assertEquals(correctUser, actualUser);
    }

    @Test
    void register_checkReturnedValue_Ok() {
        User actualUser = regService.register(correctUser);
        assertEquals(correctUser, actualUser);
    }

    @Test
    void register_addingAlreadyExistingUser_NotOk() {
        storageDao.add(correctUser);
        assertThrows(InvalidDataException.class, () -> regService.register(correctUser));
    }

    @AfterEach
    void setDefaults() {
        Storage.people.clear();
    }
}
