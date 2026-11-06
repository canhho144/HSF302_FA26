package fu.se20b01.se20b01_de181016_sdm_pe.service.impl;

import fu.se20b01.se20b01_de181016_sdm_pe.entity.Category;
import fu.se20b01.se20b01_de181016_sdm_pe.repository.CategoryRepository;
import fu.se20b01.se20b01_de181016_sdm_pe.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}