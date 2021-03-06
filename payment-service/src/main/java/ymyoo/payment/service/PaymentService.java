package ymyoo.payment.service;

import ymyoo.payment.dto.PaymentRequest;
import ymyoo.payment.entity.ReservedPayment;

public interface PaymentService {
    ReservedPayment reservePayment(PaymentRequest paymentRequest);

    void confirmPayment(Long id);

    void cancelPayment(Long id);
}
