package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.CurrentLoginIsExists;
import core.basesyntax.model.InvalidInputData;
import core.basesyntax.model.User;

import java.util.regex.Pattern;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidInputData("Inserted user data not exists");
        }
      if (user.getLogin() != null && storageDao.get(user.getLogin()) != null) {
          throw new CurrentLoginIsExists("The current login is occupied by another user. Please try again");
      }
      if (user.getLogin() == null || user.getLogin().isEmpty()) {
          throw new InvalidInputData("Field Login can't be empty or null. Try again");
      }
      if (user.getLogin().length() <= 14) {
          throw new InvalidInputData("Your login must contain more than 14 elements");
      }
      if (!(user.getLogin().matches("[a-zA-Z]\\d"))) {
          throw new InvalidInputData("Your login must much pattern [a-z-A-Z-0-9]");
      }
      if (user.getPassword() == null || user.getPassword().isEmpty()) {
          throw new InvalidInputData("Field password can't be empty or null");
      }
      if (user.getPassword().length() <= 10) {
          throw new InvalidInputData("Your password must contain more than 10 elements");
      }
      if (!(user.getPassword().matches("[a-zA-Z0-9]"))) {
          throw new InvalidInputData("Your password must much pattern [a-z-A-Z-0-9]");
      }
      if (user.getAge() < 18) {
          throw new InvalidInputData("You can't register until 18 years old. Sorry(");
      }
      if (user.getAge() == null) {
          throw new InvalidInputData("Field age can't be empty or null");
      }
      storageDao.add(user);
      return user;
    }
}
