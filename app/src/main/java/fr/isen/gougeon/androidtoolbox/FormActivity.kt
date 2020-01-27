package fr.isen.gougeon.androidtoolbox

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.media.MediaScannerConnection
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_form.*
import org.json.JSONObject
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.*


class FormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        showCalendar()

        saveButton.setOnClickListener {

            saveData()
        }
    }

    fun showCalendar() {
        datePickerDialog.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                val mYear: Int
                val mMonth: Int
                val mDay: Int

                datePickerDialog.clearFocus()

                val c: Calendar = Calendar.getInstance()
                mYear = c.get(Calendar.YEAR)
                mMonth = c.get(Calendar.MONTH)
                mDay = c.get(Calendar.DAY_OF_MONTH)


                val datePickerDialog = DatePickerDialog(
                    this,
                    OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                        datePickerDialog.setText(String.format("%02d/%02d/%04d", dayOfMonth,(monthOfYear + 1), year))

                    }, mYear, mMonth, mDay
                )
                datePickerDialog.show()

            }

        }
    }

    fun saveData()
    {
        val file = File(cacheDir.absolutePath+"/data.txt")
        val json = JSONObject()
        json.put("name", nameInputText.text)
        json.put("surname", surnameInputText.text)
        json.put("birthdate", datePickerDialog.text)
        file.writeText(json.toString())
        Toast.makeText(this, "Enregistrement des données reussi " + "${surnameInputText.text}" + "!", Toast.LENGTH_LONG).show()

    }




}
