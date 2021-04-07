package com.example.superheroapp.viewModel

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.superheroapp.network.NetworkImpl

object ViewModelUtil {
    fun getAppViewModel(activity: AppCompatActivity): HerosViewModel {
        return ViewModelProviders.of(activity, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repo = NetworkImpl()
                @Suppress("UNCHECKED_CAST")
                return HerosViewModel(repo) as T
            }
        })[HerosViewModel::class.java]
    }
}