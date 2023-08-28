package com.example.evaluationassignment.network

import com.apollographql.apollo3.ApolloClient
import com.example.evaluationassignment.ADSourcesQuery
import com.example.evaluationassignment.AllLeadsQuery
import com.example.evaluationassignment.CitiesQuery
import com.example.evaluationassignment.CountriesQuery
import com.example.evaluationassignment.CreateLeadMutation
import com.example.evaluationassignment.LanguagesQuery
import com.example.evaluationassignment.LeadIntentionsQuery
import com.example.evaluationassignment.LeadQuery
import com.example.evaluationassignment.StatusQuery
import com.example.evaluationassignment.UpdateLeadMutation
import com.example.evaluationassignment.data.response.CountryDto
import com.example.evaluationassignment.data.response.IntentionDto
import com.example.evaluationassignment.data.response.LeadDetailed
import com.example.evaluationassignment.data.response.LeadsPaginated
import com.example.evaluationassignment.data.response.StatusData
import com.example.evaluationassignment.data.response.toDetailedDto
import com.example.evaluationassignment.data.response.toPaginatedResponse
import com.example.evaluationassignment.type.CreateLeadInput
import com.example.evaluationassignment.type.FetchLeadInput
import com.example.evaluationassignment.type.FetchLeadsInput
import com.example.evaluationassignment.type.PaginationInput
import com.example.evaluationassignment.type.UpdateLeadInput
import javax.inject.Inject

class LeadsApiImpl @Inject constructor(
    private val api: ApolloClient
) : LeadsApi {
    override suspend fun getLeads(
        pagination: PaginationInput,
        params: FetchLeadsInput
    ): Result<LeadsPaginated> {
        val response = api.query(AllLeadsQuery(pagination = pagination, params = params)).execute()

        return if (response.data != null) Result.success(response.data!!.toPaginatedResponse())
        else {
            Result.failure(Throwable(response.errors?.map { it.message }.toString()))
        }
    }

    override suspend fun getLead(request: FetchLeadInput): Result<LeadDetailed> {
        val response = api.query(LeadQuery(request)).execute()
        return if (response.data != null) Result.success(response.data!!.toDetailedDto())
        else {
            Result.failure(Throwable(response.errors?.map { it.message }.toString()))
        }
    }

    override suspend fun createLead(request: CreateLeadInput): Result<LeadDetailed> {
        val response = api.mutation(CreateLeadMutation(request)).execute()
        return if (response.data != null) Result.success(response.data!!.toDetailedDto())
        else {
            Result.failure(Throwable(response.errors?.map { it.message }.toString()))
        }
    }

    override suspend fun updateLead(request: UpdateLeadInput): Result<LeadDetailed> {
        val response = api.mutation(UpdateLeadMutation(request)).execute()
        return if (response.data != null) Result.success(response.data!!.toDetailedDto())
        else {
            Result.failure(Throwable(response.errors?.map { it.message }.toString()))
        }
    }

    override suspend fun getIntentions(): Result<List<IntentionDto>?> {
        val response = api.query(LeadIntentionsQuery()).execute()
        return if (response.data != null) Result.success(response.data.toDetailedDto())
        else {
            Result.failure(Throwable(response.errors?.map { it.message }.toString()))
        }
    }

    override suspend fun getAdSources(): Result<List<IntentionDto>?> {
        val response = api.query(ADSourcesQuery()).execute()
        return if (response.data != null) Result.success(response.data!!.toDetailedDto())
        else {
            Result.failure(Throwable(response.errors?.map { it.message }.toString()))
        }
    }

    override suspend fun getCountries(): Result<List<CountryDto>?> {
        val response = api.query(CountriesQuery()).execute()
        return if (response.data != null) Result.success(response.data!!.toDetailedDto())
        else {
            Result.failure(Throwable(response.errors?.map { it.message }.toString()))
        }
    }

    override suspend fun getStatuses(): Result<List<StatusData>?> {
        val response = api.query(StatusQuery()).execute()
        return if (response.data != null) Result.success(response.data!!.toDetailedDto())
        else {
            Result.failure(Throwable(response.errors?.map { it.message }.toString()))
        }
    }

    override suspend fun getCities(id: Int): Result<List<IntentionDto>?> {
        val response = api.query(CitiesQuery(id)).execute()
        return if (response.data != null) Result.success(response.data!!.toDetailedDto())
        else {
            Result.failure(Throwable(response.errors?.map { it.message }.toString()))
        }
    }

    override suspend fun getLanguages(): Result<List<IntentionDto>?> {
        val response = api.query(LanguagesQuery()).execute()
        return if (response.data != null) Result.success(response.data!!.toDetailedDto())
        else {
            Result.failure(Throwable(response.errors?.map { it.message }.toString()))
        }
    }
}