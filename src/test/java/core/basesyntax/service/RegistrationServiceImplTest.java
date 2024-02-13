package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();
    private final User correctUser = new User();
    private final User sameLogin1User = new User();
    private final User sameLogin2User = new User();
    private final User shortLoginUser = new User();
    private final User shortPasswordUser = new User();
    private final User tooYoungUser = new User();
    private final User shadowUser = new User();

    @BeforeEach
    void setUp() {
        correctUser.setLogin("CorrectUserLogin");
        correctUser.setPassword("CorrectUserPassword");
        correctUser.setAge(18);
        //----------------
        sameLogin1User.setLogin("sameLogin");
        sameLogin1User.setPassword("sameLogin1UserPassword");
        sameLogin1User.setAge(19);
        //----------------
        sameLogin2User.setLogin("sameLogin");
        sameLogin2User.setPassword("sameLogin2UserPassword");
        sameLogin2User.setAge(20);
        //----------------
        shortLoginUser.setLogin("ShLgn");
        shortLoginUser.setPassword("ShortLoginUserPassword");
        shortLoginUser.setAge(21);
        //----------------
        shortPasswordUser.setLogin("ShortPasswordUserLogin");
        shortPasswordUser.setPassword("Pswrd");
        shortPasswordUser.setAge(22);
        //----------------
        tooYoungUser.setLogin("TooYoungUserLogin");
        tooYoungUser.setPassword("TooYoungUserPassword");
        tooYoungUser.setAge(13);

    }

    @Test
    void register_allCorrectData_Ok() {
        User actual = registrationService.register(correctUser);
        User expected = storageDao.get(correctUser.getLogin());
        assertEquals(actual,expected);
    }

    @Test
    void register_loginLength_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(shortLoginUser);
        });
    }

    @Test
    void register_passwordLength_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(shortPasswordUser);
        });
    }

    @Test
    void register_age_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(tooYoungUser);
        });
    }

    @Test
    void register_nullUserInput_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(shadowUser);
        });
    }

    @Test
    void register_nullInput_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_addUserIsInStorage_notOk() {
        registrationService.register(sameLogin1User);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(sameLogin2User);
        });
    }

    @AfterEach
    void tearDown() {

    }
}
