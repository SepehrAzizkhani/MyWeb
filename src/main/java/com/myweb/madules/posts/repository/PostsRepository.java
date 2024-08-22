package com.myweb.madules.posts.repository;

import com.myweb.madules.categories.model.Categories;
import com.myweb.madules.posts.model.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts,Long> {

    List<Posts> findFirst4ByOrderByUpdatedAtDesc();
    List<Posts> findFirst4ByCategoriesOrderByUpdatedAtDesc(Categories categories);

    Page<Posts> findByCategories(Categories categories, Pageable pageable);
}
