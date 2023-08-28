package com.example.evaluationassignment.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.apollographql.apollo3.api.Optional
import com.example.evaluationassignment.R
import com.example.evaluationassignment.data.response.CountryDto
import com.example.evaluationassignment.data.response.IntentionDto
import com.example.evaluationassignment.type.ContactDataInput
import com.example.evaluationassignment.type.CreateLeadInput
import com.example.evaluationassignment.ui.bottomsheet.SheetLayout
import com.example.evaluationassignment.ui.bottomsheet.SheetStyle
import com.example.evaluationassignment.ui.theme.BackButton
import com.example.evaluationassignment.ui.viewmodel.MainViewModel
import com.example.evaluationassignment.util.collectClickAsState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateLeadScreen(
    viewModel: MainViewModel,
    onBackPress: () -> Unit
) {

    val context = LocalContext.current

    var rowSize by remember { mutableStateOf(Size.Zero) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                ),
                navigationIcon = {
                    BackButton(onBackPress = {
                        onBackPress()
                    })
                },
                title = {
                    Text(
                        text = "Create lead",
                        fontSize = 16.sp,
                        fontWeight = FontWeight(700),
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                    )
                }
            )
        },
        containerColor = Color.White
    ) { pad ->

        var firstName by remember {
            mutableStateOf("")
        }

        var lastName by remember {
            mutableStateOf("")
        }

        var country by remember {
            mutableStateOf<CountryDto?>(null)
        }

        var city by remember {
            mutableStateOf<IntentionDto?>(null)
        }

        var intention by remember {
            mutableStateOf<IntentionDto?>(null)
        }

        var adSource by remember {
            mutableStateOf<IntentionDto?>(null)
        }

        var phone by remember {
            mutableStateOf("")
        }

        var email by remember {
            mutableStateOf("")
        }

        var language by remember {
            mutableStateOf<IntentionDto?>(null)
        }

        var sheetStyle by remember {
            mutableIntStateOf(SheetStyle.AD_SOURCE)
        }

        val coroutineScope = rememberCoroutineScope()

        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
        var showSheet by remember {
            mutableStateOf(false)
        }

        var showIntentionDialog by remember {
            mutableStateOf(false)
        }

        val intentions by viewModel.intentions.observeAsState(initial = listOf())
        val hasLoadedIntentions by viewModel.hasLoadedIntentions.observeAsState(initial = false)

        val intentionSource = remember {
            MutableInteractionSource()
        }
//        val adInteractionSource = remember {
//            MutableInteractionSource()
//        }
        val countrySource = remember {
            MutableInteractionSource()
        }
        val citySource = remember {
            MutableInteractionSource()
        }
        val languageSource = remember {
            MutableInteractionSource()
        }

//        val onAdSourceClick: () -> Unit = {
//            sheetStyle = SheetStyle.AD_SOURCE
//            coroutineScope.launch {
//                showSheet = true
//                sheetState.show()
//            }
//        }

        val onCountriesClick: () -> Unit = {
            sheetStyle = SheetStyle.COUNTRY
            coroutineScope.launch {
                showSheet = true
                sheetState.show()
            }
        }

        val onCitiesClick: () -> Unit = {
            if (country != null) {
                sheetStyle = SheetStyle.CITY
                coroutineScope.launch {
                    showSheet = true
                    sheetState.show()

                }
            } else {
                Toast
                    .makeText(
                        context,
                        "Please first select country...",
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }
        }

        val onLanguagesClick: () -> Unit = {
            sheetStyle = SheetStyle.LANGUAGE
            coroutineScope.launch {
                showSheet = true
                sheetState.show()
            }
        }

        if (intentionSource.collectClickAsState().value) {
            showIntentionDialog = true
        }

//        if (adInteractionSource.collectClickAsState().value) {
//            onAdSourceClick()
//        }

        if (countrySource.collectClickAsState().value) {
            onCountriesClick()
        }

        if (citySource.collectClickAsState().value) {
            onCitiesClick()
        }

        if (languageSource.collectClickAsState().value) {
            onLanguagesClick()
        }

        LaunchedEffect(key1 = null, block = {
            if (!hasLoadedIntentions) viewModel.getIntentions()
        })

        var firstNameError by remember {
            mutableStateOf(false)
        }
        var intentionError by remember {
            mutableStateOf(false)
        }
        var languageError by remember {
            mutableStateOf(false)
        }
        var phoneError by remember {
            mutableStateOf(false)
        }

        fun checkFields(): Boolean {

            var res = true

            if (firstName.isBlank()) {
                firstNameError = true
                res = false
            }

            if (intention == null) {
                intentionError = true
                res = false

            }

            if (language == null) {
                languageError = true
                res = false
            }

            if (phone.isBlank()) {
                phoneError = true
                res = false
            }

            return res
        }

        val progress by viewModel.createProgressFlow.collectAsState(initial = false)

        val gotCreated by viewModel.gotCreatedLead.observeAsState(initial = false)

        val error by viewModel.errorFlow.collectAsState(initial = null)

        LaunchedEffect(error) {
            if (error != null) {
                viewModel.gotError()
                Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
            }
        }

        LaunchedEffect(gotCreated) {
            if (gotCreated) {
                viewModel.gotCreated()
                onBackPress()
            }
        }


        Box(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize()
        ) {
            if (showSheet) ModalBottomSheet(
                onDismissRequest = {
                    coroutineScope.launch {
                        sheetState.hide()
                        showSheet = false
                    }
                },
                sheetState = sheetState,
                shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp),
                contentColor = Color.White,
                containerColor = Color.White,
                modifier = Modifier.fillMaxSize(),
                dragHandle = { BottomSheetDefaults.DragHandle() },
            ) {
                SheetLayout(
                    sheetStyle = sheetStyle,
                    viewModel = viewModel,
                    onAdSourceClick = {
                        adSource = it
                        coroutineScope.launch {
                            sheetState.hide()
                        }
                        showSheet = false
                    },
                    onCountryClick = {
                        if (country != it) {
                            country = it
                            city = null
                        }
                        coroutineScope.launch {
                            sheetState.hide()
                        }
                        showSheet = false
                    },
                    onCityClick = {
                        city = it
                        coroutineScope.launch {
                            sheetState.hide()
                        }
                        showSheet = false
                    },
                    onLanguageClick = {
                        languageError = false
                        language = it
                        coroutineScope.launch {
                            sheetState.hide()
                        }
                        showSheet = false
                    },
                    country = country
                )
            }
            LazyColumn(
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = 8.dp,
                    bottom = 120.dp
                )
            ) {

                item {
                    Column(Modifier.fillMaxWidth()) {

                        Text(
                            text = "First name",
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                            letterSpacing = 0.5.sp
                        )

                        OutlinedTextField(
                            value = firstName,
                            onValueChange = {
                                firstName = it
                                firstNameError = false
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                disabledBorderColor = Color(0xFFEEEEEE),
                                cursorColor = Color.Black,
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color(0xFFEEEEEE),
                                disabledPlaceholderColor = Color(0xFF616161),
                                focusedPlaceholderColor = Color(0xFF616161),
                                unfocusedPlaceholderColor = Color(0xFF616161),
                                focusedContainerColor = Color(0xFFFAFAFA),
                                unfocusedContainerColor = Color(0xFFFAFAFA),
                                disabledContainerColor = Color(0xFFFAFAFA)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            placeholder = {
                                Text(text = "Ex: John")
                            },
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            isError = firstNameError,
                            textStyle = TextStyle.Default.copy(fontSize = 16.sp)
                        )


                        Text(
                            text = "Last name",
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(top = 12.dp)
                        )

                        OutlinedTextField(
                            value = lastName,
                            onValueChange = {
                                lastName = it
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                disabledTextColor = Color(0xFF616161),
                                disabledBorderColor = Color(0xFFEEEEEE),
                                cursorColor = Color.Black,
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color(0xFFEEEEEE),
                                unfocusedPlaceholderColor = Color(0xFF616161),
                                focusedPlaceholderColor = Color(0xFF616161),
                                disabledPlaceholderColor = Color(0xFF616161),
                                focusedContainerColor = Color(0xFFFAFAFA),
                                unfocusedContainerColor = Color(0xFFFAFAFA),
                                disabledContainerColor = Color(0xFFFAFAFA)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            placeholder = {
                                Text(text = "Ex: Doe")
                            },
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            textStyle = TextStyle.Default.copy(fontSize = 16.sp)
                        )

                        Text(
                            text = "Lead intention",
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(top = 12.dp)
                        )

                        OutlinedTextField(
                            value = intention?.title ?: "",
                            onValueChange = {

                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                disabledTextColor = Color(0xFF616161),
                                disabledBorderColor = Color(0xFFEEEEEE),
                                cursorColor = Color.Black,
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color(0xFFEEEEEE),
                                unfocusedPlaceholderColor = Color(0xFF616161),
                                focusedPlaceholderColor = Color(0xFF616161),
                                disabledPlaceholderColor = Color(0xFF616161),
                                focusedContainerColor = Color(0xFFFAFAFA),
                                unfocusedContainerColor = Color(0xFFFAFAFA),
                                disabledContainerColor = Color(0xFFFAFAFA)
                            ),
                            interactionSource = intentionSource,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp)
                                .clickable(enabled = true) { showIntentionDialog = true },
                            placeholder = {
                                Text(text = "Select")
                            },
                            shape = RoundedCornerShape(12.dp),
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_arrow_down),
                                    contentDescription = ""
                                )
                            },
                            readOnly = true,
                            singleLine = true,
                            isError = intentionError,
                            textStyle = TextStyle.Default.copy(fontSize = 16.sp)
                        )

                        if (showIntentionDialog) Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .onGloballyPositioned { layoutCoordinates ->
                                    rowSize = layoutCoordinates.size.toSize()
                                }
                        ) {
                            MaterialTheme(
                                shapes = MaterialTheme.shapes.copy(
                                    extraSmall = RoundedCornerShape(
                                        12.dp
                                    )
                                )
                            ) {
                                DropdownMenu(modifier = Modifier
                                    .width(with(LocalDensity.current) { rowSize.width.toDp() })
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color.White),
                                    expanded = showIntentionDialog,
                                    onDismissRequest = { showIntentionDialog = false }
                                ) {
                                    for (item in intentions) {
                                        DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = item.title,
                                                    fontSize = 16.sp,
                                                    lineHeight = 24.sp,
                                                    fontWeight = FontWeight(400),
                                                    color = Color(0xFF212121),
                                                )
                                            },
                                            onClick = {
                                                intention = item
                                                intentionError = false
                                                showIntentionDialog = false
                                            })
                                    }
                                }
                            }
                        }

                        Text(
                            text = "AD Source",
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(top = 12.dp)
                        )

                        OutlinedTextField(
                            value = adSource?.title ?: "",
                            onValueChange = {

                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                disabledTextColor = Color(0xFF616161),
                                disabledBorderColor = Color(0xFFEEEEEE),
                                cursorColor = Color.Black,
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color(0xFFEEEEEE),
                                unfocusedPlaceholderColor = Color(0xFF616161),
                                focusedPlaceholderColor = Color(0xFF616161),
                                disabledPlaceholderColor = Color(0xFF616161),
                                focusedContainerColor = Color(0xFFFAFAFA),
                                unfocusedContainerColor = Color(0xFFFAFAFA),
                                disabledContainerColor = Color(0xFFFAFAFA)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            placeholder = {
                                Text(text = "Select")
                            },
                            shape = RoundedCornerShape(12.dp),
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_arrow_down),
                                    contentDescription = ""
                                )
                            },
                            readOnly = true,
                            singleLine = true,
//                            interactionSource = adInteractionSource,
                            textStyle = TextStyle.Default.copy(fontSize = 16.sp),
                            enabled = false
                        )

                        Text(
                            text = "Country",
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(top = 12.dp)
                        )

                        OutlinedTextField(
                            value = country?.title ?: "",
                            onValueChange = {

                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                disabledTextColor = Color(0xFF616161),
                                disabledBorderColor = Color(0xFFEEEEEE),
                                cursorColor = Color.Black,
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color(0xFFEEEEEE),
                                unfocusedPlaceholderColor = Color(0xFF616161),
                                focusedPlaceholderColor = Color(0xFF616161),
                                disabledPlaceholderColor = Color(0xFF616161),
                                focusedContainerColor = Color(0xFFFAFAFA),
                                unfocusedContainerColor = Color(0xFFFAFAFA),
                                disabledContainerColor = Color(0xFFFAFAFA)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            placeholder = {
                                Text(text = "Select")
                            },
                            shape = RoundedCornerShape(12.dp),
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_arrow_down),
                                    contentDescription = ""
                                )
                            },
                            readOnly = true,
                            singleLine = true,
                            interactionSource = countrySource,
                            textStyle = TextStyle.Default.copy(fontSize = 16.sp)
                        )

                        Text(
                            text = "City",
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(top = 12.dp)
                        )

                        OutlinedTextField(
                            value = city?.title ?: "",
                            onValueChange = {

                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                disabledTextColor = Color(0xFF616161),
                                disabledBorderColor = Color(0xFFEEEEEE),
                                cursorColor = Color.Black,
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color(0xFFEEEEEE),
                                unfocusedPlaceholderColor = Color(0xFF616161),
                                focusedPlaceholderColor = Color(0xFF616161),
                                disabledPlaceholderColor = Color(0xFF616161),
                                focusedContainerColor = Color(0xFFFAFAFA),
                                unfocusedContainerColor = Color(0xFFFAFAFA),
                                disabledContainerColor = Color(0xFFFAFAFA)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            placeholder = {
                                Text(text = "Select")
                            },
                            shape = RoundedCornerShape(12.dp),
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_arrow_down),
                                    contentDescription = ""
                                )
                            },
                            readOnly = true,
                            singleLine = true,
                            interactionSource = citySource,
                            textStyle = TextStyle.Default.copy(fontSize = 16.sp)
                        )

                        Text(
                            text = "Language",
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(top = 12.dp)
                        )

                        OutlinedTextField(
                            value = language?.title ?: "",
                            onValueChange = {

                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                disabledTextColor = Color(0xFF616161),
                                disabledBorderColor = Color(0xFFEEEEEE),
                                cursorColor = Color.Black,
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color(0xFFEEEEEE),
                                unfocusedPlaceholderColor = Color(0xFF616161),
                                focusedPlaceholderColor = Color(0xFF616161),
                                disabledPlaceholderColor = Color(0xFF616161),
                                focusedContainerColor = Color(0xFFFAFAFA),
                                unfocusedContainerColor = Color(0xFFFAFAFA),
                                disabledContainerColor = Color(0xFFFAFAFA)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            placeholder = {
                                Text(text = "Select")
                            },
                            shape = RoundedCornerShape(12.dp),
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_arrow_down),
                                    contentDescription = ""
                                )
                            },
                            readOnly = true,
                            singleLine = true,
                            interactionSource = languageSource,
                            isError = languageError,
                            textStyle = TextStyle.Default.copy(fontSize = 16.sp)
                        )

                        Text(
                            text = "Phone number",
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(top = 12.dp)
                        )

                        OutlinedTextField(
                            value = phone,
                            onValueChange = {
                                phone = it
                                phoneError = false
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                disabledTextColor = Color(0xFF616161),
                                disabledBorderColor = Color(0xFFEEEEEE),
                                cursorColor = Color.Black,
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color(0xFFEEEEEE),
                                unfocusedPlaceholderColor = Color(0xFF616161),
                                unfocusedPrefixColor = Color(0xFF616161),
                                focusedPrefixColor = Color.Black,
                                focusedPlaceholderColor = Color(0xFF616161),
                                disabledPlaceholderColor = Color(0xFF616161),
                                disabledPrefixColor = Color(0xFF616161),
                                focusedContainerColor = Color(0xFFFAFAFA),
                                unfocusedContainerColor = Color(0xFFFAFAFA),
                                disabledContainerColor = Color(0xFFFAFAFA)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            placeholder = {
                                Text(
                                    text = if (country == null) "Ex: +1234 567 89 01"
                                    else "+${country?.phoneCode ?: ""} ### ###..."
                                )
                            },
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            isError = phoneError,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            textStyle = TextStyle.Default.copy(fontSize = 16.sp)
                        )

                        Text(
                            text = "Email",
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(top = 12.dp)
                        )

                        OutlinedTextField(
                            value = email,
                            onValueChange = {
                                email = it
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                disabledTextColor = Color(0xFF616161),
                                disabledBorderColor = Color(0xFFEEEEEE),
                                cursorColor = Color.Black,
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color(0xFFEEEEEE),
                                unfocusedPlaceholderColor = Color(0xFF616161),
                                focusedPlaceholderColor = Color(0xFF616161),
                                disabledPlaceholderColor = Color(0xFF616161),
                                focusedContainerColor = Color(0xFFFAFAFA),
                                unfocusedContainerColor = Color(0xFFFAFAFA),
                                disabledContainerColor = Color(0xFFFAFAFA)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            placeholder = {
                                Text(text = "Ex: example@gmail.com")
                            },
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            textStyle = TextStyle.Default.copy(fontSize = 16.sp)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(
                        color = Color(0xD9000000),
                        shape = RoundedCornerShape(size = 20.dp)
                    )
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Button(
                    onClick = {
                        onBackPress()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color(0xD9000000)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .weight(1f)
                        .border(
                            width = 1.dp,
                            color = Color(0xFFE8A889),
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    Text(
                        text = "Cancel",
                        fontSize = 16.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFFE8A889),
                    )
                }

                Button(
                    onClick = {
                        if (checkFields()) {
                            viewModel.createLead(
                                CreateLeadInput(
                                    firstName = firstName,
                                    intentionId = intention!!.id,
                                    contacts = listOf(
                                        ContactDataInput(
                                            phone = Optional.present(phone.ifBlank { null }),
                                            email = Optional.present(email.ifBlank { null })
                                        )
                                    ),
                                    languageIds = listOf(language!!.id),
                                    lastName = Optional.present(lastName),
                                    countryId = Optional.present(country?.id),
                                    cityId = Optional.present(city?.id),
                                    communityIds = Optional.present(adSource?.id?.let { listOf(it) })
                                )
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE8A889),
                        contentColor = Color(0xFFE8A889)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .height(48.dp)
                        .border(
                            width = 1.dp,
                            color = Color(0xFFE8A889),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFE8A889)),
                    shape = RoundedCornerShape(12.dp),
                    enabled = !progress
                ) {
                    Text(
                        text = "Save",
                        fontSize = 16.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000),
                    )
                }
            }

            if (progress) {
                CircularProgressIndicator(
                    color = Color(0xD9000000), modifier = Modifier.align(
                        Alignment.Center
                    )
                )
            }

        }
    }
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateLeadScreenPreview() {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                ),
                navigationIcon = {
                    BackButton(onBackPress = {
                        // TODO: back button
                    })
                },
                title = {
                    Text(
                        text = "Create lead",
                        fontSize = 16.sp,
                        fontWeight = FontWeight(700),
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                    )
                }
            )
        },
        containerColor = Color.White
    ) { pad ->
        Box(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize()
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = 8.dp,
                    bottom = 120.dp
                )
            ) {

                item {
                    Column(Modifier.fillMaxWidth()) {

                        Text(
                            text = "First name",
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                            letterSpacing = 0.5.sp
                        )

                        OutlinedTextField(
                            value = "",
                            onValueChange = {

                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                disabledTextColor = Color(0xFF616161),
                                disabledBorderColor = Color(0xFFEEEEEE),
                                cursorColor = Color.Black,
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color(0xFFEEEEEE),
                                unfocusedPlaceholderColor = Color(0xFF616161),
                                focusedPlaceholderColor = Color(0xFF616161),
                                disabledPlaceholderColor = Color(0xFF616161),
                                focusedContainerColor = Color(0xFFFAFAFA),
                                unfocusedContainerColor = Color(0xFFFAFAFA),
                                disabledContainerColor = Color(0xFFFAFAFA)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            placeholder = {
                                Text(text = "Ex: John")
                            },
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )


                        Text(
                            text = "Last name",
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(top = 12.dp)
                        )

                        OutlinedTextField(
                            value = "",
                            onValueChange = {

                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                disabledTextColor = Color(0xFF616161),
                                disabledBorderColor = Color(0xFFEEEEEE),
                                cursorColor = Color.Black,
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color(0xFFEEEEEE),
                                unfocusedPlaceholderColor = Color(0xFF616161),
                                focusedPlaceholderColor = Color(0xFF616161),
                                disabledPlaceholderColor = Color(0xFF616161),
                                focusedContainerColor = Color(0xFFFAFAFA),
                                unfocusedContainerColor = Color(0xFFFAFAFA),
                                disabledContainerColor = Color(0xFFFAFAFA)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            placeholder = {
                                Text(text = "Ex: Doe")
                            },
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )

                        Text(
                            text = "Lead intention",
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(top = 12.dp)
                        )

                        OutlinedTextField(
                            value = "",
                            onValueChange = {

                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                disabledTextColor = Color(0xFF616161),
                                disabledBorderColor = Color(0xFFEEEEEE),
                                cursorColor = Color.Black,
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color(0xFFEEEEEE),
                                unfocusedPlaceholderColor = Color(0xFF616161),
                                focusedPlaceholderColor = Color(0xFF616161),
                                disabledPlaceholderColor = Color(0xFF616161),
                                focusedContainerColor = Color(0xFFFAFAFA),
                                unfocusedContainerColor = Color(0xFFFAFAFA),
                                disabledContainerColor = Color(0xFFFAFAFA)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            placeholder = {
                                Text(text = "Select")
                            },
                            shape = RoundedCornerShape(12.dp),
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_arrow_down),
                                    contentDescription = ""
                                )
                            },
                            readOnly = true,
                            singleLine = true
                        )

                        Text(
                            text = "AD Source",
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(top = 12.dp)
                        )

                        OutlinedTextField(
                            value = "",
                            onValueChange = {

                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                disabledTextColor = Color(0xFF616161),
                                disabledBorderColor = Color(0xFFEEEEEE),
                                cursorColor = Color.Black,
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color(0xFFEEEEEE),
                                unfocusedPlaceholderColor = Color(0xFF616161),
                                focusedPlaceholderColor = Color(0xFF616161),
                                disabledPlaceholderColor = Color(0xFF616161),
                                focusedContainerColor = Color(0xFFFAFAFA),
                                unfocusedContainerColor = Color(0xFFFAFAFA),
                                disabledContainerColor = Color(0xFFFAFAFA)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            placeholder = {
                                Text(text = "Select")
                            },
                            shape = RoundedCornerShape(12.dp),
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_arrow_down),
                                    contentDescription = ""
                                )
                            },
                            readOnly = true,
                            singleLine = true
                        )

                        Text(
                            text = "Country",
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(top = 12.dp)
                        )

                        OutlinedTextField(
                            value = "",
                            onValueChange = {

                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                disabledTextColor = Color(0xFF616161),
                                disabledBorderColor = Color(0xFFEEEEEE),
                                cursorColor = Color.Black,
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color(0xFFEEEEEE),
                                unfocusedPlaceholderColor = Color(0xFF616161),
                                focusedPlaceholderColor = Color(0xFF616161),
                                disabledPlaceholderColor = Color(0xFF616161),
                                focusedContainerColor = Color(0xFFFAFAFA),
                                unfocusedContainerColor = Color(0xFFFAFAFA),
                                disabledContainerColor = Color(0xFFFAFAFA)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            placeholder = {
                                Text(text = "Select")
                            },
                            shape = RoundedCornerShape(12.dp),
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_arrow_down),
                                    contentDescription = ""
                                )
                            },
                            readOnly = true,
                            singleLine = true
                        )

                        Text(
                            text = "City",
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(top = 12.dp)
                        )

                        OutlinedTextField(
                            value = "",
                            onValueChange = {

                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                disabledTextColor = Color(0xFF616161),
                                disabledBorderColor = Color(0xFFEEEEEE),
                                cursorColor = Color.Black,
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color(0xFFEEEEEE),
                                unfocusedPlaceholderColor = Color(0xFF616161),
                                focusedPlaceholderColor = Color(0xFF616161),
                                disabledPlaceholderColor = Color(0xFF616161),
                                focusedContainerColor = Color(0xFFFAFAFA),
                                unfocusedContainerColor = Color(0xFFFAFAFA),
                                disabledContainerColor = Color(0xFFFAFAFA)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            placeholder = {
                                Text(text = "Select")
                            },
                            shape = RoundedCornerShape(12.dp),
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_arrow_down),
                                    contentDescription = ""
                                )
                            },
                            readOnly = true,
                            singleLine = true
                        )

                        Text(
                            text = "Language",
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(top = 12.dp)
                        )

                        OutlinedTextField(
                            value = "",
                            onValueChange = {

                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                disabledTextColor = Color(0xFF616161),
                                disabledBorderColor = Color(0xFFEEEEEE),
                                cursorColor = Color.Black,
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color(0xFFEEEEEE),
                                unfocusedPlaceholderColor = Color(0xFF616161),
                                focusedPlaceholderColor = Color(0xFF616161),
                                disabledPlaceholderColor = Color(0xFF616161),
                                focusedContainerColor = Color(0xFFFAFAFA),
                                unfocusedContainerColor = Color(0xFFFAFAFA),
                                disabledContainerColor = Color(0xFFFAFAFA)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            placeholder = {
                                Text(text = "Select")
                            },
                            shape = RoundedCornerShape(12.dp),
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_arrow_down),
                                    contentDescription = ""
                                )
                            },
                            readOnly = true,
                            singleLine = true
                        )

                        Text(
                            text = "Phone number",
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(top = 12.dp)
                        )

                        OutlinedTextField(
                            value = "",
                            onValueChange = {

                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                disabledTextColor = Color(0xFF616161),
                                disabledBorderColor = Color(0xFFEEEEEE),
                                cursorColor = Color.Black,
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color(0xFFEEEEEE),
                                unfocusedPlaceholderColor = Color(0xFF616161),
                                focusedPlaceholderColor = Color(0xFF616161),
                                disabledPlaceholderColor = Color(0xFF616161),
                                focusedContainerColor = Color(0xFFFAFAFA),
                                unfocusedContainerColor = Color(0xFFFAFAFA),
                                disabledContainerColor = Color(0xFFFAFAFA)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            placeholder = {
                                Text(text = "Ex: +1234 5678 9012")
                            },
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )

                        Text(
                            text = "Email",
                            fontSize = 12.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(top = 12.dp)
                        )

                        OutlinedTextField(
                            value = "",
                            onValueChange = {

                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                disabledTextColor = Color(0xFF616161),
                                disabledBorderColor = Color(0xFFEEEEEE),
                                cursorColor = Color.Black,
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color(0xFFEEEEEE),
                                unfocusedPlaceholderColor = Color(0xFF616161),
                                focusedPlaceholderColor = Color(0xFF616161),
                                disabledPlaceholderColor = Color(0xFF616161),
                                focusedContainerColor = Color(0xFFFAFAFA),
                                unfocusedContainerColor = Color(0xFFFAFAFA),
                                disabledContainerColor = Color(0xFFFAFAFA)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            placeholder = {
                                Text(text = "Ex: example@gmail.com")
                            },
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(
                        color = Color(0xD9000000),
                        shape = RoundedCornerShape(size = 20.dp)
                    )
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color(0xD9000000)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .weight(1f)
                        .border(
                            width = 1.dp,
                            color = Color(0xFFE8A889),
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    Text(
                        text = "Cancel",
                        fontSize = 16.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFFE8A889),
                    )
                }

                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE8A889),
                        contentColor = Color(0xFFE8A889)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .height(48.dp)
                        .border(
                            width = 1.dp,
                            color = Color(0xFFE8A889),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFE8A889)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Save",
                        fontSize = 16.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000),

                        )
                }
            }
        }
    }
}