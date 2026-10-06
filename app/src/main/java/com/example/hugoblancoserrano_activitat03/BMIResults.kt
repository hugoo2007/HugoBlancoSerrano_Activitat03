package com.example.hugoblancoserrano_activitat03

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.pow

class BMIResults : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bmiresults)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bmi_results)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val gender = intent.getBooleanExtra(MainActivity.EXTRA_GEN, false)
        val height = intent.getStringExtra(MainActivity.EXTRA_HEIGHT)!!
        val weight = intent.getStringExtra(MainActivity.EXTRA_WEIGHT)!!
        val age = intent.getStringExtra(MainActivity.EXTRA_AGE)!!

        val result = findViewById<TextView>(R.id.result)

        val reCalculate = findViewById<LinearLayout>(R.id.ReCalculate)

        val classification = findViewById<TextView>(R.id.classification)

        val bottomClassification = findViewById<TextView>(R.id.bottomClassification)

        val calculate: (weight: Double, inches: Double) -> Double = {weight, height ->
            weight / (height * 0.0254).pow(2)
        }

        result.text = "%.2f".format(calculate(weight.toDouble(), height.toDouble()))

        reCalculate.setOnClickListener{it: View ->
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val toCheck = result.text.toString().toDouble()
        if(toCheck < 18.5) {
            classification.text = getString(R.string.skinny)
            classification.setTextColor(getColor(R.color.blue))
            bottomClassification.text = getString(R.string.skinny)
        } else if(toCheck < 24.9) {
            classification.text = getString(R.string.normal)
            classification.setTextColor(getColor(R.color.green))
            bottomClassification.text = getString(R.string.normal)
        } else {
            classification.text = getString(R.string.fat)
            classification.setTextColor(getColor(R.color.red))
            bottomClassification.text = getString(R.string.fat)
        }
    }
}