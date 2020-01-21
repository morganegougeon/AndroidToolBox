package fr.isen.gougeon.androidtoolbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    val goodIdentifier = "admin"
    val goodPassword = "admin"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        validateButton.setOnClickListener{
            doLogin()
        }
     }

    fun doLogin() {
        if (canLog(loginInputLayout.text.toString(), passwordInputLayout.text.toString())){
            val intent = Intent (this, HomeActivity::class.java)
            startActivity(intent)
        }
    }


    fun canLog(identifier : String, password : String) : Boolean
    {
        return identifier == goodIdentifier && password == goodPassword
    }

}