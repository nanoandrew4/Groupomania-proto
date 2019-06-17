package com.greenapper.services;

import com.greenapper.models.User;

public interface SessionService {
	void setSessionUser();

	User getSessionUser();

	void setSessionUser(final User user);
}
