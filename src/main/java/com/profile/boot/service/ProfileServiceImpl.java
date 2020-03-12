package com.profile.boot.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.profile.boot.exception.ProfileException;
import com.profile.boot.model.Profile;

@Service
public class ProfileServiceImpl implements ProfileService {

	@Value("${profile.location}")
	private String fileLocation;

	@Value("${profile.separator}")
	private String dataSeparator;

	@Override
	public String addProfile(Profile profile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Profile updateProfile(Profile profile) throws ProfileException {
		ObjectMapper mapper =  new ObjectMapper();
		try {
			mapper.writeValue(new File(fileLocation + profile.getProfileId()), profile);
		} catch (Exception e) {
			throw new ProfileException("Error Parsing the Prodile Data", e);
		}
		return profile;
	}

	@Override
	public Profile getProfileDetails(int profileId) throws ProfileException {
		Profile profile = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			 profile = mapper.readValue(new File(fileLocation + profileId), Profile.class);
			// if(profile!=null && profile.getProfilePicture()!=null) 
				// profile.setProfilePicture(encodeImage(profile.getProfilePicture()));
		} catch (Exception e) {
			throw new ProfileException("Error Parsing the Prodile Data", e);
		}

		return profile;
	}

	private String encodeImage(String pic) {
		String encodedImage =null;
		ByteArrayOutputStream baos = null;
		try {
		BufferedImage img = ImageIO.read(new File(fileLocation+pic));             
		baos = new ByteArrayOutputStream();
		ImageIO.write(img, "jpg", baos);
		baos.flush();
		Base64 base = new Base64(false);
		encodedImage = base.encodeToString(baos.toByteArray());
		
	}catch (Exception e) {
		e.printStackTrace();
	}finally {
		try {
			baos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		return encodedImage;
	}
	
}
