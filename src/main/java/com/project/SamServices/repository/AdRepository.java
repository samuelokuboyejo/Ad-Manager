package com.project.SamServices.repository;

import com.project.SamServices.dto.AdDto;
import com.project.SamServices.entities.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {

    List<AdDto> findAllByUserId(Long userId);

    List<Ad> findAllByServiceNameContaining(String name);
}
