package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class RegistrationServiceImplTest {
    private Storage storage = new Storage();
    private RegistrationServiceImpl service = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();

    @BeforeEach
    void storageClear() {
        Storage.people.clear();
    }

    @Test
    void register_validUser_ok() {
        User firstUser = new User();
        firstUser.setAge(18);
        firstUser.setLogin("Oksana");
        firstUser.setPassword("Oksana2005");
        service.register(firstUser);
        User actualUser = storageDao.get(firstUser.getLogin());
        assertSame(actualUser, firstUser);
    }

    @Test
    void register_userIsNull_notOk() {
        User firstUser = null;
        assertThrows(RegistrationException.class, () -> {
            service.register(firstUser);
        });
    }

    @Test
    void register_loginIsNull_notOk() {
        User firstUser = new User();
        firstUser.setAge(18);
        firstUser.setPassword("Ok25");
        assertThrows(RegistrationException.class, () -> {
            service.register(firstUser);
        });
    }

    @Test
    void register_passwordIsNull_notOk() {
        User firstUser = new User();
        firstUser.setAge(18);
        firstUser.setLogin("Oksana");
        assertThrows(RegistrationException.class, () -> {
            service.register(firstUser);
        });
    }

    @Test
    void register_ageIsNull_notOk() {
        User firstUser = new User();
        firstUser.setLogin("Oksana");
        firstUser.setPassword("Ok25");
        assertThrows(RegistrationException.class, () -> {
            service.register(firstUser);
        });
    }

    @Test
    void register_existingLogin_notOk() {
        User firstUser = new User();
        firstUser.setAge(18);
        firstUser.setLogin("Oksana");
        firstUser.setPassword("Oksana2005");
        service.register(firstUser);
        assertThrows(RegistrationException.class, () -> {
            service.register(firstUser);
        });
    }

    @Test
    void register_passwordTooShort_notOk() {
        User firstUser = new User();
        firstUser.setAge(18);
        firstUser.setLogin("Oksana");
        firstUser.setPassword("Ok25");
        assertThrows(RegistrationException.class, () -> {
            service.register(firstUser);
        });
    }

    @Test
    void register_loginTooShort_notOk() {
        User firstUser = new User();
        firstUser.setAge(18);
        firstUser.setLogin("Oksan");
        firstUser.setPassword("Oksana2005");
        assertThrows(RegistrationException.class, () -> {
            service.register(firstUser);
        });
    }
}
