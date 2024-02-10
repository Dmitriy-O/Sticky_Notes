package org.byovsiannikov.sticky_notes.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateDTO {
    private String title;
    private String description;

}
