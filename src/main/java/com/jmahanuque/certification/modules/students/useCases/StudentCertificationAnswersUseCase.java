package com.jmahanuque.certification.modules.students.useCases;

import com.jmahanuque.certification.modules.questions.entities.QuestionEntity;
import com.jmahanuque.certification.modules.questions.repositories.QuestionRepository;
import com.jmahanuque.certification.modules.students.dto.StudentCertificationAnswerDTO;
import com.jmahanuque.certification.modules.students.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentCertificationAnswersUseCase {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public StudentCertificationAnswerDTO execute(StudentCertificationAnswerDTO dto) throws Exception{
        // verify if user exists
        var student = studentRepository.findByEmail(dto.getEmail());

        if (student.isEmpty()) {
            throw new Exception("E-mail do estudante incorrecto");
        }

        // Get questions alternatives (Correct or incorrect)
        List<QuestionEntity> questionsEntity = questionRepository.findByTechnology(dto.getTechnology());

        dto.getQuestionsAnswers().stream().forEach(questionAnswer -> {
            var question = questionsEntity.stream().filter(q -> q.getId().equals(questionAnswer.getQuestionID()))
                    .findFirst().get();

            var findCorrectAlternative = question.getAlternatives().stream().filter(alternative -> alternative.isCorrect()).findFirst().get();

            if (findCorrectAlternative.getId().equals(questionAnswer.getAlternativeID())) {
                questionAnswer.setCorrect(true);
            } else {
                questionAnswer.setCorrect(false);
            }
        });

        return dto;
        // Save the information of certifications
    }
}
