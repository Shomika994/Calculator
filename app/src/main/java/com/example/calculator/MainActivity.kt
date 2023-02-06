package com.example.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var display: TextView? = null
    private var lastDigit = false
    private var lastDot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.tvResult)
    }

    fun onClear(view: View) {
        display?.text = ""
    }

    fun onDigit(view: View) {
        display?.append((view as Button).text.toString())
        lastDigit = true
        lastDot = false
    }

    fun onDot(view: View) {
        if (lastDigit || !lastDot) {
            display?.append(".")
            lastDigit = false
            lastDot = true
        }
    }

    private fun isOperatorCalled(view: String): Boolean {
        return if (view.startsWith("-")) {
            false
        } else
            view.contains("/")
                    || view.contains("*")
                    || view.contains("+")
                    || view.contains("-")
    }

    fun onOperator(view: View) {
        display?.text?.let {
            if (!isOperatorCalled(it.toString())) {
                display?.append((view as Button).text)
                lastDigit = false
                lastDot = false
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun onEqual(view: View) {
        var displayString = display?.text.toString()
        var prefix = ""
        var splitOne: String
        var splitTwo: String
        try {
            if (displayString.startsWith("-")) {
                prefix = "-"
                displayString = displayString.substring(1)
            }

            if (displayString.contains("-")) {
                val subStrings = displayString.split("-")
                splitOne = subStrings[0]
                splitTwo = subStrings[1]

                if (prefix.isNotEmpty()) {
                    splitOne = prefix + splitOne
                }

                val math =
                    removeLastTwoIndices((splitOne.toDouble() - splitTwo.toDouble()).toString())

                display?.text = math

            } else if (displayString.contains("+")) {
                val subStrings = displayString.split("+")
                splitOne = subStrings[0]
                splitTwo = subStrings[1]

                if (prefix.isNotEmpty()) {
                    splitOne = prefix + splitOne
                }

                val math =
                    removeLastTwoIndices((splitOne.toDouble() + splitTwo.toDouble()).toString())

                display?.text = math

            } else {

                if (displayString.startsWith("")) {
                    prefix = "-"
                    displayString.substring(1)
                }
                if (displayString.contains("*")) {
                    val subStrings = displayString.split("*")
                    splitOne = subStrings[0]
                    splitTwo = subStrings[1]

                    if (prefix.isNotEmpty()) {
                        splitOne = prefix + splitOne
                    }

                    val math =
                        removeLastTwoIndices((splitOne.toDouble() * splitTwo.toDouble()).toString())

                    display?.text = math
                } else {

                    if (displayString.startsWith("-")) {
                        prefix = "-"
                        displayString.substring(1)
                    }
                }
                if (displayString.contains("/")) {
                    val subStrings = displayString.split("/")
                    splitOne = subStrings[0]
                    splitTwo = subStrings[1]

                    if (prefix.isNotEmpty()) {
                        splitOne = prefix + splitOne
                    }

                    val math =
                        removeLastTwoIndices((splitOne.toDouble() / splitTwo.toDouble()).toString())

                    display?.text = math
                }
            }


        } catch (e: ArithmeticException) {
            Log.d("MainActivity", e.message.orEmpty())
        }
    }


    private fun isNegative(tvString: String): Boolean {
        return tvString.startsWith("-")
    }

    private fun removeLastTwoIndices(value: String): String {
        var result = value
        if (value.contains(".0")) {
            result = value.substring(0, value.length - 2)
        }
        return result
    }
}

