package org.byovsiannikov.sticky_notes.dto.request;

import lombok.Data;

@Data
public class JWTRegitsterDTO {
    private String userName;
    private String email;
    private String password;
    private String passwordConfirm;

    JWTRegitsterDTO(String userName, String email, String password, String passwordConfirm) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public static JWTRegitsterDTOBuilder builder() {
        return new JWTRegitsterDTOBuilder();
    }

    public static class JWTRegitsterDTOBuilder {
        private String userName;
        private String email;
        private String password;
        private String passwordConfirm;

        JWTRegitsterDTOBuilder() {
        }

        public JWTRegitsterDTOBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public JWTRegitsterDTOBuilder email(String email) {
            this.email = email;
            return this;
        }

        public JWTRegitsterDTOBuilder password(String password) {
            this.password = password;
            return this;
        }

        public JWTRegitsterDTOBuilder passwordConfirm(String passwordConfirm) {
            this.passwordConfirm = passwordConfirm;
            return this;
        }

        public JWTRegitsterDTO build() {
            return new JWTRegitsterDTO(this.userName, this.email, this.password, this.passwordConfirm);
        }

        public String toString() {
            return "JWTRegitsterDTO.JWTRegitsterDTOBuilder(userName=" + this.userName + ", email=" + this.email + ", password=" + this.password + ", passwordConfirm=" + this.passwordConfirm + ")";
        }
    }
}
