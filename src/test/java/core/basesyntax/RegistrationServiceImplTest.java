package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final String TEST_LOGIN = "Kolya";
    private static final String TEST_PASSWORD = "password";
    private static final Integer TEST_AGE = 23;
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
      void registrationService_registerValidUser_ok() {
        User user = new User();
        User actual = user;
        actual.setLogin(TEST_LOGIN);
        actual.setPassword(TEST_PASSWORD);
        actual.setAge(TEST_AGE);
        registrationService.register(actual);
        User expected = registrationService.register(actual);
        assertEquals(actual, expected);
    }

    @Test
    void registrationService_registerUserIsNull_notOk() {
        User actual = null;
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
      void registrationService_registerUserWithLoginNull_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword(TEST_PASSWORD);
        user.setAge(TEST_AGE);
        User actual = user;
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
      void registrationService_registerUserWithLoginIsEmpty_notOk() {
        User user = new User();
        String login = "";
        user.setLogin(login);
        user.setPassword(TEST_PASSWORD);
        user.setAge(TEST_AGE);
        User actual = user;
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
      void registrationService_registerUserWithLoginTooLong_notOk() {
        User user = new User();
        String login = "Pan Ataman Grician Tavrichesky Zatmevayuschiy Nebo Nashey Prekrasnoy"
                + " Strany Velichiem Svoego Razuma Veduschego Chelovechestvo K Pobede Svetloy"
                + " Communisticheskoy Ideyi Marksizma-Leninizma Pryamo V Geroicheskoye Zavtra"
                + " Ogovorennoe Mudrym Resheniyem XXV S'ezda Vsesoyuznoy Molodyuzhy "
                + "Kommunisticheskoy Partii Trudyaschihsya Rabochih i Krestyan";
        user.setLogin(login);
        user.setPassword(TEST_PASSWORD);
        user.setAge(TEST_AGE);
        User actual = user;
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
      void registrationService_registerUserWithAgeNull_notOk() {
        User user = new User();
        user.setLogin(TEST_LOGIN);
        user.setPassword(TEST_PASSWORD);
        user.setAge(null);
        User actual = user;
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
      void registrationService_registerUserWithAgeLess_notOk() {
        User user = new User();
        Integer age = 4;
        user.setLogin(TEST_LOGIN);
        user.setPassword(TEST_PASSWORD);
        user.setAge(age);
        User actual = user;
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
      void registrationService_registerUserWithAgeTooMuch_notOk() {
        User user = new User();
        Integer age = 151;
        user.setLogin(TEST_LOGIN);
        user.setPassword(TEST_PASSWORD);
        user.setAge(age);
        User actual = user;
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
      void registrationService_registerUserWithPasswordNull_notOk() {
        User user = new User();
        user.setLogin(TEST_LOGIN);
        user.setPassword(null);
        user.setAge(TEST_AGE);
        User actual = user;
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
      void registrationService_registerUserWithPasswordShort_notOk() {
        User user = new User();
        String password = "abc";
        user.setLogin(TEST_LOGIN);
        user.setPassword(password);
        user.setAge(TEST_AGE);
        User actual = user;
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
      void registrationService_registerUserWithPasswordLong_notOk() {
        User user = new User();
        String password = "hrenovy hackery NIKOGDA !@#$%^&*() ne vzlomayut moy parol"
                + " patamushta YA PRIMenyayu tut $pecialnyye simvoly stochniye i propisnuye"
                + " bikvy 1234567890 cifry pishu TRANSlitom stavlyu znaki prepinaniya,. i "
                + "vvobsche dopuskayu celuyu kuchu grMMatiCHeskikH oshibok pravda yesli ya"
                + " etot parol poteryayu to niKAKIE hackery iz Pentagona vspomnit' ego mne"
                + " ne POMOGUT";
        user.setLogin(TEST_LOGIN);
        user.setPassword(password);
        user.setAge(TEST_AGE);
        User actual = user;
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }
}
