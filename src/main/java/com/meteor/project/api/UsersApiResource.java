package com.meteor.project.api;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@Api(value = "Users")
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UsersApiResource {

	@GetMapping
	@ApiOperation(value = "Retrieve list of users", notes = "Example Requests:\n" + "\n" + "users\n" + "\n" + "\n" + "users?fields=id,username,email,officeName")
	public String retrieve(Principal principal) {
		return "Welcome, " + principal.getName() + "!";
	}

}
