package com.loaizasoftware.phrasalverbshero.domain.model

data class Definition(
    val id: Long,
    val definition: String,
    val examples: List<Example>? = null
)