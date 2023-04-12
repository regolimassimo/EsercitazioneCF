package com.massimoregoli.esercitazionecf.screens

import android.app.DatePickerDialog
import android.content.res.Configuration
import android.widget.DatePicker
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.massimoregoli.esercitazionecf.model.User
import com.massimoregoli.esercitazionecf.R
import com.massimoregoli.esercitazionecf.model.format
import com.massimoregoli.esercitazionecf.ui.theme.BackgroundColor
import com.massimoregoli.esercitazionecf.ui.theme.BorderColor
import com.simonsickle.compose.barcodes.Barcode
import com.simonsickle.compose.barcodes.BarcodeType
import java.util.*

@Composable
fun MainView(user: User) {
    val configuration = LocalConfiguration.current

    when (configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            Column {
                Row1(user)
                Row2(user)
                AutoCountry(user)
                BarCodeResult(user)
            }
        }
        else -> {
            Row {
                Column(
                    modifier = Modifier
                        .weight(1.0f)
                        .fillMaxHeight()
                ) {
                    Row1(user)
                    Row2(user)
                    AutoCountry(user)
                }
                Column(
                    modifier = Modifier
                        .weight(1.0f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    BarCodeResult(user)
                }
            }
        }
    }
}

@Composable
fun Row1(user: User) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        MyOutlinedTextField(
            field = user.lastName,
            label = stringResource(id = R.string.lastname),
            modifier = Modifier.weight(1.0f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        MyOutlinedTextField(
            field = user.firstName, label = stringResource(id = R.string.firstname),
            modifier = Modifier.weight(1.0f)
        )
    }
}

@Composable
fun Row2(user: User) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BirthDatePicker(user)
        GenderSelector(user)
    }
}

@Composable
fun BirthDatePicker(user: User) {
    val mContext = LocalContext.current
    val mCalendar = Calendar.getInstance()
    val mYear: Int = mCalendar.get(Calendar.YEAR)
    val mMonth: Int = mCalendar.get(Calendar.MONTH)
    val mDay: Int = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, theYear: Int,
          theMonth: Int,
          theDayOfMonth: Int ->
            user.birthDate.value =
                "${theDayOfMonth.format()}/${
                    (theMonth + 1)
                        .format()
                }/${theYear.format()}"
        }, mYear, mMonth, mDay
    )
    MyOutlinedTextField(field = user.birthDate,
        label = stringResource(id = R.string.birthdate),
        enabled = false,
        modifier = Modifier.clickable {
            mDatePickerDialog.show()
        })
}

@Composable
fun GenderSelector(user: User) {
    val radioOptions = listOf(
        stringResource(R.string.male),
        stringResource(R.string.female)
    )
    val (selectedOption, onOptionSelected) =
        remember {
            mutableStateOf(radioOptions[0])
        }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
    ) {
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                            user.gender.value = text
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    modifier = Modifier,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = BorderColor,
                        unselectedColor = BorderColor
                    ),

                    onClick = {
                        onOptionSelected(text)
                        user.gender.value = text
                    }
                )
                Text(
                    text = text
                )
            }
        }
    }
}

@Composable
fun AutoCountry(user: User) {
    val options = user.listOfCountries.countries
    var exp by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Column {
        MyOutlinedTextField(
            field = user.country,
            label = stringResource(id = R.string.country),
            modifier = Modifier
        ) { exp = true }
        LazyColumn(modifier = Modifier.padding(4.dp)) {
            val filterOpts = options.filter {
                it.name.startsWith(
                    user.country.value,
                    ignoreCase = true
                )
            }
            if (exp && filterOpts.size < 20
                && filterOpts.isNotEmpty()
                && user.country.value.isNotBlank()
            ) {
                itemsIndexed(filterOpts) { _, item ->
                    Text(
                        text = "${item.code} - " +
                                "${item.name} (${item.prov})",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable(onClick = {
                                user.country.value = item.name
                                exp = false
                                focusManager.clearFocus()
                            })
                    )
                }

            }
        }
    }
}

@Composable
fun BarCodeResult(user: User) {
    Surface(
        modifier = Modifier
            .padding(all = 4.dp)
            .border(1.dp, BorderColor, RoundedCornerShape(4.dp))
            .padding(8.dp),
        color = BackgroundColor
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            if (BarcodeType.CODE_39
                    .isValueValid(user.toString())
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = user.toString()
                )
                Barcode(
                    modifier = Modifier
                        .padding(0.dp)
                        .align(Alignment.CenterHorizontally)
                        .height(250.dp)
                        .width(250.dp),
                    showProgress = true,
                    type = BarcodeType.CODE_39,
                    value = user.toString(),
                    resolutionFactor = 5
                )
            }
        }
    }
}