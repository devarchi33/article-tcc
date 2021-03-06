package ymyoo.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ymyoo.payment.dto.ParticipantLink;
import ymyoo.payment.dto.PaymentRequest;
import ymyoo.payment.entity.ReservedPayment;
import ymyoo.payment.service.PaymentService;

import java.net.URI;
import java.util.Date;

import static ymyoo.payment.service.impl.PaymentServiceImpl.TIMEOUT;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentRestController {

    private PaymentService paymentService;

    @Autowired
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<ParticipantLink> tryPayment(@RequestBody PaymentRequest paymentRequest) {
        final ReservedPayment reservedPayment = paymentService.reservePayment(paymentRequest);

        final ParticipantLink participantLink = buildParticipantLink(reservedPayment.getId(), reservedPayment.getCreated());

        return new ResponseEntity<>(participantLink, HttpStatus.CREATED);
    }

    private ParticipantLink buildParticipantLink(final Long id, final Date created) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        final long expires = created.getTime() + TIMEOUT;

        return new ParticipantLink(location, new Date(expires));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> confirmPayment(@PathVariable Long id) {
        try {
            paymentService.confirmPayment(id);
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelPayment(@PathVariable Long id) {
        paymentService.cancelPayment(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
