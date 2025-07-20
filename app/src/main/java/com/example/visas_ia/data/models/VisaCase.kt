package com.example.visas_ia.data.models

import java.util.Date

data class VisaCase(
    val id: String,
    val country: Country,
    val visaType: VisaType,
    val status: String,
    val createdAt: Date,
    val analysisResult: String? = null
) 