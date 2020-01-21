package fr.isen.gougeon.androidtoolbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_life_cycle.*



class LifeCycleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life_cycle)

    }

    override fun onStart()
    {
        super.onStart()
        contentText.setText("Activité démarrée")
    }

    override fun onPause()
    {
        super.onPause()
        Log.d("lifeCycle","Activité en pause")
    }

    override fun onDestroy()
    {
        Toast.makeText(this, "Activitée fermée", Toast.LENGTH_LONG).show()
        super.onDestroy()

    }
}
