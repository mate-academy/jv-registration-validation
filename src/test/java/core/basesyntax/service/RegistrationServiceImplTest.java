package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
  private static RegistrationService registrationService;
  private static StorageDao storageDao;

 @BeforeAll
  static void beforeAll() {
   registrationService = new RegistrationServiceImpl();
   storageDao = new StorageDaoImpl();
 }

 @Test
  void correctDataForRegistration_Ok() throws RegistrationUserException {
   User user = new User("Bogdan", "krutoi228", 88);
   User user1 = new User("Speedwagon","coolguy2008", 36);
   User user2 = new User("Olegovich", "password", 43);
   assertTrue(Storage.people.contains(registrationService.register(user)));
   assertTrue(Storage.people.contains(registrationService.register(user1)));
   assertTrue(Storage.people.contains(registrationService.register(user2)));
 }

 @Test
  void successfulAddToList_Ok() {
   User user = new User("BogdAnus", "NeOleg", 18);
   storageDao.add(user);
   assertTrue(Storage.people.contains(user));
 }

 @Test
  void successfulRegistration_Ok() throws RegistrationUserException {
   User user = new User("Kirigaya", "Kazuto", 21);
   User registeredUser = registrationService.register(user);
   assertNotNull(registeredUser.getId());
   assertEquals(user.getLogin(), registeredUser.getLogin());
   assertEquals(user.getPassword(), registeredUser.getPassword());
   assertEquals(user.getAge(), registeredUser.getAge());
 }

 @Test
  void checkIfUserIsNull_NotOk() {
   User user = null;
   assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
 }

 @Test
  void checkUsersWithSameLogin_NotOk() throws RegistrationUserException {
  User user = new User("oleksandr", "average", 87);
  registrationService.register(user);
  assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
 }

  @Test
  void checkLoginForMinCountSymbols_NotOk() {
    User user = new User("gachi", "orlcmsht", 26);
    assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
  }

  @Test
  void checkPasswordForMinCountSymbols_NotOk() {
    User user = new User("children", "asdgf", 26);
    assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
  }

  @Test
  void checkAgeLessThanMin_NotOk() {
    User user = new User("shadow_fiend", "zxc123", 9);
    assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
  }

  @Test
  void checkNullValues_NotOk() {
    User user = new User(null, "notNull", 29);
    User user1 = new User("alsoNotNull", null, 65);
    User user2 = new User("loginNotNull", "passwordNotNull", 0);
    assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    assertThrows(RegistrationUserException.class, () -> registrationService.register(user1));
    assertThrows(RegistrationUserException.class, () -> registrationService.register(user2));
  }

  @Test
  void checkForIncorrectAge_NotOk() {
    User user = new User("Dungeon_Master", "orlcmsht", -5);
    User user1 = new User("nevermind", "nevermind", 105);
    assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    assertThrows(RegistrationUserException.class, () -> registrationService.register(user1));
  }
}