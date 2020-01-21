package fr.isen.gougeon.androidtoolbox

import android.content.Intent
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
    }

    fun openLifeCycleActivity() {

        val intent = Intent (this, LifeCycleActivity::class.java)
        startActivity(intent)

    }
}
