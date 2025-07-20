package com.example.visas_ia.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.visas_ia.databinding.ActivityLoginBinding
import com.example.visas_ia.ui.dashboard.DashboardActivity
import com.example.visas_ia.utils.SharedPreferencesManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class LoginActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private lateinit var auth: FirebaseAuth
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        sharedPreferencesManager = SharedPreferencesManager(this)
        auth = FirebaseAuth.getInstance()
        
        setupUI()
        setupClickListeners()
        checkCurrentUser()
    }
    
    private fun setupUI() {
        // Configurar el toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Iniciar Sesión"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    
    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener {
            performLogin()
        }
        
        binding.btnRegister.setOnClickListener {
            showRegisterDialog()
        }
        
        binding.btnForgotPassword.setOnClickListener {
            showForgotPasswordDialog()
        }
    }
    
    private fun checkCurrentUser() {
        // Verificar si ya hay un usuario logueado
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // Usuario ya está logueado, ir al dashboard
            startDashboardActivity()
        }
    }
    
    private fun performLogin() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        
        if (validateInput(email, password)) {
            showLoading(true)
            
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    showLoading(false)
                    
                    if (task.isSuccessful) {
                        // Login exitoso
                        val user = auth.currentUser
                        if (user != null) {
                            sharedPreferencesManager.saveUserSession(user.email ?: "", user.displayName ?: "Usuario")
                            Toast.makeText(this, "¡Bienvenido!", Toast.LENGTH_SHORT).show()
                            startDashboardActivity()
                        }
                    } else {
                        // Login fallido
                        handleLoginError(task.exception)
                    }
                }
        }
    }
    
    private fun handleLoginError(exception: Exception?) {
        val errorMessage = when (exception) {
            is FirebaseAuthInvalidUserException -> "No existe una cuenta con este email"
            is FirebaseAuthInvalidCredentialsException -> "Contraseña incorrecta"
            else -> "Error de autenticación: ${exception?.message}"
        }
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }
    
    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            binding.etEmail.error = "El email es requerido"
            return false
        }
        
        if (password.isEmpty()) {
            binding.etPassword.error = "La contraseña es requerida"
            return false
        }
        
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Email inválido"
            return false
        }
        
        return true
    }
    
    private fun showLoading(show: Boolean) {
        binding.btnLogin.isEnabled = !show
        binding.btnRegister.isEnabled = !show
        binding.btnForgotPassword.isEnabled = !show
        
        if (show) {
            binding.btnLogin.text = "Iniciando sesión..."
        } else {
            binding.btnLogin.text = "Iniciar Sesión"
        }
    }
    
    private fun startDashboardActivity() {
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }
    
    private fun showRegisterDialog() {
        // Mostrar diálogo de registro
        val registerDialog = RegisterDialogFragment()
        registerDialog.show(supportFragmentManager, "RegisterDialog")
    }
    
    private fun showForgotPasswordDialog() {
        // Mostrar diálogo de recuperación de contraseña
        val forgotPasswordDialog = ForgotPasswordDialogFragment()
        forgotPasswordDialog.show(supportFragmentManager, "ForgotPasswordDialog")
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
} 