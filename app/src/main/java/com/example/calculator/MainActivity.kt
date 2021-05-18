package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view: View) {
        calcInput.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View) {
        calcInput.text = null
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            calcInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onEquals(view: View) {
        if (lastNumeric) {
            var calcValue = calcInput.text.toString()
            var prefix = ""

            try {
                if (calcValue.startsWith('-')) {
                    prefix = "-"
                    calcValue = calcValue.substring(1)
                }

                var operator = ""
                if (calcValue.contains('-')) {
                    operator = "-"
                } else if (calcValue.contains('+')) {
                    operator = "+"
                } else if (calcValue.contains('*')) {
                    operator = "*"
                } else if (calcValue.contains('/')) {
                    operator = "/"
                }

                val values = calcValue.split(operator)
                var first = values[0]
                var second = values[1]
                if (prefix.isNotEmpty()) {
                    first = prefix + first
                }

                when (operator) {
                    "+" -> calcInput.text = (first.toDouble() + second.toDouble()).toString()
                    "-" -> calcInput.text = (first.toDouble() - second.toDouble()).toString()
                    "*" -> calcInput.text = (first.toDouble() * second.toDouble()).toString()
                    "/" -> calcInput.text = (first.toDouble() / second.toDouble()).toString()
                }


            } catch (e: ArithmeticException) {
                Toast.makeText(this, "An Unknown Error Occured.", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }

        }
    }

    fun onOperator(view: View) {
        if (lastNumeric && !isOperatorAdded(calcInput.text.toString())) {
            calcInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains('/') || value.contains('*') || value.contains('-') || value.contains('+')
        }
    }

}