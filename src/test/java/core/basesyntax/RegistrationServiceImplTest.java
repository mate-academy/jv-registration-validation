package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.InvalidDataException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {

    @Test
    void register_isUserInStorage_notOK() {
        RegistrationServiceImpl RegistrationServiceImpl = new RegistrationServiceImpl();
        StorageDao storageDao = new StorageDaoImpl();
        User testUserOk = new User();
        testUserOk.setAge(20);
        testUserOk.setLogin("Login");
        testUserOk.setPassword("Password");
        storageDao.add(testUserOk);
        Assertions.assertThrows(InvalidDataException.class, () -> {
            RegistrationServiceImpl.register(testUserOk);
        });
    }

    @Test
    void register_userAbsentInStorage_OK() {
        StorageDao storageDao = new StorageDaoImpl();
        User testUserOk1 = new User();
        testUserOk1.setAge(22);
        testUserOk1.setLogin("Login1");
        testUserOk1.setPassword("Password1");
        RegistrationServiceImpl RegistrationServiceImpl = new RegistrationServiceImpl();
        try {
            RegistrationServiceImpl.register(testUserOk1);
        } catch (InvalidDataException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertNotNull(storageDao.get(testUserOk1.getLogin()));
    }

    @Test
    void register_passwordShortThenNeed_notOk() {
        RegistrationServiceImpl RegistrationServiceImpl = new RegistrationServiceImpl();
        User testUserNotOk = new User();
        testUserNotOk.setAge(20);
        testUserNotOk.setLogin("Login");
        testUserNotOk.setPassword("Pass");
        Assertions.assertThrows(InvalidDataException.class, () -> {
            RegistrationServiceImpl.register(testUserNotOk);
        });
    }

    @Test
    void register_passwordIsRight_Ok() {
        RegistrationServiceImpl RegistrationServiceImpl = new RegistrationServiceImpl();
        StorageDao storageDao = new StorageDaoImpl();
        User testUserOk2 = new User();
        testUserOk2.setAge(26);
        testUserOk2.setLogin("Login2");
        testUserOk2.setPassword("Password2");
        try {
            RegistrationServiceImpl.register(testUserOk2);
        } catch (InvalidDataException e) {
            throw new RuntimeException(e);
        }
        String rightPassword = "Password2";
        User userInStorage = storageDao.get(testUserOk2.getLogin());
        Assertions.assertEquals(rightPassword, userInStorage.getPassword());
    }

    @Test
    void register_userIsVeryYoung_notOk() {
        RegistrationServiceImpl RegistrationServiceImpl = new RegistrationServiceImpl();
        User testUserNotOk = new User();
        testUserNotOk.setAge(15);
        testUserNotOk.setLogin("Login");
        testUserNotOk.setPassword("Password");
        Assertions.assertThrows(InvalidDataException.class, () -> {
            RegistrationServiceImpl.register(testUserNotOk);
        });
    }

    @Test
    void  register_userHasPermissibleAge_Ok() {
        RegistrationServiceImpl RegistrationServiceImpl = new RegistrationServiceImpl();
        StorageDao storageDao = new StorageDaoImpl();
        User testUserOk3 = new User();
        testUserOk3.setAge(30);
        testUserOk3.setLogin("Login3");
        testUserOk3.setPassword("Password3");
        try {
            RegistrationServiceImpl.register(testUserOk3);
        } catch (InvalidDataException e) {
            throw new RuntimeException(e);
        }
        int age = 30;
        User userInStorage = storageDao.get(testUserOk3.getLogin());
        Assertions.assertEquals(age, userInStorage.getAge());
    }
}
