package com.example.evaluationassignment.data.response

import android.util.Log
import com.example.evaluationassignment.ADSourcesQuery
import com.example.evaluationassignment.CitiesQuery
import com.example.evaluationassignment.CountriesQuery
import com.example.evaluationassignment.CreateLeadMutation
import com.example.evaluationassignment.LanguagesQuery
import com.example.evaluationassignment.LeadIntentionsQuery
import com.example.evaluationassignment.LeadQuery
import com.example.evaluationassignment.StatusQuery
import com.example.evaluationassignment.UpdateLeadMutation
import com.example.evaluationassignment.util.toDateTime

data class LeadDetailed(
    val data: LeadDetailedDto
)

data class LeadDetailedDto(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val status: StatusData,
    val quality: Int,
    val intention: IntentionDto,
    val adSource: IntentionDto,
    val country: CountryDto?,
    val city: IntentionDto,
    val languages: List<IntentionDto>,
    val displayName: String,
    val contacts: ContactData,
    val avatar: AvatarDto,
    val createdAt: String = ""
)

fun LeadQuery.Data.toDetailedDto(): LeadDetailed {
    Log.d("VVVVV", "toDetailedDto: $this")
    return LeadDetailed(
        data = LeadDetailedDto(
            id = this.fetchLead?.data?.id ?: 0,
            firstName = this.fetchLead?.data?.firstName ?: "",
            lastName = this.fetchLead?.data?.lastName ?: "",
            status = StatusData(
                id = this.fetchLead?.data?.status?.id ?: 0,
                step = this.fetchLead?.data?.status?.step ?: 0,
                stepsCount = this.fetchLead?.data?.status?.stepsCount ?: 0,
                title = this.fetchLead?.data?.status?.title ?: "",
                color = this.fetchLead?.data?.status?.color ?: "",
                backgroundColor = this.fetchLead?.data?.status?.backgroundColor ?: "",
            ),
            quality = this.fetchLead?.data?.quality ?: 0,
            intention = IntentionDto(
                id = this.fetchLead?.data?.intention?.id ?: 0,
                title = this.fetchLead?.data?.intention?.title ?: ""
            ),
            adSource = IntentionDto(
                id = this.fetchLead?.data?.adSource?.id ?: 0,
                title = this.fetchLead?.data?.adSource?.title ?: ""
            ),
            country = CountryDto(
                id = this.fetchLead?.data?.country?.id ?: 0,
                phoneCode = this.fetchLead?.data?.country?.phoneCode ?: "",
                shortCode1 = this.fetchLead?.data?.country?.shortCode1 ?: "",
                emoji = this.fetchLead?.data?.country?.emoji ?: "",
                title = this.fetchLead?.data?.country?.title ?: ""
            ),
            city = IntentionDto(
                id = this.fetchLead?.data?.city?.id ?: 0,
                title = this.fetchLead?.data?.city?.title ?: ""
            ),
            languages = this.fetchLead?.data?.languages?.map {
                IntentionDto(it.id, it.title)
            } ?: listOf(),
            displayName = this.fetchLead?.data?.displayName ?: "",
            contacts = ContactData(this.fetchLead?.data?.contacts?.data?.map {
                ContactDto(
                    phoneContact = PhoneCode(
                        it.phoneContact?.phone ?: "",
                        it.phoneContact?.title ?: ""
                    ),
                    emailContact = Email(
                        it.emailContact?.email ?: "",
                        it.emailContact?.title ?: ""
                    )
                )
            } ?: listOf()),
            avatar = AvatarDto(
                this.fetchLead?.data?.avatar?.path,
                this.fetchLead?.data?.avatar?.name
            )
        )
    )
}

fun CreateLeadMutation.Data.toDetailedDto(): LeadDetailed {
    return LeadDetailed(
        data = LeadDetailedDto(
            id = this.createLead.data.id,
            firstName = this.createLead.data.firstName ?: "",
            lastName = this.createLead.data.lastName ?: "",
            status = StatusData(
                id = this.createLead.data.status?.id ?: 0,
                step = this.createLead.data.status?.step ?: 0,
                stepsCount = this.createLead.data.status?.stepsCount ?: 0,
                title = this.createLead.data.status?.title ?: "",
                color = this.createLead.data.status?.color ?: "",
                backgroundColor = this.createLead.data.status?.backgroundColor ?: "",
            ),
            quality = this.createLead.data.quality ?: 0,
            intention = IntentionDto(
                id = this.createLead.data.intention?.id ?: 0,
                title = this.createLead.data.intention?.title ?: ""
            ),
            adSource = IntentionDto(
                id = this.createLead.data.adSource?.id ?: 0,
                title = this.createLead.data.adSource?.title ?: ""
            ),
            country = CountryDto(
                id = this.createLead.data.country?.id ?: 0,
                phoneCode = this.createLead.data.country?.phoneCode ?: "",
                shortCode1 = this.createLead.data.country?.shortCode1 ?: "",
                emoji = this.createLead.data.country?.emoji ?: "",
                title = this.createLead.data.country?.title ?: ""
            ),
            city = IntentionDto(
                id = this.createLead.data.city?.id ?: 0,
                title = this.createLead.data.city?.title ?: ""
            ),
            languages = this.createLead.data.languages?.map {
                IntentionDto(it.id, it.title)
            } ?: listOf(),
            displayName = this.createLead.data.displayName ?: "",
            contacts = ContactData(this.createLead.data.contacts?.data?.map {
                ContactDto(
                    phoneContact = PhoneCode(
                        it.phoneContact?.phone ?: "",
                        it.phoneContact?.title ?: ""
                    ),
                    emailContact = Email(
                        it.emailContact?.email ?: "",
                        it.emailContact?.title ?: ""
                    )
                )
            } ?: listOf()),
            avatar = AvatarDto(
                this.createLead.data.avatar?.path,
                this.createLead.data.avatar?.name
            ),
            createdAt = this.createLead.data.createdAt.toDateTime())
    )
}

fun UpdateLeadMutation.Data.toDetailedDto(): LeadDetailed {
    return LeadDetailed(
        data = LeadDetailedDto(
            id = this.updateLead.data.id,
            firstName = this.updateLead.data.firstName ?: "",
            lastName = this.updateLead.data.lastName ?: "",
            status = StatusData(
                id = this.updateLead.data.status?.id ?: 0,
                step = this.updateLead.data.status?.step ?: 0,
                stepsCount = this.updateLead.data.status?.stepsCount ?: 0,
                title = this.updateLead.data.status?.title ?: "",
                color = this.updateLead.data.status?.color ?: "",
                backgroundColor = this.updateLead.data.status?.backgroundColor ?: "",
            ),
            quality = this.updateLead.data.quality ?: 0,
            intention = IntentionDto(
                id = this.updateLead.data.intention?.id ?: 0,
                title = this.updateLead.data.intention?.title ?: ""
            ),
            adSource = IntentionDto(
                id = this.updateLead.data.adSource?.id ?: 0,
                title = this.updateLead.data.adSource?.title ?: ""
            ),
            country = CountryDto(
                id = this.updateLead.data.country?.id ?: 0,
                phoneCode = this.updateLead.data.country?.phoneCode ?: "",
                shortCode1 = this.updateLead.data.country?.shortCode1 ?: "",
                emoji = this.updateLead.data.country?.emoji ?: "",
                title = this.updateLead.data.country?.title ?: ""
            ),
            city = IntentionDto(
                id = this.updateLead.data.city?.id ?: 0,
                title = this.updateLead.data.city?.title ?: ""
            ),
            languages = this.updateLead.data.languages?.map {
                IntentionDto(it.id, it.title)
            } ?: listOf(),
            displayName = this.updateLead.data.displayName ?: "",
            contacts = ContactData(this.updateLead.data.contacts?.data?.map {
                ContactDto(
                    phoneContact = PhoneCode(
                        it.phoneContact?.phone ?: "",
                        it.phoneContact?.title ?: ""
                    ),
                    emailContact = Email(
                        it.emailContact?.email ?: "",
                        it.emailContact?.title ?: ""
                    )
                )
            } ?: listOf()),
            avatar = AvatarDto(
                this.updateLead.data.avatar?.path,
                this.updateLead.data.avatar?.name
            ),
            createdAt = this.updateLead.data.createdAt.toDateTime()
        )
    )
}

fun LeadIntentionsQuery.Data?.toDetailedDto(): List<IntentionDto>? {
    return this?.fetchLeadIntentionTypes?.map {
        IntentionDto(it.id, it.title)
    }
}

fun ADSourcesQuery.Data?.toDetailedDto(): List<IntentionDto>? {
    return this?.fetchAdSources?.map {
        IntentionDto(it.id, it.title)
    }
}

fun CitiesQuery.Data?.toDetailedDto(): List<IntentionDto>? {
    return this?.cities?.map {
        IntentionDto(it.id, it.title)
    }
}

fun LanguagesQuery.Data?.toDetailedDto(): List<IntentionDto>? {
    return this?.languages?.map {
        IntentionDto(it.id, it.title)
    }
}

fun CountriesQuery.Data?.toDetailedDto(): List<CountryDto>? {
    return this?.fetchCountries?.map {
        CountryDto(
            id = it.id,
            title = it.title,
            phoneCode = it.phoneCode,
            shortCode1 = it.shortCode1,
            emoji = it.emoji ?: ""
        )
    }
}

fun StatusQuery.Data?.toDetailedDto(): List<StatusData>? {
    return this?.fetchLeadStatusTypes?.map {
        StatusData(
            id = it.id,
            title = it.title,
            step = it.step,
            stepsCount = it.stepsCount,
            color = it.color,
            backgroundColor = it.backgroundColor
        )
    }
}