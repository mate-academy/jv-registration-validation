package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.CurrentLoginIsExists;
import core.basesyntax.model.InvalidInputData;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    StorageDaoImpl storageDao = new StorageDaoImpl();
    User user = new User();
    User sameUser = new User();
    User nullUser = new User();
    User nullPasswordUser = new User();
    User nullLoginUser = new User();
    User nullAgeUser = new User();
    User shortPasswordUser = new User();
    User shortLoginUser = new User();
    User misMathcPatternUser = new User();
    Pattern pattern = Pattern.compile("[a-zA-Z]");
    Matcher matcher;

    @BeforeEach
    void setUp() {
        user.setAge(18);
        user.setLogin("programator12341");
        user.setPassword("passatic123");
        registrationService.register(user);
        sameUser.setAge(18);
        sameUser.setLogin("programator12341");
        sameUser.setPassword("passatic123");
        nullUser.setAge(null);
        nullUser.setLogin(null);
        nullUser.setPassword(null);
        nullLoginUser.setPassword("hellomyfriend123");
        nullLoginUser.setLogin(null);
        nullLoginUser.setAge(20);
        nullPasswordUser.setAge(20);
        nullPasswordUser.setLogin("HelloworldMyfait");
        nullPasswordUser.setPassword(null);
        nullAgeUser.setLogin("User22222222222");
        nullAgeUser.setLogin("HelloToWorldOr00");
        shortLoginUser.setLogin("short");
        shortLoginUser.setPassword("normalPassword");
        shortPasswordUser.setAge(23);
        shortLoginUser.setLogin("short");
        shortLoginUser.setPassword("normalPassword");
        shortLoginUser.setAge(23);
        misMathcPatternUser.setPassword("--------------");
        misMathcPatternUser.setLogin("-------------------");
        misMathcPatternUser.setAge(25);


    }

    @Test
    void current_Login_Is_Not_Exists_In_Storage_Is_Ok() {
        boolean actual = storageDao.get(user.getLogin()) == null;
        assertTrue(actual);
    }

    @Test
    void checkLoginFieldIsNotEmptyOrNullIs_Ok() {
        boolean actual = user.getLogin() == null || user.getLogin().isEmpty();
        assertFalse(actual);
    }

    @Test
    void checkInputLoginLengthIs_Ok() {
        boolean actual = user.getLogin().length() >= 14;
        assertTrue(actual);
    }

    @Test
    void checkLoginMatchPatternIs_Ok() {
        matcher = pattern.matcher(user.getLogin());
        boolean actual = matcher.matches();
        assertTrue(actual);
    }

    @Test
    void currentPasswordIsNotEmptyOrNullIs_Ok() {
        boolean actual = user.getPassword() != null || !(user.getPassword().isEmpty());
        assertTrue(actual);
    }

    @Test
    void checkInputPasswordLengthIs_Ok() {
        boolean actual = user.getPassword().length() >= 10;
        assertTrue(actual);
    }

    @Test
    void checkPasswordMatchPatternIs_Ok() {
        boolean actual = user.getPassword().matches("[a-zA-Z]");
        assertTrue(actual);
    }

    @Test
    void currentLoginIsExistsExceptionIfNot_Ok() {
        try {
            registrationService.register(sameUser);
        } catch (CurrentLoginIsExists exception) {
            return;
        }
        fail("CurrentLoginIsExist must thrown if current login is located in storage");
    }

    @Test
    void invalidInputLoginExceptionIfNullOrEmpty() {
        try {
            registrationService.register(nullLoginUser);
        } catch (InvalidInputData exception) {
            return;
        }
        fail("InvalidInputData must thrown if Login is null or empty");
    }

    @Test
    void invalidInputPasswordIfNullOrEmpty() {
        try {
            registrationService.register(nullPasswordUser);
        } catch (InvalidInputData exception) {
            return;
        }
        fail("InvalidInputData must thrown if Password is null or empty");
    }

    @Test
    void checkShortPasswordException() {
        try {
            registrationService.register(shortPasswordUser);
        } catch (InvalidInputData exception) {
            return;
        }
        fail("InvalidInputData must thrown if Password is less than 10 elements.");
    }

    @Test
    void checkShortLoginException() {
        try {
            registrationService.register(shortLoginUser);
        } catch (InvalidInputData exception) {
            return;
        }
        fail("InvalidInputData must thrown if Login is less than 14 elements.");
    }

    @Test
    void checkMismatchPatternException() {
        try {
            registrationService.register(misMathcPatternUser);
        } catch (InvalidInputData exception) {
            return;
        }
        fail("InvalidInputData must thrown Login or Password mismatch pattern [a-z-A-Z-0-9]");
    }
}