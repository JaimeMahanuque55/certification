package com.jmahanuque.certification.modules.students.repositories;

import com.jmahanuque.certification.modules.students.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {

    public StudentEntity findByEmail(String email);
}
