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
import com.mixdor.covertpots.databinding.FragEditarPlantaBinding
import com.mixdor.covertpots.model.mPlanta


class FragEditarPlanta(plant:mPlanta) : DialogFragment() {

    private val planta:mPlanta = plant

    private var _binding: FragEditarPlantaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragEditarPlantaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.FragEditEdNumSerie.setText(planta.id.toString())
        binding.FragEditEdNombrePlanta.setText(planta.nombre)

        binding.fracEditarToolbar.title = "Editar Planta"
        binding.fracEditarToolbar.setNavigationOnClickListener {
            dismiss()
        }
        binding.fracEditarToolbar.setTitleTextAppearance(view.context, R.style.fullDialogTitle)
        binding.fracEditarToolbar.setTitleTextColor(Color.WHITE)
        binding.fracEditarToolbar.inflateMenu(R.menu.menu_dialog)
        binding.fracEditarToolbar.setOnMenuItemClickListener {

            //Guardar al editar planta

            true
        }
    }

    /** The system calls this only when creating the layout in a dialog. */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }
}