package com.loaizasoftware.phrasalverbshero.domain.model

data class Question(
    val type: QuestionTypeEnum,
    val title: String,
    val answer: Answer,
    val wrongAnswers: List<Answer>,
    val imageCard: String? = null
) {

    /**
     * Returns a shuffled list of all possible answers for the question,
     * including the correct answer and all wrong answers.
     *
     * This is typically used to display multiple-choice options to the user.
     *
     * @return a randomly ordered list of [Answer] objects, where exactly one is correct.
     */
    fun getAllAnswers(): List<Answer> {
        val allAnswers = mutableListOf<Answer>()
        allAnswers.add(answer)
        allAnswers.addAll(wrongAnswers)
        allAnswers.shuffle()
        return allAnswers
    }

}
