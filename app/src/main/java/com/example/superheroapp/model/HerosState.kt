package com.example.superheroapp.model

data class HerosState (
        val heroData : HeroResponse = HeroResponse.YetToStart
)

//Heros Data response state
sealed class HeroResponse {

    //get Heros request is yet to start
    object YetToStart : HeroResponse()

    //get Heros request is in flight
    object RequestInFlight : HeroResponse()

    //success response with HerosData
    data class Success(val HerosData: List<HerosResponse>) : HeroResponse()

    //Heros has other Undefined errors
    object DefaultError : HeroResponse()
}