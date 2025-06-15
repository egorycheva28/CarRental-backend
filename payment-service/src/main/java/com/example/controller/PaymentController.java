package com.example.controller;

import com.example.dto.response.GetPayment;
import com.example.dto.response.ListPayments;
import com.example.dto.response.SuccessResponse;
import com.example.model.StatusPayment;
import com.example.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // Хранение записей по платежам с их статусами ("Новый платёж", "Оплачен", "Отменён")

    //Получение списка платежей (с пагинацией)
    //Получение списка платежей на оплату
//все
    @GetMapping()
    public ListPayments getPayments(Authentication authentication, StatusPayment statusPayment, Long size, Long current) {
        return paymentService.getPayments(authentication, statusPayment, size, current);
    }

    //Оплата платежа
//админ
   /* @PutMapping("/{id}")
    public SuccessResponse statusPaid(@PathVariable(name = "id") UUID carId, StatusPayment status) {
        return paymentService.statusPaid(carId, status);
    }*/
    //Отмена неоплаченного платежа

    //Получение данных о платеже
    //все
    @GetMapping("/{id}")
    public GetPayment getPaymentById(@PathVariable(name = "id") UUID carId, Authentication authentication) {
        return paymentService.getPaymentById(carId, authentication);
    }

    //Отправка данных в сервис аренды о проведении платежа

}
