package com.example.weatherapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.weatherapp.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.toString

class WeatherFragment : Fragment() {
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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_weather, container, false)

        cityText = view.findViewById(R.id.cityText)
        weatherButton = view.findViewById(R.id.getWeather)
        weatherDescription = view.findViewById(R.id.weatherDescription)
        weatherTemp = view.findViewById(R.id.weatherTemp)
        weatherCity = view.findViewById(R.id.weatherCity)

        weatherButton.setOnClickListener {
            val city = cityText.text.toString()

            if (city.isNotEmpty()) {
                fetchWeather(city)
            } else {
                Toast.makeText(requireContext(), "Please enter a city name", Toast.LENGTH_SHORT).show()
            }
        }
        return view
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
                    Toast.makeText(requireContext(), "Network Failure", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(
                call: Call<WeatherResponse?>,
                t: Throwable
            ) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    }