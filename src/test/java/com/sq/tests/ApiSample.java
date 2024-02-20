package com.sq.tests;

import io.restassured.RestAssured;

public class ApiSample {
    public static void main(String[] args) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//        System.out.println(dateFormat.format(new Date()));

        RestAssured.given().header("Content-Type", "application/json")
                .header("Content-Language", "en")
                .header("X-Channel-ID", "MYP_APP")
                .header("Date", "Fri, 12 Jan 2024 13:48:08 +0800+08:00")
                .header("Accept", "application/json")
                .header("X-gateway-apikey", "b993ebbc-a917-469c-9fc3-7cfc04253149")
                .header("X-Request-ID", "MYP_APP20240112134808970")
                .body("{\"username\":\"ckhoo_digi_001\",\"password\":\"Aia@13579\"}")
                .post("https://apiuat.aia.com.my/gateway/myaia-security/1.0/token").then().log().all();
        System.exit(0);
    }


}
