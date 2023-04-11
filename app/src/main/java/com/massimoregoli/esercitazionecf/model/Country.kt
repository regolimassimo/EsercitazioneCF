package com.massimoregoli.esercitazionecf.model

import android.content.Context
import java.io.InputStream

class Country {
    var name: String = ""
    var prov: String = ""
    var code: String = ""
}

abstract class ListOfCountries {
    var countries = mutableListOf<Country>()
}

class SingletonListOfCountries : ListOfCountries() {
    companion object {
        private var listOfCountries: SingletonListOfCountries? = null
        fun getInstance(context: Context): SingletonListOfCountries =
            listOfCountries ?: synchronized(this) {
                listOfCountries ?: readFile(context).also { listOfCountries = it }
            }

        private fun readFile(context: Context): SingletonListOfCountries {
            val listOfCountries = SingletonListOfCountries()
            val assets = context.assets
            val inputStream: InputStream = assets.open("belfiore.csv")

            inputStream.bufferedReader().useLines { sequence ->
                sequence.forEach {
                    val country = Country()
                    val fields = it.split(",")
                    country.name = fields[0]
                    country.prov = fields[1]
                    country.code = fields[2]
                    listOfCountries.countries.add(country)
                }
            }
            return listOfCountries
        }

    }
}