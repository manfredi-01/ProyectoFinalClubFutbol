package com.example.proyectofinal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinal.ClienteRetrofit
import com.example.proyectofinal.Evento
import kotlinx.coroutines.launch

class ListaViewModel: ViewModel() {
    private val _lista=mutableListOf<Evento>()
    val lista:List<Evento>
        get()=_lista

    private var _cambio= MutableLiveData(0)
    val cambio: LiveData<Int>
        get() = _cambio

    //Para mostrar las tosatadas cuando realicemos alguna accion
    private val _mostrarTostada = MutableLiveData<String?>()
    val mostrarTostada: LiveData<String?>
        get() = _mostrarTostada


    init{
        //Corrutinas
        viewModelScope.launch {
            try {
                val actualizaLista= ClienteRetrofit.servicio.actualiza()
                _lista.clear()
                _lista.addAll(actualizaLista)
                _cambio.value=_cambio.value!!+1
                _mostrarTostada.value = "CARGA DE DATOS CORRECTA"
            } catch (e: Exception) {
                //Tostada
                _mostrarTostada.value = "Error en la carga de datos"
            }
        }
    }

    //Funcion para recargar los datos desde la API
    fun recarga(){

        viewModelScope.launch {
            try {
                val actualizaLista=ClienteRetrofit.servicio.actualiza()
                _lista.clear()
                _lista.addAll(actualizaLista)
                _cambio.value=_cambio.value!!+1
            } catch (e: Exception) {
                //Tostada
                _mostrarTostada.value="Error en la recarga de eventos"
            }
        }
    }

    //Funcion para eliminar evento
    fun eliminaEvento(posicion:Int){

        viewModelScope.launch {
            try {
                val eliminado = _lista[posicion]
                ClienteRetrofit.servicio.borraUno(eliminado.id)
                _lista.removeAt(posicion)
                _cambio.value=_cambio.value!!+1
            } catch (e: Exception) {
                //Tostada
                _mostrarTostada.value="Error al borrar el evento"
            }
        }
    }
}