package com.example.paymentservice.controller;

import com.example.paymentservice.dto.response.GetPayment;
import com.example.paymentservice.dto.response.ListPayments;
import com.example.paymentservice.dto.response.SuccessResponse;
import com.example.paymentservice.model.StatusPayment;
import com.example.paymentservice.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.UUID;

@RestController
@RequestMapping("payment")
@RequiredArgsConstructor
@Tag(name = "Payment")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Получение списка платежей", description = "для админа")
    public ListPayments getAllPayments(@RequestParam(value = "statusPayment", required = false) StatusPayment statusPayment, Long size, Long current) {
        return paymentService.getAllPayments(statusPayment, size, current);
    }

    @GetMapping()
    @Operation(summary = "Получение списка своих платежей")
    public ListPayments getPayments(@RequestParam(value = "statusPayment", required = false) StatusPayment statusPayment, Long size, Long current) {
        return paymentService.getPayments(statusPayment, size, current);
    }

    @GetMapping("/{paymentId}")
    @Operation(summary = "Получение данных о платеже")
    public GetPayment getPaymentById(@PathVariable(name = "paymentId") UUID paymentId) {
        return paymentService.getPaymentById(paymentId);
    }

    @PostMapping("/{paymentId}")
    @Operation(summary = "Оплата платежа")
    public SuccessResponse statusPaid(@PathVariable(name = "paymentId") UUID paymentId) {
        return paymentService.statusPaid(paymentId);
    }

    @PutMapping("/{paymentId}")
    @Operation(summary = "Отмена неоплаченного платежа")
    public SuccessResponse cancelPayment(@PathVariable(name = "paymentId") UUID paymentId) {
        return paymentService.cancelPayment(paymentId);
    }
}
