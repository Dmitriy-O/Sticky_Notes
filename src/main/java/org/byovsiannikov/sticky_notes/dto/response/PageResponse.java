package org.byovsiannikov.sticky_notes.dto.response;

import lombok.Builder;
import lombok.Data;
import org.byovsiannikov.sticky_notes.dto.request.NoteDTO;

import java.util.List;

@Data
@Builder
public class PageResponse {
    private final List<NoteDTO> content;
    private final Integer pageNo;
    private final Integer pageSize;
    private final Long totalElements;
    private final Integer totalPages;
    private final Boolean last;

}
