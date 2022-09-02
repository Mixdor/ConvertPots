package com.mixdor.covertpots.fragment

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.mixdor.covertpots.R
import com.mixdor.covertpots.databinding.FragDetallesPlantaBinding
import com.mixdor.covertpots.model.mPlanta

class FragDetallesPlanta(plant: mPlanta) : DialogFragment() {

    private val planta:mPlanta = plant

    private var _binding: FragDetallesPlantaBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as dialog or embedded fragment
        _binding = FragDetallesPlantaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val serialId = "#"+planta.id.toString()+"     "
        binding.fragTextVSerial.text = serialId

        binding.fracToolbar.title = planta.nombre
        binding.fracToolbar.setNavigationOnClickListener {
            dismiss()
        }
        binding.fracToolbar.setTitleTextAppearance(view.context, R.style.fullDialogTitle)
        binding.fracToolbar.setTitleTextColor(Color.WHITE)


        binding.fragGaugeHumS.value = planta.sHumS.toDouble()
        binding.fragGaugeHumA.value = planta.sHumA.toDouble()
        binding.fragGaugeIlum.value = planta.sIlu.toDouble()
        binding.fragGaugeTemp.value = planta.sTemp.toDouble()

        binding.fragGaugeHumS.valueColor = ContextCompat.getColor(view.context, R.color.colorTexto)
        binding.fragGaugeHumA.valueColor = ContextCompat.getColor(view.context, R.color.colorTexto)
        binding.fragGaugeIlum.valueColor = ContextCompat.getColor(view.context, R.color.colorTexto)
        binding.fragGaugeTemp.valueColor = ContextCompat.getColor(view.context, R.color.colorTexto)

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