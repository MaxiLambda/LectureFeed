package com.lecturefeed.repository;


import com.lecturefeed.entity.Child;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository<Child, Long> {}
