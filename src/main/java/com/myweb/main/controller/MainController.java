package com.myweb.main.controller;

import com.myweb.madules.categories.model.Categories;
import com.myweb.madules.categories.service.CategoriesService;
import com.myweb.madules.posts.model.Posts;
import com.myweb.madules.posts.service.PostsService;
import com.myweb.madules.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private PostsService postsService;
    @Autowired
    private CategoriesService categoriesService;
    @Autowired
    private UsersService usersService;


    @Autowired
    public MainController(PostsService postsService, CategoriesService categoriesService, UsersService usersService) {
        this.postsService = postsService;
        this.categoriesService=categoriesService;
        this.usersService=usersService;
    }

    @RequestMapping(" ")
    public String index(Model model, Authentication authentication) {
        model.addAttribute("postsAi", postsService.findByCategory("Artificial Intelligence"));
        model.addAttribute("postsCelebrity", postsService.findByCategory("Celebrities"));
        model.addAttribute("postsProducts", postsService.findByCategory("Products"));
        model.addAttribute("postsProgramming", postsService.findByCategory("Programming"));
        model.addAttribute("postsMore", postsService.findByCategory("more"));
        model.addAttribute("posts", postsService.findNewPosts());
        model.addAttribute("categories",categoriesService.findAllCategories());
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isAuthenticated",isAuthenticated);
        String role = "";
        if(authentication != null && authentication.isAuthenticated()){
            for(GrantedAuthority authority : authentication.getAuthorities()){
                role = authority.getAuthority();
                break;
            }
        }
        model.addAttribute("role",role);
        return "index/index";
    }

    @RequestMapping(value = "/{category}",method = RequestMethod.GET)
    public String allPosts(Model model, @PathVariable("category")String category, Authentication authentication, @PageableDefault(size = 12) Pageable pageable){
        model.addAttribute("posts", postsService.findByCategory(category,pageable));
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isAuthenticated",isAuthenticated);
        String role = "";
        if(authentication != null && authentication.isAuthenticated()){
            for(GrantedAuthority authority : authentication.getAuthorities()){
                role = authority.getAuthority();
                break;
            }
        }
        model.addAttribute("role",role);
        return "index/all-posts";
    }



    @RequestMapping(value = "/posts/{id}",method = RequestMethod.GET)
    public String showPost(Model model, @PathVariable("id")long id,Authentication authentication){
        model.addAttribute("post",postsService.findById(id));
        model.addAttribute("posts", postsService.findNewPosts());
        model.addAttribute("categories",categoriesService.findAllCategories());
        model.addAttribute("postsMore", postsService.findByCategory("more"));

        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isAuthenticated",isAuthenticated);
        String role = "";
        if(authentication != null && authentication.isAuthenticated()){
            for(GrantedAuthority authority : authentication.getAuthorities()){
                role = authority.getAuthority();
                break;
            }
        }
        model.addAttribute("role",role);

        return "index/article";
    }

    @RequestMapping(value = "/dash")
    public String dashboard(Model model) {
        Long postCount=postsService.countPosts();
        Long userCount=usersService.countUsers();
        Long categoryCount=categoriesService.countCategories();

        model.addAttribute("posts", postsService.findAllPosts());
        model.addAttribute("postCount",postCount);
        model.addAttribute("categories",categoriesService.findAllCategories());
        model.addAttribute("categoryCount",categoryCount);
        model.addAttribute("users",usersService.findAllUsers());
        model.addAttribute("userCount",userCount);
        return "admin/dashboard";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login() {
        return "register/login";
    }

    @RequestMapping(value = "/403",method = RequestMethod.GET)
    public String error403() {
        return "errors/403";
    }

    @RequestMapping(value = "/404",method = RequestMethod.GET)
    public String error404() {
        return "errors/404";
    }

}

