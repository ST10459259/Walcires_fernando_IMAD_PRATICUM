package com.walciresFernando
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.walciresFernando.databinding.ActivityDetailViewBinding

class DetailViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using ViewBinding
        binding = ActivityDetailViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve data from the intent
        val days = intent.getStringArrayExtra("days") ?: arrayOf()
        val morningSpending = intent.getFloatArrayExtra("morningSpending") ?: floatArrayOf()
        val afternoonSpending = intent.getFloatArrayExtra("afternoonSpending") ?: floatArrayOf()
        val expenseNotes = intent.getStringArrayExtra("expenseNotes") ?: arrayOf()

        // Build detailed information string
        val details = StringBuilder()
        var highestExpense = 0f
        var highestExpenseDay = ""

        for (i in days.indices) {
            val totalSpending = morningSpending[i] + afternoonSpending[i]
            if (totalSpending > highestExpense) {
                highestExpense = totalSpending
                highestExpenseDay = days[i]
            }

            val averageSpending = totalSpending / 2 // Morning and afternoon spending per day
            details.append("Day: ${days[i]}\n")
            details.append("Morning Spending: R${"%.2f".format(morningSpending[i])}\n")
            details.append("Afternoon Spending: R${"%.2f".format(afternoonSpending[i])}\n")
            details.append("Notes: ${expenseNotes[i]}\n")
            details.append("Total Spending: R${"%.2f".format(totalSpending)}\n")
            details.append("Average Spending: R${"%.2f".format(averageSpending)}\n\n")
        }

        // Update the TextView with details
        binding.txtDetails.text = details.toString()

        // Button to display the day with the highest expenses
        binding.btnHighestExpense.setOnClickListener {
            binding.txtResult.text = "Day with Highest Expenses: $highestExpenseDay (R${"%.2f".format(highestExpense)})"
        }

        // Button to navigate back to the main screen
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}
