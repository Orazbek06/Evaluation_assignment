package com.example.evaluationassignment.repository

import com.example.evaluationassignment.data.response.CountryDto
import com.example.evaluationassignment.data.response.IntentionDto
import com.example.evaluationassignment.data.response.LeadDetailed
import com.example.evaluationassignment.data.response.LeadsGeneral
import com.example.evaluationassignment.data.response.StatusData
import com.example.evaluationassignment.type.CreateLeadInput
import com.example.evaluationassignment.type.FetchLeadInput
import com.example.evaluationassignment.type.FetchLeadsInput
import com.example.evaluationassignment.type.UpdateLeadInput
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun getLeads(
        params: FetchLeadsInput? = null
    ): Flow<Result<List<LeadsGeneral>>>

    suspend fun getLead(lead: FetchLeadInput): Flow<Result<LeadDetailed>>

    suspend fun createLead(lead: CreateLeadInput): Flow<Result<LeadDetailed>>

    suspend fun updateLead(lead: UpdateLeadInput): Flow<Result<LeadDetailed>>

    suspend fun getIntentions(): Flow<Result<List<IntentionDto>>>

    suspend fun getAdSources(): Flow<Result<List<IntentionDto>>>

    suspend fun getCountries(): Flow<Result<List<CountryDto>>>

    suspend fun getStatuses(): Flow<Result<List<StatusData>>>

    suspend fun getCities(id: Int): Flow<Result<List<IntentionDto>>>

    suspend fun getLanguages(): Flow<Result<List<IntentionDto>>>

    fun clearPagination()
}