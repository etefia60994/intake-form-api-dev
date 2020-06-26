package com.bfl.intakeform.DTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterDTO {
        @NotNull

        @Size(min = 3, max = 20, message = "Invalid username. Must be between 3 and 30 characters.")
        private String username;

        @NotNull

        @Size(min = 5, max = 20, message = "Invalid password. Must be between 5 and 30 characters.")
        private String password;

        private String firstname;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    private String lastname;

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

}
