package org.byovsiannikov.sticky_notes.services;

import org.assertj.core.api.Assertions;
import org.byovsiannikov.sticky_notes.converter.NoteDTO_Entity_Converter;
import org.byovsiannikov.sticky_notes.dto.AuthorDTO;
import org.byovsiannikov.sticky_notes.dto.request.NoteDTO;
import org.byovsiannikov.sticky_notes.dto.Positions;
import org.byovsiannikov.sticky_notes.dto.response.PageResponse;
import org.byovsiannikov.sticky_notes.entitiy.AuthorEntity;
import org.byovsiannikov.sticky_notes.entitiy.NoteEntity;
import org.byovsiannikov.sticky_notes.repository.NoteRepository;
import org.byovsiannikov.sticky_notes.service.impl.NoteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

//ExtendWith Annoitation for JUnit 5
@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {


    //Inject mocks - mocked object for test
    @InjectMocks
    NoteServiceImpl noteService;
    //Mocks - binded object of mocked object
    @Mock
    NoteRepository noteRepository;
    @Mock
    NoteDTO_Entity_Converter noteDTOEntityConverter;
    private NoteDTO noteDTOForSave;
    private NoteDTO  noteDTOResult;
    private NoteEntity  noteEntity;
    private List<NoteDTO> noteDTOList;
    private List<NoteEntity> noteEntityList;
@BeforeEach
    public void init() {
    noteDTOForSave = NoteDTO
            .builder()
            .title("Task1")
            .author(AuthorDTO.builder()
                    .name("Jack")
                    .surname("Russle")
                    .position(Positions.USER)
                    .build())
            .description("lorem ipsum")
            .build();

    noteDTOResult = NoteDTO
            .builder()
            .title("Task1")
            .author(AuthorDTO.builder()
                    .name("Jack")
                    .surname("Russle")
                    .position(Positions.USER)
                    .build())
            .description("lorem ipsum")
            .dateIssue(LocalDateTime.ofInstant(Instant.now(),ZoneId.systemDefault()))
            .dateIssue(LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()))
            .build();

    noteEntity = NoteEntity
            .builder()
            .id(1l)
            .title("Task1")
            .author(AuthorEntity.builder()
                    .name("Jack")
                    .surname("Russle")
                    .position("USER")
                    .build())
            .description("lorem ipsum")
            .dateIssue(BigInteger.valueOf(Instant.now().getEpochSecond()))
            .dateUpdated(BigInteger.valueOf(Instant.now().getEpochSecond()))
            .isActive(true)
            .build();
}

    @Test
    public void NoteService_PostEntity_PostedEntity() {
        //arrange
        //act
        Mockito.when(noteDTOEntityConverter.apply(noteDTOForSave)).thenReturn(noteEntity);
        Mockito.when(noteRepository.save(noteEntity)).thenReturn(noteEntity);
        Mockito.when(noteDTOEntityConverter.reverseConverter(noteEntity)).thenReturn(noteDTOResult);
        //assert
        NoteDTO noteEntity1 = noteService.postNote(noteDTOForSave);
        Assertions.assertThat(noteEntity1).isNotNull();
        //verify
        Mockito.verify(noteRepository, Mockito.times(1)).save(noteEntity);
    }

//    @Test
//    public void NoteService_PostEntity_CatchEmptyBdError() {
//        //arrange
//        NoteDTO noteDTOForSave = NoteDTO
//                .builder()
//                .title("Task1")
//                .author(AuthorDTO.builder()
//                        .name("Jack")
//                        .surname("Russle")
//                        .position(Positions.USER)
//                        .build())
//                .description("lorem ipsum")
//                .build();
//        NoteEntity noteEntityForReturn = NoteEntity
//                .builder()
//                .id(1l)
//                .title("Task1")
//                .author(AuthorEntity.builder()
//                        .name("Jack")
//                        .surname("Russle")
//                        .position("USER")
//                        .build())
//                .description("lorem ipsum")
//                .dateIssue(BigInteger.valueOf(new Date().getTime()))
//                .dateUpdated(BigInteger.valueOf(new Date().getTime()))
//                .isActive(true)
//                .build();
//        //act
//        Mockito.when(noteRepository.findByTitle(noteDTOForSave.getTitle())).thenThrow(InvalidDataAccessResourceUsageException.class);
//        Mockito.when(noteRepository.save(noteDTOForSave)).thenReturn((noteEntityForReturn));
//        //assert
//        NoteEntity noteEntity = noteService.postNote(noteDTOForSave);
//        Assertions.assertThat(noteEntity).isNotNull();
//        Assertions.assertThatExceptionOfType(InvalidDataAccessResourceUsageException.class).isThrownBy(() -> {
//                    throw new InvalidDataAccessResourceUsageException("MyException");
//                }
//        ).withMessage("MyException");
//        Mockito.verify(noteRepository, Mockito.times(1)).save(noteDTOForSave);
//    }
//    @Test
//    public void NoteService_PostEntity_ReturnNull() {
//        //arrange
//        NoteEntity noteDTOForSave = NoteEntity
//                .builder()
//                .title("Task1")
//                .author(AuthorEntity.builder()
//                        .name("Jack")
//                        .surname("Russle")
//                        .position("USER")
//                        .build())
//                .description("lorem ipsum")
//                .dateIssue(BigInteger.valueOf(new Date().getTime()))
//                .dateUpdated(BigInteger.valueOf(new Date().getTime()))
//                .isActive(true)
//                .build();
//
//        NoteEntity noteEntityForReturn = NoteEntity
//                .builder()
//                .id(1l)
//                .title("Task1")
//                .author(AuthorEntity.builder()
//                        .name("Jack")
//                        .surname("Russle")
//                        .position("USER")
//                        .build())
//                .description("lorem ipsum")
//                .dateIssue(BigInteger.valueOf(new Date().getTime()))
//                .dateUpdated(BigInteger.valueOf(new Date().getTime()))
//                .isActive(true)
//                .build();
//
//        //act
//        Mockito.when(noteRepository.findByTitle(noteDTOForSave.getTitle())).thenReturn(Optional.ofNullable(noteEntityForReturn));
//
//        NoteEntity noteEntity = noteService.postNote(noteDTOForSave);
//
//        //assert
//        Assertions.assertThat(noteEntity).isNull();
//        //verify
//        Mockito.verify(noteRepository, Mockito.times(1)).findByTitle(noteDTOForSave.getTitle());
//
//    }
    //todo test refactoring
@Test
public void testGetAllNotes() {
    // Given (set up mocks, data, and input parameters)
    Integer pageNo = 1;
    Integer pageSize = 10;
    Page<NoteEntity> mockPage = Mockito.mock(Page.class);
    List<NoteEntity> mockNoteList = new ArrayList<>();
    // ... fill mockNoteList with sample data
    Mockito.when(noteRepository.findAllByIsActiveTrue(PageRequest.of(pageNo, pageSize)))
            .thenReturn(mockPage);
    Mockito.when(mockPage.getContent()).thenReturn(mockNoteList);
    Mockito.when(noteDTOEntityConverter.reverseConverter(any(NoteEntity.class)))
            .thenReturn(// ... create the expected NoteDTO from mockNoteEntity);

                    // When (call the method with prepared data)
                    PageResponse actualResponse = myService.getAllNotes(pageNo, pageSize);

    // Then (verify interactions and assert outputs)
    Mockito.verify(noteRepository).findAllByIsActiveTrue(PageRequest.of(pageNo, pageSize));
    Mockito.verify(mockPage).getContent();
    mockNoteList.forEach(note -> Mockito.verify(noteDTOEntityConverter).reverseConverter(note));

    // ... further assertions on actualResponse based on expected behavior
}
//
//    @Test
//    public void NoteService_GetAllNotes_AllNotes() {
//        //arrange
//        NoteEntity noteEntityForReturn = NoteEntity
//                .builder()
//                .id(1l)
//                .title("Task1")
//                .author(AuthorEntity.builder()
//                        .name("Jack")
//                        .surname("Russle")
//                        .position("USER")
//                        .build())
//                .description("lorem ipsum")
//                .dateIssue(BigInteger.valueOf(Instant.now().getEpochSecond()))
//                .dateUpdated(BigInteger.valueOf(Instant.now().getEpochSecond()))
//                .isActive(true)
//                .build();
//        NoteEntity noteEntityForReturn1 = NoteEntity
//                .builder()
//                .id(2l)
//                .title("Task1")
//                .author(AuthorEntity.builder()
//                        .name("Jack")
//                        .surname("Russle")
//                        .position("USER")
//                        .build())
//                .description("lorem ipsum")
//                .dateIssue(BigInteger.valueOf(Instant.now().getEpochSecond()))
//                .dateUpdated(BigInteger.valueOf(Instant.now().getEpochSecond()))
//                .isActive(true)
//                .build();
//        NoteEntity noteEntityForReturn2 = NoteEntity
//                .builder()
//                .id(3l)
//                .title("Task1")
//                .author(AuthorEntity.builder()
//                        .name("Jack")
//                        .surname("Russle")
//                        .position("USER")
//                        .build())
//                .description("lorem ipsum")
//                .dateIssue(BigInteger.valueOf(Instant.now().getEpochSecond()))
//                .dateUpdated(BigInteger.valueOf(Instant.now().getEpochSecond()))
//                .isActive(true)
//                .build();
//        Page noteEntityPage=Mockito.mock(Page.class);
//        Pageable pageable= PageRequest.of(1,10);
//        List<NoteEntity> noteEntitysForSave = List.of(noteEntityForReturn, noteEntityForReturn1, noteEntityForReturn2);
//        //act
//        Mockito.when(noteRepository.findAllByIsActiveTrue(pageable)).thenReturn(noteEntityPage);
//        //assert
//       PageResponse pageResponse = noteService.getAllNotes(1,10);
//        Assertions.assertThat(pageResponse).isNotNull();
//        //verify
//        Mockito.verify(noteRepository, Mockito.times(1)).findAllByIsActiveTrue(pageable);
//    }
//
//    @Test
//    public void NoteService_GetNoteById_Note() {
//        //arrange
//        NoteEntity noteEntityForReturn = NoteEntity
//                .builder()
//                .id(1l)
//                .title("Task1")
//                .author(AuthorEntity.builder()
//                        .name("Jack")
//                        .surname("Russle")
//                        .position("USER")
//                        .build())
//                .description("lorem ipsum")
//                .dateIssue(BigInteger.valueOf(new Date().getTime()))
//                .dateUpdated(BigInteger.valueOf(new Date().getTime()))
//                .isActive(true)
//                .build();
//
//        //act
//        Mockito.when(noteRepository.findById(1L)).thenReturn(Optional.ofNullable(noteEntityForReturn));
//        //assert
//        NoteEntity noteEntityCheck = noteService.getNoteById(1L);
//        Assertions.assertThat(noteEntityCheck).isNotNull();
//        //verify
//        Mockito.verify(noteRepository, Mockito.times(3)).findById(1L);
//    }
//    @Test
//    public void NoteService_GetNoteById_ReturnsNull() {
//        //arrange
//        //act
//        Mockito.when(noteRepository.findById(1l)).thenReturn(Optional.ofNullable(Mockito.isNull()));
//        //assert
//        NoteEntity noteEntityCheck = noteService.getNoteById(1l);
//        Assertions.assertThat(noteEntityCheck).isNull();
//        //verify
//        Mockito.verify(noteRepository, Mockito.times(1)).findById(1l);
//    }
//
//    @Test
//    public void NoteService_GetNoteById_isActiveCheck_ReturnsNull() {
//        //arrange
//        NoteEntity noteEntityForReturn = NoteEntity
//                .builder()
//                .id(1l)
//                .title("Task1")
//                .author(AuthorEntity.builder()
//                        .name("Jack")
//                        .surname("Russle")
//                        .position("USER")
//                        .build())
//                .description("lorem ipsum")
//                .dateIssue(BigInteger.valueOf(new Date().getTime()))
//                .dateUpdated(BigInteger.valueOf(new Date().getTime()))
//                .isActive(false)
//                .build();
//
//        //act
//        Mockito.when(noteRepository.findById(1l)).thenReturn(Optional.ofNullable(noteEntityForReturn));
//        //assert
//        NoteEntity noteEntityCheck = noteService.getNoteById(1l);
//        Assertions.assertThat(noteEntityCheck).isNull();
//        //verify
//        Mockito.verify(noteRepository, Mockito.times(2)).findById(1l);
//    }
//    @Test
//    public void NoteService_UpdateNoteByID_UpdatedNote() {
//        //arrange
//        NoteEntity noteEntityForReturn = NoteEntity
//                .builder()
//                .id(1l)
//                .title("Task1")
//                .author(AuthorEntity.builder()
//                        .name("Jack")
//                        .surname("Russle")
//                        .position("USER")
//                        .build())
//                .description("lorem ipsum")
//                .dateIssue(BigInteger.valueOf(new Date().getTime()))
//                .dateUpdated(BigInteger.valueOf(new Date().getTime()))
//                .isActive(true)
//                .build();
//
//        NoteEntity valuesForUpdate = NoteEntity
//                .builder()
//                .title("Task100")
//                .dateUpdated(BigInteger.valueOf(new Date().getTime()))
//                .description("lorem ipsadwadwum")
//                .build();
//        //act
//        Mockito.when(noteRepository.findById(1l)).thenReturn(Optional.of(noteEntityForReturn));
//        //assert
//        NoteEntity noteEntityCheck = noteService.updateNoteById(1l,valuesForUpdate);
//        Assertions.assertThat(noteEntityCheck).isNotNull();
//        //verify
//        Mockito.verify(noteRepository, Mockito.times(2)).findById(1l);
//        Mockito.verify(noteRepository, Mockito.times(1)).save(noteEntityForReturn);
//    }
//    @Test
//    public void NoteService_UpdateNoteByID_ReturnsNull() {
//        //arrange
//
//        NoteEntity valuesForUpdate = NoteEntity
//                .builder()
//                .title("Task100")
//                .dateUpdated(BigInteger.valueOf(new Date().getTime()))
//                .description("lorem ipsadwadwum")
//                .build();
//        //act
//        Mockito.when(noteRepository.findById(1l)).thenReturn(Optional.ofNullable(Mockito.any(NoteEntity.class)));
//        //assert
//        NoteEntity noteEntityCheck = noteService.updateNoteById(1L,valuesForUpdate);
//        Assertions.assertThat(noteEntityCheck).isNull();
//        //verify
//        Mockito.verify(noteRepository, Mockito.times(1)).findById(1L);
//    }
//    @Test
//    public void NoteService_DeleteNote_StringMessage() {
//        //arrange
//        NoteEntity noteEntityForReturn = NoteEntity
//                .builder()
//                .id(1l)
//                .title("Task1")
//                .author(AuthorEntity.builder()
//                        .name("Jack")
//                        .surname("Russle")
//                        .position("USER")
//                        .build())
//                .description("lorem ipsum")
//                .dateIssue(BigInteger.valueOf(new Date().getTime()))
//                .dateUpdated(BigInteger.valueOf(new Date().getTime()))
//                .isActive(true)
//                .build();
//
//        //act
//        Mockito.when(noteRepository.findById(1l)).thenReturn(Optional.ofNullable(noteEntityForReturn));
//        //assert
//
//        String message = noteService.deleteNoteById(1L);
//        Assertions.assertThat(message).isEqualTo("Entity was deleted");
//        //verify
//        Mockito.verify(noteRepository, Mockito.times(3)).findById(1L);
//    }
//    @Test
//    public void NoteService_DeleteNote_ReturnsNull() {
//        //arrange
//
//
//        //act
//        Mockito.when(noteRepository.findById(1l)).thenReturn(Optional.ofNullable(Mockito.any(NoteEntity.class)));
//        //assert
//
//        String message = noteService.deleteNoteById(1L);
//        Assertions.assertThat(message).isNull();
//        //verify
//        Mockito.verify(noteRepository, Mockito.times(1)).findById(1L);
//    }
//todo test for second condition
}
