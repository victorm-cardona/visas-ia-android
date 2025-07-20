package com.example.visas_ia.ui.auth

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.visas_ia.databinding.FragmentForgotPasswordDialogBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class ForgotPasswordDialogFragment : DialogFragment() {

    private var _binding: FragmentForgotPasswordDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotPasswordDialogBinding.inflate(inflater, container, false)
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
        binding.btnSendReset.setOnClickListener {
            sendPasswordResetEmail()
        }
        
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun sendPasswordResetEmail() {
        val email = binding.etEmail.text.toString().trim()

        if (validateInput(email)) {
            showLoading(true)
            
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(requireActivity()) { task ->
                    showLoading(false)
                    
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), 
                            "Se ha enviado un email de recuperación a $email", 
                            Toast.LENGTH_LONG).show()
                        dismiss()
                    } else {
                        handleResetError(task.exception)
                    }
                }
        }
    }

    private fun validateInput(email: String): Boolean {
        if (email.isEmpty()) {
            binding.etEmail.error = "El email es requerido"
            return false
        }
        
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Email inválido"
            return false
        }
        
        return true
    }

    private fun handleResetError(exception: Exception?) {
        val errorMessage = when (exception) {
            is FirebaseAuthInvalidUserException -> "No existe una cuenta con este email"
            else -> "Error al enviar email de recuperación: ${exception?.message}"
        }
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
    }

    private fun showLoading(show: Boolean) {
        binding.btnSendReset.isEnabled = !show
        binding.btnCancel.isEnabled = !show
        
        if (show) {
            binding.btnSendReset.text = "Enviando..."
        } else {
            binding.btnSendReset.text = "Enviar"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 