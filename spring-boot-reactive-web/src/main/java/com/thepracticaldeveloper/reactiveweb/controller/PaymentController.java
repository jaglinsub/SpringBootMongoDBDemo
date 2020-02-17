package com.thepracticaldeveloper.reactiveweb.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.thepracticaldeveloper.reactiveweb.domain.*;
import com.thepracticaldeveloper.reactiveweb.repository.ParentMongoReactiveRepository;
import com.thepracticaldeveloper.reactiveweb.repository.UserMongoReactiveRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sun.tools.jconsole.JConsole;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);
    //@Value("${sk_test_SHHXKkoYptaSc74RuewRYJ0y00f8jkA7Tz}")
    private String secretKey = "sk_test_SHHXKkoYptaSc74RuewRYJ0y00f8jkA7Tz";
    private UserMongoReactiveRepository userMongoReactiveRepository;
    private ParentMongoReactiveRepository parentMongoReactiveRepository;

    public PaymentController(final UserMongoReactiveRepository userMongoReactiveRepository, final ParentMongoReactiveRepository parentMongoReactiveRepository) {
        this.userMongoReactiveRepository = userMongoReactiveRepository;
        this.parentMongoReactiveRepository = parentMongoReactiveRepository;
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

    @GetMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    void getProducts() throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("limit", 3);

        ProductCollection products = Product.list(params);
        log.info("ProductCollection=" + products.toJson());
    }

    @GetMapping("/plans")
    @ResponseStatus(HttpStatus.CREATED)
    Flux<PaymentPlans>  getPlans() throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("limit", 10);

        PlanCollection plans = Plan.list(params);
        log.info("Plan Collection=" + plans.toJson());
        return getPlans(plans);
    }

    @GetMapping("/planbyproductid")
    @ResponseStatus(HttpStatus.CREATED)
    Flux<PaymentPlans> getPlanByProductId(final @RequestParam(name = "id") String id) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("limit", 10);
        params.put("product", id);

        PlanCollection plans = Plan.list(params);
        return getPlans(plans);
    }

    @GetMapping("/subscription")
    @ResponseStatus(HttpStatus.CREATED)
    SubscriptionDetails getSubscription(final @RequestParam(name = "subId") String subId) throws StripeException {
        Subscription subscription = Subscription.retrieve(subId);
        log.info("Subscription by id=" + subscription.toJson());
        SubscriptionDetails subscriptionDetails = new SubscriptionDetails();
        subscriptionDetails.setId(subscription.getId());
        subscriptionDetails.setCustomerId(subscription.getCustomer());
        subscriptionDetails.setBillingCycleAnchor(new Date(subscription.getBillingCycleAnchor() * 1000));
        return subscriptionDetails;
    }

    private Flux<PaymentPlans> getPlans(PlanCollection plans) {
        ObjectMapper mapper = new ObjectMapper();
        Flux<PaymentPlans> plansFlux = null;
        try {
            String json = mapper.writeValueAsString(plans.getData());
//            log.info("Plans Json=" + json);
            List<PaymentPlans> plansList = mapper.readValue(json, new TypeReference<List<PaymentPlans>>(){});
            plansFlux = Flux.fromArray(plansList.toArray(new PaymentPlans[plansList.size()]));
//            for (PaymentPlans pp : plansList) {
//                log.info("Plan Name=" + pp.toString());
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return plansFlux;
    }
    @PostMapping("/createSubscription")
    @ResponseStatus(HttpStatus.CREATED)
    SubscriptionDetails create(@RequestBody PaymentDetails paymentDetails) throws StripeException {

        Map<String, Object> customerParams = new HashMap<String, Object>();
        customerParams.put("payment_method", paymentDetails.getPaymentMethod());
        customerParams.put("name", paymentDetails.getName());
        customerParams.put("email", paymentDetails.getEmail());
        customerParams.put("phone", paymentDetails.getPhone());

//        Map<String, String> address = new HashMap<String, String>();
//        address.put("line1", "10300 devon street");
//        address.put("postal_code", paymentDetails.getZipCode());
//        customerParams.put("address", address);

        Map<String, String> invoiceSettings = new HashMap<String, String>();
        invoiceSettings.put("default_payment_method", paymentDetails.getPaymentMethod());
        customerParams.put("invoice_settings", invoiceSettings);

        Customer customer = Customer.create(customerParams);
        log.info("Customer JSON=" + customer.toJson());
        updateParent(paymentDetails.getParentId(), customer.getId());
        log.info("Plan Id=" + paymentDetails.getPlanId());
        // Subscribe customer to a plan
        Map<String, Object> item = new HashMap<>();
//        item.put("plan", "plan_GZBXYhjDEsZI3z");
        item.put("plan", paymentDetails.getPlanId());
        Map<String, Object> items = new HashMap<>();
        items.put("0", item);

        Map<String, Object> expand = new HashMap<>();
        expand.put("0", "latest_invoice.payment_intent");
        Map<String, Object> params = new HashMap<>();
        params.put("customer", customer.getId());
        params.put("items", items);
        params.put("expand", expand);
        params.put("trial_from_plan", true);
        Subscription subscription = Subscription.create(params);
        log.info("Subscription JSON=" + subscription.toJson());
        SubscriptionDetails subscriptionDetails = new SubscriptionDetails();
        subscriptionDetails.setId(subscription.getId());
        subscriptionDetails.setCustomerId(subscription.getCustomer());
        subscriptionDetails.setBillingCycleAnchor(new Date(subscription.getBillingCycleAnchor() * 1000));
        updateStudent(paymentDetails.getStudentId(), subscription.getId(), paymentDetails.getPlanId(), paymentDetails.getPlanName());
        return subscriptionDetails;
    }

    private void updateParent(String parentId, String customerId) {
        ParentUser parentUser = this.parentMongoReactiveRepository.findParentUserById(parentId).block();
        parentUser.setSubcriptionCustId(customerId);
        this.parentMongoReactiveRepository.save(parentUser).block();
    }

    private void updateStudent(String studentId, String subscriptionId, String planId, String planName) {
        User user = this.userMongoReactiveRepository.findUserById(studentId).block();
        user.setSubscriptionId(subscriptionId);
        user.setSubscriptionPlanName(planName);
        user.setSubscriptionPlanId(planId);
        this.userMongoReactiveRepository.save(user).block();
    }

    @PostMapping("/updateCard")
    @ResponseStatus(HttpStatus.CREATED)
    void updateCard(@RequestBody PaymentDetails paymentDetails) throws StripeException {
        ParentUser parentUser = this.parentMongoReactiveRepository.findParentUserById(paymentDetails.getParentId()).block();
        String customerId = parentUser.getSubcriptionCustId();

        Customer customer = Customer.retrieve(customerId);
        log.info("Customer at updateCard=" + customer.toJson());
        log.info("Old PaymentMethod() at updateCard=" + customer.getInvoiceSettings().getDefaultPaymentMethod());
        log.info("New PaymentMethod() at updateCard=" + paymentDetails.getPaymentMethod());

        PaymentMethod paymentMethod = PaymentMethod.retrieve(paymentDetails.getPaymentMethod());
        log.info("Payment Id before attaching=" + paymentMethod.getId());
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("customer", customer.getId());
        PaymentMethod afterAttachPaymentMethod = paymentMethod.attach(params);
        log.info("Payment Id after attaching=" + afterAttachPaymentMethod.getId());
//        customer.getInvoiceSettings().setDefaultPaymentMethod(paymentDetails.getPaymentMethod());
//        Map<String, Object> customerParams = new HashMap<String, Object>();
//        customerParams.put("payment_method", paymentDetails.getPaymentMethod());

        Map<String, Object> customerParams = new HashMap<String, Object>();
        Map<String, String> invoiceSettings = new HashMap<String, String>();
        invoiceSettings.put("default_payment_method", paymentDetails.getPaymentMethod());
        customerParams.put("invoice_settings", invoiceSettings);

        Customer updCustomer = customer.update(customerParams);
        log.info("Customer at updateCard after update=" + customer.toJson());
    }
}
