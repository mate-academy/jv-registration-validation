package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.InvalidUserDataException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class HelloWorldTest {
    private static StorageDao storageDao;
    private static RegistrationService registrationService;

    @BeforeAll
    static void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void addUser() {
        User torin = new User();
        torin.setAge(60);
        torin.setLogin("oakShield");
        torin.setPassword("123456");
        registrationService.register(torin);
        assertNotNull(storageDao.get("oakShield"));
    }

    @Test
    void sizeAfterAdd() {
        User bruce = new User();
        bruce.setAge(19);
        bruce.setLogin("Batman");
        bruce.setPassword("whereisthedetonator");
        registrationService.register(bruce);
        int expectedSize = 1;
        assertEquals(expectedSize, Storage.people.size());
    }

    @Test
    void smallGuyNotOk() {
        User youngUser = new User();
        youngUser.setLogin("Kotyhoroshko");
        youngUser.setPassword("3,1415926535");
        youngUser.setAge(15);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(youngUser);
        });
    }

    @Test
    void shortPasswordNotOk() {
        User peterParker = new User();
        peterParker.setAge(22);
        peterParker.setLogin("AmazingSpiderMan");
        peterParker.setPassword("MJ");
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(peterParker);
        });
    }

    @Test
    void nullLoginNotOk() {
        User bigBrother = new User();
        bigBrother.setLogin(null);
        bigBrother.setPassword("qwerty123456");
        bigBrother.setAge(41);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(bigBrother);
        });
    }

    @Test
    void addTwoPerson() {
        User personOne = new User();
        personOne.setAge(30);
        personOne.setLogin("Kitana");
        personOne.setPassword("qazxcxzaq");
        User personTwo = new User();
        personTwo.setAge(30);
        personTwo.setLogin("Milena");
        personTwo.setPassword("wasd1234");
        registrationService.register(personOne);
        registrationService.register(personTwo);
        assertNotNull(storageDao.get("Kitana"));
        assertNull(storageDao.get("Jade"));
        assertNotNull(storageDao.get("Milena"));
    }

    @Test
    void addNotOkPerson() {
        User personOne = new User();
        personOne.setLogin("PaulAtrid");
        personOne.setPassword("duna");
        personOne.setAge(18);
        User personTwo = new User();
        personTwo.setAge(21);
        personTwo.setLogin("Lord");
        personTwo.setPassword("zxcvbn10");
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(personOne);
        });
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(personTwo);
        });
    }
}
