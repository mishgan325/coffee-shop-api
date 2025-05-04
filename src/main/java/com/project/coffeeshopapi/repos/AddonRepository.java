package com.project.coffeeshopapi.repos;

import com.project.coffeeshopapi.models.Addon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddonRepository extends JpaRepository<Addon, Long> {
    List<Addon> findAllByAddonIdIn(List<Long> ids);
}
