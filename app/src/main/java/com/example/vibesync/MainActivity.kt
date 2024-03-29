package com.example.vibesync

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var myRecyclerView: RecyclerView
    lateinit var myAdapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myRecyclerView = findViewById(R.id.recyclerview)
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://deezerdevs-deezer.p.rapidapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiInterface = retrofitBuilder.create(APIInterface::class.java)

        val retrofitData = apiInterface.getData(query = "ed sheeran")
        //------
//        CoroutineScope(Dispatchers.IO).launch {
//            Log.d("thread", "thread - ${Thread.currentThread().name}")

            //enqueue method performs actions based on success and failure
            retrofitData.enqueue(object :
                Callback<MyData?> {     //dont write whole block manually,use shortcut Ctr+Shift+Spc
                override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                    //if the API call is a success then this method is executed
                    val dataList = response.body()?.data!!
//                val textView = findViewById<TextView>(R.id.helloText)
//                textView.text = dataList.toString()

                    myAdapter = MyAdapter(this@MainActivity, dataList)
                    myRecyclerView.adapter = myAdapter
                    myRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)


                    Log.d("TAG: onSuccess", "onResponse: " + response.body())
                }

                override fun onFailure(call: Call<MyData?>, t: Throwable) {
                    //if the API call fails then this method is executed
                    Log.d("TAG: onFailure", "onFailure: " + t.message)
                }
            })
        }
    }

