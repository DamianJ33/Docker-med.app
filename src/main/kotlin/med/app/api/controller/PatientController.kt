package med.app.api.controller


import med.app.api.model.Patient
import med.app.api.service.PatientService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/patients")
class PatientController(private val service: PatientService) {

    @GetMapping
    fun getAllPatients(): List<Patient> = service.getAllPatients()

    @GetMapping("/{email}")
    fun getPatientByEmail(@PathVariable email: String): Patient? = service.getPatientByEmail(email)

    @PostMapping
    fun addPatient(@RequestBody patient: Patient): Patient = service.addPatient(patient)


    @PostMapping("/register")
    fun registerPatient(@RequestBody patient: Patient): ResponseEntity<Any> {
        return try {
            service.registerPatient(patient)
            ResponseEntity.ok(mapOf("message" to "konto zostało utworzone"))
        } catch (e: Exception) {
            println("Błąd przy rejestracji: ${e.message}")
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(mapOf("message" to "Rejestracja nieudana: ${e.message}"))
        }
    }

    @GetMapping("/login")
    fun loginPatient(
        @RequestParam("email") email: String,
        @RequestParam("password") password: String
    ): ResponseEntity<Any> {
        return try {
            val patient = service.loginPatient(email, password)
            if (patient != null) {
                ResponseEntity.ok(mapOf("message" to "Logowanie udane", "patient" to patient))
            } else {
                ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(mapOf("message" to "Nieprawidłowy email lub hasło"))
            }
        } catch (e: Exception) {
            println("Błąd przy logowaniu: ${e.message}")
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(mapOf("message" to "Błąd serwera podczas logowania"))
        }
    }

}


