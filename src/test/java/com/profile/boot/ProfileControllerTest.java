package com.profile.boot;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profile.boot.api.ProfileController;
import com.profile.boot.exception.ProfileException;
import com.profile.boot.model.Profile;
import com.profile.boot.service.ProfileService;

@RunWith(SpringRunner.class)
@WebMvcTest( ProfileController.class)
public class ProfileControllerTest {
	
	@Autowired
	   private MockMvc mvc;

	@MockBean
	   private ProfileService profileController;
	
	@Test
	public void getProfile() throws Exception, ProfileException{
		//ResponseEntity<Profile> p =  new ResponseEntity<Profile>(new Profile(),HttpStatus.OK);
		Profile p = new Profile();
		p.setDisplayName("Beula");
		p.setRealName("BeulaNaheamiah");
		p.setGender("Female");
		p.setMaritalStatus("Married");
		p.setDob(new SimpleDateFormat("yyyy-MM-dd").parse("1980-07-05"));
		 given(profileController.getProfileDetails("1000001")).willReturn(p);

	       mvc.perform(get("/profile/get?profileId=1000001")
	               .contentType(APPLICATION_JSON))
	               .andExpect(status().isOk())
	               .andExpect(jsonPath("$", hasSize(1)))
	               .andExpect(jsonPath("$[0].displayName",is(p.getDisplayName())))
	               .andExpect(jsonPath("$[o].dob", is(p.getDob())))
	              ;	
	}
	
	
	@Test
	public void updateProfile() throws Exception, ProfileException{
		//ResponseEntity<Profile> p =  new ResponseEntity<Profile>(new Profile(),HttpStatus.OK);
		ObjectMapper mapper =  new ObjectMapper();
		Profile p = new Profile();
		p.setProfileId(1000001);
		p.setDisplayName("Sathya");
		 given(profileController.updateProfile(p)).willReturn(p);

	       mvc.perform(post("/profile/edit")
	               .contentType(APPLICATION_JSON).
	               content(mapper.writeValueAsString(p))
	               )
	               .andExpect(status().isOk())
	               .andExpect(jsonPath("$", hasSize(1)))
	               .andExpect(jsonPath("$[0].displayName",is(p.getDisplayName())))
	               .andExpect(jsonPath("$[o].profileId", is(p.getProfileId())))
	              ;	
	}
}
