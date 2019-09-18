package com.example.trabalhounidade2

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_nota.view.*

class NotaAdapter(
    private val notas: List<Nota>,
    private val callback:(Nota) -> Unit,
    private val callbackLong:(Nota, Int) -> Unit
) : RecyclerView.Adapter<NotaAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaAdapter.VH {
        val v = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_nota,parent, false)

        val vh = VH(v)

        vh.itemView.setOnClickListener {
            val nota = notas[vh.adapterPosition]
            callback(nota)
        }

        vh.itemView.setOnLongClickListener {
            val nota = notas[vh.adapterPosition]
            callbackLong(nota, vh.adapterPosition)
            true
        }
        return vh
    }

    override fun getItemCount(): Int {
        return notas.size
    }

    override fun onBindViewHolder(holder: NotaAdapter.VH, position: Int) {
        val (texto) = notas[position]
        holder.txtNota.text = texto
    }

    class  VH(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtNota: TextView = itemView.txtNota
    }
}