package com.example.proyectofinal.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.databinding.FragmentEventosBinding
import com.example.proyectofinal.viewmodel.ListaViewModel

class EventosFragment : Fragment() {
    private var _enlace:FragmentEventosBinding?=null
    private val enlace:FragmentEventosBinding
        get()=_enlace!!

    val vm: ListaViewModel by viewModels()

    val adaptador:EventosAdapter by lazy{
        EventosAdapter(this,vm)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _enlace= FragmentEventosBinding.inflate(inflater,container,false)
        return enlace.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(enlace.listado){
            layoutManager= LinearLayoutManager(activity)
            adapter=adaptador
        }

        //Manejamos los gestos sobre el recyclerview
        val manejadorGestos= ItemTouchHelper(object:
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                vm.eliminaEvento(viewHolder.absoluteAdapterPosition)
            }
        })
        manejadorGestos.attachToRecyclerView(enlace.listado)

        //Añadir el menú
        activity?.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu,menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId){
                    R.id.recargar -> vm.recarga()
                }
                return true
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _enlace=null
    }

}