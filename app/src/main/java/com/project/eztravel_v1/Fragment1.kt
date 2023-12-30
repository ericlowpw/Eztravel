package com.project.eztravel_v1

import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.project.eztravel_v1.databinding.ActivityMainBinding
import com.project.eztravel_v1.databinding.Fragment1Binding
//import kotlinx.android.synthetic.main.activity_main.*
import org.mariuszgromada.math.mxparser.Expression
import java.text.DecimalFormat


// Calculator : https://github.com/JahidHasanCO/Calculator-App

// Currency Converter : https://www.youtube.com/watch?v=pMZjgL9l4oY


class Fragment1 : Fragment() {

    private lateinit var binding : Fragment1Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                savedInstanceState: Bundle?): View {


    binding = Fragment1Binding.inflate(inflater, container, false)
    return binding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Perform actions after the view has been created
        val imgBtn0: AppCompatButton = binding.button0
        val imgBtn1: AppCompatButton = binding.button1
        val imgBtn2: AppCompatButton = binding.button2
        val imgBtn3: AppCompatButton = binding.button3
        val imgBtn4: AppCompatButton = binding.button4
        val imgBtn5: AppCompatButton = binding.button5
        val imgBtn6: AppCompatButton = binding.button6
        val imgBtn7: AppCompatButton = binding.button7
        val imgBtn8: AppCompatButton = binding.button8
        val imgBtn9: AppCompatButton = binding.button9
        val imgBtnDot: AppCompatButton = binding.buttonDot
        val imgBtnClear: AppCompatButton = binding.buttonClear
        val imgBtnBracket: AppCompatButton = binding.buttonBracket
        val imgBtnBracketR: AppCompatButton = binding.buttonBracketR
        val imgBtnCroxx: AppCompatButton = binding.buttonCroxx
        val imgBtnDivision: AppCompatButton = binding.buttonDivision
        val imgBtnMultiple: AppCompatButton = binding.buttonMultiply
        val imgBtnSubstraction: AppCompatButton = binding.buttonSubtraction
        val imgBtnAddition: AppCompatButton = binding.buttonAddition
        val imgBtnEqual: AppCompatButton = binding.buttonEquals


        imgBtn0.setOnClickListener {
            //Toast.makeText(requireContext(), "Btn 0 is clicked", Toast.LENGTH_SHORT).show()
            binding.input.text = addToInputText("0")
        }
        imgBtn1.setOnClickListener {
            binding.input.text = addToInputText("1")
        }
        imgBtn2.setOnClickListener {
            binding.input.text =  addToInputText("2")
        }
        imgBtn3.setOnClickListener {
            binding.input.text =  addToInputText("3")
        }
        imgBtn4.setOnClickListener {
            binding.input.text =  addToInputText("4")
        }
        imgBtn5.setOnClickListener {
            binding.input.text =  addToInputText("5")
        }
        imgBtn6.setOnClickListener {
            binding.input.text =  addToInputText("6")
        }
        imgBtn7.setOnClickListener {
            binding.input.text =  addToInputText("7")
        }
        imgBtn8.setOnClickListener {
            binding.input.text =  addToInputText("8")
        }
        imgBtn9.setOnClickListener {
            binding.input.text =  addToInputText("9")
        }
        imgBtnDot.setOnClickListener {
            binding.input.text = addToInputText(".")
        }
        imgBtnClear.setOnClickListener {
                val removedLast = binding.input.text.toString().dropLast(1)
                binding.input.text = removedLast
        }
        imgBtnBracket.setOnClickListener {
                binding.input.text = addToInputText("(")
        }
        imgBtnBracketR.setOnClickListener {
                binding.input.text = addToInputText(")")
        }
        imgBtnCroxx.setOnClickListener {
                binding.input.text = ""
                binding.output.text = ""
        }
        imgBtnDivision.setOnClickListener {
                binding.input.text = addToInputText("÷") // ALT + 0247
        }
        imgBtnMultiple.setOnClickListener {
                binding.input.text = addToInputText("×") // ALT + 0215
        }
        imgBtnSubstraction.setOnClickListener {
            binding.input.text = addToInputText("-")
        }
        imgBtnAddition.setOnClickListener {
            binding.input.text = addToInputText("+")
        }
        imgBtnEqual.setOnClickListener {
            showResult()
        }
    }

    private fun showResult() {
        try {
            val expression = getInputExpression()
            val result = Expression(expression).calculate()
            if (result.isNaN()) {
                // Show Error Message
                binding.output.text = ""
                binding.output.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            } else {
                // Show Result
                binding.output.text = DecimalFormat("0.######").format(result).toString()
                binding.output.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }
        } catch (e: Exception) {
            // Show Error Message
            binding.output.text = ""
            binding.output.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        }
    }

    private fun getInputExpression(): String {
        var expression = binding.input.text.replace(Regex("÷"), "/")
        expression = expression.replace(Regex("×"), "*")
        return expression
    }


    private fun addToInputText(buttonValue: String): String {
        return binding.input.text.toString() + "" + buttonValue
    }

    // Other lifecycle methods can be overridden as needed
}

