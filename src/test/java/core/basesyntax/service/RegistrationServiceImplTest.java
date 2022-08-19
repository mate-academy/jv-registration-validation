package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        storageDao.add(new User("popova", "justpopova", 26));
        storageDao.add(new User("sterniuk", "1123313", 30));
        storageDao.add(new User("pony", "dfyrg6", 17));
    }

    @Test
    public void userNull_isNotValid() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    public void userSameLogin_isNotValid() {
        User newUser = new User("popova", "justpopova", 26);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    public void userLogin_isValid() {
        User newUser = new User("gabriella", "gabi2000", 22);
        Assertions.assertEquals(newUser, registrationService.register(newUser));
    }

    @Test
    public void userLoginNull_isNotValid() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(null, "hellokitty", 19));
        });
    }

    @Test
    public void userLoginIsEmpty_isNotValid() {
        String emptyName = "";
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(emptyName, "hellokitty", 19));
        });
    }

    @Test
    public void userLoginContainsEmptySpace_isNotValid() {
        String userLogin = "S     ";
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(userLogin, "emptySpaces", 24));
        });
    }

    @Test
    public void userLoginStartsWithCharacter_isNotValid() {
        String userLogin = "1anita";
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(userLogin, "dreamsaway", 130));
        });
    }

    @Test
    public void userLoginStartsWithCharacter_isValid() {
        String userLogin = "mamasita";
        Assertions.assertEquals(new User(userLogin, "mamasita1", 20),
                registrationService.register(new User(userLogin, "mamasita1", 20)));
    }

    @Test
    public void userPasswordNull_isNotValid() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User("defaultUser", null, 17));
        });
    }

    @Test
    public void userPasswordLength_isNotValid() {
        User newUser = new User("defaultUser", "12345", 17);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    public void userPasswordLength_isValid() {
        User newUser = new User("florenta", "flora15", 22);
        Assertions.assertEquals(newUser, registrationService.register(newUser));
    }

    @Test
    public void userAgeNull_isNotValid() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User("kristine", "password1", null));
        });
    }

    @Test
    public void userAgeLessThanAccepted_isNotValid() {
        User newUser = new User("pinguin", "blackAndWhite", 15);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    public void userAgeIsLessThanZero_isNotValid() {
        User newUser = new User("pinguin", "blackAndWhite", -10);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    public void userAge_isValid() {
        User newUser = new User("hellboy", "hellOfTime", 35);
        Assertions.assertEquals(newUser, registrationService.register(newUser));
    }
}
