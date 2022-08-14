package com.mixdor.covertpots.fragment

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.ekn.gruzer.gaugelibrary.ArcGauge
import com.mixdor.covertpots.R
import com.mixdor.covertpots.model.mPlanta

class FragDetallesPlanta(plant: mPlanta) : DialogFragment() {

    private val planta:mPlanta = plant
    /** The system calls this to get the DialogFragment's layout, regardless
    of whether it's being displayed as a dialog or an embedded fragment. */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as dialog or embedded fragment
        val view = inflater.inflate(R.layout.frag_detalles_planta, container, false)

        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.fracToolbar)
        val textSerial = view.findViewById<TextView>(R.id.fragTextVSerial)
        val gause1 = view.findViewById<ArcGauge>(R.id.fraghalfGauge)
        val gause2 = view.findViewById<ArcGauge>(R.id.fraghalfGauge2)
        val gause3 = view.findViewById<ArcGauge>(R.id.fraghalfGauge3)
        val gause4 = view.findViewById<ArcGauge>(R.id.fraghalfGauge4)

        val serialId = "#"+planta.id.toString()+"     "
        textSerial.text = serialId

        toolbar.title = planta.nombre
        toolbar.setNavigationOnClickListener {
            dismiss()
        }
        toolbar.setTitleTextAppearance(requireContext(), R.style.fullDialogTitle)
        toolbar.setTitleTextColor(Color.WHITE)


        gause1.value = planta.sHumS.toDouble()
        gause2.value = planta.sHumA.toDouble()
        gause3.value = planta.sIlu.toDouble()
        gause4.value = planta.sTemp.toDouble()

        gause1.valueColor = ContextCompat.getColor(view.context, R.color.colorTexto)
        gause2.valueColor = ContextCompat.getColor(view.context, R.color.colorTexto)
        gause3.valueColor = ContextCompat.getColor(view.context, R.color.colorTexto)
        gause4.valueColor = ContextCompat.getColor(view.context, R.color.colorTexto)

        return view
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