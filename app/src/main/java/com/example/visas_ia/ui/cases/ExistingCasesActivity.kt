package com.example.visas_ia.ui.cases

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.visas_ia.databinding.ActivityExistingCasesBinding
import com.example.visas_ia.data.model.VisaCase
import com.example.visas_ia.data.model.Country
import com.example.visas_ia.data.model.VisaType
import java.util.Date

class ExistingCasesActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityExistingCasesBinding
    private lateinit var casesAdapter: CasesAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExistingCasesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        setupRecyclerView()
        loadCases()
    }
    
    private fun setupUI() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Casos Existentes"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    
    private fun setupRecyclerView() {
        casesAdapter = CasesAdapter { case ->
            // Seleccionar Caso según el diagrama
            selectCase(case)
        }
        
        binding.recyclerViewCases.apply {
            layoutManager = LinearLayoutManager(this@ExistingCasesActivity)
            adapter = casesAdapter
        }
    }
    
    private fun loadCases() {
        // Simular carga de casos existentes
        val mockCases = listOf(
            VisaCase(
                id = "1",
                country = Country("US", "Estados Unidos"),
                visaType = VisaType("TOURIST", "Turista"),
                status = "PENDIENTE",
                createdAt = Date()
            ),
            VisaCase(
                id = "2",
                country = Country("CA", "Canadá"),
                visaType = VisaType("STUDENT", "Estudiante"),
                status = "EN_PROCESO",
                createdAt = Date()
            ),
            VisaCase(
                id = "3",
                country = Country("UK", "Reino Unido"),
                visaType = VisaType("BUSINESS", "Negocios"),
                status = "APROBADA",
                createdAt = Date()
            )
        )
        
        casesAdapter.submitList(mockCases)
    }
    
    private fun selectCase(case: VisaCase) {
        // Ver Detalles según el diagrama
        val intent = Intent(this, CaseDetailsActivity::class.java)
        intent.putExtra("case_id", case.id)
        startActivity(intent)
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

class CasesAdapter(
    private val onCaseClick: (VisaCase) -> Unit
) : androidx.recyclerview.widget.ListAdapter<VisaCase, CasesAdapter.CaseViewHolder>(CaseDiffCallback()) {
    
    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): CaseViewHolder {
        val binding = com.example.visas_ia.databinding.ItemCaseBinding.inflate(
            android.view.LayoutInflater.from(parent.context), parent, false
        )
        return CaseViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: CaseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class CaseViewHolder(
        private val binding: com.example.visas_ia.databinding.ItemCaseBinding
    ) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
        
        fun bind(case: VisaCase) {
            binding.tvCountry.text = case.country.name
            binding.tvVisaType.text = case.visaType.name
            binding.tvStatus.text = case.status
            binding.tvDate.text = case.createdAt.toString()
            
            binding.root.setOnClickListener {
                onCaseClick(case)
            }
        }
    }
}

class CaseDiffCallback : androidx.recyclerview.widget.DiffUtil.ItemCallback<VisaCase>() {
    override fun areItemsTheSame(oldItem: VisaCase, newItem: VisaCase): Boolean {
        return oldItem.id == newItem.id
    }
    
    override fun areContentsTheSame(oldItem: VisaCase, newItem: VisaCase): Boolean {
        return oldItem == newItem
    }
} 