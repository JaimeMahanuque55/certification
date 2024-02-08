package com.jmahanuque.certification.modules.students.controllers;

import com.jmahanuque.certification.modules.students.dto.StudentCertificationAnswerDTO;
import com.jmahanuque.certification.modules.students.dto.VerifyIfHasCertificationDTO;
import com.jmahanuque.certification.modules.students.useCases.StudentCertificationAnswersUseCase;
import com.jmahanuque.certification.modules.students.useCases.VerifyIfHasCertificationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {

    // I need to use my useCase
    @Autowired
    private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;

    @Autowired
    private StudentCertificationAnswersUseCase studentCertificationAnswersUseCase;

    @PostMapping("verifyIfHasCertification")
    public String verifyIfHasCertification(@RequestBody VerifyIfHasCertificationDTO verifyIfHasCertificationDTO) {

        var result = this.verifyIfHasCertificationUseCase.execute(verifyIfHasCertificationDTO);
        if (result) {
            return  "Usuario ja fez a prova";
        }

        return "Usuario pode fazer a prova";
    }

    @PostMapping("/certification/answer")
    public StudentCertificationAnswerDTO certificationAnswer(@RequestBody StudentCertificationAnswerDTO studentCertificationAnswerDTO) {
        return studentCertificationAnswersUseCase.execute(studentCertificationAnswerDTO);
    }

}





















