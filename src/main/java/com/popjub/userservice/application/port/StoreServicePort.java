package com.popjub.userservice.application.port;

import java.util.UUID;

public interface StoreServicePort {
	boolean exists(UUID storeId);
}
