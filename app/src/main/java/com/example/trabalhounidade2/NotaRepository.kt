package com.example.trabalhounidade2

interface NotaRepository {
    fun save(nota: Nota)
    fun remove(vararg notas: Nota)
    fun findById(id: Long, callback: (Nota?) -> Unit)
    fun search(term: String, callback: (List<Nota>) -> Unit)
    fun findAll() : List<Nota>
}