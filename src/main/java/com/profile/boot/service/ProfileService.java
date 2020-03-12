package com.profile.boot.service;

import com.profile.boot.exception.ProfileException;
import com.profile.boot.model.Profile;

public interface ProfileService {
	
	public String addProfile(Profile profile);
	public Profile updateProfile(Profile profile) throws ProfileException;
	public Profile getProfileDetails(int profileId) throws ProfileException;

}
