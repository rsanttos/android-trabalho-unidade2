package com.example.trabalhounidade2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class SQLiteRepository(ctx: Context) : NotaRepository {
    override fun findAll(): List<Nota> {
        var sql ="SELECT * FROM $TABLE_NAME"
        var args: Array<String>? = null

        sql += " ORDER BY $COLUMN_TITULO"
        val db = helper.writableDatabase
        val cursor = db.rawQuery(sql, args)
        val notas = ArrayList<Nota>()
        while(cursor.moveToNext()){
            val nota = notaFromCursor(cursor)
            notas.add(nota)
        }
        cursor.close()
        db.close()
        return notas
    }

    private val helper: NotaSqlHelper = NotaSqlHelper(ctx)

    private fun insert(n : Nota){
        val db = helper.writableDatabase
        val cv = ContentValues().apply{
            put(COLUMN_TITULO, n.titulo)
            put(COLUMN_TEXTO, n.texto)
        }

        val id = db.insert(TABLE_NAME, null, cv)
        if(id != 1L){
            n.id = id
        }
        db.close()
    }


    private fun update(n : Nota){
        val db = helper.writableDatabase
        val cv = ContentValues().apply{
            put(COLUMN_TITULO, n.titulo)
            put(COLUMN_TEXTO, n.texto)
        }

        db.update(TABLE_NAME, cv, "$COLUMN_ID = ?", arrayOf(n.id.toString()))

        db.close()
    }

    override fun save(nota: Nota) {
        if(nota.id == 0L){
            insert(nota)
        } else {
            update(nota)
        }
    }

    override fun remove(vararg notas: Nota) {
        val db = helper.writableDatabase
        for(p in notas){
            db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(p.id.toString()))
        }
        db.close()
    }

    override fun findById(id: Long, callback: (Nota?) -> Unit) {
        val sql = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = ? "
        val db = helper.writableDatabase
        val cursor = db.rawQuery(sql, arrayOf(id.toString()))
        val p = if(cursor.moveToNext())notaFromCursor(cursor) else null

        callback(p)
    }

    private fun notaFromCursor(cursor: Cursor) : Nota {
        val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
        val titulo = cursor.getString(cursor.getColumnIndex(COLUMN_TITULO))
        val texto = cursor.getString(cursor.getColumnIndex(COLUMN_TEXTO))
        val nota : Nota = Nota(id)
        nota.titulo = titulo
        nota.texto = texto
        return nota
    }

    override fun search(term: String, callback: (List<Nota>) -> Unit) {
        var sql ="SELECT * FROM $TABLE_NAME"
        var args: Array<String>? = null

        if(term.isNotEmpty()){
            sql += " WHERE $COLUMN_TEXTO LIKE ?"
            args = arrayOf("%$term%")
        }

        sql += " ORDER BY $COLUMN_TITULO"
        val db = helper.writableDatabase
        val cursor = db.rawQuery(sql, args)
        val notas = ArrayList<Nota>()
        while(cursor.moveToNext()){
            val nota = notaFromCursor(cursor)
            notas.add(nota)
        }
        cursor.close()
        db.close()
        callback(notas)
    }
}