package cl.ufro.dci.cardiocare.message.controller;

import cl.ufro.dci.cardiocare.message.domain.Message;
import cl.ufro.dci.cardiocare.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mensajes")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService service;

    @GetMapping("/paciente/{patientId}")
    public List<Message> historial(@PathVariable Long patientId) {
        return service.getMessagesByPatient(patientId);
    }

    @PostMapping
    public Message enviar(@RequestBody Message msg) {
        return service.sendMessage(msg);
    }

    @GetMapping("/no-leidos")
    public long noLeidos() {
        return service.countUnread();
    }
}

