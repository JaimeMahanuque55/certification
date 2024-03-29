package com.jmahanuque.certification.modules.students.useCases;

import com.jmahanuque.certification.modules.questions.entities.QuestionEntity;
import com.jmahanuque.certification.modules.questions.repositories.QuestionRepository;
import com.jmahanuque.certification.modules.students.dto.StudentCertificationAnswerDTO;
import com.jmahanuque.certification.modules.students.dto.VerifyIfHasCertificationDTO;
import com.jmahanuque.certification.modules.students.entities.AnswersCertificationsEntity;
import com.jmahanuque.certification.modules.students.entities.CertificationStudentEntity;
import com.jmahanuque.certification.modules.students.entities.StudentEntity;
import com.jmahanuque.certification.modules.students.repositories.CertificationStudentRepository;
import com.jmahanuque.certification.modules.students.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StudentCertificationAnswersUseCase {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CertificationStudentRepository certificationStudentRepository;

    @Autowired
    private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;

    public CertificationStudentEntity execute(StudentCertificationAnswerDTO dto) throws Exception{

        var hastCertification = this.verifyIfHasCertificationUseCase.execute(new VerifyIfHasCertificationDTO(dto.getEmail(), dto.getTechnology()));

        if (hastCertification) {
            throw new Exception("Voce ja tirou sua certificacao!");
        }

        // Get questions alternatives (Correct or incorrect)
        List<QuestionEntity> questionsEntity = questionRepository.findByTechnology(dto.getTechnology());
        List<AnswersCertificationsEntity> answersCertifications = new ArrayList<>();

        AtomicInteger correctAnswers = new AtomicInteger(0);

        dto.getQuestionsAnswers().stream().forEach(questionAnswer -> {
            var question = questionsEntity.stream().filter(q -> q.getId().equals(questionAnswer.getQuestionID()))
                    .findFirst().get();

            var findCorrectAlternative = question.getAlternatives().stream()
                    .filter(alternative -> alternative.isCorrect()).findFirst().get();

            if (findCorrectAlternative.getId().equals(questionAnswer.getAlternativeID())) {
                questionAnswer.setCorrect(true);
                correctAnswers.incrementAndGet();
            } else {
                questionAnswer.setCorrect(false);
            }

            var answerrsCertificationsEntity = AnswersCertificationsEntity.builder().answerID(questionAnswer.getAlternativeID()).questionID(questionAnswer.getQuestionID()).isCorrect(questionAnswer.isCorrect()).build();


            answersCertifications.add(answerrsCertificationsEntity);
        });

        // Verify if students exists by email
        var student = studentRepository.findByEmail(dto.getEmail());
        UUID studentID;
        if (student.isEmpty()) {
            var studentCreated = StudentEntity.builder().email(dto.getEmail()).build();
            studentCreated = studentRepository.save(studentCreated);
            studentID = studentCreated.getId();
        } else {
            studentID = student.get().getId();
        }

        CertificationStudentEntity certificationStudentEntity = CertificationStudentEntity.builder()
                .technology(dto.getTechnology())
                .studentID(studentID)
                .grade(correctAnswers.get())
                .build();

        var certificationStudentCreated = certificationStudentRepository.save(certificationStudentEntity);

        answersCertifications.stream().forEach(answerCertification -> {
            answerCertification.setCertificationID(certificationStudentEntity.getId());
            answerCertification.setCertificationStudentEntity((certificationStudentEntity));
        });

        certificationStudentEntity.setAnswersCertificationsEntities(answersCertifications);

        certificationStudentRepository.save(certificationStudentEntity);


        return certificationStudentCreated;
        // Save the information of certifications
    }
}
























