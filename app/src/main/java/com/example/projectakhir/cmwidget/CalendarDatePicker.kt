import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CalendarDatePicker(
    label: String,
    selectedDate: String,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = remember { Calendar.getInstance() }

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val updatedOnDateSelected = rememberUpdatedState(onDateSelected)

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                GregorianCalendar(selectedYear, selectedMonth, selectedDay).time
            )
            updatedOnDateSelected.value(formattedDate)
        },
        year,
        month,
        day
    )

    OutlinedTextField(
        value = selectedDate,
        onValueChange = {}, // Read-only, no direct change
        label = { Text(label, color = Color.White) },
        modifier = Modifier.fillMaxWidth(),
        readOnly = true,
        trailingIcon = {
            androidx.compose.material3.IconButton(onClick = { datePickerDialog.show() }) {
                Text("Pilih", color = Color.White)
            }
        }
    )
}
