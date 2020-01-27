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
import java.util.*


class FormActivity : AppCompatActivity() {

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
            Toast.makeText(
                this,
                "Veuillez completer tous les champs" ,
                Toast.LENGTH_LONG
            ).show()
        }



    }

    fun seeSavedData () {
        val file = File(cacheDir.absolutePath+"/data.txt")
        val readFile = file.readText()
        val json = JSONObject(readFile)


        val dialogBuilder = AlertDialog.Builder(this)

        // set message of alert dialog
        dialogBuilder.setMessage("Vous etes " + json.get("surname") + " " + json.get("name") + " et vous etes né(e) le " + json.get("birthdate"))

            .setNegativeButton("Fermer", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Vos données ont bien été sauvegardées !")
        // show alert dialog
        alert.show()

    }




}
