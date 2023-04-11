package com.massimoregoli.esercitazionecf.model

import android.content.Context
import android.os.Parcelable
import androidx.compose.runtime.mutableStateOf
import com.massimoregoli.esercitazionecf.R
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
class User(@IgnoredOnParcel var context: Context? = null) : Parcelable {
    companion object {
        const val CONS = "BCDFGHJKLMNPQRSTVWXYZ"
        const val MONTH = "ABCDEHLMPRST"
        const val RESTMAP = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val ODDMAP = mapOf(
            "0" to 1,
            "1" to 0,
            "2" to 5,
            "3" to 7,
            "4" to 9,
            "5" to 13,
            "6" to 15,
            "7" to 17,
            "8" to 19,
            "9" to 21,
            "A" to 1,
            "B" to 0,
            "C" to 5,
            "D" to 7,
            "E" to 9,
            "F" to 13,
            "G" to 15,
            "H" to 17,
            "I" to 19,
            "J" to 21,
            "K" to 2,
            "L" to 4,
            "M" to 18,
            "N" to 20,
            "O" to 11,
            "P" to 3,
            "Q" to 6,
            "R" to 8,
            "S" to 12,
            "T" to 14,
            "U" to 16,
            "V" to 10,
            "W" to 22,
            "X" to 25,
            "Y" to 24,
            "Z" to 23,
        )
        val EVENMAP = mapOf(
            "0" to 0,
            "1" to 1,
            "2" to 2,
            "3" to 3,
            "4" to 4,
            "5" to 5,
            "6" to 6,
            "7" to 7,
            "8" to 8,
            "9" to 9,
            "A" to 0,
            "B" to 1,
            "C" to 2,
            "D" to 3,
            "E" to 4,
            "F" to 5,
            "G" to 6,
            "H" to 7,
            "I" to 8,
            "J" to 9,
            "K" to 10,
            "L" to 11,
            "M" to 12,
            "N" to 13,
            "O" to 14,
            "P" to 15,
            "Q" to 16,
            "R" to 17,
            "S" to 18,
            "T" to 19,
            "U" to 20,
            "V" to 21,
            "W" to 22,
            "X" to 23,
            "Y" to 24,
            "Z" to 25,
        )

    }


    @IgnoredOnParcel
    var gender = mutableStateOf("")

    @IgnoredOnParcel
    var birthDate = mutableStateOf("")

    @IgnoredOnParcel
    var firstName = mutableStateOf("")

    @IgnoredOnParcel
    var lastName = mutableStateOf("")

    @IgnoredOnParcel
    var country = mutableStateOf("")

    @IgnoredOnParcel
    var listOfCountries = SingletonListOfCountries.getInstance(context!!)

    private fun lN(): String {
        var cons = ""
        var vowel = ""
        lastName.value.uppercase().forEach {
            if (it != ' ') {
                if (it in CONS) {
                    cons += it
                } else {
                    vowel += it
                }
            }
        }
        return (cons + vowel + "XXX").slice(0..2)
    }

    private fun fN(): String {
        var cons = ""
        var vowel = ""
        firstName.value.uppercase().forEach {
            if (it != ' ') {
                if (it in CONS) {
                    cons += it
                } else {
                    vowel += it
                }
            }
        }
        var ret: String = (cons + vowel + "XXX")
        ret = if (cons.length <= 3)
            "${ret[0]}${ret[1]}${ret[2]}"
        else
            "${ret[0]}${ret[2]}${ret[3]}"
        return ret
    }

    private fun bD(): String {
        if (birthDate.value.length == 10) { // dd/mm/yyyy
            val year = birthDate.value
                .slice(6..9).toInt()
            val month = birthDate.value
                .slice(3..4).toInt()
            var day = birthDate.value
                .slice(0..1).toInt()
            if (gender.value == context?.getString(R.string.female))
                day += 40
            return (year % 100).format() +
                    MONTH[month - 1] +
                    day.format()
        }
        return "99A99"
    }

    private fun cO(): String {
        if (country.value.isNotEmpty()) {
            val theCountry =
                listOfCountries.countries.filter { it.name == country.value.uppercase() }
            if (theCountry.size == 1)
                return theCountry[0].code
        }
        return "X999"
    }

    private fun cH(code: String): String {
        var checksum = 0
        code.forEachIndexed { i, c ->
            checksum += if (i % 2 == 0)
                ODDMAP[c.toString()]!!
            else
                EVENMAP[c.toString()]!!
        }
        return RESTMAP[checksum % 26].toString()
    }

    override fun toString(): String {
        val code = lN() + fN() + bD() + cO()
        return code + cH(code)
    }
}


fun Int.format(): String {
    return if (this < 10) {
        "0$this"
    } else {
        "$this"
    }
}
