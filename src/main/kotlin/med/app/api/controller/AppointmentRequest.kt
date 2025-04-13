package med.app.api.controller

data class AppointmentRequest(
    val date: String,
    val time: String,
    val reason: String
)