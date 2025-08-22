package com.loaizasoftware.phrasalverbshero.domain.usecase

import com.loaizasoftware.phrasalverbshero.domain.model.Definition
import com.loaizasoftware.phrasalverbshero.domain.repository.PhrasalVerbRepository
import com.loaizasoftware.shared.core.UseCase

open class GetDefinitionsUseCase /*@Inject*/ constructor(private val repository: PhrasalVerbRepository): UseCase<List<Definition>, Long>() {

    override suspend fun run(params: Long): List<Definition> {

        return repository.getPhrasalVerbDefinitions(params)

    }

}