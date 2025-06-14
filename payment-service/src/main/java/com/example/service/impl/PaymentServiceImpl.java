package com.example.service.impl;

import com.example.dto.Pagination;
import com.example.dto.response.GetPayment;
import com.example.dto.response.ListPayments;
import com.example.exception.PaginationException;
import com.example.exception.PaymentNotFoundException;
import com.example.mapper.PaymentMapper;
import com.example.model.Payment;
import com.example.model.StatusPayment;
import com.example.repository.PaymentRepository;
import com.example.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    //все
    @Override
    public ListPayments getPayments(Authentication authentication, StatusPayment statusPayment, Long size, Long current) {
        List<Payment> payments = (List<Payment>) paymentRepository.findAll();

        List<GetPayment> filterPayments = new ArrayList<>();//отфильтрованный список

        //фильтрация
        for (Payment payment : payments) {
            if (statusPayment == null || statusPayment.equals(payment.getStatus())) {
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

    //все
    @Override
    public GetPayment getPaymentById(UUID paymentId, Authentication authentication) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Такой машины нет!"));

        return PaymentMapper.getPayment(payment);
    }

    @Override
    public GetPayment statusPaid(){

    }
}
