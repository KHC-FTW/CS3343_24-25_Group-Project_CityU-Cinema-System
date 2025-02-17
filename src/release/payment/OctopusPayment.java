package release.payment;

import release.externalAPI.*;

/**
 * OctopusPayment class<br>
 * It is used to do payment with Octopus card
 */
public class OctopusPayment implements Payment {
    private final ExternalAPI octopusAPI;
    private PaymentStatus paymentStatus;

    /**
     * Constructor<br>
     * Create OctopusAPI object for Octopus card API to simulate the payment process
     */
    OctopusPayment() {
        octopusAPI = new OctopusAPIFactory().getExternalAPI();
        paymentStatus = PaymentStatus.NOT_PROCEED;
    }

    /**
     * Constructor<br>
     * Create OctopusAPI object for Octopus card API to simulate the payment process for testing purpose
     *
     * @param octopusAPI ExternalAPI object to be used for testing
     */
    OctopusPayment(ExternalAPI octopusAPI) {
        this.octopusAPI = octopusAPI;
        paymentStatus = PaymentStatus.NOT_PROCEED;
    }

    /**
     * Do payment with Octopus card by simulating the payment process using Octopus card API
     *
     * @param price price of the product
     * @return true if the payment is successful, false otherwise
     */
    @Override
    public boolean doPayment(double price) {
        System.out.printf("Octopus Payment: $%.1f%n", price);
        boolean result = octopusAPI.doPayment(price);
        paymentStatus = result ? PaymentStatus.SUCCESS : PaymentStatus.FAIL;
        return result;
    }

    /**
     * Get the payment type
     *
     * @return PaymentType.OCTOPUS for Octopus card
     */
    @Override
    public PaymentType getPaymentType() {
        return PaymentType.OCTOPUS;
    }

    /**
     * get the payment status of this payment
     *
     * @return PaymentStatus.NOT_PROCEED if the payment have not done, else return the result of payment (PaymentStatus.SUCCESS if success, otherwise return PaymentStatus.FAIL.)
     */
    @Override
    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }
}
