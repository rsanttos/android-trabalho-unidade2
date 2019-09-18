package com.example.trabalhounidade2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val notas = mutableListOf<Nota>()
    private var adapter = NotaAdapter(notas, this::onNotaItemClick, this::onNotaItemLongClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()

        fabAdd.setOnClickListener {
            DialogNota.show(supportFragmentManager, object : DialogNota.OnTextListener {
                override fun onSetText(text: String) {
                    addNota(text)
                }
            })
        }
    }


    fun onNotaItemClick(nota: Nota){
        val s = "${nota.texto}"
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    fun onNotaItemLongClick(nota: Nota, position: Int){
        val s = "Long Click - ${nota.texto}"
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
        notas.remove(nota)
        adapter.notifyItemRemoved(position)
    }

    fun initSwipeDelete(){
        val swipe = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                notas.removeAt(position)
                adapter.notifyItemRemoved(position)
            }

        }

        val itemTouchHelper = ItemTouchHelper(swipe)
        itemTouchHelper.attachToRecyclerView(rvNotas)
    }

    fun initRecyclerView(){
        rvNotas.adapter = adapter

        val layoutMAnager = GridLayoutManager(this, 1)

        rvNotas.layoutManager = layoutMAnager

        initSwipeDelete()
    }

    fun addNota(texto: String){
        val nota = Nota(texto)
        notas.add(nota)
        adapter.notifyItemInserted(notas.lastIndex)
    }

}
