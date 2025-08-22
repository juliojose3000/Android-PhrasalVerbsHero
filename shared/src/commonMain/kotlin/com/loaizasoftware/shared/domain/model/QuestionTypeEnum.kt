package com.loaizasoftware.phrasalverbshero.domain.model

enum class QuestionTypeEnum(val typeId: Int, val label: String) {
    ALL(1, "All"),
    SELECT_DEFINITION(2, "Select Definition"),
    WRITE_PHRASAL_VERB(3, "Write Phrasal Verb"),
    IMAGE_CARD(4, "Image Card"),
    SELECT_PHRASAL_VERB(5, "Select Phrasal Verb");

    companion object {
        fun fromTypeId(id: Int): QuestionTypeEnum? =
            entries.find { it.typeId == id }
    }
}
