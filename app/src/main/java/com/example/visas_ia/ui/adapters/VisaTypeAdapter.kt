package com.example.visas_ia.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.visas_ia.R
import com.example.visas_ia.data.models.VisaType

class VisaTypeAdapter(
    context: Context,
    private val visaTypes: List<VisaType>
) : ArrayAdapter<VisaType>(context, 0, visaTypes) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    private fun createItemView(position: Int, recycledView: View?, parent: ViewGroup): View {
        val visaType = getItem(position)
        val view = recycledView ?: LayoutInflater.from(context)
            .inflate(android.R.layout.simple_spinner_item, parent, false)

        view.findViewById<TextView>(android.R.id.text1).text = visaType?.name ?: ""

        return view
    }
} 