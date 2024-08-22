package com.myweb.madules.users.controller;

import com.myweb.madules.categories.model.Categories;
import com.myweb.madules.users.model.Users;
import com.myweb.madules.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class UsersControllers {
    private UsersService usersService;
    @Autowired
    public UsersControllers(UsersService usersService){
        this.usersService=usersService;
    }


    @RequestMapping(value = "/dash/users",method = RequestMethod.GET)
    public String users(Model model, @PageableDefault(size = 5) Pageable pageable){
        Page<Users> users = usersService.findAllUsers(pageable);
        model.addAttribute("users",users);
        return "admin/tables/usersTBL";
    }


    @RequestMapping(value = "/UserRegister",method = RequestMethod.GET)
    public String registerPageU(Model model){
        model.addAttribute("user",new Users() );
        return "register/registerUser";
    }
    @RequestMapping(value = "/UserRegister",method = RequestMethod.POST)
    public String registerU(@ModelAttribute Users users) throws IOException {
        usersService.userRegister(users);
        return "redirect:/";
    }


    @RequestMapping(value = "/dash/users/UserRegister",method = RequestMethod.GET)
    public String registerPageUADMIN(Model model){
        model.addAttribute("user",new Users() );
        return "register/admin_registerUser";
    }
    @RequestMapping(value = "/dash/users/UserRegister",method = RequestMethod.POST)
    public String registerUADMIN(@ModelAttribute Users users) throws IOException {
        usersService.userRegister(users);
        return "redirect:/";
    }

    @RequestMapping(value = "/dash/users/edit/{id}",method = RequestMethod.GET)
    public String editPageUADMIN(Model model,@PathVariable("id")long id){
        model.addAttribute("user",usersService.findById(id));
        return "register/admin_registerUser";
    }
    @RequestMapping(value = "/dash/users/delete/{id}",method = RequestMethod.GET)
    public String deletePageU(@PathVariable("id")long id){
        usersService.deleteById(id);
        return "redirect:/dash/users";
    }

    @RequestMapping(value = {"/rest"},method = RequestMethod.GET)
    public @ResponseBody List<Users> getUsers(){
        return this.usersService.findAllUsers();
    }
    @RequestMapping(value = {"/rest"},method = RequestMethod.POST)
    public @ResponseBody Users userRegister(@RequestBody Users users) throws IOException {
        return usersService.userRegister(users);
    }

}
