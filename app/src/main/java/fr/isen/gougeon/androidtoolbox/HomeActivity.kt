package fr.isen.gougeon.androidtoolbox

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        lifeButton.setOnClickListener{
            openLifeCycleActivity()
        }

        deconnexionButton.setOnClickListener{
            deconnexion()
        }
    }

    fun openLifeCycleActivity() {

        val intent = Intent (this, LifeCycleActivity::class.java)
        startActivity(intent)

    }

    fun deconnexion() {
        val sharedPreferencesLogs : SharedPreferences?
        sharedPreferencesLogs = getSharedPreferences("identifiers", Context.MODE_PRIVATE)
        sharedPreferencesLogs.edit().remove("password")
        sharedPreferencesLogs.edit().remove("username")
        sharedPreferencesLogs.edit().clear()
        sharedPreferencesLogs.edit().commit()

        val intent = Intent (this, LoginActivity::class.java)
        startActivity(intent)
    }
}
