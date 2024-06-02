package com.example.proyectofinal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListaAdapter(private val reservas:ArrayList<Reserva>) : RecyclerView.Adapter<ListaAdapter.ViewHolder>() {
    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val reservaId = itemView.findViewById<TextView>(R.id.idEntrada)
        val nombreId = itemView.findViewById<TextView>(R.id.idNombre)
        val eventoId = itemView.findViewById<TextView>(R.id.idEvento)
        val tipoEntradaId = itemView.findViewById<TextView>(R.id.idTipoEntrada)

        /**//**//*RESOLVER PROBLEMA DE POR QUE NO SE MUESTRAN LOS DATOS DEL ARRAY*//**//**/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.lista_reservas, parent, false)
        )
    }

    override fun getItemCount() = reservas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reserva = reservas[position]

        holder.reservaId.text = "Reserva nº ${reserva.id}"
        holder.nombreId.text = reserva.nombre
        holder.eventoId.text = reserva.evento
        holder.tipoEntradaId.text = reserva.tipo
    }
}