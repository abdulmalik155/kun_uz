package kunuz.uz.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SmsSenderService {
    @Value("${sms.eskiz.mail}")
    private String email;

    @Value("${sms.eskiz.password}")
    private String password;

    @Autowired
    private RestTemplate restTemplate;



}
