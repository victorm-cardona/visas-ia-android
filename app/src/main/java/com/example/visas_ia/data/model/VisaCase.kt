package com.example.visas_ia.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "visa_cases")
data class VisaCase(
    @PrimaryKey
    val id: String,
    val country: Country,
    val visaType: VisaType,
    val status: String, // PENDIENTE, EN_PROCESO, APROBADA, RECHAZADA
    val createdAt: Date,
    val updatedAt: Date = Date(),
    val analysisResult: String? = null,
    val documents: List<String> = emptyList(),
    val notes: String? = null,
    val userId: String? = null
)

data class Country(
    val code: String,
    val name: String,
    val requirements: List<String> = emptyList()
)

data class VisaType(
    val code: String,
    val name: String,
    val description: String = "",
    val requirements: List<String> = emptyList()
)

data class Document(
    val id: String,
    val name: String,
    val type: String, // PASSPORT, PHOTO, BANK_STATEMENT, etc.
    val isRequired: Boolean = true,
    val isUploaded: Boolean = false,
    val filePath: String? = null
) 