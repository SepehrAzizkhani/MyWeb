package com.myweb.madules.posts.service;

import com.myweb.madules.categories.model.Categories;
import com.myweb.madules.categories.repository.CategoriesRepository;
import com.myweb.madules.categories.service.CategoriesService;
import com.myweb.madules.posts.model.Posts;
import com.myweb.madules.posts.repository.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Id;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostsService {
    private PostsRepository postsRepository;
    private CategoriesRepository categoriesRepository;

    private CategoriesService categoriesService;
    @Autowired
    public PostsService(PostsRepository postsRepository,CategoriesRepository categoriesRepository, CategoriesService categoriesService){
        this.postsRepository = postsRepository;
        this.categoriesRepository = categoriesRepository;
        this.categoriesService = categoriesService;
    }

    @Transactional
    public Posts  registerPosts(Posts posts) throws IOException {
        String name= UUID.randomUUID()+"."+ Objects.requireNonNull(posts.getFile().getContentType()).split("/")[1];
        String path= ResourceUtils.getFile("classpath:static/img/").getAbsolutePath();
        byte [] bytes = posts.getFile().getBytes();
        Files.write(Paths.get(path + File.separator + name),bytes);
        posts.setCover(name);

        List<Categories> categories = posts.getCategories();
        boolean noCategory = categories.stream()
                .noneMatch(
                        categories1 -> categories1.getTitle().equalsIgnoreCase("Artificial Intelligence") ||
                        categories1.getTitle().equalsIgnoreCase("Celebrities") ||
                        categories1.getTitle().equalsIgnoreCase("Products") ||
                        categories1.getTitle().equalsIgnoreCase("Programming")
                ) ;

        if (noCategory){
            Categories categoryMore = categoriesRepository.findByTitle("more");
            if (categoryMore == null ){
                categoryMore = new Categories();
                categoryMore.setTitle("more");
                categoriesRepository.save(categoryMore);
            }
            categories.add(categoryMore);
        }
        posts.setCategories(categories);

        return this.postsRepository.save(posts);
    }

    public List<Posts> findAllPosts(){return this.postsRepository.findAll();}
    public Page<Posts> findAllPosts(Pageable pageable){return this.postsRepository.findAll(pageable);}

    public List<Posts> findNewPosts(){
        return this.postsRepository.findFirst4ByOrderByUpdatedAtDesc();}

    public List<Posts> findByCategory(String category){
        Categories categories = categoriesRepository.findByTitle(category);
        return this.postsRepository.findFirst4ByCategoriesOrderByUpdatedAtDesc(categories);
    }
    public Page<Posts> findByCategory(String category,Pageable pageable){
        Categories categories = categoriesRepository.findByTitle(category);
        return this.postsRepository.findByCategories(categories,pageable);
    }

    public Posts findById(long id) {
        return postsRepository.getOne(id);
    }

    @Transactional
    public void deleteById(long id) {
        postsRepository.deleteById(id);
    }


    public Long countPosts(){
        return postsRepository.count();
    }

}
