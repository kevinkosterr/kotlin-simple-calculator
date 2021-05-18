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
        /**
         * This function is called when any digit is pressed.
         * It will output the digit on the screen.
         */
        calcInput.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View) {
        /**
         * This function is called when the Clear button is pressed.
         * The function clears all output on the screen.
         */
        calcInput.text = null
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View) {
        /**
         * This function is called when the Decimal button is pressed.
         * As you may be able to guess, this outputs a decimal to the screen.
         */
        if (lastNumeric && !lastDot) {
            calcInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onEquals(view: View) {
        /**
         * This function is called when the Equals button is pressed.
         * It determines which operator is used and it outputs the answer on screen.
         */
        if (lastNumeric) {
            var calcValue = calcInput.text.toString()
            var prefix = ""

            try {
                /**
                 * we're doing this to exclude the first '-', because this isn't supposed to be used
                 * as an operator.
                 */
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
        /**
         * This function gets called when an operator button is pressed.
         * It outputs the operator onto the screen.
         */
        // we want to check if an operator is already added, because we don't allow more than one operator at a time.
        // we also want to check whether or not the last input is numeric or not.
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