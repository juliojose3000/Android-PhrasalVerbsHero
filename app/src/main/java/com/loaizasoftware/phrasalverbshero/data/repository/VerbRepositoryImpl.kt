package com.loaizasoftware.phrasalverbshero.data.repository

import com.loaizasoftware.phrasalverbshero.data.api.ApiService
import com.loaizasoftware.phrasalverbshero.domain.model.Verb
import com.loaizasoftware.phrasalverbshero.domain.repository.VerbRepository
import io.reactivex.Single
import retrofit2.Call

open class VerbRepositoryImpl(private val apiService: ApiService): VerbRepository {

    /*override suspend fun getVerbs(): List<Verb> {
        return apiService.getVerbs()  // Just delegates to ApiService
    }*/

    override suspend fun getVerbsSingle(): List<Verb> {
        return apiService.getVerbsSingle()  // Just delegates to ApiService
    }

    override suspend fun getPrepsAdverbs(): List<String> {
        return apiService.getPrepsAdverbs()
    }


}