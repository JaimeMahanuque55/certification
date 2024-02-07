package com.jmahanuque.certification.modules.students.useCases;

import com.jmahanuque.certification.modules.students.dto.VerifyIfHasCertificationDTO;
import com.jmahanuque.certification.modules.students.repositories.CertificationStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerifyIfHasCertificationUseCase {

    @Autowired
    private CertificationStudentRepository certificationStudentRepository;

    public boolean execute(VerifyIfHasCertificationDTO dto ) {
       var result = this.certificationStudentRepository.findByStudentEmailAndTechnology(dto.getEmail(), dto.getTechnology());
        if (!result.isEmpty()) {
            return true;
        }
        return false;
    }
}
