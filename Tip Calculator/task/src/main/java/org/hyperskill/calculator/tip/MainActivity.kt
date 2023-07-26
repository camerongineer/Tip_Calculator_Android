package org.hyperskill.calculator.tip

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var editText: EditText
    lateinit var seekBar: SeekBar
    lateinit var tipPercentTextView: TextView
    lateinit var billValueTextView: TextView
    lateinit var tipAmountTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText = findViewById(R.id.edit_text)
        seekBar = findViewById(R.id.seek_bar)
        tipPercentTextView = findViewById(R.id.tip_percent_tv)
        billValueTextView = findViewById(R.id.bill_value_tv)
        tipAmountTextView = findViewById(R.id.tip_amount_tv)
        initEditText()
        initSeekBar()
    }

    private fun initEditText() {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!isEditTextFilled()) {
                    wipeTextViews()
                } else {
                    tipPercentTextView.text = String.format("Tip: %d%%", seekBar.progress)
                    val billValue = s.toString().toDoubleOrNull() ?: 0.0
                    billValueTextView.text = String.format("Bill Value: $%.2f", billValue)
                    val tipAmount = billValue * (seekBar.progress.toDouble() / 100)
                    tipAmountTextView.text = String.format("Tip Amount: $%.2f", tipAmount)
                }
            }
        })
    }

    private fun initSeekBar() {
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (!isEditTextFilled()) {
                    wipeTextViews()
                } else {
                    tipPercentTextView.text = String.format("Tip: %d%%", progress)
                    val billValue = editText.text.toString().toDoubleOrNull() ?: 0.0
                    val tipAmount = billValue * (seekBar?.progress.toString().toDouble() / 100)
                    tipAmountTextView.text = String.format("Tip Amount: $%.2f", tipAmount)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun wipeTextViews() {
        tipPercentTextView.text = ""
        billValueTextView.text = ""
        tipAmountTextView.text = ""
    }

    private fun isEditTextFilled(): Boolean {
        return editText.text.isNotEmpty() &&
                editText.text.toString().toDoubleOrNull() != null &&
                editText.text.toString().toDouble() > 0
    }
}