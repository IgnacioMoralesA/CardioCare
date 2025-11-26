package cl.ufro.dci.cardiocare.message.controller;

import cl.ufro.dci.cardiocare.message.domain.Message;
import cl.ufro.dci.cardiocare.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/mensajes")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService service;

    // Listar todos los mensajes
    @GetMapping
    public List<Message> listarTodos() {
        return service.getAll();
    }

    // Historial de un paciente
    @GetMapping("/paciente/{patientId}")
    public List<Message> historial(@PathVariable Long patientId) {
        return service.getMessagesByPatient(patientId);
    }

    // Enviar mensaje
    @PostMapping
    public Message enviar(@RequestBody Message msg) {
        return service.sendMessage(msg);
    }

    // Contar no leídos
    @GetMapping("/no-leidos")
    public long noLeidos() {
        return service.countUnread();
    }
}
