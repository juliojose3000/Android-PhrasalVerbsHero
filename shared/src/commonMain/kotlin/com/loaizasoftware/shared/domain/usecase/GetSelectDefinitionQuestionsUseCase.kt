package com.loaizasoftware.phrasalverbshero.domain.usecase

import com.loaizasoftware.phrasalverbshero.domain.model.Question
import com.loaizasoftware.phrasalverbshero.domain.repository.QuestionRepository
import com.loaizasoftware.shared.core.UseCase

class GetSelectDefinitionQuestionsUseCase /*@Inject constructor*/(private val repository: QuestionRepository):
    UseCase<List<Question>, String>() {

    override suspend fun run(params: String): List<Question> {
        return repository.getQuestions(params)
    }

}




