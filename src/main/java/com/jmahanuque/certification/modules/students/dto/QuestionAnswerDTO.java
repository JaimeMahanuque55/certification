package com.jmahanuque.certification.modules.students.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionAnswerDTO {

    private String questionID;
    private String alternativeID;
    private boolean isCorrect;
}
