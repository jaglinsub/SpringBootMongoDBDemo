package com.thepracticaldeveloper.reactiveweb.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.SetupIntent;
import com.stripe.model.Subscription;
import com.thepracticaldeveloper.reactiveweb.domain.IntentSecrets;
import com.thepracticaldeveloper.reactiveweb.domain.PaymentDetails;
import com.thepracticaldeveloper.reactiveweb.domain.Profile;
import com.thepracticaldeveloper.reactiveweb.domain.SubscriptionDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);
    //@Value("${sk_test_SHHXKkoYptaSc74RuewRYJ0y00f8jkA7Tz}")
    private String secretKey = "sk_test_SHHXKkoYptaSc74RuewRYJ0y00f8jkA7Tz";

    public PaymentController() {
        Stripe.apiKey = secretKey;
    }

    @GetMapping("/intentsecret")
    @ResponseStatus(HttpStatus.CREATED)
    IntentSecrets getIntentToken() throws StripeException {
        Map<String, Object> paramsIntent = new HashMap<>();
        SetupIntent intent = SetupIntent.create(paramsIntent); // ... Fetch or create the SetupIntent

        log.info("client_secret=" + intent.getClientSecret());
        IntentSecrets intentSecrets = new IntentSecrets();
        intentSecrets.setSecret(intent.getClientSecret());
        return intentSecrets;
    }

    @PostMapping("/createSubscription")
    @ResponseStatus(HttpStatus.CREATED)
    SubscriptionDetails create(@RequestBody PaymentDetails paymentDetails) throws StripeException {

        Map<String, Object> customerParams = new HashMap<String, Object>();
        customerParams.put("payment_method", paymentDetails.getPaymentMethod());
        customerParams.put("name", paymentDetails.getName());
        customerParams.put("email", paymentDetails.getEmail());
        customerParams.put("phone", paymentDetails.getPhone());

        Map<String, String> address = new HashMap<String, String>();
        address.put("line1", "10300 devon street");
        address.put("postal_code", paymentDetails.getZipCode());
        customerParams.put("address", address);

        Map<String, String> invoiceSettings = new HashMap<String, String>();
        invoiceSettings.put("default_payment_method", paymentDetails.getPaymentMethod());
        customerParams.put("invoice_settings", invoiceSettings);

        Customer customer = Customer.create(customerParams);
        log.info("Customer JSON=" + customer.toJson());

        // Subscribe customer to a plan
        Map<String, Object> item = new HashMap<>();
        item.put("plan", "plan_GZBXYhjDEsZI3z");
        Map<String, Object> items = new HashMap<>();
        items.put("0", item);

        Map<String, Object> expand = new HashMap<>();
        expand.put("0", "latest_invoice.payment_intent");
        Map<String, Object> params = new HashMap<>();
        params.put("customer", customer.getId());
        params.put("items", items);
        params.put("expand", expand);
        Subscription subscription = Subscription.create(params);
        log.info("Subscription JSON=" + subscription.toJson());
        SubscriptionDetails subscriptionDetails = new SubscriptionDetails();
        subscriptionDetails.setId(subscription.getId());
        subscriptionDetails.setCustomerId(subscription.getCustomer());
        return subscriptionDetails;
    }
}
