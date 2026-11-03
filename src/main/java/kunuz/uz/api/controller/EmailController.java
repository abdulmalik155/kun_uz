package kunuz.uz.api.controller;

import kunuz.uz.api.dto.request.MessageDTO;
import kunuz.uz.api.service.EmailSendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/email-send")
public class EmailController {

    @Autowired
    private EmailSendingService service;

    @PostMapping(value = "/simple")
    public ResponseEntity<?> sendEmail(@RequestBody MessageDTO dto){
        service.sendSimpleMessage(dto);
        return ResponseEntity.ok().build();
    }

/*    @PostMapping(value = "/html-formatted")
    public ResponseEntity<?> sendEmailHtmlFormatted(@RequestBody MessageDTO dto){
        service.sendEmailHtmlFormatted(dto);
        return ResponseEntity.ok().build();
    }*/
}
