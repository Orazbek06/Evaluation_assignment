package com.example.evaluationassignment.ui.bottomsheet

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.evaluationassignment.data.response.CountryDto
import com.example.evaluationassignment.data.response.IntentionDto
import com.example.evaluationassignment.ui.screen.AdSourcesLayout
import com.example.evaluationassignment.ui.screen.CitiesLayout
import com.example.evaluationassignment.ui.screen.CountriesListModalSheet
import com.example.evaluationassignment.ui.screen.LanguagesLayout
import com.example.evaluationassignment.ui.viewmodel.MainViewModel
import com.example.evaluationassignment.ui.viewmodel.MainViewModelImpl

@Composable
fun SheetLayout(
    sheetStyle: Int,
    country: CountryDto?,
    viewModel: MainViewModel = hiltViewModel<MainViewModelImpl>(),
    onAdSourceClick: (IntentionDto) -> Unit,
    onCountryClick: (CountryDto) -> Unit,
    onCityClick: (IntentionDto) -> Unit,
    onLanguageClick: (IntentionDto) -> Unit,
) {

    when (sheetStyle) {
        SheetStyle.AD_SOURCE -> {
            AdSourcesLayout(viewModel = viewModel, onAdSourceClick)
        }

        SheetStyle.COUNTRY -> {
            CountriesListModalSheet(viewModel = viewModel, onCountryClick)
        }

        SheetStyle.CITY -> {
            country?.let { CitiesLayout(it.id, viewModel = viewModel, onCityClick) }
        }

        SheetStyle.LANGUAGE -> {
            LanguagesLayout(viewModel = viewModel, onLanguageClick)
        }
    }

}