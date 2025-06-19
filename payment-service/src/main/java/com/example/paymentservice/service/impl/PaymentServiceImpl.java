package com.example.paymentservice.service.impl;

import com.example.paymentservice.dto.Pagination;
import com.example.paymentservice.dto.response.GetPayment;
import com.example.paymentservice.dto.response.ListPayments;
import com.example.paymentservice.dto.response.SuccessResponse;
import com.example.paymentservice.exception.PaginationException;
import com.example.paymentservice.exception.PaymentNotFoundException;
import com.example.paymentservice.kafka.KafkaEvent;
import com.example.paymentservice.kafka.KafkaSenderPayment;
import com.example.paymentservice.mapper.PaymentMapper;
import com.example.paymentservice.model.Payment;
import com.example.paymentservice.model.StatusPayment;
import com.example.paymentservice.repository.PaymentRepository;
import com.example.paymentservice.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import com.example.paymentservice.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final KafkaSenderPayment kafkaSenderPayment;

    @Autowired
    private HttpServletRequest request;

    @Override
    public ListPayments getAllPayments(StatusPayment statusPayment, Long size, Long current) {
        List<Payment> payments = (List<Payment>) paymentRepository.findAll();

        List<GetPayment> filterPayments = new ArrayList<>();//отфильтрованный список

        //фильтрация
        for (Payment payment : payments) {
            if (statusPayment == null || statusPayment.equals(payment.getStatusPayment())) {
                filterPayments.add(PaymentMapper.getPayment(payment));
            }
        }

        //пагинация
        //size - количество на одной странице (размер страницы), count - всего страниц, current - текущая
        int countPayments = filterPayments.size();//всего переводов
        int countPage = (int) Math.ceil((double) countPayments / size);//всего страниц

        if (countPayments > 0 && current > countPage) {
            throw new PaginationException("Вы хотите посмотреть несуществующую страницу!");
        }

        List<GetPayment> currentPayments = filterPayments.stream().skip((current - 1) * size).limit(size).toList(); //список переводов на данной странице

        Pagination pagination = new Pagination(size, countPayments, current);
        return PaymentMapper.listPayments(currentPayments, pagination);
    }

    @Override
    public ListPayments getPayments(StatusPayment statusPayment, Long size, Long current) {
        UUID userId = JwtUtils.getUserIdFromRequest(request);

        List<Payment> payments = (List<Payment>) paymentRepository.findAll();

        List<GetPayment> filterPayments = new ArrayList<>();//отфильтрованный список

        //фильтрация
        for (Payment payment : payments) {
            if (userId.equals(payment.getUserId()) && (statusPayment == null || statusPayment.equals(payment.getStatusPayment()))) {
                filterPayments.add(PaymentMapper.getPayment(payment));
            }
        }

        //пагинация
        //size - количество на одной странице (размер страницы), count - всего страниц, current - текущая
        int countPayments = filterPayments.size();//всего переводов
        int countPage = (int) Math.ceil((double) countPayments / size);//всего страниц

        if (countPayments > 0 && current > countPage) {
            throw new PaginationException("Вы хотите посмотреть несуществующую страницу!");
        }

        List<GetPayment> currentPayments = filterPayments.stream().skip((current - 1) * size).limit(size).toList(); //список переводов на данной странице

        Pagination pagination = new Pagination(size, countPayments, current);
        return PaymentMapper.listPayments(currentPayments, pagination);
    }

    @Override
    public GetPayment getPaymentById(UUID paymentId) {
        UUID userId = JwtUtils.getUserIdFromRequest(request);

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Такого платежа нет!"));

        if (!userId.equals(payment.getUserId())) {
            throw new PaymentNotFoundException("Это не ваш платеж!");
        }

        return PaymentMapper.getPayment(payment);
    }

    @Override
    public SuccessResponse doPayment(UUID paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Такого платежа нет!"));

        payment.setStatusPayment(StatusPayment.PAID);
        payment.setEditDate(LocalDateTime.now());
        paymentRepository.save(payment);

        //отправка в кафку события на смену статусов у машины и у брони на арендована
//kafkaSenderPayment.doPayment();
        return new SuccessResponse("Платёж успешно оплачен!");
    }

    @Override
    public SuccessResponse cancelPayment(UUID paymentId) {
        UUID userId = JwtUtils.getUserIdFromRequest(request);

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Такого платежа нет!"));

        if (!userId.equals(payment.getUserId())) {
            throw new PaymentNotFoundException("Это не ваш платёж!");
        }

        payment.setStatusPayment(StatusPayment.CANCELLED);
        payment.setEditDate(LocalDateTime.now());
        paymentRepository.save(payment);

        kafkaSenderPayment.cancelPayment(new KafkaEvent(null, payment.getBookingId(), paymentId, userId));
        return new SuccessResponse("Неоплаченный платёж успешно отменён!");
    }
}
