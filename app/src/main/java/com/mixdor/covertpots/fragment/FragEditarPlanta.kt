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
import com.mixdor.covertpots.model.mPlanta


class FragEditarPlanta(plant:mPlanta) : DialogFragment() {

    private val planta:mPlanta = plant

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.frag_editar_planta, container, false)

        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.fracEditarToolbar)

        toolbar.title = "Editar Planta"
        toolbar.setNavigationOnClickListener {
            dismiss()
        }
        toolbar.setTitleTextAppearance(view.context, R.style.fullDialogTitle)
        toolbar.setTitleTextColor(Color.WHITE)

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