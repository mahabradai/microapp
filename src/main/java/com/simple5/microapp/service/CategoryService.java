package com.simple5.microapp.service;

import com.simple5.microapp.domain.Category;
import com.simple5.microapp.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Category}.
 */
@Service
@Transactional
public class CategoryService {

    private final Logger log = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Save a category.
     *
     * @param category the entity to save.
     * @return the persisted entity.
     */
    public Mono<Category> save(Category category) {
        log.debug("Request to save Category : {}", category);
        return categoryRepository.save(category);
    }

    /**
     * Update a category.
     *
     * @param category the entity to save.
     * @return the persisted entity.
     */
    public Mono<Category> update(Category category) {
        log.debug("Request to update Category : {}", category);
        return categoryRepository.save(category);
    }

    /**
     * Partially update a category.
     *
     * @param category the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<Category> partialUpdate(Category category) {
        log.debug("Request to partially update Category : {}", category);

        return categoryRepository
            .findById(category.getId())
            .map(existingCategory -> {
                if (category.getName() != null) {
                    existingCategory.setName(category.getName());
                }
                if (category.getDescription() != null) {
                    existingCategory.setDescription(category.getDescription());
                }

                return existingCategory;
            })
            .flatMap(categoryRepository::save);
    }

    /**
     * Get all the categories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<Category> findAll(Pageable pageable) {
        log.debug("Request to get all Categories");
        return categoryRepository.findAllBy(pageable);
    }

    /**
     * Returns the number of categories available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return categoryRepository.count();
    }

    /**
     * Get one category by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<Category> findOne(Long id) {
        log.debug("Request to get Category : {}", id);
        return categoryRepository.findById(id);
    }

    /**
     * Delete the category by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Category : {}", id);
        return categoryRepository.deleteById(id);
    }
}
