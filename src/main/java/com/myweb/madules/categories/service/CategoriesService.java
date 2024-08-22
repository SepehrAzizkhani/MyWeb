package com.myweb.madules.categories.service;

import com.myweb.madules.categories.model.Categories;
import com.myweb.madules.categories.repository.CategoriesRepository;
import com.myweb.madules.posts.model.Posts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CategoriesService {
   private CategoriesRepository categoriesRepository;
   @Autowired
   public CategoriesService(CategoriesRepository categoriesRepository){
       this.categoriesRepository=categoriesRepository;
   }
   public Categories registerCategory(Categories categories){
       return  this.categoriesRepository.save(categories);
   }

   public List<Categories> findAllCategories(){
       return this.categoriesRepository.findAll();
   }
   public Page<Categories> findAllCategories(Pageable pageable){return this.categoriesRepository.findAll(pageable);}
    public Categories findById(long id) {
       return categoriesRepository.getOne(id);
    }
@Transactional
    public void deleteById(long id) {
       categoriesRepository.deleteById(id);
    }

    public Long countCategories(){
        return categoriesRepository.count();
    }
}
