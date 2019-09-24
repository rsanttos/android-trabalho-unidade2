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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        val s = "${nota.texto}"
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
        var newIntent = Intent(this, NotaActivity::class.java)
        newIntent.putExtra("NOTA_POSITION", position.toString())
        newIntent.putExtra("NOTA_TITULO", nota.titulo)
        newIntent.putExtra("NOTA_TEXTO", nota.texto)
        startActivityForResult(newIntent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.v("atv2-v", resultCode.toString())

        Log.v("atv2-v", data?.hasExtra("NOTA_TEXTO").toString())

        var position = data?.getStringExtra("NOTA_POSITION")
        var titulo = data?.getStringExtra("NOTA_TITULO")
        var texto = data?.getStringExtra("NOTA_TEXTO")

        var intPos : Int;

        val nota : Nota

        if(position != null && titulo != null && texto != null){
            Log.v("atv2-v-position", position)
            intPos = Integer.parseInt(position)
            nota = Nota(titulo, texto)
            notas[intPos] = nota
            adapter.notifyItemChanged(intPos)
        } else if(titulo != null && texto != null){
            Log.v("atv2-v-titulo", titulo)
            Log.v("atv2-v-texto", texto)
            nota = Nota(titulo, texto)
            notas.add(nota)
            adapter.notifyItemInserted(notas.lastIndex)
        }

    }


    fun onNotaItemLongClick(nota: Nota, position: Int){
        val s = "Long Click - ${nota.texto}"
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
        notas.remove(nota)
        adapter.notifyItemRemoved(position)
    }

    fun initRecyclerView(){
        rvNotas.adapter = adapter
        val layoutMAnager = GridLayoutManager(this, 1)
        rvNotas.layoutManager = layoutMAnager
    }

}
