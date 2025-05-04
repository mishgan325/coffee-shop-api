package com.project.coffeeshopapi.repos;

import com.project.coffeeshopapi.models.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
}
