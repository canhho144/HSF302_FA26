package fu.se20b01.se20b01_de181016_sdm_pe.service;


import fu.se20b01.se20b01_de181016_sdm_pe.entity.Category;

import java.util.List;

public interface CategoryService {
    // Q3.1 / Q5.2.9 - Category dropdown must be populated dynamically from the database
    List<Category> getAllCategories();
}

