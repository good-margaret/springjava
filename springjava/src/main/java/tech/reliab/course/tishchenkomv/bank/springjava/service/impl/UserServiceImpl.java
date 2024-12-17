package tech.reliab.course.tishchenkomv.bank.springjava.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.reliab.course.tishchenkomv.bank.springjava.entity.User;
import tech.reliab.course.tishchenkomv.bank.springjava.model.UserRequest;
import tech.reliab.course.tishchenkomv.bank.springjava.repository.UserRepository;
import tech.reliab.course.tishchenkomv.bank.springjava.service.UserService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public User createUser(UserRequest userRequest) {
        User user = new User(userRequest.getFullName(), userRequest.getBirthDate(), userRequest.getJob());
        user.setMonthlyIncome(generateMonthlyIncome());
        user.setCreditRating(generateCreditRating(user.getMonthlyIncome()));
        return userRepository.save(user);
    }

    private int generateMonthlyIncome() {
        return new Random().nextInt(10001);
    }

    private int generateCreditRating(double monthlyIncome) {
        return (int) Math.ceil(monthlyIncome / 1000.0) * 100;
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User was not found"));
    }

    public User getUserDtoById(int id) {
        return getUserById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(int id, String name) {
        User user = getUserById(id);
        user.setFullName(name);
        return userRepository.save(user);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
