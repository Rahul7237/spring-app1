package com.example.Overflow.repository;

import com.example.Overflow.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRep  extends JpaRepository<Test, Long> {
}
