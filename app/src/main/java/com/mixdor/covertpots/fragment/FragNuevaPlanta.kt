package com.mixdor.covertpots.fragment

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.mixdor.covertpots.R

class FragNuevaPlanta : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.frag_nueva_planta, container, false)

        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.fracNuevaToolbar)
        toolbar.title = "Agregar Nueva Planta"
        toolbar.setNavigationOnClickListener {
            dismiss()
        }
        toolbar.setTitleTextAppearance(view.context, R.style.fullDialogTitle)
        toolbar.setTitleTextColor(Color.WHITE)

        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        return dialog
    }

}