package com.loaizasoftware.phrasalverbshero.domain.model

//import com.squareup.moshi.Json

//import com.google.gson.annotations.SerializedName

//@JsonClass(generateAdapter = true) // ✅ Required for Moshi serialization
data class Verb(
    val id: Long,
    //@SerializedName("verb") val name: String,
    /*@Json(name = "verb")*/ val verb: String,
    val phrasalVerbs: List<PhrasalVerb>
)