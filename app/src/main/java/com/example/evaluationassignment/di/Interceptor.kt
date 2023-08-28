package com.example.evaluationassignment.di

import com.apollographql.apollo3.api.ApolloRequest
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.interceptor.ApolloInterceptor
import com.apollographql.apollo3.interceptor.ApolloInterceptorChain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Interceptor @Inject constructor() : ApolloInterceptor {
    override fun <D : Operation.Data> intercept(
        request: ApolloRequest<D>,
        chain: ApolloInterceptorChain
    ): Flow<ApolloResponse<D>> {
        return chain.proceed(buildRequestWithToken(request = request))
    }


    private fun <D : Operation.Data> buildRequestWithToken(
        request: ApolloRequest<D>,
        tokenType: String = "Bearer"
    ): ApolloRequest<D> {

        return request.newBuilder().addHttpHeader(
            "Authorization",
            "$tokenType eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlSWQiOjE1ODIsImZJZCI6IkN6UGRtc3lYelpiYmRqR0wiLCJzSWQiOjYzLCJpYXQiOjE2OTE5OTE0NTMsImV4cCI6MTY5MzIwMTA1M30.ghmG_ay64DkPmCUDUS-u_jiVRrAUEtEpF3UaFIpVFN8"
        ).build()
    }

}