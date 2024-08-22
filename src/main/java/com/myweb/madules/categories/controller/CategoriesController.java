package com.myweb.madules.categories.controller;

import com.myweb.madules.categories.model.Categories;
import com.myweb.madules.categories.service.CategoriesService;
import com.myweb.madules.posts.model.Posts;
import com.myweb.madules.posts.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dash/categories")
public class CategoriesController {
    private CategoriesService categoriesService;
    @Autowired
    public  CategoriesController(CategoriesService categoriesService){
        this.categoriesService=categoriesService;
    }


    @RequestMapping(value = "",method = RequestMethod.GET)
    public String categories(Model model, @PageableDefault(size = 5) Pageable pageable){
        Page<Categories> categories = categoriesService.findAllCategories(pageable);
        model.addAttribute("categories",categories);
        return "admin/tables/categoriesTBL";
    }
    @RequestMapping(value = "CategoryRegister",method = RequestMethod.GET)
    public String registerPageC(Model model){
        model.addAttribute("category",new Categories() );
        return "register/registerCategory";
    }
    @RequestMapping(value = "CategoryRegister",method = RequestMethod.POST)
    public String registerC(@ModelAttribute Categories categories){
        categoriesService.registerCategory(categories);
        return "redirect:/dash/categories";
    }
    @RequestMapping(value = "/edit/{id}",method = RequestMethod.GET)
    public String editPageC(Model model,@PathVariable("id")long id){
        model.addAttribute("category",categoriesService.findById(id));
        return "register/registerCategory";
    }
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    public String deletePageC(@PathVariable("id")long id){
        categoriesService.deleteById(id);
        return "redirect:/dash/categories";
    }



    @RequestMapping(value = {"/rest"},method = RequestMethod.GET)
    public @ResponseBody List<Categories> getCategories(){
        return this.categoriesService.findAllCategories();
    }
    @RequestMapping(value = {"/rest"},method = RequestMethod.POST)
    public @ResponseBody Categories categoriesRegister(@RequestBody Categories categories){
        return categoriesService.registerCategory(categories);
    }
}
