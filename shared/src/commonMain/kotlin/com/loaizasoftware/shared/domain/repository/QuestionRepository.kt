package com.loaizasoftware.phrasalverbshero.domain.repository

import com.loaizasoftware.phrasalverbshero.domain.model.Question
//import io.reactivex.Single

interface QuestionRepository {

    suspend fun getQuestions(verbId: String): List<Question>

}