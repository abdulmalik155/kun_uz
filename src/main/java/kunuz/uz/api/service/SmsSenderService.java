package kunuz.uz.api.service;

import kunuz.uz.api.dto.SmsAuthDTO;
import kunuz.uz.api.dto.SmsSendResponseDTO;
import kunuz.uz.api.exceptions.AppBadException;
import kunuz.uz.api.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class SmsSenderService {
//    @Value("${sms.eskiz.mail}")
//    private String email;
//
//    @Value("${sms.eskiz.password}")
//    private String password;

    @Value("${sms.eskiz.url}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;


    public void sendRegistrationSms(String phone, String message) {
        sendSms(phone, message);
    }


    private String login() {
        SmsAuthDTO dto = new SmsAuthDTO();
        dto.setUsername("998998707128");
        dto.setPassword("12345");
        try {
            return restTemplate.postForObject(url + "/auth/login", dto, String.class);
        } catch (RestClientException | NullPointerException e) {
            throw new RuntimeException();
        }
    }

    private void sendSms(String phone, String message) {
        Map<String, String> body = new LinkedHashMap<>();
        body.put("phone", phone);
        body.put("message", message + " " + RandomUtil.fiveDigit());
        body.put("from", "4546");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("User-Id", login());

        RequestEntity<Map<String, String>> request = RequestEntity
                .post(url + "/message/sms/send")
                .headers(headers)
                .body(body);
        System.out.println("keldik");
        try {
            ResponseEntity<SmsSendResponseDTO> response = restTemplate.exchange(request, SmsSendResponseDTO.class);

            System.out.println("Sms send response: status " + response.getStatusCode());
            SmsSendResponseDTO responseDTO = response.getBody();
            System.out.println("Sms send id:  " + responseDTO.getId());
            System.out.println("Sms send message:  " + responseDTO.getMessage());
            System.out.println("Sms send status:  " + responseDTO.getStatus());

        } catch (RestClientException | NullPointerException | AppBadException e) {
            throw new AppBadException(e.toString());
        }

    }

}
