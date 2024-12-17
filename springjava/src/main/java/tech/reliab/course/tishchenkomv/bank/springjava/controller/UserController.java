package tech.reliab.course.tishchenkomv.bank.springjava.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import tech.reliab.course.tishchenkomv.bank.springjava.entity.User;
import tech.reliab.course.tishchenkomv.bank.springjava.model.UserRequest;

import java.util.List;

public interface UserController {

    ResponseEntity<User> createUser(@RequestBody UserRequest userRequest);

    ResponseEntity<Void> deleteUser(@PathVariable int id);

    ResponseEntity<User> updateUser(@PathVariable int id, @RequestParam(name = "name") String name);

    ResponseEntity<User> getUserById(@PathVariable int id);

    ResponseEntity<List<User>> getAllUsers();
}
