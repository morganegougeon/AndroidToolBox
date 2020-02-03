package fr.isen.gougeon.androidtoolbox

import android.app.DownloadManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_web_services.*


class WebServicesActivity : AppCompatActivity(), RandomContactAdapter.OnItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_services)

        requestRandomContact()

    }



    fun requestRandomContact(){
        val textView = findViewById<TextView>(R.id.text)

        val queue = Volley.newRequestQueue(this)
        val url = "https://randomuser.me/api/?results=5"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
                Log.d("volley", response)
                val gson = Gson()
                val result = gson.fromJson(response, RandomContact::class.java)
                result.results?.let{
                    Log.d("volley", it[0].email.toString())
                    Toast.makeText(this, "${it[0].email}", Toast.LENGTH_LONG).show()
                }
                randomContactRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                randomContactRecyclerView.adapter = result.results?.let { RandomContactAdapter(it, this) }


            },
            Response.ErrorListener {
                Log.d("volley", it.toString())
            })

        queue.add(stringRequest)


    }

    override fun onItemClicked(contact: RandomContactModel?) {
        Log.d("string", "coucouc le test")
        val intent = Intent (this, HomeActivity::class.java)
        startActivity(intent)
    }
}
