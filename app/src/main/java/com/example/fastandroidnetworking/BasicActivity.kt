package com.example.fastandroidnetworking

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener

import kotlinx.android.synthetic.main.activity_basic.*
import kotlinx.android.synthetic.main.content_basic.*
import org.json.JSONObject
import java.util.ArrayList

class BasicActivity : AppCompatActivity() {

    val events = ArrayList<Event>()

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic)
        setSupportActionBar(toolbar)
        getData()

        rv_event_list.layoutManager = LinearLayoutManager(this,LinearLayout.VERTICAL, false)
        sr_event.setOnRefreshListener {
            getData()
        }
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun getData() {
        events.clear()
        rv_event_list.adapter = null
        AndroidNetworking.get(EndPoints.BASH_URL)
            .addQueryParameter("action","read")
            .addQueryParameter("table_name", "Sheet1")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener{
                override fun onResponse(response: JSONObject) {
                    Log.d("EVENT RESULT", response.toString())
                    val eventsData = response.getJSONArray("data")
                    for (i in 0 until eventsData.length()){
                        val data = eventsData.getJSONObject(i)

                        events.add(
                            Event(
                            data.optString("title"),
                            data.optString("date"),
                            data.optString("image"),
                            data.optString("description"),
                            data.optDouble("latitude"),
                            data.optDouble("longitude")
                        )
                        )

                    }

                    rv_event_list.adapter = EventAdapter(events)
                    sr_event.isRefreshing = false
                }

                override fun onError(anError: ANError?) {
                    Log.e("ERROR", anError.toString())
                }
            })
    }

}
