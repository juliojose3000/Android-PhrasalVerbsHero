package com.loaizasoftware.phrasalverbshero.domain.repository

import com.loaizasoftware.phrasalverbshero.domain.model.Verb
//import io.reactivex.Single
//import retrofit2.Call

interface VerbRepository {

    //suspend fun getVerbs(): List<Verb>

    suspend fun getVerbsSingle(): List<Verb>

    suspend fun getPrepsAdverbs(): List<String>

}