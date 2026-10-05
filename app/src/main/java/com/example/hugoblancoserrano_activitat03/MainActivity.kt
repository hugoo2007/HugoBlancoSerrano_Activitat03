package com.example.hugoblancoserrano_activitat03

import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.card.MaterialCardView
import com.google.android.material.slider.Slider
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    /*Aqui estem definint totes les variables amb els elements que
    farem servir quan volguem programar els comportaments d'aquestes
    propietats. Lateint el que fa es esperarse a que es defineixi el valor d'aquestes
    per a que no siguin null i no exploti res. */
    lateinit var male_card: MaterialCardView
    lateinit var female_card: MaterialCardView
    lateinit var inches: Slider
    lateinit var inchesNumber: TextView
    lateinit var weightNumber: TextView
    lateinit var ageNumber: TextView

    lateinit var ageSumButton: Button
    lateinit var ageRestButton: Button

    lateinit var weightSumButton: Button
    lateinit var weightRestButton: Button
    var isMale: Boolean = false
    var isFemale: Boolean = false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /*Assignem per id els camps del layout, indicant quina mena de camp retorna la cerca als <>*/
        male_card = findViewById<MaterialCardView>(R.id.seleccio_home)
        female_card = findViewById<MaterialCardView>(R.id.seleccio_dona)

        male_card.setOnClickListener(::cardClick)

        female_card.setOnClickListener(::cardClick)

        inches = findViewById<Slider>(R.id.slider_inches)
        inchesNumber = findViewById<TextView>(R.id.inchesNumber)
        /*Afegim un event que indica que en quant es faci un canvi al scroll, activi aquest event,
        que bàsicament agafa el valor del slider i el parseja a string per mostrar-lo al text numéric.
        */
        inches.addOnChangeListener { it: Slider, value: Float, wasPerson: Boolean ->
            inchesNumber.text = "%.1f".format(it.value)
        }

        /*Aquí bàsicament fem el mateix pero per els botons, fent sumes i restes dels valors
        cada cop que es prem el botó de sumar o restar, sempre comprovant que no sigui 0 o superior al limit
        d'edat o pes.
        */
        weightNumber = findViewById<TextView>(R.id.weightNumbers)
        weightSumButton = findViewById<Button>(R.id.summonButtonWeight)
        weightRestButton = findViewById<Button>(R.id.restButonWeight)
        weightSumButton.setOnClickListener { it: View ->
            val numberParsed: Int = weightNumber.text.toString().toInt()
            weightNumber.text = if(numberParsed < 250) (numberParsed + 1).toString() else 250.toString()
        }
        weightRestButton.setOnClickListener{it: View ->
            val numberParsed = weightNumber.text.toString().toInt()
            weightNumber.text = if(numberParsed > 0) (numberParsed -1).toString() else 0.toString()
        }

        ageNumber = findViewById<TextView>(R.id.ageNumbers)
        ageSumButton = findViewById<Button>(R.id.summonButtonAge)
        ageRestButton = findViewById<Button>(R.id.restButonAge)
        ageSumButton.setOnClickListener { it: View ->
            val numberParsed: Int = ageNumber.text.toString().toInt()
            ageNumber.text = if(numberParsed < 150) (numberParsed + 1).toString() else 150.toString()
        }
        ageRestButton.setOnClickListener { it: View ->
            val numberParsed: Int = ageNumber.text.toString().toInt()
            ageNumber.text = if(numberParsed > 0) (numberParsed - 1).toString() else 0.toString()
        }
    }

    /*Aquí, bàsicament estem dient que fer cada cop que es premi un dels valors
    corresponents al botó que s'ha pulsat. Alterna bàsicament, el color d'aquests
    comparant les referències a cada onclick.
    */
    fun cardClick(it: View): Unit {
        if(it == male_card) {
            female_card.setCardBackgroundColor(  ContextCompat.getColor(this, R.color.marine_blue))
            male_card.setCardBackgroundColor(Color.GREEN)
            isMale=true
            isFemale=false
        } else {
            male_card.setCardBackgroundColor(ContextCompat.getColor(this, R.color.marine_blue))
            female_card.setCardBackgroundColor(Color.GREEN)
            isFemale=true
            isMale=false
        }
    }

    fun changeInches(it: Slider, value: Float, fromUser: Boolean) {

    }
}