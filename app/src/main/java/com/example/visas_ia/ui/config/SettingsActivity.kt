package com.example.visas_ia.ui.config

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.visas_ia.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivitySettingsBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        setupClickListeners()
    }
    
    private fun setupUI() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Configuración"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    
    private fun setupClickListeners() {
        // Configurar Perfil según el diagrama
        binding.cardProfile.setOnClickListener {
            // Abrir configuración de perfil
        }
        
        // Configurar Notificaciones según el diagrama
        binding.cardNotifications.setOnClickListener {
            // Abrir configuración de notificaciones
        }
        
        // Configurar Privacidad según el diagrama
        binding.cardPrivacy.setOnClickListener {
            // Abrir configuración de privacidad
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
} 