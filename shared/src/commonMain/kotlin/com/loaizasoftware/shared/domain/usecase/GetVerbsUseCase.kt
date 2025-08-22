package com.loaizasoftware.phrasalverbshero.domain.usecase


import com.loaizasoftware.phrasalverbshero.domain.model.Verb
import com.loaizasoftware.phrasalverbshero.domain.repository.VerbRepository
import com.loaizasoftware.shared.core.None
import com.loaizasoftware.shared.core.UseCase


open class GetVerbsUseCase /*@Inject constructor*/(private val repository: VerbRepository): UseCase<List<Verb>, None>() {

    /*fun execute(): Call<List<Verb>> {
        return repository.getVerbs()
    }*/

    override suspend fun run(params: None): List<Verb> {
        return repository.getVerbsSingle()
    }

}
