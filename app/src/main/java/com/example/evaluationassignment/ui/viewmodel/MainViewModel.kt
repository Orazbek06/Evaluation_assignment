package com.example.evaluationassignment.ui.viewmodel

import androidx.lifecycle.LiveData
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

interface MainViewModel {

    val progressFlow: Flow<Boolean>
    val createProgressFlow: Flow<Boolean>
    val updateProgressFlow: Flow<Boolean>
    val errorFlow: Flow<String?>
    val leadsFlow: LiveData<List<LeadsGeneral>>
    val leadItemFlow: LiveData<LeadDetailed?>
    val createLeadFlow: LiveData<LeadDetailed?>
    val updateLeadFlow: LiveData<LeadDetailed?>
    val navigateItemScreen: LiveData<Boolean>
    val intentions: LiveData<List<IntentionDto>>
    val adSources: LiveData<List<IntentionDto>>
    val countries: LiveData<List<CountryDto>>
    val cities: LiveData<List<IntentionDto>>
    val languages: LiveData<List<IntentionDto>>
    val statuses: LiveData<List<StatusData>>
    val hasLoadedIntentions: LiveData<Boolean>
    val hasLoadedAdSources: LiveData<Boolean>
    val hasLoadedCountries: LiveData<Boolean>
    val hasLoadedLanguages: LiveData<Boolean>
    val hasLoadedStatuses: LiveData<Boolean>

    val gotLoadedLead: LiveData<Boolean>
    val gotCreatedLead: LiveData<Boolean>
    val gotUpdatedLead: LiveData<Boolean>

    val firstInit: LiveData<Boolean>

    fun getLeads(filter: FetchLeadsInput? = null)

    fun getLead(lead: FetchLeadInput, onSuccess: ((Int) -> Unit)? = null)

    fun createLead(createLeadInput: CreateLeadInput)

    fun updateLead(updateLeadInput: UpdateLeadInput)

    fun firstInit()

    fun refreshLeads()

    fun gotCreateSuccess()

    fun gotUpdateSuccess()

    fun gotError()

    fun gotNavigateToItemScreen()

    fun getIntentions()

    fun getAdSources()

    fun getCountries()

    fun getStatuses()

    fun getCities(id: Int)

    fun getLanguages()

    fun searchCountry(str: String)

    fun gotLoaded()

    fun gotCreated()

    fun gotUpdated()

}