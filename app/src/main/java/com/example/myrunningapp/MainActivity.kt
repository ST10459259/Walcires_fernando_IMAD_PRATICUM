package WalciresFernando

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.workshop.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // Arrays to store spending data
    private val morningSpending = mutableListOf<Float>()
    private val afternoonSpending = mutableListOf<Float>()
    private val expenseNotes = mutableListOf<String>()
    private val days = mutableListOf<String>("2024-10-07", "2024-10-08", "2024-10-09", "T2024-10-10", "2024-10-11", "2024-10-12", "2024-10-13")

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Submit data
        binding.btnSubmit.setOnClickListener {
            val morningSpend = binding.edtMorningSpend.text.toString().toFloatOrNull()
            val afternoonSpend = binding.edtAfternoonSpend.text.toString().toFloatOrNull()
            val note = binding.edtExpenseNote.text.toString().trim()

            if (morningSpend == null || afternoonSpend == null || note.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show()
            } else {
                morningSpending.add(morningSpend)
                afternoonSpending.add(afternoonSpend)
                expenseNotes.add(note)

                Toast.makeText(this, "Data Saved!", Toast.LENGTH_SHORT).show()

                // Clear the input fields
                binding.edtMorningSpend.text.clear()
                binding.edtAfternoonSpend.text.clear()
                binding.edtExpenseNote.text.clear()
            }
        }

        // Clear fields
        binding.btnClear.setOnClickListener {
            binding.edtMorningSpend.text.clear()
            binding.edtAfternoonSpend.text.clear()
            binding.edtExpenseNote.text.clear()
            Toast.makeText(this, "Fields Cleared!", Toast.LENGTH_SHORT).show()
        }

        // Calculate average spending
        binding.btnCalculateAverage.setOnClickListener {
            if (morningSpending.isEmpty() || afternoonSpending.isEmpty()) {
                Toast.makeText(this, "No data available!", Toast.LENGTH_SHORT).show()
            } else {
                val totalSpending = morningSpending.sum() + afternoonSpending.sum()
                val averageSpending = totalSpending / (morningSpending.size + afternoonSpending.size)
                binding.txtAverage.text = "Average Spending: R${"%.2f".format(averageSpending)}"
            }
        }

        // View details
        binding.btnViewDetails.setOnClickListener {
            if (morningSpending.isEmpty() || afternoonSpending.isEmpty() || expenseNotes.isEmpty()) {
                Toast.makeText(this, "No data to display!", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, DetailViewActivity::class.java)
                intent.putExtra("days", days.toTypedArray())
                intent.putExtra("morningSpending", morningSpending.toFloatArray())
                intent.putExtra("afternoonSpending", afternoonSpending.toFloatArray())
                intent.putExtra("expenseNotes", expenseNotes.toTypedArray())
                startActivity(intent)
            }
        }
    }
}
