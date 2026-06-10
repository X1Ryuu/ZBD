package com.example.zbd.repositories;

import com.example.zbd.entities.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findCategoryById(Long id);




    //--------------------------------- select
    @Query("""
SELECT c.name, c.description
FROM Category c
WHERE c.parentCategory IS NULL
""")
    List<Object[]> findRootCategories();

    //--------------------------------- update
    @Modifying
    @Transactional
    @Query("""
           UPDATE Category c
           SET c.parentCategory = null
           WHERE c.id = :id
           """)
    int removeParentCategory(
            @Param("id") Long id);

    //--------------------------------- delete
    void deleteAllById(Long id);

    //--------------------------------- insert


    //--------------------------------- aggregation


    //--------------------------------- join
    @Query("""
           SELECT c.name, p.name
           FROM Category c
           LEFT JOIN c.parentCategory p
           """)
    List<Object[]> findCategoriesWithParent();
}
