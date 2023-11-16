## Common mistakes (jv-registration-validation)

* Don't ignore checkstyle rules.
* Figure out which way is better to initialize your RegistrationServiceImpl class instance: `@BeforeEach` or `@BeforeAll`, what's the difference?
* Make sure you name your methods according to [convention](https://google.github.io/styleguide/javaguide.html#s5.2.3-method-names).
* Add tests for all possible User's parameters (null login/password/age, user under 18/18/over 18 years old, negative age, and so on...).
* Make sure to **not** add something that is not described in the task requirements, or tied to something that may change over time like max user age. 
It is likely that average and maximum human lifespan will grow over time, so let's keep it in mind.
* In your tests very important to check **all possible edge cases**. For example, we have min password length 6 characters. 
In this case would be good to test: empty password (0-length - not-ok), 'abc' (3-length - not-ok), 'abcdf' (5-length - **not-ok edge case**), 
'abcdef' (6-length - **ok edge case**), 'abcdefgh' (8-length - ok). Few not-ok checks you can have in one not-ok test method. Same for ok-test.
Testing only 'abc' (3-length - not-ok) and 'abcdefgh' (8-length - ok) is not enough because we do not check how the program behave with edge cases.
* Make sure that after successful registration user was added to storage and `register` method returned correct user.
* Use low-level methods like `Storage.people.add()` directly instead of `RegistrationService.register()` method to add users for testing. 
* Because we can not use the same method which is under a test to insert some mock test data into our storage since we can not guarantee its correctness yet.
* Don't create one big if with all conditions. Better to split one big if into several small ones.

  - Bad example:
  ```java
    public User register(User user) {
     if (user.getLogin() == null 
       || user.getPassword() == null
       || user.getAge() < MIN_AGE) {
       throw new RegistrationException("Invalid data");
     }
     return storageDao.add(user);
    }
  ```

  - Good example:
  ```java
    public User register(User user) {
     if (user.getLogin() == null) {
       throw new RegistrationException("Login can't be null");
     }
     if (user.getPassword() == null) {
       throw new RegistrationException("Password can't be null");
     }
     if (user.getAge() < MIN_AGE) {
       throw new RegistrationException("Not valid age: " + user.getAge() + ". Min allowed age is " + MIN_AGE);
     }
     return storageDao.add(user);
    }
  ```
* Exception messages should be informative (see code example above).
* You don't need main method to see how your solution works, you have tests for this purpose.
* If you are expecting exception to be thrown in the test, you can do it this way:
```
assertThrows(ExpectedException.class, () -> object.methodUnderTest(inputParams);
```
* Don't use `NullPointerException`, because in such case we can't distinguish was this exception because of our mistake or because of validation.

* Move all hardcoded values to [constant fields](https://mate-academy.github.io/style-guides/java/java.html#s5.2.4-constant-names) and give them informative names.

* Check your code for coverage with tests, expected coverage should be 90%+. [How to run](https://www.jetbrains.com/help/idea/running-test-with-coverage.html#run-config-with-coverage)

* Remember about naming of test methods.
  There are a lot of ways to name your test methods. The main point is that 
  they should have informative names and be consistent along with other developers in your team. 
  For this task use such convention: `<methodUnderTest>_<state>_<expectedBehavior>`. 
  For example, if we are testing the method `register` with a `null` user's age 
  the test method name should be `register_nullAge_notOk`. `notOk` is because 
  the test expects the register method to throw an exception.
  
