package com.example.visas_ia.utils

import android.content.Context
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.File
import java.io.IOException

class OCRProcessor {
    
    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    
    fun processDocument(context: Context, uri: Uri, callback: (String) -> Unit) {
        try {
            val image = InputImage.fromFilePath(context, uri)
            
            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    val extractedText = visionText.text
                    callback(extractedText)
                }
                .addOnFailureListener { e ->
                    callback("Error en OCR: ${e.message}")
                }
        } catch (e: IOException) {
            callback("Error al procesar documento: ${e.message}")
        }
    }
    
    fun processImage(context: Context, imageFile: File, callback: (String) -> Unit) {
        try {
            val image = InputImage.fromFilePath(context, Uri.fromFile(imageFile))
            
            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    val extractedText = visionText.text
                    callback(extractedText)
                }
                .addOnFailureListener { e ->
                    callback("Error en OCR: ${e.message}")
                }
        } catch (e: IOException) {
            callback("Error al procesar imagen: ${e.message}")
        }
    }
    
    fun extractKeyInformation(text: String): Map<String, String> {
        val extractedInfo = mutableMapOf<String, String>()
        
        // Extraer información clave del texto
        val lines = text.split("\n")
        
        for (line in lines) {
            when {
                line.contains("nombre", ignoreCase = true) -> {
                    extractedInfo["nombre"] = extractValue(line)
                }
                line.contains("apellido", ignoreCase = true) -> {
                    extractedInfo["apellido"] = extractValue(line)
                }
                line.contains("fecha", ignoreCase = true) && line.contains("nacimiento", ignoreCase = true) -> {
                    extractedInfo["fecha_nacimiento"] = extractValue(line)
                }
                line.contains("pasaporte", ignoreCase = true) -> {
                    extractedInfo["numero_pasaporte"] = extractValue(line)
                }
                line.contains("nacionalidad", ignoreCase = true) -> {
                    extractedInfo["nacionalidad"] = extractValue(line)
                }
            }
        }
        
        return extractedInfo
    }
    
    private fun extractValue(line: String): String {
        return line.split(":", "=").lastOrNull()?.trim() ?: ""
    }
    
    fun validateDocument(text: String, documentType: String): Boolean {
        return when (documentType) {
            "PASSPORT" -> validatePassport(text)
            "ID_CARD" -> validateIdCard(text)
            "BANK_STATEMENT" -> validateBankStatement(text)
            else -> true
        }
    }
    
    private fun validatePassport(text: String): Boolean {
        return text.contains("pasaporte", ignoreCase = true) ||
               text.contains("passport", ignoreCase = true) ||
               text.contains("MRZ", ignoreCase = true)
    }
    
    private fun validateIdCard(text: String): Boolean {
        return text.contains("identidad", ignoreCase = true) ||
               text.contains("identity", ignoreCase = true) ||
               text.contains("DNI", ignoreCase = true)
    }
    
    private fun validateBankStatement(text: String): Boolean {
        return text.contains("banco", ignoreCase = true) ||
               text.contains("bank", ignoreCase = true) ||
               text.contains("saldo", ignoreCase = true) ||
               text.contains("balance", ignoreCase = true)
    }
} 