package com.myweb.madules.categories.repository;

import com.myweb.madules.categories.model.Categories;
import com.myweb.madules.posts.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriesRepository extends JpaRepository<Categories,Long> {
    Categories findByTitle(String category);
}
