package com.loaizasoftware.phrasalverbshero.domain.repository

import com.loaizasoftware.phrasalverbshero.domain.model.Definition
import com.loaizasoftware.phrasalverbshero.domain.model.PhrasalVerb

interface PhrasalVerbRepository {

    suspend fun getPhrasalVerbs(verbId: Long): List<PhrasalVerb>

    suspend fun getPhrasalVerbs(phrasalVerbPart: String): List<PhrasalVerb>

    //suspend fun getPhrasalVerbsSafely(verbId: Long): ApiResult<List<PhrasalVerb>>

    suspend fun getPhrasalVerbDefinitions(phrasalVerbId: Long): List<Definition>

}