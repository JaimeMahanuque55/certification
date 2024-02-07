package com.jmahanuque.certification.modules.students.useCases;

import com.jmahanuque.certification.modules.students.dto.VerifyIfHasCertificationDTO;
import org.springframework.stereotype.Service;

@Service
public class VerifyIfHasCertificationUseCase {

    public boolean execute(VerifyIfHasCertificationDTO dto ) {
        if (dto.getEmail().equals("jaime@gmail.com") && dto.getTechnology().equals("JAVA")) {
            return true;
        }
        return false;
    }
}
