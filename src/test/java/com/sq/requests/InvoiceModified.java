package com.sq.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Getter
@Setter

public class InvoiceModified {

    public String partyReqInvoiceId;
    public String origPartyReqInvId;
    public String origPartyReqInvoiceId;
    public String taxType;
    public String freqOfBill;
    public String countryCode;
    public String irbmUuid;
    public String validatedDateTime;
    public String issuerDigitalSignature;
    public String partyRequestDate;
    public String invoiceDate;
    public int totalExcludingTax;
    public int totalIncludingTax;
    public String businessActivityDescription;
    public String invoiceVersion;
    public String invoicePurpose;
    public String invoiceCurrencyCode;
    public String invoiceType;
    public String classification;
    public String billingStartDate;
    public String billingEndDate;
    public double exchangeRate;
    public String paymentMode;
    public String incoterms;
    public String referenceNoOfCustom;
    public SupplierDetails supplierDetails = new SupplierDetails();
    public BuyerDetails buyerDetails = new BuyerDetails();
    public List<InvoiceDetail> invoiceDetails= Collections.singletonList(new InvoiceDetail());


    @SneakyThrows
    public InvoiceModified()  {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        this.partyRequestDate = dateFormat.format(new Date());
        this.invoiceDate  = dateFormat.format(new Date());
    }

    @Getter
    @Setter
    public static class BuyerDetails{
        public String name;
        public String addressLine1;
        public String addressLine2;
        public String addressLine3;
        public String city;
        public String postCode;
        public String state;
        public String country;
        public String registrationNo;
        public String mykadIdNo;
        public String passportNumber;
        public String sstRegistrationNumber;
        public String tin;
        public String email;
        public String website;
        public String contactNumber;
        public String bankAccountNumber;
        public String resource;
        public String type;
        public String msicCode;
    }

    @Getter
    @Setter
    public static class InvoiceDetail{
        public String serialNo;
        public String productDescription;
        public int quantity;
        public String measurement;
        public String hsCode;
        public String classification;
        public int unitPrice;
        public String currency;
        public int discountRate;
        public int discountAmount;
        public String taxType;
        public int taxAmount;
        public double taxRate;
        public int amountExemptedTax;
        public String detailsOfTaxExemtion;
        public int subTotal;
    }


    @Getter
    @Setter
    public static class SupplierDetails{
        public String name;
        public String addressLine1;
        public String addressLine2;
        public String addressLine3;
        public String city;
        public String postCode;
        public String state;
        public String country;
        public String registrationNo;
        public String mykadIdNo;
        public String passportNumber;
        public String sstRegistrationNumber;
        public String tin;
        public String tourismTaxRegistrationNumber;
        public String email;
        public String website;
        public String contactNumber;
        public String bankAccountNumber;
        public String resource;
        public String type;
        public String msicCode;
    }


}
