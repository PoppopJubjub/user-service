package com.popjub.userservice.infrastructure.client;

import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class StoreClientFallback implements StoreServiceClient {

	@Override
	public Boolean existsStore(UUID storeId) {
		log.warn("Store Service 호출 실패 - Fallback 실행");
		log.warn("처리 - storeId={} 팝업이 존재한다고 가정하고 진행", storeId);

		return true;
	}
}
