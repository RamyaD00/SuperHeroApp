package com.example.superheroapp.model

import com.google.gson.annotations.SerializedName

data class HerosResponse(
        @SerializedName("name")
        val name : String = "",

        @SerializedName("powerstats")
        val powerStats : PowerStats = PowerStats(),

        @SerializedName("images")
       val image : Images = Images()
)


data class PowerStats(
        @SerializedName("strength")
        val strength : Int = 0
)

data class Images(
        @SerializedName("md")
        val url : String = ""
)