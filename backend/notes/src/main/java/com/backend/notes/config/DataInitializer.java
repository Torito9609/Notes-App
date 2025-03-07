package com.backend.notes.config;


import com.backend.notes.model.Category;
import com.backend.notes.repository.ICategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ICategoryRepository categoryRepository;

    public DataInitializer(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() == 0) {
            Category cat1 = new Category();
            cat1.setName("Work");
            cat1.setDefault(true);

            Category cat2 = new Category();
            cat2.setName("Personal");
            cat2.setDefault(true);

            Category cat3 = new Category();
            cat3.setName("Important");
            cat3.setDefault(true);

            Category cat4 = new Category();
            cat4.setName("Others");
            cat4.setDefault(true);

            categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4));

            System.out.println("Default categories created.");
        }
    }
}
