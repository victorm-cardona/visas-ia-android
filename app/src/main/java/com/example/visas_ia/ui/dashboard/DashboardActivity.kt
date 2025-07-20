package com.example.visas_ia.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.visas_ia.databinding.ActivityDashboardBinding
import com.example.visas_ia.ui.cases.NewCaseActivity
import com.example.visas_ia.ui.cases.ExistingCasesActivity
import com.example.visas_ia.ui.config.SettingsActivity
import com.example.visas_ia.ui.ai.ConsultationActivity
import com.example.visas_ia.ui.auth.LoginActivity
import com.example.visas_ia.utils.SharedPreferencesManager
import com.google.firebase.auth.FirebaseAuth

class DashboardActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private lateinit var auth: FirebaseAuth
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        sharedPreferencesManager = SharedPreferencesManager(this)
        auth = FirebaseAuth.getInstance()
        
        setupUI()
        setupClickListeners()
        checkAuthentication()
    }
    
    private fun setupUI() {
        // Configurar el toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Dashboard Principal"
        
        // Mostrar información del usuario
        val currentUser = auth.currentUser
        val userName = currentUser?.displayName ?: currentUser?.email ?: "Usuario"
        binding.tvWelcome.text = "Bienvenido, $userName"
    }
    
    private fun setupClickListeners() {
        // Menú Principal según el diagrama de flujo
        
        // 1. Nuevo Caso
        binding.cardNewCase.setOnClickListener {
            val intent = Intent(this, NewCaseActivity::class.java)
            startActivity(intent)
        }
        
        // 2. Ver Casos Existentes
        binding.cardExistingCases.setOnClickListener {
            val intent = Intent(this, ExistingCasesActivity::class.java)
            startActivity(intent)
        }
        
        // 3. Configuración
        binding.cardSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
        
        // 4. Consultoría IA
        binding.cardAiConsultation.setOnClickListener {
            val intent = Intent(this, ConsultationActivity::class.java)
            startActivity(intent)
        }
        
        // 5. Cerrar Sesión
        binding.btnLogout.setOnClickListener {
            showLogoutConfirmation()
        }
    }
    
    private fun checkAuthentication() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            // Usuario no autenticado, ir al login
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    
    private fun showLogoutConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Cerrar Sesión")
            .setMessage("¿Estás seguro de que quieres cerrar sesión?")
            .setPositiveButton("Sí") { _, _ ->
                logout()
            }
            .setNegativeButton("No", null)
            .show()
    }
    
    private fun logout() {
        auth.signOut()
        sharedPreferencesManager.clearUserSession()
        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
        
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    
    override fun onResume() {
        super.onResume()
        // Actualizar estadísticas del dashboard
        updateDashboardStats()
    }
    
    private fun updateDashboardStats() {
        // Aquí se actualizarían las estadísticas del dashboard
        // como número de casos activos, casos pendientes, etc.
    }
} 