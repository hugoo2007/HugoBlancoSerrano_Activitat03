package com.example.hugoblancoserrano_activitat03

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.card.MaterialCardView
import com.google.android.material.slider.Slider

class MainActivity : AppCompatActivity() {

    /*Aqui estem definint totes les variables amb els elements que
    farem servir quan volguem programar els comportaments d'aquestes
    propietats. Lateint el que fa es esperarse a que es defineixi el valor d'aquestes
    per a que no siguin null i no exploti res. */
    lateinit var calculateButton: CardView
    lateinit var maleCard: MaterialCardView
    lateinit var femaleCard: MaterialCardView
    lateinit var inches: Slider
    lateinit var inchesNumber: TextView
    lateinit var weightNumber: TextView
    lateinit var ageNumber: TextView

    lateinit var ageSumButton: Button
    lateinit var ageRestButton: Button

    lateinit var weightSumButton: Button
    lateinit var weightRestButton: Button
    var isMale: Boolean = false
    var isFemale: Boolean = false

    //Definim aquest companion object, per a fer les claus dels intents més segures.
    companion object {
        const val EXTRA_GEN = "extra_gen"
        const val EXTRA_HEIGHT = "extra_height"
        const val EXTRA_WEIGHT = "extra_weight"
        const val EXTRA_AGE = "extra_age"
    }

    //Això, bàsicament, li està indicant a la nostra app, que es el que volem carregar
    //tan sovint com es faci la creació de l'aplicacio.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //---------------------------------------------------------------------------------------------------

        /*Assignem per id els camps del layout, indicant quina mena de camp retorna la cerca als <>*/
        maleCard = findViewById<MaterialCardView>(R.id.seleccio_home)
        femaleCard = findViewById<MaterialCardView>(R.id.seleccio_dona)

        maleCard.setOnClickListener(::cardClick)
        femaleCard.setOnClickListener(::cardClick)

        inches = findViewById<Slider>(R.id.slider_inches)
        inchesNumber = findViewById<TextView>(R.id.inchesNumber)

        //---------------------------------------------------------------------------------------------------
        /*Afegim un event que indica que en quant es faci un canvi al scroll, activi aquest event,
        que bàsicament agafa el valor del slider i el parseja a string per mostrar-lo al text numéric.
        */
        inches.addOnChangeListener { it: Slider, value: Float, wasPerson: Boolean ->
            inchesNumber.text = "%.1f".format(it.value)
        }

        //----------------------------------------------------------------------------------------------------
        /*Aquí bàsicament fem el mateix pero per als botons, fent sumes i restes dels valors
        cada cop que es prem el botó de sumar o restar, sempre comprovant que no sigui 0 o superior al limit
        d'edat o pes.
        */
        weightNumber = findViewById<TextView>(R.id.weightNumbers)
        weightSumButton = findViewById<Button>(R.id.summonButtonWeight)
        weightRestButton = findViewById<Button>(R.id.restButonWeight)

        weightSumButton.setOnClickListener { it: View ->
            val numberParsed: Int = weightNumber.text.toString().toInt()
            weightNumber.text = if(checkLimits(weightNumber, true)) (numberParsed + 1).toString() else 250.toString()
        }
        weightRestButton.setOnClickListener{it: View ->
            val numberParsed = weightNumber.text.toString().toInt()
            weightNumber.text = if(checkLimits(weightNumber, false)) (numberParsed -1).toString() else 0.toString()
        }

        ageNumber = findViewById<TextView>(R.id.ageNumbers)
        ageSumButton = findViewById<Button>(R.id.summonButtonAge)

        ageRestButton = findViewById<Button>(R.id.restButonAge)
        ageSumButton.setOnClickListener { it: View ->
            val numberParsed: Int = ageNumber.text.toString().toInt()
            ageNumber.text = if(checkLimits(ageNumber, true)) (numberParsed + 1).toString() else 150.toString()
        }
        ageRestButton.setOnClickListener { it: View ->
            val numberParsed: Int = ageNumber.text.toString().toInt()
            ageNumber.text = if(checkLimits(ageNumber, false)) (numberParsed - 1).toString() else 0.toString()
        }

        //!! Aquestes funcions, fan servir una altre que es trobarà definida més a sota,
        //més comentada es clar!!!.

        /*Fem el binding (encara no es pot), així que agafem l'element que volem que faci el canvi d'activity.*/
        calculateButton=findViewById<CardView>(R.id.calculate)
        calculateButton.setOnClickListener (::changeView)
    }

    //--------------------------------------------------------------------------------------------

    /*Aquí, bàsicament estem dient que fer cada cop que es premi un dels valors
    corresponents al botó que s'ha pulsat. Alterna bàsicament, el color d'aquests
    comparant les referències a cada onclick.
    */
    fun cardClick(it: View) {
        if(it == maleCard) {
            femaleCard.setCardBackgroundColor(  ContextCompat.getColor(this, R.color.marine_blue))
            maleCard.setCardBackgroundColor(Color.GREEN)
            isMale=true
            isFemale=false
        } else {
            maleCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.marine_blue))
            femaleCard.setCardBackgroundColor(Color.GREEN)
            isFemale=true
            isMale=false
        }
    }

    //Aquí, bàsicament, estem definint la funció, que serà l'encarregada de fer que no puguem
    //sumar ni restar més dels límits. D'aquesta manera, evitem sobrecalculs i valors negatius./*
    fun checkLimits(field: TextView, increment: Boolean): Boolean {
        val numberParsed: Int = field.text.toString().toInt()
        return increment && numberParsed < 150 || !increment && numberParsed > 0
    }

    //Resumidament, estem definint l'intent. Que es una propietat que ens permet realitzar
    //el canvi d'ativitats.
    fun changeView(it: View) {
        val intent = Intent(this, BMIResults::class.java).apply {
            putExtra(EXTRA_GEN, isMale)
            putExtra(EXTRA_HEIGHT, inchesNumber.text)
            putExtra(EXTRA_WEIGHT, weightNumber.text)
            putExtra(EXTRA_AGE, ageNumber.text)
        }
        startActivity(intent)
        //Val, aquí li estem dient que, des d'aquesta activitat, que fa referència this, volem canviar a la classe/activitat BMIResults.
        //Un cop que tenim definida dita acció, fem el canvi.
    }
}