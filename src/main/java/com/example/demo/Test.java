package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.cloudfoundry.identity.uaa.scim.ScimUser;
import org.cloudfoundry.identity.uaa.scim.ScimUser.Email;
import org.cloudfoundry.identity.uaa.scim.ScimUser.Name;
import org.cloudfoundry.identity.uaa.util.JsonUtils;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

public class Test {

	public static void main(String[] args) {
		createScimUser();
	}

	
	public static void createScimUser() {
		ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
		resourceDetails.setClientId("app");
		resourceDetails.setClientSecret("appclientsecret");
		resourceDetails.setAccessTokenUri("http://localhost:8080/uaa/oauth/token");
		OAuth2RestOperations restOperations = new OAuth2RestTemplate(resourceDetails);
		ScimUser user = new ScimUser();
		user.setUserName("test4");
		user.setPassword("test");
		user.setName(new Name("givenName", "familyName"));
		List<Email> emails = new ArrayList<>();
		Email email = new Email();
		email.setValue("P94lav@test.org");
		email.setPrimary(true);
		emails.add(email);
		user.setEmails(emails);
		user.setActive(true);
		user.setOrigin("uaa");
		user.setSchemas(new String[] { "urn:scim:schemas:core:1.0" });
		user.setVerified(true);
		ScimUser response = restOperations.postForObject("http://localhost:8080/uaa/Users", user, ScimUser.class);
		System.out.println(JsonUtils.writeValueAsString(response));
	}

	public static void getPasswordAccessToken() {
		ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
		resourceDetails.setClientId("app");
		resourceDetails.setClientSecret("appclientsecret");
		resourceDetails.setAccessTokenUri("http://localhost:8080/uaa/oauth/token");
		resourceDetails.setUsername("marissa");
		resourceDetails.setPassword("koala");
		OAuth2RestOperations restOperations = new OAuth2RestTemplate(resourceDetails);
		OAuth2AccessToken token = restOperations.getAccessToken();
		System.out.println("access_token:" + token.getValue());
		System.out.println("token_type:" + token.getTokenType());
		System.out.println("refresh_token:" + token.getRefreshToken());
		System.out.println("expires_in:" + token.getExpiresIn());
		System.out.println("scope:" + token.getScope());
	}

	public static void getClientCredentialsAccessToken() {
		ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
		resourceDetails.setClientId("app");
		resourceDetails.setClientSecret("appclientsecret");
		resourceDetails.setAccessTokenUri("http://localhost:8080/uaa/oauth/token");
		OAuth2RestOperations restOperations = new OAuth2RestTemplate(resourceDetails);
		OAuth2AccessToken token = restOperations.getAccessToken();
		System.out.println("access_token:" + token.getValue());
		System.out.println("token_type:" + token.getTokenType());
		System.out.println("refresh_token:" + token.getRefreshToken());
		System.out.println("expires_in:" + token.getExpiresIn());
		System.out.println("scope:" + token.getScope());
	}

}
