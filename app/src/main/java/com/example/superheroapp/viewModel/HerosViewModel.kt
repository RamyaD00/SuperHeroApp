package com.example.superheroapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.superheroapp.model.HeroResponse
import com.example.superheroapp.model.HerosResponse
import com.example.superheroapp.model.HerosState
import com.example.superheroapp.network.JsonResponse
import com.example.superheroapp.network.NetworkImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class HerosViewModel(private val networkImpl: NetworkImpl) : ViewModel() {

    val viewState = MutableLiveData<HerosState>()

    fun fetchHerosList(){

        viewModelScope.launch(Dispatchers.IO) {

            try {

                val liveData = viewState.value

                if (liveData == null || liveData.heroData is HeroResponse.YetToStart || liveData.heroData is HeroResponse.DefaultError) {

                    viewState.postValue(HerosState(heroData = HeroResponse.RequestInFlight))

                    networkImpl.getHerosList(object : JsonResponse {

                        override fun onSuccess(list: List<HerosResponse>) {
                            viewState.postValue(HerosState(heroData = HeroResponse.Success(list)))
                        }

                        override fun onFailure() {
                            viewState.postValue(HerosState(heroData = HeroResponse.DefaultError))
                        }
                    })
                }
            }catch (e : Exception){

                viewState.postValue(HerosState(heroData = HeroResponse.DefaultError))

            }
        }

    }


}