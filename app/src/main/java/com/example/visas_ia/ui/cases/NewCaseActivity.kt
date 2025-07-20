package com.example.visas_ia.ui.cases

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.visas_ia.R
import com.example.visas_ia.databinding.ActivityNewCaseBinding
import com.example.visas_ia.data.models.VisaCase
import com.example.visas_ia.data.models.VisaType
import com.example.visas_ia.data.models.Country
import com.example.visas_ia.ui.adapters.CountryAdapter
import com.example.visas_ia.ui.adapters.VisaTypeAdapter
import com.example.visas_ia.utils.AIAnalyzer
import com.example.visas_ia.utils.OCRProcessor
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class NewCaseActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityNewCaseBinding
    private var imageCapture: ImageCapture? = null
    private lateinit var outputDirectory: File
    private lateinit var aiAnalyzer: AIAnalyzer
    private lateinit var ocrProcessor: OCRProcessor
    
    private var selectedCountry: Country? = null
    private var selectedVisaType: VisaType? = null
    private var currentStep = 0
    
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { processDocumentUpload(it) }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewCaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        setupClickListeners()
        initializeComponents()
    }
    
    private fun setupUI() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Nuevo Caso"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        outputDirectory = getOutputDirectory()
        showStep1()
    }
    
    private fun setupClickListeners() {
        // Paso 1: Selección de País y Tipo de Visa
        binding.btnNextStep1.setOnClickListener {
            if (validateStep1()) {
                showStep2()
            }
        }
        
        // Paso 2: Métodos de Ingreso de Datos
        binding.btnDigitalForm.setOnClickListener {
            showDigitalForm()
        }
        
        binding.btnDocumentUpload.setOnClickListener {
            selectDocument()
        }
        
        binding.btnCameraScan.setOnClickListener {
            startCamera()
        }
        
        binding.btnAnalyze.setOnClickListener {
            performAIAnalysis()
        }
        
        binding.btnSaveCase.setOnClickListener {
            saveCase()
        }
    }
    
    private fun initializeComponents() {
        aiAnalyzer = AIAnalyzer()
        ocrProcessor = OCRProcessor()
    }
    
    // PASO 1: Selección País Destino y Tipo Visa
    private fun showStep1() {
        currentStep = 1
        binding.step1Layout.visibility = android.view.View.VISIBLE
        binding.step2Layout.visibility = android.view.View.GONE
        binding.step3Layout.visibility = android.view.View.GONE
        
        // Cargar países disponibles
        loadCountries()
        loadVisaTypes()
    }
    
    private fun loadCountries() {
        val countries = listOf(
            Country("US", "Estados Unidos", "US"),
            Country("CA", "Canadá", "CA"),
            Country("UK", "Reino Unido", "UK"),
            Country("AU", "Australia", "AU"),
            Country("NZ", "Nueva Zelanda", "NZ"),
            Country("EU", "Unión Europea", "EU")
        )
        
        // val countryAdapter = CountryAdapter(this, countries)
        // binding.spinnerCountry.adapter = countryAdapter
    }
    
    private fun loadVisaTypes() {
        val visaTypes = listOf(
            VisaType("TOURIST", "Turista", "Visa de turista"),
            VisaType("BUSINESS", "Negocios", "Visa de negocios"),
            VisaType("STUDENT", "Estudiante", "Visa de estudiante"),
            VisaType("WORK", "Trabajo", "Visa de trabajo"),
            VisaType("FAMILY", "Familiar", "Visa familiar")
        )
        
        // val visaTypeAdapter = VisaTypeAdapter(this, visaTypes)
        // binding.spinnerVisaType.adapter = visaTypeAdapter
    }
    
    private fun validateStep1(): Boolean {
        if (selectedCountry == null) {
            Toast.makeText(this, "Selecciona un país de destino", Toast.LENGTH_SHORT).show()
            return false
        }
        
        if (selectedVisaType == null) {
            Toast.makeText(this, "Selecciona un tipo de visa", Toast.LENGTH_SHORT).show()
            return false
        }
        
        return true
    }
    
    // PASO 2: Métodos de Ingreso de Datos
    private fun showStep2() {
        currentStep = 2
        binding.step1Layout.visibility = android.view.View.GONE
        binding.step2Layout.visibility = android.view.View.VISIBLE
        binding.step3Layout.visibility = android.view.View.GONE
    }
    
    // 2.1 Formulario Digital
    private fun showDigitalForm() {
        val intent = Intent(this, DigitalFormActivity::class.java)
        intent.putExtra("country", selectedCountry?.name ?: "")
        intent.putExtra("visaType", selectedVisaType?.name ?: "")
        startActivity(intent)
    }
    
    // 2.2 Subida de Documentos
    private fun selectDocument() {
        getContent.launch("application/pdf")
    }
    
    private fun processDocumentUpload(uri: Uri) {
        // Procesamiento OCR
        ocrProcessor.processDocument(this, uri) { extractedText ->
            runOnUiThread {
                binding.tvOcrResult.text = "Texto extraído: $extractedText"
                binding.btnAnalyze.isEnabled = true
            }
        }
    }
    
    // 2.3 Escaneo con Cámara
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            
            val preview = Preview.Builder().build()
            imageCapture = ImageCapture.Builder().build()
            
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
                preview.setSurfaceProvider(binding.viewFinder.surfaceProvider)
            } catch (exc: Exception) {
                Toast.makeText(this, "Error al iniciar la cámara", Toast.LENGTH_SHORT).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }
    
    private fun takePhoto() {
        val currentImageCapture = imageCapture ?: return
        
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US)
                .format(System.currentTimeMillis()) + ".jpg"
        )
        
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        
        currentImageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Toast.makeText(this@NewCaseActivity, "Error al tomar la foto", Toast.LENGTH_SHORT).show()
                }
                
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    processImageCapture(photoFile)
                }
            }
        )
    }
    
    private fun processImageCapture(photoFile: File) {
        // Procesamiento de imagen
        ocrProcessor.processImage(this, photoFile) { extractedText ->
            runOnUiThread {
                binding.tvImageResult.text = "Texto extraído: $extractedText"
                binding.btnAnalyze.isEnabled = true
            }
        }
    }
    
    // PASO 3: Análisis IA y Resultados
    private fun performAIAnalysis() {
        showStep3()
        
        // Simular análisis de IA
        binding.progressBar.visibility = android.view.View.VISIBLE
        binding.btnAnalyze.isEnabled = false
        
                // Usar coroutine para llamar a la función suspend
        CoroutineScope(Dispatchers.Main).launch {
            aiAnalyzer.analyzeCase(selectedCountry!!, selectedVisaType!!) { analysisResult ->
                binding.progressBar.visibility = android.view.View.GONE
                binding.btnAnalyze.isEnabled = true
                showAnalysisResults(analysisResult)
            }
        }
    }
    
    private fun showStep3() {
        currentStep = 3
        binding.step1Layout.visibility = android.view.View.GONE
        binding.step2Layout.visibility = android.view.View.GONE
        binding.step3Layout.visibility = android.view.View.VISIBLE
    }
    
    private fun showAnalysisResults(analysisResult: String) {
        binding.tvAnalysisResult.text = analysisResult
        binding.btnSaveCase.isEnabled = true
    }
    
    private fun saveCase() {
        val visaCase = VisaCase(
            id = UUID.randomUUID().toString(),
            country = selectedCountry!!,
            visaType = selectedVisaType!!,
            status = "PENDIENTE",
            createdAt = Date(),
            analysisResult = binding.tvAnalysisResult.text.toString()
        )
        
        // Guardar en base de datos
        // caseRepository.saveCase(visaCase)
        
        Toast.makeText(this, "Caso guardado exitosamente", Toast.LENGTH_SHORT).show()
        
        // Regresar al dashboard
        finish()
    }
    
    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
} 