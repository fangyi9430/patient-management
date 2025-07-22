package com.pm.billingservice.grpc;

import net.devh.boot.grpc.server.service.GrpcService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {
  
  private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);
  @Override
  public void createBillingAccount(billing.BillingRequest billingRequest, StreamObserver<BillingResponse> responseObserver) {
    log.info("createBillingAccount request received {}", billingRequest.toString());
      
      // business logic.
    // return the Billing Response
    BillingResponse response = BillingResponse.newBuilder().setAccountId("1234")
            .setStatus("ACTIVE")
            .build();
    responseObserver.onNext(response);// send response back to client
    responseObserver.onCompleted();
    
    
  }
}
