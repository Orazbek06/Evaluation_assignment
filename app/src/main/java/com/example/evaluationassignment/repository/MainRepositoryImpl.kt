package com.example.evaluationassignment.repository

import com.apollographql.apollo3.api.Optional
import com.example.evaluationassignment.data.response.CountryDto
import com.example.evaluationassignment.data.response.IntentionDto
import com.example.evaluationassignment.data.response.LeadDetailed
import com.example.evaluationassignment.data.response.LeadsGeneral
import com.example.evaluationassignment.data.response.StatusData
import com.example.evaluationassignment.network.LeadsApi
import com.example.evaluationassignment.type.CreateLeadInput
import com.example.evaluationassignment.type.FetchLeadInput
import com.example.evaluationassignment.type.FetchLeadsInput
import com.example.evaluationassignment.type.PaginationInput
import com.example.evaluationassignment.type.UpdateLeadInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val leadsApi: LeadsApi
) : MainRepository {

    private var cursor: String? = null
    private var hasNext = true

    override suspend fun getLeads(
        params: FetchLeadsInput?
    ): Flow<Result<List<LeadsGeneral>>> = flow {
        if (!hasNext) emit(Result.failure(Throwable("End of pagination reached!")))
        else {
            val pagination = PaginationInput().copy(
                cursor = Optional.present(this@MainRepositoryImpl.cursor),
                take = Optional.present(15)
            )
            val response = leadsApi.getLeads(pagination = pagination, params ?: FetchLeadsInput())

            if (response.isSuccess) {
                val res = response.getOrNull()
                if (res != null) {
                    cursor = res.cursor
                    hasNext = res.hasMore
                    emit(Result.success(res.data))
                }
            } else {
                emit(
                    Result.failure(
                        response.exceptionOrNull()
                            ?: Throwable("Problem while connecting to server!")
                    )
                )
            }
        }
    }.catch {
        emit(Result.failure(Throwable(it.message)))
    }.flowOn(Dispatchers.IO)

    override suspend fun getLead(lead: FetchLeadInput): Flow<Result<LeadDetailed>> =
        flow {
            val response = leadsApi.getLead(lead)

            if (response.isSuccess) {
                val res = response.getOrNull()
                if (res != null) {
                    emit(Result.success(res))
                }
            } else {
                emit(
                    Result.failure(
                        response.exceptionOrNull()
                            ?: Throwable("Problem while connecting to server!")
                    )
                )
            }
        }.catch {
            emit(Result.failure(Throwable(it.message)))
        }.flowOn(Dispatchers.IO)

    override suspend fun createLead(lead: CreateLeadInput): Flow<Result<LeadDetailed>> =
        flow {
            val response = leadsApi.createLead(lead)

            if (response.isSuccess) {
                val res = response.getOrNull()
                if (res != null) {
                    emit(Result.success(res))
                }
            } else {
                emit(
                    Result.failure(
                        response.exceptionOrNull()
                            ?: Throwable("Problem while connecting to server!")
                    )
                )
            }
        }.catch {
            emit(Result.failure(Throwable(it.message)))
        }.flowOn(Dispatchers.IO)

    override suspend fun updateLead(lead: UpdateLeadInput): Flow<Result<LeadDetailed>> =
        flow {
            val response = leadsApi.updateLead(lead)

            if (response.isSuccess) {
                val res = response.getOrNull()
                if (res != null) {
                    emit(Result.success(res))
                }
            } else {
                emit(
                    Result.failure(
                        response.exceptionOrNull()
                            ?: Throwable("Problem while connecting to server!")
                    )
                )
            }
        }.catch {
            emit(Result.failure(Throwable(it.message)))
        }.flowOn(Dispatchers.IO)


    override fun clearPagination() {
        cursor = null
        hasNext = true
    }

    override suspend fun getIntentions(): Flow<Result<List<IntentionDto>>> =
        flow {

            val response = leadsApi.getIntentions()

            if (response.isSuccess) {
                val res = response.getOrNull()
                if (res != null) {
                    emit(Result.success(res))
                }
            } else {
                emit(
                    Result.failure(
                        response.exceptionOrNull()
                            ?: Throwable("Problem while connecting to server!")
                    )
                )
            }
        }.catch {
            emit(Result.failure(Throwable(it.message)))
        }.flowOn(Dispatchers.IO)


    override suspend fun getAdSources(): Flow<Result<List<IntentionDto>>> =
        flow {

            val response = leadsApi.getAdSources()

            if (response.isSuccess) {
                val res = response.getOrNull()
                if (res != null) {
                    emit(Result.success(res))
                }
            } else {
                emit(
                    Result.failure(
                        response.exceptionOrNull()
                            ?: Throwable("Problem while connecting to server!")
                    )
                )
            }
        }.catch {
            emit(Result.failure(Throwable(it.message)))
        }.flowOn(Dispatchers.IO)


    override suspend fun getCountries(): Flow<Result<List<CountryDto>>> =
        flow {

            val response = leadsApi.getCountries()

            if (response.isSuccess) {
                val res = response.getOrNull()
                if (res != null) {
                    emit(Result.success(res))
                }
            } else {
                emit(
                    Result.failure(
                        response.exceptionOrNull()
                            ?: Throwable("Problem while connecting to server!")
                    )
                )
            }
        }.catch {
            emit(Result.failure(Throwable(it.message)))
        }.flowOn(Dispatchers.IO)


    override suspend fun getStatuses(): Flow<Result<List<StatusData>>> =
        flow {

            val response = leadsApi.getStatuses()

            if (response.isSuccess) {
                val res = response.getOrNull()
                if (res != null) {
                    emit(Result.success(res))
                }
            } else {
                emit(
                    Result.failure(
                        response.exceptionOrNull()
                            ?: Throwable("Problem while connecting to server!")
                    )
                )
            }
        }.catch {
            emit(Result.failure(Throwable(it.message)))
        }.flowOn(Dispatchers.IO)


    override suspend fun getCities(id: Int): Flow<Result<List<IntentionDto>>> =
        flow {

            val response = leadsApi.getCities(id)

            if (response.isSuccess) {
                val res = response.getOrNull()
                if (res != null) {
                    emit(Result.success(res))
                }
            } else {
                emit(
                    Result.failure(
                        response.exceptionOrNull()
                            ?: Throwable("Problem while connecting to server!")
                    )
                )
            }
        }.catch {
            emit(Result.failure(Throwable(it.message)))
        }.flowOn(Dispatchers.IO)

    override suspend fun getLanguages(): Flow<Result<List<IntentionDto>>> =
        flow {

            val response = leadsApi.getLanguages()

            if (response.isSuccess) {
                val res = response.getOrNull()
                if (res != null) {
                    emit(Result.success(res))
                }
            } else {
                emit(
                    Result.failure(
                        response.exceptionOrNull()
                            ?: Throwable("Problem while connecting to server!")
                    )
                )
            }
        }.catch {
            emit(Result.failure(Throwable(it.message)))
        }.flowOn(Dispatchers.IO)

}