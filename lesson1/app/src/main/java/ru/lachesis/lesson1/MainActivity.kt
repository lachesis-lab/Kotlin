package ru.lachesis.lesson1

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    val currentPersonTag: String = "lesson1.currentPerson"
    lateinit var currentPersonText: String
    var persons: ArrayList<Person> = ArrayList()
    var currentIndex: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button: MaterialButton = findViewById(R.id.button)
        val view: TextView = findViewById(R.id.tv)

        val petya = Person("Petya", 28)
        persons.add(petya)
        val vasya = petya.copy("Vasya", 20)
        persons.add(vasya)
        persons.add(petya.copy("Kolya", 32))
        currentPersonText = persons.get(0).toString()
        if (savedInstanceState != null)
            view.text = savedInstanceState.getString(currentPersonTag)
        else
            view.text = persons.get(0).toString()
        button.setOnClickListener {
            if (currentIndex < persons.size - 1)
                currentIndex = currentIndex + 1
            else currentIndex = 0
            currentPersonText = persons.get(currentIndex).toString()
            view.text = currentPersonText
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(currentPersonTag, currentPersonText)
    }
}

data class Person(val name: String, var age: Int) {
    override fun toString(): String = "${this.name} : ${this.age}"
}

