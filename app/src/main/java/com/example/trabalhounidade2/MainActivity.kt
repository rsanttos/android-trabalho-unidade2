package com.example.trabalhounidade2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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
    val notaRepository = SQLiteRepository(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notas.addAll(notaRepository.findAll())

        initRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actions,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id == R.id.action_add){
            var newIntent = Intent(this, NotaActivity::class.java)
            startActivityForResult(newIntent, 1)
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }


    fun onNotaItemClick(nota: Nota, position: Int){
        var newIntent = Intent(this, NotaActivity::class.java)
        newIntent.putExtra("NOTA_POSITION", position.toString())
        newIntent.putExtra("NOTA_ID", nota.id.toString())
        newIntent.putExtra("NOTA_TITULO", nota.titulo)
        newIntent.putExtra("NOTA_TEXTO", nota.texto)
        startActivityForResult(newIntent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.v("atv2-v", resultCode.toString())

        var position = data?.getStringExtra("NOTA_POSITION")
        var id = data?.getStringExtra("NOTA_ID")
        var titulo = data?.getStringExtra("NOTA_TITULO")
        var texto = data?.getStringExtra("NOTA_TEXTO")

        var intPos : Int;
        var idLong : Long;

        val nota : Nota = Nota(0)

        if(position != null && id != null && titulo != null && texto != null){
            intPos = Integer.parseInt(position)
            idLong = Integer.parseInt(id).toLong()
            nota.id = idLong
            nota.titulo = titulo
            nota.texto = texto
            notas[intPos] = nota
            adapter.notifyItemChanged(intPos)
        } else if(titulo != null && texto != null){
            nota.titulo = titulo
            nota.texto = texto
            notas.add(nota)
            adapter.notifyItemInserted(notas.lastIndex)
        }

        notaRepository.save(nota)

        Log.v("atv2 - notas - ", notaRepository.findAll().toString())
    }


    fun onNotaItemLongClick(nota: Nota, position: Int){
        notas.remove(nota)
        adapter.notifyItemRemoved(position)
        notaRepository.remove(nota)
    }

    fun initRecyclerView(){
        rvNotas.adapter = adapter
        val layoutMAnager = GridLayoutManager(this, 1)
        rvNotas.layoutManager = layoutMAnager
    }

}
