package com.example.trabalhounidade2

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class DialogNota : DialogFragment() {

    private var editTextTitulo: EditText? = null
    private var listener: OnTextListener? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        builder.setTitle("Informe o título da nota")

        builder.setPositiveButton("OK") { dialogInterface, i ->
            if (listener != null) {
                val titulo = editTextTitulo!!.text.toString()
                listener!!.onSetText(titulo)
            }
        }

        builder.setNegativeButton("Cancelar") { dialogInterface, i -> dismiss() }

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_nota, null)
        editTextTitulo = view.findViewById(R.id.edtTituloNota)
        builder.setView(view)
        return builder.create()
    }

    interface OnTextListener {
        fun onSetText(titulo: String)
    }

    companion object {

        fun show(fm: FragmentManager, listener: OnTextListener) {
            val dialog = DialogNota()
            dialog.listener = listener
            dialog.show(fm, "textDialog")
        }
    }
}