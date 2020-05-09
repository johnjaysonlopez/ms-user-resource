package com.project.api;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "Users")
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UsersApiResource {

	@GetMapping
	@ApiOperation(value = "Authenticated greeting endpoint", notes = "Returns the login identification name of the user.")
	public String retrieve(@ApiIgnore Principal principal) {
		return "Welcome, " + principal.getName() + "!";
	}

}
