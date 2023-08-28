package com.example.evaluationassignment.network

import com.example.evaluationassignment.data.response.CountryDto
import com.example.evaluationassignment.data.response.IntentionDto
import com.example.evaluationassignment.data.response.LeadDetailed
import com.example.evaluationassignment.data.response.LeadsPaginated
import com.example.evaluationassignment.data.response.StatusData
import com.example.evaluationassignment.type.CreateLeadInput
import com.example.evaluationassignment.type.FetchLeadInput
import com.example.evaluationassignment.type.FetchLeadsInput
import com.example.evaluationassignment.type.PaginationInput
import com.example.evaluationassignment.type.UpdateLeadInput

interface LeadsApi {

    suspend fun getLeads(
        pagination: PaginationInput,
        params: FetchLeadsInput
    ): Result<LeadsPaginated>

    suspend fun getLead(request: FetchLeadInput): Result<LeadDetailed>

    suspend fun createLead(request: CreateLeadInput): Result<LeadDetailed>

    suspend fun updateLead(request: UpdateLeadInput): Result<LeadDetailed>

    suspend fun getIntentions(): Result<List<IntentionDto>?>

    suspend fun getAdSources(): Result<List<IntentionDto>?>

    suspend fun getCountries(): Result<List<CountryDto>?>

    suspend fun getStatuses(): Result<List<StatusData>?>

    suspend fun getCities(id: Int): Result<List<IntentionDto>?>

    suspend fun getLanguages(): Result<List<IntentionDto>?>

}