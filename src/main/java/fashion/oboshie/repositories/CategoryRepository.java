package fashion.oboshie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fashion.oboshie.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, long> {

    Category findByCategoryName(String categoryName);

}
