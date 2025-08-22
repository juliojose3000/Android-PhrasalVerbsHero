package com.loaizasoftware.phrasalverbshero.data.repository

import com.loaizasoftware.phrasalverbshero.core.network.ApiResult
import com.loaizasoftware.shared.core.network.safeApiCall
import com.loaizasoftware.phrasalverbshero.data.api.ApiService
import com.loaizasoftware.phrasalverbshero.domain.model.Definition
import com.loaizasoftware.phrasalverbshero.domain.model.PhrasalVerb
import com.loaizasoftware.phrasalverbshero.domain.repository.PhrasalVerbRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class FakePhrasalVerbRepositoryImpl(private val apiService: ApiService): PhrasalVerbRepository {

    override suspend fun getPhrasalVerbs(verbId: Long): Single<List<PhrasalVerb>> {
        return apiService.getPhrasalVerbs(verbId)
    }

    override suspend fun getPhrasalVerbs(phrasalVerbPart: String): Single<List<PhrasalVerb>> {
        return apiService.getPhrasalVerbs(phrasalVerbPart)
    }

    override fun getPhrasalVerbsSafely(verbId: Long): Single<ApiResult<List<PhrasalVerb>>> {
        return Single.fromCallable {
            safeApiCall {
                apiService.getPhrasalVerbs(verbId).blockingGet()
            }
        }.subscribeOn(Schedulers.io())
    }

    override suspend fun getPhrasalVerbDefinitions(phrasalVerbId: Long): Single<List<Definition>> {
        return apiService.getPhrasalVerbDefinitions(phrasalVerbId)
    }

}