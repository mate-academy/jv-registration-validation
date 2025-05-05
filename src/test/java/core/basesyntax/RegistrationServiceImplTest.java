package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.InvalidUserDataException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static StorageDao storageDao;
    private static RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void clearUp() {
        Storage.people.clear();
    }

    @Test
    void register_validData_ok() {
        User userOne = new User();
        userOne.setAge(18);
        userOne.setLogin("login1");
        userOne.setPassword("password1");
        registrationService.register(userOne);
        assertNotNull(storageDao.get("login1"));
        User personOne = new User();
        personOne.setAge(30);
        personOne.setLogin("loginOne");
        personOne.setPassword("passwordOne");
        User personTwo = new User();
        personTwo.setAge(30);
        personTwo.setLogin("loginTwo");
        personTwo.setPassword("passwordTwo");
        registrationService.register(personOne);
        registrationService.register(personTwo);
        assertNotNull(storageDao.get("loginOne"));
        assertNotNull(storageDao.get("loginTwo"));
    }

    @Test
    void register_smallAge_notOk() {
        User youngUser = new User();
        youngUser.setLogin("youngLogin");
        youngUser.setPassword("youngPassword");
        youngUser.setAge(15);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(youngUser);
        });
    }

    @Test
    void register_5lengthPassword_notOk() {
        User userTwo = new User();
        userTwo.setAge(22);
        userTwo.setLogin("loginTwo");
        userTwo.setPassword("12345");
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(userTwo);
        });
    }

    @Test
    void register_3lengthPassword_notOk() {
        User userTwo = new User();
        userTwo.setAge(22);
        userTwo.setLogin("loginTwo");
        userTwo.setPassword("123");
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(userTwo);
        });
    }

    @Test
    void register_6lengthPassword_Ok() {
        User userTwo = new User();
        userTwo.setAge(22);
        userTwo.setLogin("loginTwo");
        userTwo.setPassword("123456");
        registrationService.register(userTwo);
        assertNotNull(storageDao.get("loginTwo"));
    }

    @Test
    void register_4lengthLogin_notOk() {
        User userThree = new User();
        userThree.setLogin("name");
        userThree.setPassword("passwordThree");
        userThree.setAge(41);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(userThree);
        });
    }

    @Test
    void register_5lengthLogin_notOk() {
        User userThree = new User();
        userThree.setLogin("login");
        userThree.setPassword("passwordThree");
        userThree.setAge(41);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(userThree);
        });
    }

    @Test
    void register_6lengthLogin_Ok() {
        User userThree = new User();
        userThree.setLogin("login3");
        userThree.setPassword("passwordThree");
        userThree.setAge(41);
        registrationService.register(userThree);
        assertNotNull(storageDao.get("login3"));
    }

    @Test
    void register_nullAge_notOk() {
        User personOne = new User();
        personOne.setLogin("loginOne");
        personOne.setPassword("pass");
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(personOne);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        User userTwo = new User();
        userTwo.setAge(51);
        userTwo.setLogin("passTwo");
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(userTwo);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setAge(78);
        user.setPassword("userPassword");
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_negativeAge_notOk() {
        User user = new User();
        user.setLogin("userLogin");
        user.setAge(-1);
        user.setPassword("userPassword");
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
    }

}
