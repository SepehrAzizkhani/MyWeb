package com.myweb.madules.users.service;

import com.myweb.enums.Roles;
import com.myweb.madules.categories.model.Categories;
import com.myweb.madules.users.model.Users;
import com.myweb.madules.users.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.persistence.PrePersist;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class UsersService{
    private UsersRepository usersRepository;
    @Autowired
    public UsersService(UsersRepository usersRepository){
        this.usersRepository= usersRepository;
    }
    public Users userRegister(Users users) throws IOException {
        String name= UUID.randomUUID()+"."+ Objects.requireNonNull(users.getFile().getContentType()).split("/")[1];
        String path= ResourceUtils.getFile("classpath:static/img/").getAbsolutePath();
        byte [] bytes = users.getFile().getBytes();
        Files.write(Paths.get(path + File.separator + name),bytes);
        users.setCover(name);

        if(users.getRoles() == null || users.getRoles().isEmpty()){
            users.setRoles(Arrays.asList(Roles.USER));
        }

          return this.usersRepository.save(users);
    }
    public List<Users> findAllUsers(){
        return this.usersRepository.findAll();
    }
    public Page<Users> findAllUsers(Pageable pageable){return this.usersRepository.findAll(pageable);}

    public Users findById(long id) {
        return usersRepository.getOne(id);
    }

    @Transactional
    public void deleteById(long id) {
        usersRepository.deleteById(id);
    }

    public Long countUsers(){
        return usersRepository.count();
    }

    public Users findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

}
