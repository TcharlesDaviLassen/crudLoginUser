package com.example.UserLoginSpring.service;

import com.example.UserLoginSpring.confg.PasswordEncoderUtil;
import com.example.UserLoginSpring.model.User;
import com.example.UserLoginSpring.repository.UserRepository;
import com.example.UserLoginSpring.usersession.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    final private UserRepository userRepository;
    final private UserSession userSession;

    @Autowired
    public UserService(UserRepository userRepository, UserSession userSession) {
        this.userRepository = userRepository;
        this.userSession = userSession;
    }

    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user!= null) {
            var data =  PasswordEncoderUtil.matchesPass(password, user.getPassword());
            if (data) {

                userSession.setUser(user);
                userSession.setLoggedIn(true);

                return true;
            }
        }

        userSession.setLoggedIn(false);
        return false;

        //        if (user != null && user.getPassword().equals(password)) {
        //            PasswordEncoderUtil.matchesPass(password, user.getPassword());
        //
        //            userSession.setUser(user);
        //            userSession.setLoggedIn(true);
        //            return true;
        //        }
    }


    //    public boolean authenticate(String username, String password) {
    //        User user = userRepository.findByUsername(username);
    //        if (user != null) {
    //            return PasswordEncoderUtil.matchesPass(password, user.getPassword());
    //        }
    //        return false;
    //    }

    public boolean isAuthenticated() {
        return userSession.getUser() != null;
    }


    public User createUser(User user) {
        // Realize a validação e o processamento necessário antes de criar o usuário
        // Exemplo: criptografar a senha antes de salvar
        String encodedPassword = PasswordEncoderUtil.encodePassword(user.getPassword());
        user.setPassword(encodedPassword);
        user.getUsername().toUpperCase().trim();

        return userRepository.save(user);

        //        // Implemente a lógica para criar um usuário
        //        // Verifique a existência do email, valide os dados, etc.
        //        // Por exemplo:
        //        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow();
    }

    public User updateUser(Long id, User user) {

        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) {
            return null;
        }
        String encodedPassword = PasswordEncoderUtil.encodePassword(user.getPassword());

        // Realize a atualização dos campos necessários
        existingUser.setUsername(user.getUsername().toUpperCase());
        existingUser.setPassword(encodedPassword);

        return userRepository.save(existingUser);

        //        User user = getUserById(id);
        //
        //        // Atualize os detalhes do usuário com base nos dados fornecidos
        //        user.setUsername(userDetails.getUsername());
        //        user.setPassword(userDetails.getPassword());
        //
        //        return userRepository.save(user);
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);

            return true;
        }
        return false;
    }
}

