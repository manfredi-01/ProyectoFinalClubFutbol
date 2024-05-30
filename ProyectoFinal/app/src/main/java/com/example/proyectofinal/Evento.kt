package com.example.proyectofinal

class Evento(
    val id:String,
    val nombre:String,
    var fecha:Int
) {
    override fun toString(): String {
        return "$id - $nombre - $fecha"
    }
}