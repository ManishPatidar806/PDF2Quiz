package com.backend.internalhackthon.Repository;

import com.backend.internalhackthon.Model.PDF;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PDFRepository extends JpaRepository<PDF,Long> {
    List<PDF> findAllByUserId(long userId);
}
