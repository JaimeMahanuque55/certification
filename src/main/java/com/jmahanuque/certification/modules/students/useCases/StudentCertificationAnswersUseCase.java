package com.jmahanuque.certification.modules.students.useCases;

import com.jmahanuque.certification.modules.students.dto.StudentCertificationAnswerDTO;
import com.jmahanuque.certification.modules.students.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentCertificationAnswersUseCase {

    @Autowired
    private StudentRepository studentRepository;

    public void execute(StudentCertificationAnswerDTO dto) throws Exception{
        // verify if user exists
        var student = studentRepository.findByEmail(dto.getEmail());

        if (student.isEmpty()) {
            throw new Exception("E-mail do estudante incorrecto");
        }



        // Get questions alternatives (Correct or incorrect)

        // Save the information of certifications
    }
}
