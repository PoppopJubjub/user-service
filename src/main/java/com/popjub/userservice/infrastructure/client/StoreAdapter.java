package com.popjub.userservice.infrastructure.client;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.popjub.userservice.application.port.StoreServicePort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StoreAdapter implements StoreServicePort {

	private final StoreServiceClient storeClient;

	@Override
	public boolean exists(UUID storeId) {
		return storeClient.existsStore(storeId);
	}
}
