package com.loaizasoftware.phrasalverbshero.data.repository

import com.loaizasoftware.phrasalverbshero.data.api.ApiService
import com.loaizasoftware.phrasalverbshero.domain.model.Definition
import com.loaizasoftware.phrasalverbshero.domain.model.PhrasalVerb
import com.loaizasoftware.phrasalverbshero.domain.repository.PhrasalVerbRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class PhrasalVerbRepositoryImpl(private val apiService: ApiService): PhrasalVerbRepository {

    override suspend fun getPhrasalVerbs(verbId: Long): List<PhrasalVerb> {
        return apiService.getPhrasalVerbs(verbId)
    }

    /*override fun getPhrasalVerbsSafely(verbId: Long): Single<ApiResult<List<PhrasalVerb>>> {
        return Single.fromCallable {
            safeApiCall {
                apiService.getPhrasalVerbs(verbId).blockingGet()
            }
        }.subscribeOn(Schedulers.io())
    }*/

    override suspend fun getPhrasalVerbDefinitions(phrasalVerbId: Long): List<Definition> {
        return apiService.getPhrasalVerbDefinitions(phrasalVerbId)
    }

    override suspend fun getPhrasalVerbs(phrasalVerbPart: String): List<PhrasalVerb> {
        return apiService.getPhrasalVerbs(phrasalVerbPart)
    }

}