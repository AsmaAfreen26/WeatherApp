package com.example.weatherapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var cityText: EditText
    private lateinit var weatherButton: Button
    private lateinit var weatherDescription: TextView
    private lateinit var weatherTemp: TextView
    private lateinit var weatherCity: TextView

    private val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val weatherApiService = retrofit.create(WeatherApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

       cityText = findViewById(R.id.cityText)
        weatherButton = findViewById(R.id.getWeather)
        weatherDescription = findViewById(R.id.weatherDescription)
        weatherTemp = findViewById(R.id.weatherTemp)
        weatherCity = findViewById(R.id.weatherCity)

        weatherButton.setOnClickListener{
            val city = cityText.text.toString()

            if(city.isNotEmpty()){
                fetchWeather(city)
            }else{
                Toast.makeText(this, "Please enter the city name", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun fetchWeather(city: String){
        val apiKey = "159e103336108558ba99ce4cdca14090"
        val call = weatherApiService.getWeather(city,apiKey)

        call.enqueue(object : Callback<WeatherResponse>{
            override fun onResponse(
                call: Call<WeatherResponse?>,
                response: Response<WeatherResponse?>
            ) {
               if(response.isSuccessful) {
                   val weather = response.body()
                   weatherCity.text = "City: ${weather?.name}"
                   weatherDescription.text = "Description: ${weather?.weather?.get(0)?.description}"
                   weatherTemp.text = "Temperature: ${weather?.main?.temp}°C"
               }else{
                   Toast.makeText(this@MainActivity, "City not found", Toast.LENGTH_LONG).show()
               }
            }

            override fun onFailure(
                call: Call<WeatherResponse?>,
                t: Throwable
            ) {
                Toast.makeText(this@MainActivity, "Network Failure", Toast.LENGTH_LONG).show()
            }

        })
    }
}