package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.exception.ValidatorException;
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

    private List<User> initUsersList() {
        List<User> usersList = new ArrayList<>();
        usersList.add(new User("BobA", "4385482", 43));
        usersList.add(new User(null, "655234322", 21));
        usersList.add(new User("AlexFreedom", "543234113", null));
        usersList.add(new User("NickKarlosn", null, 43));
        usersList.add(new User("YuliaMikovskay", "3244", 56));
        usersList.add(new User("AlenTurke", "3243234", 17));
        usersList.add(new User("", "3243234", 43));
        usersList.add(new User("LizaVavilova", "", 24));
        usersList.add(new User("AndrewSmith", "43543535", -10));
        usersList.add(new User("4LizaValencia", "43243234", 54));

        return usersList;
    }

    private static List<User> initCorrectUsersList() {
        List<User> correctList = new ArrayList<>();
        correctList.add(new User("FillCurrent", "12345678", 23));
        correctList.add(new User("JhonMelkavich", "987654321", 34));
        correctList.add(new User("AlenaBekovskay", "123987654", 18));
        correctList.add(new User("RitaVonalich", "999384633", 37));

        return correctList;
    }

    @BeforeAll
    static void createService() {
        registerServiceTest = new RegistrationServiceImpl();
        for (User user : users) {
            registerServiceTest.register(user);
        }
    }

    @Test
    void isNullUser() {
        User nullUser = null;
        assertThrows(NullPointerException.class, () -> {
            registerServiceTest.register(nullUser);
        });
    }

    @Test
    void isNullLogin() {
        List<User> actual = initUsersList();
        try {
            registerServiceTest.register(actual.get(1));
        } catch (ValidatorException e) {
            return;
        }
        fail("ValidatorException should be thrown if login is null");
    }

    @Test
    void isNullPassword() {
        List<User> actual = initUsersList();
        try {
            registerServiceTest.register(actual.get(3));
        } catch (ValidatorException e) {
            return;
        }
        fail("ValidatorException should be thrown if password is null");
    }

    @Test
    void isNullPAge() {
        List<User> actual = initUsersList();
        try {
            registerServiceTest.register(actual.get(2));
        } catch (ValidatorException e) {
            return;
        }
        fail("ValidatorException should be thrown if age is null");
    }

    @Test
    void isEmptyLogin() {
        List<User> actual = initUsersList();
        try {
            registerServiceTest.register(actual.get(6));
        } catch (ValidatorException e) {
            return;
        }
        fail("ValidatorException should be thrown if login is empty");
    }

    @Test
    void isEmptyPassword() {
        List<User> actual = initUsersList();
        try {
            registerServiceTest.register(actual.get(7));
        } catch (ValidatorException e) {
            return;
        }
        fail("ValidatorException should be thrown if password is empty");
    }

    @Test
    void isLoginLength_LessThan_Six() {
        List<User> actual = initUsersList();
        try {
            registerServiceTest.register(actual.get(0));
        } catch (ValidatorException e) {
            return;
        }
        fail("ValidatorException should be thrown if login length less than 6");
    }

    @Test
    void isPasswordLength_LessThan_Six() {
        List<User> actual = initUsersList();
        try {
            registerServiceTest.register(actual.get(4));
        } catch (ValidatorException e) {
            return;
        }
        fail("ValidatorException should be thrown if password length less than 6");
    }

    @Test
    void isAge_LessThan_Range() {
        List<User> actual = initUsersList();
        try {
            registerServiceTest.register(actual.get(5));
        } catch (ValidatorException e) {
            return;
        }
        fail("ValidatorException should be thrown if age less than 18");
    }

    @Test
    void isNegativeAge() {
        List<User> actual = initUsersList();
        try {
            registerServiceTest.register(actual.get(8));
        } catch (ValidatorException e) {
            return;
        }
        fail("ValidatorException should be thrown if age is negative");
    }

    @Test
    void isLoginStartWithNumbers() {
        List<User> actual = initUsersList();
        try {
            registerServiceTest.register(actual.get(9));
        } catch (ValidatorException e) {
            return;
        }
        fail("ValidatorException should be thrown if login starts with number");
    }

    @Test
    void getUserByLogin() {
        try {
            RegistrationServiceImpl registerSpecialService =
                    (RegistrationServiceImpl) registerServiceTest;
            registerSpecialService.getUserByLogin("YuilaMelek");
        } catch (ValidatorException e) {
            return;
        }
        fail("ValidatorException should be thrown if user does not exist");
    }

    @Test
    void getUserByLoginCorrect() {
        String correctLogin = "RitaVonalich";
        RegistrationServiceImpl registerSpecialService =
                (RegistrationServiceImpl) registerServiceTest;
        boolean actual = registerSpecialService.getUserByLogin(correctLogin) != null
                ? true : false;
        assertTrue(actual);
    }

    @Test
    void registerSameUser_AlreadyRegisted() {
        List<User> actual = initCorrectUsersList();
        try {
            registerServiceTest.register(actual.get(0));
        } catch (ValidatorException e) {
            return;
        }
        fail("ValidatorException should be thrown if user is already exist");
    }
}
