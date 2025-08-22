package com.loaizasoftware.phrasalverbshero.domain.usecase

import com.loaizasoftware.phrasalverbshero.domain.repository.VerbRepository
import com.loaizasoftware.shared.core.None
import com.loaizasoftware.shared.core.UseCase

open class GetPrepsAdverbsUseCase /*@Inject constructor*/(private val repository: VerbRepository): UseCase<List<String>, None>() {

    override suspend fun run(params: None): List<String> {
        return repository.getPrepsAdverbs()
    }

}
