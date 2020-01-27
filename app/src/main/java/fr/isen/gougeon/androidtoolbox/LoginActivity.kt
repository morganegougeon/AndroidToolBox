package fr.isen.gougeon.androidtoolbox

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    val goodIdentifier = "admin"
    val goodPassword = "123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        autoLog()

        validateButton.setOnClickListener{
            doLogin()
        }
     }

    fun doLogin() {

        val sharedPreferencesLogs : SharedPreferences ?
        sharedPreferencesLogs = getSharedPreferences("identifiers", Context.MODE_PRIVATE)
        sharedPreferencesLogs.edit().putString("username", "${loginInputLayout.text}").apply()
        sharedPreferencesLogs.edit().putString("password", "${passwordInputLayout.text}").apply()


        if (canLog(loginInputLayout.text.toString(), passwordInputLayout.text.toString())){
            val intent = Intent (this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            Toast.makeText(this, "Bonjour ${loginInputLayout.text}", Toast.LENGTH_LONG).show()
            startActivity(intent)
        }
        else{
            Toast.makeText(this, "Login ou mot de passe incorrect", Toast.LENGTH_LONG).show()
        }
    }


    fun canLog(identifier : String, password : String) : Boolean
    {
        return identifier == goodIdentifier && password == goodPassword
    }

    fun autoLog ()
    {
        val sharedPreferencesLogs : SharedPreferences ?
        sharedPreferencesLogs = getSharedPreferences("identifiers", Context.MODE_PRIVATE)
        val pswd : String ?
        pswd = sharedPreferencesLogs.getString("password", "")
        val usr : String ?
        usr = sharedPreferencesLogs.getString("username", "")

        if (canLog(usr.toString(), pswd.toString())){
            val intent = Intent (this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            Toast.makeText(this, "Bonjour ${usr.toString()}", Toast.LENGTH_LONG).show()
            startActivity(intent)
        }
    }

}