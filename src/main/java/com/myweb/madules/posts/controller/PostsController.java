package com.myweb.madules.posts.controller;

import com.myweb.madules.categories.model.Categories;
import com.myweb.madules.categories.service.CategoriesService;
import com.myweb.madules.posts.model.Posts;
import com.myweb.madules.posts.service.PostsService;
import com.myweb.madules.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/dash/posts")
public class PostsController {
    private PostsService postsService;
    private CategoriesService categoryService;
    private UsersService usersService;

    @Autowired
    public PostsController(PostsService postsService, CategoriesService categoryService, UsersService usersService){
        this.postsService=postsService;
        this.categoryService = categoryService;
        this.usersService = usersService;
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    public String posts(Model model , @PageableDefault(size = 10) Pageable pageable){
        Page<Posts> posts = postsService.findAllPosts(pageable);
        model.addAttribute("posts",posts);
        return "admin/tables/postsTBL";
    }

    @RequestMapping(value = "/PostRegister",method = RequestMethod.GET)
    public String registerPageP(Model model){
        model.addAttribute("post",new Posts() );
        model.addAttribute("categories",categoryService.findAllCategories());
        return "register/registerPost";
    }
    @RequestMapping(value = "/PostRegister",method = RequestMethod.POST)
    public String registerP(@ModelAttribute Posts posts, Principal principal) throws IOException, InvocationTargetException, IllegalAccessException {
        posts.setUsers(usersService.findByEmail(principal.getName()));
        postsService.registerPosts(posts);
        return "redirect:/dash/posts";
    }
    @RequestMapping(value = "/edit/{id}",method = RequestMethod.GET)
    public String editPageP(Model model,@PathVariable("id")long id){
        model.addAttribute("post",postsService.findById(id));
        return "register/registerPost";
    }
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    public String deletePageP(@PathVariable("id")long id){
        postsService.deleteById(id);
        return "redirect:/dash/posts";
    }




    @RequestMapping(value = "/rest",method = RequestMethod.GET)
    public @ResponseBody List<Posts> getPosts(){
        return this.postsService.findAllPosts();
    }
    @RequestMapping(value ="/rest",method = RequestMethod.POST)
    public @ResponseBody Posts postRegister(@RequestBody Posts posts)throws IOException, InvocationTargetException, IllegalAccessException {
        return postsService.registerPosts(posts);
    }

}
