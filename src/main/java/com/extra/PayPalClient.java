/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.extra;

import com.braintreepayments.http.HttpResponse;
import com.braintreepayments.http.serializer.Json;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.Order;
import com.paypal.orders.OrdersGetRequest;
import java.io.IOException;
import org.json.JSONObject;


public class PayPalClient {

  /**
   *Set up the PayPal Java SDK environment with PayPal access credentials.  
   *This sample uses SandboxEnvironment. In production, use LiveEnvironment.
   */
  private final PayPalEnvironment environment = new PayPalEnvironment.Sandbox(
    "AfBvUyTbd88alKd6Wp-M_nqYk_zK-_BrnxeqvIHpW5BjlEZedjd05EH4rVImd92kdvaPhbBHkauvANXV",
    "EED_ev3pdh8nsJ2IyDbslmY-c8OZADuHxVRLlMMK5YmwKNBYjjEq4g3py65GfiEcHuleTu_UAsY4vJgP");

  /**
   *PayPal HTTP client instance with environment that has access
   *credentials context. Use to invoke PayPal APIs.
   */
  PayPalHttpClient client = new PayPalHttpClient(environment);

  /**
   *Method to get client object
   *
   *@return PayPalHttpClient client
   */
  public PayPalHttpClient client() {
    return this.client;
  }
  
   public JSONObject getOrder(String orderId) throws IOException {
    OrdersGetRequest request = new OrdersGetRequest(orderId);
    //Call PayPal to get the transaction
    HttpResponse<Order> response = client().execute(request);
    
    return new JSONObject(new Json().serialize(response.result()));
  }

}
