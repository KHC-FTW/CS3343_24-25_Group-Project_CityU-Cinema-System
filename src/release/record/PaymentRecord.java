package release.record;


import release.exception.ExNoPaymentRecord;
import release.payment.Payment;
import release.product.MovieTicket;
import release.product.Product;
import release.user.Member;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * PaymentRecord class that contains the payment record of a customer<br>
 * The payment record contains the customer, products, movie tickets and payment details<br>
 * The payment record is stored in a list of all payment records<br>
 * The payment record is created when a customer makes a payment<br>
 * The payment record is stored in record class, so that they cannot be modified once created<br>
 * <br><br> <strong>Note: the getters are generated by java, to use them, simply call the methods by its corresponding variable name
 * e,g to get the customer info, you can use customer()</strong>
 */
public record PaymentRecord(Member user, Map<Product, Integer> productList, List<MovieTicket> movieTicketList,
                            Payment payment) {
    /**
     * Constructor for PaymentRecord class
     *
     * @param user            : customer that made the payment
     * @param productList     : list of products bought
     * @param movieTicketList : list of movie tickets bought
     * @param payment         : payment details
     */
    public PaymentRecord(Member user, Map<Product, Integer> productList, List<MovieTicket> movieTicketList, Payment payment) {
        this.user = user;
        // cannot set the list to point to the list passed in, as it will be modified outside
        // e.g. may be removed by the clear function in shopping cart
        // therefore, must create a new list to store the data
        this.productList = new LinkedHashMap<>(productList);
        this.movieTicketList = new ArrayList<>(movieTicketList);
        this.payment = payment;
    }


    /**
     * Format the payment record to a string
     *
     * @param paymentRecords list of payment records to format
     * @return the formatted string of the payment record
     * @throws ExNoPaymentRecord if there is no payment record
     */
    public static String showAllPaymentRecords(List<PaymentRecord> paymentRecords) throws ExNoPaymentRecord {
        if (paymentRecords.isEmpty()) {
            throw new ExNoPaymentRecord();
        }
        int recordCnt = 0;
        StringBuilder results = new StringBuilder();
        final int lineSeparator = 73;
        for (PaymentRecord paymentRecord : paymentRecords) {
            results.append(String.format("Payment Record %d (%s):%n", ++recordCnt,
                    paymentRecord.payment().getPaymentType().name()));
            List<MovieTicket> movieTickets = paymentRecord.movieTicketList();
            results.append(MovieTicket.formatMovieTicketList(movieTickets));
            Map<Product, Integer> products = paymentRecord.productList();
            results.append(Product.formatProductMap(products));
            results.append("~".repeat(lineSeparator)).append("\n\n");
        }
        return results.toString();
    }
} 
