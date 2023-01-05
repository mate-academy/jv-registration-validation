package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static StorageDaoImpl storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        storageDao.add(new User(123456789L, "unknownCactus", "CanLive_withoutWater22", 23));
        storageDao.add(new User(234567891L, "notASportsman", "KFC_Matters121212", 45));
        storageDao.add(new User(345678912L, "starInTheSky", "needATelescopeToSeeMe777", 18));
        storageDao.add(new User(456789123L, "baldBlueBeard", "ShutUP_andTakeMyMoney$$", 57));
    }

    @Test
    void loginValue_IsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(515544859L, null, "hellohellohello", 22));
        });
    }

    @Test
    void passwordLength_IsLessThenNeeded_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(515544859L,
                    "sonOfYourMothersFriend", "hello", 22));
        });
    }

    @Test
    void userAge_IsLessThenNeeded_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(515544859L,
                    "sonOfYourMothersFriend", "helloMyFriend", 16));
        });
    }

    @Test
    void userWithSuchLogin_IsInStorage_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(515544859L,
                    "unknownCactus", "123fffddd456", 22));
        });
    }

    @Test
    void loginValueAndPassword_WrongValues_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(515544859L,
                    null, "123", 22));
        });
    }

    @Test
    void loginValueAndAge_WrongValues_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(515544859L,
                    null, "1234567", 17));
        });
    }

    @Test
    void loginValueExists_AndPasswordWrongValue_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(515544859L,
                    "unknownCactus", "1234", 22));
        });
    }

    @Test
    void loginValueExists_AndAgeWrongValue_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(515544859L,
                    "unknownCactus", "12344566q", 15));
        });
    }

    @Test
    void passwordLength_AndAgeWrongValue_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(515544859L,
                    "flowersFlowers234", "12", 15));
        });
    }

    @Test
    void loginValueNull_AndOtherWrongValues_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(515544859L,
                    null, "123", 141));
        });
    }

    @Test
    void loginValueExists_AndOtherWrongValues_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User(515544859L,
                    "unknownCactus", "12", 130));
        });
    }

    @Test
    void validLoginData_Ok() {
        User actual = registrationService.register(new User(856321455L,
                "seniorJuniorDeveloper", "LoveToWorkPayless", 48));
    }

}
