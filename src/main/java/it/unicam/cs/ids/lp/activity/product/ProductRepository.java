package it.unicam.cs.ids.lp.activity.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Set<Product> findByActivity_Id(long id);
}
