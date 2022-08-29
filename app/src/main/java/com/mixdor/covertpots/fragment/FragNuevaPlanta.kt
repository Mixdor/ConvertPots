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
import com.mixdor.covertpots.databinding.FragNuevaPlantaBinding

class FragNuevaPlanta : DialogFragment() {

    private var _binding: FragNuevaPlantaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragNuevaPlantaBinding.inflate(layoutInflater)
        val view = binding.root

        binding.fracNuevaToolbar.title = "Agregar Nueva Planta"
        binding.fracNuevaToolbar.setNavigationOnClickListener {
            dismiss()
        }
        binding.fracNuevaToolbar.setTitleTextAppearance(view.context, R.style.fullDialogTitle)
        binding.fracNuevaToolbar.setTitleTextColor(Color.WHITE)
        binding.fracNuevaToolbar.inflateMenu(R.menu.menu_dialog)
        binding.fracNuevaToolbar.setOnMenuItemClickListener {

            //Guardar Nueva planta

            true
        }

        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        return dialog
    }

}