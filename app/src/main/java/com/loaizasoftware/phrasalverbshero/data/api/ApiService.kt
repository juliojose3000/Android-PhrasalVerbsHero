package com.loaizasoftware.phrasalverbshero.data.api

import com.loaizasoftware.phrasalverbshero.domain.model.Definition
import com.loaizasoftware.phrasalverbshero.domain.model.PhrasalVerb
import com.loaizasoftware.phrasalverbshero.domain.model.Question
import com.loaizasoftware.phrasalverbshero.domain.model.Verb
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    /*@GET("api/verbs")
    fun getVerbs(): Call<List<Verb>>*/

    @GET("api/verbs")
    suspend fun getVerbsSingle(): List<Verb>

    @GET("api/phrasalverbs/getByVerbId")
    suspend fun getPhrasalVerbs(@Query("verbId") verbId: Long): List<PhrasalVerb>

    @GET("api/phrasalverbs/getByPrepOrAdverb")
    suspend fun getPhrasalVerbs(@Query("prepositionAdverb") prepositionAdverb: String): List<PhrasalVerb>

    @GET("api/phrasalverbs/getDefinitions")
    suspend fun getPhrasalVerbDefinitions(@Query("phrasalVerbId") phrasalVerbId: Long): List<Definition>

    @GET("api/questions/getSelectDefinitionQuestions")
    suspend fun getQuestions(@Query("phrasalVerbPart") phrasalVerbPart: String): List<Question>

    @GET("api/phrasalverbs/getAllPrepAndAdverbs")
    suspend fun getPrepsAdverbs(): List<String>

}