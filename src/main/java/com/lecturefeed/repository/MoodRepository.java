package com.lecturefeed.repository;

import com.lecturefeed.entity.model.MoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoodRepository extends JpaRepository<MoodEntity,Integer> {
}
