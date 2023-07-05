package com.humaltelu.drinkinggame.controller;

import java.util.List;

// import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.humaltelu.drinkinggame.model.Pack;
import com.humaltelu.drinkinggame.model.User;
import com.humaltelu.drinkinggame.repository.GameRepository;

@RestController
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @PostMapping("/user")
    public User saveUser(@RequestBody User user) {
        return gameRepository.saveU(user);
        
    }

    @PostMapping("/user/pack")
    public Pack savepack(@RequestBody Pack pack) {
        return gameRepository.saveP(pack);
        
    }

    // @GetMapping("/workout/{name}/year")
    // public List<User> getAllUser(@PathVariable("name") String user_id) {
    //     String user_id = name1;
    //     // System.out.print("DEBUG!!!!");
    //     return gameRepository.getAll(user_id);
    // }

   

    @GetMapping("/user/packs/{id}")
    public List<Pack> getUserPacks(@PathVariable("id") String user_id){
        // System.out.print("coming through");
        // System.out.print(workoutRepository.getWorkoutById(workoutid));
    return gameRepository.getUserPacks(user_id);
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") String user_id){
        // System.out.print("coming through");
        // System.out.print(workoutRepository.getWorkoutById(workoutid));
    return gameRepository.getUserById(user_id);
    }

    @GetMapping("/user/pack/{user_id}/{pack_id}")
    public Pack getPack(@PathVariable("user_id") String user_id, @PathVariable("pack_id") String pack_id){
        // System.out.print("coming through");
        // System.out.print(workoutRepository.getWorkoutById(workoutid));
    return gameRepository.getPackById(user_id, pack_id);
    }

    @GetMapping("/user/pack/new")
    public List<Pack> getPacksNew(){
        // System.out.print("coming through");
        // System.out.print(workoutRepository.getWorkoutById(workoutid));
    return gameRepository.getUserPacksNew();
    }

    @GetMapping("/user/packs")
    public List<Pack> getAll(){
        // System.out.print("coming through");
        // System.out.print(workoutRepository.getWorkoutById(workoutid));
    return gameRepository.getAllPacks();
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable("id") String user_id){
        return gameRepository.delete(user_id);
    }

    @PutMapping("/user/{id}")
    public String updateWorkout(@PathVariable("id") String user_id, @RequestBody User user){
        return gameRepository.update(user_id, user);
    }
    
}
