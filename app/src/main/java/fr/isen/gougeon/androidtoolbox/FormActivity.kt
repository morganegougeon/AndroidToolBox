package fr.isen.gougeon.androidtoolbox

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_form.*
import org.json.JSONObject
import java.io.File
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class FormActivity : AppCompatActivity() {

    var currentDate = Date()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        showCalendar()

        saveButton.setOnClickListener {

            saveData()
            seeSavedData()
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
        if(nameInputText.text.toString().isNotEmpty() && surnameInputText.text.toString().isNotEmpty() && nameInputText.text.toString().isNotEmpty()) {
            val file = File(cacheDir.absolutePath + "/data.txt")
            val json = JSONObject()
            json.put("name", nameInputText.text)
            json.put("surname", surnameInputText.text)
            json.put("birthdate", datePickerDialog.text)
            file.writeText(json.toString())
            Toast.makeText(
                this,
                "Enregistrement des données reussi " + "${surnameInputText.text}" + "!",
                Toast.LENGTH_LONG
            ).show()
        }
        else
        {
            Toast.makeText(this, "Veuillez completer tous les champs" , Toast.LENGTH_LONG).show()
        }
    }

    fun seeSavedData () {

        val file = File(cacheDir.absolutePath+"/data.txt")
        val readFile = file.readText()
        val json = JSONObject(readFile)

        /*val formatedDate = SimpleDateFormat("dd/MM/yyyy")
        val dateString = formatedDate.format()
        */
        val parts = json.get("birthdate").toString().split("/")

        val birthDay = parts[0]
        val birthMonth = parts[1]
        val birthYear = parts[2]

        val age = getAge(birthYear.toInt(), birthMonth.toInt(), birthDay.toInt())

        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder.setMessage("Vous etes " + json.get("surname") + " " + json.get("name") + " et vous etes né(e) le " + json.get("birthdate") + ". Vous avez donc " + "${age}" + " ans.")

            .setNegativeButton("Fermer", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })

        val alert = dialogBuilder.create()
        alert.setTitle("Vos données ont bien été sauvegardées !")
        alert.show()

    }

    fun getAge (year : Int , month : Int, day : Int): Int {


        val formatedDate = SimpleDateFormat("dd/MM/yyyy")
        val dateString = formatedDate.format(currentDate)
        val parts = dateString.split("/")

        val currentDay = parts[0]
        val currentMonth = parts[1]
        val currentYear = parts[2]
        currentDay.toInt()
        currentMonth.toInt()
        currentYear.toInt()

        if (currentMonth.toInt() > month)
        {
            return (currentYear.toInt() - year)
        }
        if (currentMonth.toInt() < month)
        {
            return (currentYear.toInt() - year -1)
        }
        else
        {
            if(currentDay.toInt() >= day)
            {
                return (currentYear.toInt() - year)
            }
            else
            {
                return (currentYear.toInt() - year - 1)
            }
        }

        return -1

    }
}
