package fr.isen.gougeon.androidtoolbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_life_cycle.*



class LifeCycleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life_cycle)


        var state :Int = 0

        changeFragment.setOnClickListener{
            var fragmentRequired : Fragment
            //fragmentNumber = NeutralFragment()

             if (state%2 == 0)
            {
                fragmentRequired = LifeCycleSecondFragment()
            }
            else
            {
                fragmentRequired = LifeCycleFragment()
            }
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragmentRequired).commit()
            state++
        }
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
        Toast.makeText(this, "Activité fermée", Toast.LENGTH_LONG).show()
        super.onDestroy()

    }
}
