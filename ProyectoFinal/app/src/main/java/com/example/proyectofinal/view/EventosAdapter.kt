package com.example.proyectofinal.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.Evento
import com.example.proyectofinal.R
import com.example.proyectofinal.viewmodel.ListaViewModel

class EventosAdapter (padre: Fragment, private val vm: ListaViewModel): RecyclerView.Adapter<EventosAdapter.Holder>(){
    private var lista=vm.lista

    init{
        vm.cambio.observe(padre.viewLifecycleOwner){
            if(it>0){
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val vista= LayoutInflater.from(parent.context).inflate(R.layout.linea,parent,false)
        return Holder(vista)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val orco= lista[position]
        holder.rellena(orco)
    }

    inner class Holder(itemView: View) :RecyclerView.ViewHolder(itemView){
        private val nombre=itemView.findViewById<TextView>(R.id.nombre)
        private val fecha=itemView.findViewById<TextView>(R.id.fecha)

        fun rellena(evento: Evento){
            nombre.text=evento.nombre
            fecha.text=evento.fecha.toString()
        }
    }
}