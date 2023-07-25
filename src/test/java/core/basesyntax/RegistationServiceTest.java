package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistationServiceTest {
    private static RegistrationService registerServiceTest;
    private static List<User> users = initCorrectUsersList();

    @BeforeAll
    static void createService() {
        registerServiceTest = new RegistrationServiceImpl();
        for (User user : users) {
            registerServiceTest.register(user);
        }
    }

    @Test
    void register_isNullUser_NotOk() {
        User nullUser = null;
        assertThrows(NullPointerException.class, () -> {
            registerServiceTest.register(nullUser);
        });
    }

    @Test
    void register_isNullLogin_NotOk() {
        User actual = new User(null, "655234322", 21);
        try {
            registerServiceTest.register(actual);
        } catch (ValidationException e) {
            return;
        }
        fail("ValidatorException should be thrown if login is null");
    }

    @Test
    void register_isNullPassword_NotOk() {
        User actual = new User("NickKarlosn", null, 43);
        try {
            registerServiceTest.register(actual);
        } catch (ValidationException e) {
            return;
        }
        fail("ValidatorException should be thrown if password is null");
    }

    @Test
    void register_isNullAge_NotOk() {
        User actual = new User("AlexFreedom", "543234113", null);
        try {
            registerServiceTest.register(actual);
        } catch (ValidationException e) {
            return;
        }
        fail("ValidatorException should be thrown if age is null");
    }

    @Test
    void register_isEmptyLogin_NotOk() {
        User actual = new User("", "3243234", 43);
        try {
            registerServiceTest.register(actual);
        } catch (ValidationException e) {
            return;
        }
        fail("ValidatorException should be thrown if login is empty");
    }

    @Test
    void register_isEmptyPassword_NotOk() {
        User actual = new User("LizaVavilova", "", 24);
        try {
            registerServiceTest.register(actual);
        } catch (ValidationException e) {
            return;
        }
        fail("ValidatorException should be thrown if password is empty");
    }

    @Test
    void register_isLoginLength_LessThan_Six_NotOk() {
        User actual = new User("BobA", "4385482", 43);
        try {
            registerServiceTest.register(actual);
        } catch (ValidationException e) {
            return;
        }
        fail("ValidatorException should be thrown if login length less than 6");
    }

    @Test
    void register_isPasswordLength_LessThan_Six_NotOk() {
        User actual = new User("YuliaMikovskay", "3244", 56);
        try {
            registerServiceTest.register(actual);
        } catch (ValidationException e) {
            return;
        }
        fail("ValidatorException should be thrown if password length less than 6");
    }

    @Test
    void register_isAgeLessThanRange_NotOk() {
        User actual = new User("AlenTurke", "3243234", 17);
        try {
            registerServiceTest.register(actual);
        } catch (ValidationException e) {
            return;
        }
        fail("ValidatorException should be thrown if age less than 18");
    }

    @Test
    void register_isLoginStartWithNumbers_NotOk() {
        User actual = new User("4LizaValencia", "43243234", 54);
        try {
            registerServiceTest.register(actual);
        } catch (ValidationException e) {
            return;
        }
        fail("ValidatorException should be thrown if login starts with number");
    }

    @Test
    void registerData_getUserByLogin_NotOk() {
        try {
            RegistrationServiceImpl registerSpecialService =
                    (RegistrationServiceImpl) registerServiceTest;
            registerSpecialService.getUserByLogin("YuilaMelek");
        } catch (ValidationException e) {
            return;
        }
        fail("ValidatorException should be thrown if user does not exist");
    }

    @Test
    void registerData_getUserByLoginCorrect_Ok() {
        String correctLogin = "RitaVonalich";
        RegistrationServiceImpl registerSpecialService =
                (RegistrationServiceImpl) registerServiceTest;
        boolean actual = registerSpecialService.getUserByLogin(correctLogin) != null
                ? true : false;
        assertTrue(actual);
    }

    @Test
    void registerData_registerSameUserAlreadyRegisted_NotOk() {
        List<User> actual = initCorrectUsersList();
        try {
            registerServiceTest.register(actual.get(0));
        } catch (ValidationException e) {
            return;
        }
        fail("ValidatorException should be thrown if user is already exist");
    }

    private static List<User> initCorrectUsersList() {
        List<User> correctList = new ArrayList<>();
        correctList.add(new User("FillCurrent", "12345678", 23));
        correctList.add(new User("JhonMelkavich", "987654321", 34));
        correctList.add(new User("AlenaBekovskay", "123987654", 18));
        correctList.add(new User("RitaVonalich", "999384633", 37));

        return correctList;
    }
}
