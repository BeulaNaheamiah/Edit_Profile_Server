package com.profile.boot.service;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profile.boot.exception.ProfileException;
import com.profile.boot.model.Profile;

@Service
public class ProfileServiceImpl implements ProfileService {

	@Value("${profile.location}")
	private String fileLocation;


	@Value("${profile.defaulted.to.one}")
	private String defaulted;


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
	public Profile getProfileDetails(String profileId) throws ProfileException {
		Profile profile = null;
		ObjectMapper mapper = new ObjectMapper();
		try {		
			profileId =  ProfileService.defaultFile;
			if(profileId==null) 
				profile = createProfile(profile, mapper,Boolean.valueOf(defaulted));
			else
			 profile = mapper.readValue(new File(fileLocation + profileId), Profile.class);
		} catch(FileNotFoundException f) {
			profile = createProfile(profile, mapper,Boolean.valueOf(defaulted));
		}
		catch (Exception e) {
			throw new ProfileException("Error Parsing the Prodile Data", e);
		}

		return profile;
	}

	private Profile createProfile(Profile profile, ObjectMapper mapper, boolean defaulted) throws ProfileException {
		try {
		profile = mapper.readValue(ClassLoader.getSystemResource(ProfileService.defaultFile), Profile.class);
		profile.setProfileId(defaulted?Long.parseLong(ProfileService.defaultFile):System.currentTimeMillis());
		//Get the file reference
		Path path = Paths.get(fileLocation+profile.getProfileId());
		 
		//Use try-with-resource to get auto-closeable writer instance
		try (BufferedWriter writer = Files.newBufferedWriter(path)) 
		{
		    writer.write(mapper.writeValueAsString(profile));
		}
		}catch(AccessDeniedException e) {
			throw new ProfileException("Please Check the Path provided has a required access "+fileLocation, e);
		}
		catch (Exception e) {
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
