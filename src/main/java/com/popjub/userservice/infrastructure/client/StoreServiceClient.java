package com.popjub.userservice.infrastructure.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "store-service", path = "/internal/stores", fallback = StoreClientFallback.class)
public interface StoreServiceClient {

	@GetMapping("/{storeId}/exists")
	Boolean existsStore(@PathVariable("storeId") UUID storeId);
}
