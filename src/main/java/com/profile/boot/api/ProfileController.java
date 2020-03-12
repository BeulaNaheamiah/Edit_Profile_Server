package com.profile.boot.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.profile.boot.exception.ProfileException;
import com.profile.boot.model.Profile;
import com.profile.boot.service.ProfileService;

@RestController()
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
	private ProfileService profileService;

	@GetMapping("/get")
	public ResponseEntity<Profile> getProfile() {
		Profile profile = null;
		try {
			profile = profileService.getProfileDetails(100000001);

		} catch (ProfileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(profile);
		}
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(profile);
	}

	@PostMapping("/edit")
	public ResponseEntity<Profile> updateProfile(@RequestBody Profile profile) {
System.out.println("CALLLLLLLLLLLLLL");
		try {
			 profileService.updateProfile(profile);
		} catch (ProfileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(profile);
		}
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(profile);

	}

}
