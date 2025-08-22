package com.loaizasoftware.phrasalverbshero.domain.usecase

import com.loaizasoftware.phrasalverbshero.domain.model.PhrasalVerb
import com.loaizasoftware.phrasalverbshero.domain.repository.PhrasalVerbRepository
import com.loaizasoftware.shared.core.UseCase


open class GetPhrasalVerbsByPart /*@Inject constructor*/(private val repository: PhrasalVerbRepository): UseCase<List<PhrasalVerb>, String>() {

    override suspend fun run(params: String): List<PhrasalVerb> {
        return repository.getPhrasalVerbs(params)
    }

}