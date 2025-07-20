package com.example.visas_ia.ui.ai

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.visas_ia.R
import com.example.visas_ia.databinding.ActivityConsultationBinding
import com.example.visas_ia.databinding.ItemChatMessageBinding
import com.example.visas_ia.utils.AIAnalyzer

class ConsultationActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityConsultationBinding
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var aiAnalyzer: AIAnalyzer
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsultationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        setupRecyclerView()
        setupClickListeners()
        initializeAI()
    }
    
    private fun setupUI() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Consultoría IA"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    
    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter()
        val layoutManager = LinearLayoutManager(this@ConsultationActivity)
        binding.recyclerViewChat.apply {
            this.layoutManager = layoutManager
            adapter = chatAdapter
        }
    }
    
    private fun setupClickListeners() {
        binding.btnSend.setOnClickListener {
            sendMessage()
        }
        
        binding.btnKnowledgeBase.setOnClickListener {
            // Consulta Base Conocimientos según el diagrama
            queryKnowledgeBase()
        }
    }
    
    private fun initializeAI() {
        aiAnalyzer = AIAnalyzer()
        
        // Mensaje de bienvenida
        addMessage("IA", "¡Hola! Soy tu asistente de IA para visas. ¿En qué puedo ayudarte?")
    }
    
    private fun sendMessage() {
        val message = binding.etMessage.text.toString().trim()
        if (message.isNotEmpty()) {
            // Chat con IA según el diagrama
            addMessage("Usuario", message)
            binding.etMessage.text.clear()
            
            // Simular respuesta de IA
            processAIResponse(message)
        }
    }
    
    private fun processAIResponse(userMessage: String) {
        // Simular procesamiento de IA
        val response = when {
            userMessage.contains("visa", ignoreCase = true) -> {
                "Para solicitar una visa, necesitas seguir estos pasos:\n" +
                "1. Selecciona el país de destino\n" +
                "2. Elige el tipo de visa\n" +
                "3. Completa el formulario o sube documentos\n" +
                "4. Nuestro sistema de IA analizará tu caso"
            }
            userMessage.contains("documentos", ignoreCase = true) -> {
                "Los documentos típicamente requeridos son:\n" +
                "• Pasaporte válido\n" +
                "• Formulario de solicitud\n" +
                "• Fotografías\n" +
                "• Estados de cuenta bancarios\n" +
                "• Carta de invitación (si aplica)"
            }
            userMessage.contains("tiempo", ignoreCase = true) -> {
                "Los tiempos de procesamiento varían según el país:\n" +
                "• Estados Unidos: 15-30 días\n" +
                "• Canadá: 10-20 días\n" +
                "• Reino Unido: 15-25 días\n" +
                "• Australia: 20-35 días"
            }
            else -> {
                "Entiendo tu consulta. Te recomiendo revisar nuestra base de conocimientos " +
                "o contactar con nuestro equipo de soporte para obtener información más específica."
            }
        }
        
        addMessage("IA", response)
    }
    
    private fun queryKnowledgeBase() {
        // Consulta Base Conocimientos según el diagrama
        addMessage("IA", "Consultando base de conocimientos...")
        
        val knowledgeResponse = """
            📚 **Base de Conocimientos - Visas**
            
            **Países más solicitados:**
            • Estados Unidos - Visa B1/B2
            • Canadá - Visa de Visitante
            • Reino Unido - Visa de Turista
            • Australia - Visa de Turista
            
            **Documentos comunes:**
            • Pasaporte con 6 meses de validez
            • Formulario DS-160 (EEUU)
            • Comprobante de fondos
            • Itinerario de viaje
            
            **Consejos importantes:**
            • Solicita con anticipación
            • Mantén documentación actualizada
            • Sé honesto en tu solicitud
            • Prepárate para la entrevista
        """.trimIndent()
        
        addMessage("IA", knowledgeResponse)
    }
    
    private fun addMessage(sender: String, message: String) {
        val chatMessage = ChatMessage(sender, message, System.currentTimeMillis())
        chatAdapter.addMessage(chatMessage)
        
        // Scroll al final
        binding.recyclerViewChat.post {
            binding.recyclerViewChat.smoothScrollToPosition(chatAdapter.itemCount - 1)
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

data class ChatMessage(
    val sender: String,
    val message: String,
    val timestamp: Long
)

class ChatAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    
    private val messages = mutableListOf<ChatMessage>()
    
    fun addMessage(message: ChatMessage) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }
    
    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemChatMessageBinding.inflate(
            android.view.LayoutInflater.from(parent.context), parent, false
        )
        return ChatViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(messages[position])
    }
    
    override fun getItemCount(): Int = messages.size
    
    class ChatViewHolder(
        private val binding: ItemChatMessageBinding
    ) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
        
        fun bind(message: ChatMessage) {
            binding.tvSender.text = message.sender
            binding.tvMessage.text = message.message
            binding.tvTimestamp.text = formatTimestamp(message.timestamp)
            
            // Ajustar layout según el remitente
            if (message.sender == "Usuario") {
                binding.messageLayout.setBackgroundResource(R.color.primary_light)
            } else {
                binding.messageLayout.setBackgroundResource(R.color.background_secondary)
            }
        }
        
        private fun formatTimestamp(timestamp: Long): String {
            val date = java.util.Date(timestamp)
            val format = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
            return format.format(date)
        }
    }
} 