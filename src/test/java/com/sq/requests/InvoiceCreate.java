package com.sq.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class InvoiceCreate {
    @JsonIgnore
//    private SimpleDateFormat dateFormat ;
    public String partyReqInvId = "SPI-2023-09-00146";
    public String partyReqInvoiceId = "SPI-2023-09-00146";
    public String taxTypeDescription = "Sales Tax";
    public String freqOfBill = "09";
    public String countryCode = "MYS";
    public String partyRequestDate ;
    public String invoiceDate ;
    public String partyType = "supplier";
    public int totalExcludingTax = 12500;
    public int totalIncludingTax = 13750;
    public String partyMsicCode = "01111";
    public String businessActivityDescription = "abcd";
    public String invoiceVersion = "1.0";
    public String invoicePurpose = "for Md R only";
    public String invoiceCurrencyCode = "MYR";
    public String invoiceType = "01";
    public String classification = "005";
    public String billingStartDate = "11/12/2023";
    public String billingEndDate = "11/12/2023";
    public int exchangeRate = 1;
    public String buyerRegistrationNo = "MY909776513M";
    public String buyerSst_registrationNumber = "R5104035";
    public String buyerTIN = "TIN510304";
    public String buyerName = "Asaraf";
    public String buyerAddress = "Lot 1002, Jln Raja Jumaat, 41000 Klang, Selangor";
    public String buyerEmail = "ashraf@skillquotient.net";
    public String buyerContactNumber = "60133334333";
    public String paymentMode = "Cash";
    public String buyerType = "Business";
    public String partyId = "5c4ded06-2330-40c4-8b4e-4c8f6a0df37a";
    public List<Integer> walkInBuyerId = Collections.singletonList(1);
    public List<InvoiceDetail> invoiceDetails = Collections.singletonList(new InvoiceDetail());

    @SneakyThrows
    public InvoiceCreate()  {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        this.partyRequestDate = dateFormat.format(new Date());
        this.invoiceDate  = dateFormat.format(new Date());
    }

//    public Date setpartyRequestDate(){
//        try {
//            return dateFormat.parse(dateFormat.format(new Date()));
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public Date setinvoiceDate(){
//        try {
//            return dateFormat.parse(dateFormat.format(new Date()));
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Getter
    @Setter
    public class InvoiceDetail {
        public String serialNo = "1";
        public String productDescription = "VR";
        public int quantity = 5;
        public String measurement = "PCS";
        public String hsCode = "8501";
        public String classification = "005";
        public int unitPrice = 2500;
        public String currency = "MYR";
        public int discountRate = 0;
        public int discountAmount = 0;
        public String taxType = "01";
        public int taxAmount = 1250;
        public double taxRate = 0.10;
        public int amountExemptedTax = 12500;
        public String detailsOfTaxExemtion = "C-1000";
        public int subTotal = 13750;
    }

}

