package com.loaizasoftware.phrasalverbshero.domain.usecase


import com.loaizasoftware.phrasalverbshero.domain.model.PhrasalVerb
import com.loaizasoftware.phrasalverbshero.domain.repository.PhrasalVerbRepository
import com.loaizasoftware.shared.core.UseCase

open class GetPhrasalVerbsUseCase /*@Inject constructor*/(private val repository: PhrasalVerbRepository): UseCase<List<PhrasalVerb>, Long>() {

    /*fun get(params: Long): Single<ApiResult<List<PhrasalVerb>>> {
        return repository.getPhrasalVerbsSafely(params)
    }

    fun get(params: String): Single<List<PhrasalVerb>> {
        return repository.getPhrasalVerbs(params)
    }*/

    override suspend fun run(params: Long): List<PhrasalVerb> {
        return repository.getPhrasalVerbs(params)
    }

}