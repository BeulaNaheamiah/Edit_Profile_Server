package com.profile.boot.service;

import com.profile.boot.exception.ProfileException;
import com.profile.boot.model.Profile;

public interface ProfileService {
	
	public static final String defaultFile="100000001";
	
	public String addProfile(Profile profile);
	public Profile updateProfile(Profile profile) throws ProfileException;
	public Profile getProfileDetails(String profileId) throws ProfileException;

}
