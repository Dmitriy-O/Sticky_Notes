package org.byovsiannikov.sticky_notes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public enum Positions {
    @JsonProperty("User")
    USER,
    @JsonProperty("Admin")
    ADMIN;
}
