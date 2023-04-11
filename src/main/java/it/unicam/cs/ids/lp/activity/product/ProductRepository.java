package it.unicam.cs.ids.lp.activity.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByActivities_Id(long id);
}
