package com.example.visas_ia.ui.auth

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.visas_ia.R
import com.example.visas_ia.databinding.FragmentRegisterDialogBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class RegisterDialogFragment : DialogFragment() {

    private var _binding: FragmentRegisterDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterDialogBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        
        setupClickListeners()
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setCanceledOnTouchOutside(false)
        }
    }

    private fun setupClickListeners() {
        binding.btnRegister.setOnClickListener {
            performRegister()
        }
        
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun performRegister() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()
        val name = binding.etName.text.toString().trim()

        if (validateInput(email, password, confirmPassword, name)) {
            showLoading(true)
            
            // Intentar registro directo con manejo mejorado de errores
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    showLoading(false)
                    
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        if (user != null) {
                            // Actualizar el nombre del usuario
                            val profileUpdates = com.google.firebase.auth.UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .build()
                            
                            user.updateProfile(profileUpdates)
                                .addOnCompleteListener { profileTask ->
                                    if (profileTask.isSuccessful) {
                                        Toast.makeText(requireContext(), "¡Cuenta creada exitosamente!", Toast.LENGTH_SHORT).show()
                                        dismiss()
                                    } else {
                                        Toast.makeText(requireContext(), "Cuenta creada pero error al actualizar perfil", Toast.LENGTH_SHORT).show()
                                        dismiss()
                                    }
                                }
                        }
                    } else {
                        // Manejo específico de errores de reCAPTCHA
                        when {
                            task.exception?.message?.contains("CONFIGURATION_NOT_FOUND") == true -> {
                                Toast.makeText(requireContext(), 
                                    "Error de configuración. Por favor, contacta al administrador.", 
                                    Toast.LENGTH_LONG).show()
                            }
                            task.exception?.message?.contains("reCAPTCHA") == true -> {
                                Toast.makeText(requireContext(), 
                                    "Error de verificación. Intenta nuevamente.", 
                                    Toast.LENGTH_LONG).show()
                            }
                            else -> {
                                handleRegisterError(task.exception)
                            }
                        }
                    }
                }
        }
    }
    


    private fun validateInput(email: String, password: String, confirmPassword: String, name: String): Boolean {
        if (name.isEmpty()) {
            binding.etName.error = "El nombre es requerido"
            return false
        }
        
        if (email.isEmpty()) {
            binding.etEmail.error = "El email es requerido"
            return false
        }
        
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Email inválido"
            return false
        }
        
        if (password.isEmpty()) {
            binding.etPassword.error = "La contraseña es requerida"
            return false
        }
        
        if (password.length < 6) {
            binding.etPassword.error = "La contraseña debe tener al menos 6 caracteres"
            return false
        }
        
        if (confirmPassword.isEmpty()) {
            binding.etConfirmPassword.error = "Confirma tu contraseña"
            return false
        }
        
        if (password != confirmPassword) {
            binding.etConfirmPassword.error = "Las contraseñas no coinciden"
            return false
        }
        
        return true
    }

    private fun handleRegisterError(exception: Exception?) {
        val errorMessage = when (exception) {
            is FirebaseAuthWeakPasswordException -> "La contraseña es muy débil"
            is FirebaseAuthInvalidCredentialsException -> "Email inválido"
            is FirebaseAuthUserCollisionException -> "Ya existe una cuenta con este email"
            is com.google.firebase.FirebaseException -> {
                when (exception.message) {
                    "CONFIGURATION_NOT_FOUND" -> "Error de configuración de reCAPTCHA. Contacta al administrador."
                    else -> "Error de Firebase: ${exception.message}"
                }
            }
            else -> "Error al crear cuenta: ${exception?.message}"
        }
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
    }

    private fun showLoading(show: Boolean) {
        binding.btnRegister.isEnabled = !show
        binding.btnCancel.isEnabled = !show
        
        if (show) {
            binding.btnRegister.text = "Creando cuenta..."
        } else {
            binding.btnRegister.text = "Registrarse"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 