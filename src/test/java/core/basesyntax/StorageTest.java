package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class StorageTest {
    private static final String testLogin = "Kolya";
    private static final String testPassword = "password";
    private static final Integer testAge = 23;
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private User user = new User();
    private StorageDao dao = new StorageDaoImpl();

    @Test
    void register_validUser_ok() {
        User actual = user;
        actual.setLogin(testLogin);
        actual.setPassword(testPassword);
        actual.setAge(testAge);
        registrationService.register(actual);
        User expected = dao.get(actual.getLogin());
        assertEquals(actual, expected);
    }

    @Test
      void getUserByLogin_ok() {
        String login = "Vasya";
        user.setLogin(login);
        user.setPassword(testPassword);
        user.setAge(testAge);
        User actual = user;
        registrationService.register(actual);
        User expected = dao.get(login);
        assertEquals(actual, expected);
    }

    @Test
      void getNotExistUserByLogin_OK() {
        String login = "Semen";
        user.setLogin(testLogin);
        user.setPassword(testPassword);
        user.setAge(testAge);
        User expected = dao.get(login);
        User actual = user;
        registrationService.register(actual);
        assertNull(expected);
    }

    @Test
      void registerUser_loginNull_notOk() {
        String login = null;
        user.setLogin(login);
        user.setPassword(testPassword);
        user.setAge(testAge);
        User actual = user;
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
      void registerUser_loginEmpty_notOk() {
        String login = "";
        user.setLogin(login);
        user.setPassword(testPassword);
        user.setAge(testAge);
        User actual = user;
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
      void registerUser_loginTooLong_notOk() {
        String login = "Pan Ataman Grician Tavrichesky Zatmevayuschiy Nebo Nashey Prekrasnoy"
                + " Strany Velichiem Svoego Razuma Veduschego Chelovechestvo K Pobede Svetloy"
                + " Communisticheskoy Ideyi Marksizma-Leninizma Pryamo V Geroicheskoye Zavtra"
                + " Ogovorennoe Mudrym Resheniyem XXV S'ezda Vsesoyuznoy Molodyuzhy "
                + "Kommunisticheskoy Partii Trudyaschihsya Rabochih i Krestyan";
        user.setLogin(login);
        user.setPassword(testPassword);
        user.setAge(testAge);
        User actual = user;
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
      void registerUser_ageNull_notOk() {
        Integer age = null;
        user.setLogin(testLogin);
        user.setPassword(testPassword);
        user.setAge(age);
        User actual = user;
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
      void registerUser_ageLess_notOk() {
        Integer age = 4;
        user.setLogin(testLogin);
        user.setPassword(testPassword);
        user.setAge(age);
        User actual = user;
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
      void registerUser_ageTooMuch_notOk() {
        Integer age = 151;
        user.setLogin(testLogin);
        user.setPassword(testPassword);
        user.setAge(age);
        User actual = user;
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
      void registerUser_passwordNull_notOk() {
        String password = null;
        user.setLogin(testLogin);
        user.setPassword(password);
        user.setAge(testAge);
        User actual = user;
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
      void registerUser_passwordShort_notOk() {
        String password = "abc";
        user.setLogin(testLogin);
        user.setPassword(password);
        user.setAge(testAge);
        User actual = user;
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }

    @Test
      void registerUser_passwordLong_notOk() {
        String password = "hrenovy hackery NIKOGDA !@#$%^&*() ne vzlomayut moy parol"
                + " patamushta YA PRIMenyayu tut $pecialnyye simvoly stochniye i propisnuye"
                + " bikvy 1234567890 cifry pishu TRANSlitom stavlyu znaki prepinaniya,. i "
                + "vvobsche dopuskayu celuyu kuchu grMMatiCHeskikH oshibok pravda yesli ya"
                + " etot parol poteryayu to niKAKIE hackery iz Pentagona vspomnit' ego mne"
                + " ne POMOGUT";
        user.setLogin(testLogin);
        user.setPassword(password);
        user.setAge(testAge);
        User actual = user;
        assertThrows(RuntimeException.class, () -> registrationService.register(actual));
    }
}
