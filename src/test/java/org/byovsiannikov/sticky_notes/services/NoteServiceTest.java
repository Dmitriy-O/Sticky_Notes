package org.byovsiannikov.sticky_notes.services;

import org.assertj.core.api.Assertions;
import org.byovsiannikov.sticky_notes.entitiy.AuthorEntity;
import org.byovsiannikov.sticky_notes.entitiy.NoteEntity;
import org.byovsiannikov.sticky_notes.repository.NoteRepository;
import org.byovsiannikov.sticky_notes.service.impl.NoteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.InvalidDataAccessResourceUsageException;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;

//ExtendWith Annoitation for JUnit 5
@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {


    //Inject mocks - mocked object for test
    @InjectMocks
    NoteServiceImpl noteService;
    //Mocks - binded object of mocked object
    @Mock
    NoteRepository noteRepository;


    @Test
    public void NoteService_PostEntity_PostedEntity() {
        //arrange
        NoteEntity noteEntityForSave = NoteEntity
                .builder()
                .title("Task1")
                .author(AuthorEntity.builder()
                        .name("Jack")
                        .surname("Russle")
                        .position("USER")
                        .build())
                .description("lorem ipsum")
                .dateIssue(BigInteger.valueOf(new Date().getTime()))
                .dateUpdated(BigInteger.valueOf(new Date().getTime()))
                .isActive(true)
                .build();
        NoteEntity noteEntityForReturn = NoteEntity
                .builder()
                .id(1l)
                .title("Task1")
                .author(AuthorEntity.builder()
                        .name("Jack")
                        .surname("Russle")
                        .position("USER")
                        .build())
                .description("lorem ipsum")
                .dateIssue(BigInteger.valueOf(new Date().getTime()))
                .dateUpdated(BigInteger.valueOf(new Date().getTime()))
                .isActive(true)
                .build();
        //act
        Mockito.when(noteRepository.save(noteEntityForSave)).thenReturn(noteEntityForReturn);
        //assert
        NoteEntity noteEntity = noteService.postNote(noteEntityForSave);
        Assertions.assertThat(noteEntity).isNotNull();
        //verify
        Mockito.verify(noteRepository, Mockito.times(1)).save(noteEntityForSave);
    }

    @Test
    public void NoteService_PostEntity_CatchEmptyBdError() {
        //arrange
        NoteEntity noteEntityForSave = NoteEntity
                .builder()
                .title("Task1")
                .author(AuthorEntity.builder()
                        .name("Jack")
                        .surname("Russle")
                        .position("USER")
                        .build())
                .description("lorem ipsum")
                .dateIssue(BigInteger.valueOf(new Date().getTime()))
                .dateUpdated(BigInteger.valueOf(new Date().getTime()))
                .isActive(true)
                .build();
        NoteEntity noteEntityForReturn = NoteEntity
                .builder()
                .id(1l)
                .title("Task1")
                .author(AuthorEntity.builder()
                        .name("Jack")
                        .surname("Russle")
                        .position("USER")
                        .build())
                .description("lorem ipsum")
                .dateIssue(BigInteger.valueOf(new Date().getTime()))
                .dateUpdated(BigInteger.valueOf(new Date().getTime()))
                .isActive(true)
                .build();
        //act
        Mockito.when(noteRepository.findByTitle(noteEntityForSave.getTitle())).thenThrow(InvalidDataAccessResourceUsageException.class);
        Mockito.when(noteRepository.save(noteEntityForSave)).thenReturn((noteEntityForReturn));
        //assert
        NoteEntity noteEntity = noteService.postNote(noteEntityForSave);
        Assertions.assertThat(noteEntity).isNotNull();
        Assertions.assertThatExceptionOfType(InvalidDataAccessResourceUsageException.class).isThrownBy(() -> {
                    throw new InvalidDataAccessResourceUsageException("MyException");
                }
        ).withMessage("MyException");
        Mockito.verify(noteRepository, Mockito.times(1)).save(noteEntityForSave);
    }
    @Test
    public void NoteService_PostEntity_ReturnNull() {
        //arrange
        NoteEntity noteEntityForSave = NoteEntity
                .builder()
                .title("Task1")
                .author(AuthorEntity.builder()
                        .name("Jack")
                        .surname("Russle")
                        .position("USER")
                        .build())
                .description("lorem ipsum")
                .dateIssue(BigInteger.valueOf(new Date().getTime()))
                .dateUpdated(BigInteger.valueOf(new Date().getTime()))
                .isActive(true)
                .build();

        NoteEntity noteEntityForReturn = NoteEntity
                .builder()
                .id(1l)
                .title("Task1")
                .author(AuthorEntity.builder()
                        .name("Jack")
                        .surname("Russle")
                        .position("USER")
                        .build())
                .description("lorem ipsum")
                .dateIssue(BigInteger.valueOf(new Date().getTime()))
                .dateUpdated(BigInteger.valueOf(new Date().getTime()))
                .isActive(true)
                .build();

        //act
        Mockito.when(noteRepository.findByTitle(noteEntityForSave.getTitle())).thenReturn(Optional.ofNullable(noteEntityForReturn));

        NoteEntity noteEntity = noteService.postNote(noteEntityForSave);

        //assert
        Assertions.assertThat(noteEntity).isNull();
        //verify
        Mockito.verify(noteRepository, Mockito.times(1)).findByTitle(noteEntityForSave.getTitle());
    }

    @Test
    public void NoteService_GetAllNotes_AllNotes() {
        //arrange
        NoteEntity noteEntityForReturn = NoteEntity
                .builder()
                .id(1l)
                .title("Task1")
                .author(AuthorEntity.builder()
                        .name("Jack")
                        .surname("Russle")
                        .position("USER")
                        .build())
                .description("lorem ipsum")
                .dateIssue(BigInteger.valueOf(new Date().getTime()))
                .dateUpdated(BigInteger.valueOf(new Date().getTime()))
                .isActive(true)
                .build();
        NoteEntity noteEntityForReturn1 = NoteEntity
                .builder()
                .id(2l)
                .title("Task1")
                .author(AuthorEntity.builder()
                        .name("Jack")
                        .surname("Russle")
                        .position("USER")
                        .build())
                .description("lorem ipsum")
                .dateIssue(BigInteger.valueOf(new Date().getTime()))
                .dateUpdated(BigInteger.valueOf(new Date().getTime()))
                .isActive(true)
                .build();
        NoteEntity noteEntityForReturn2 = NoteEntity
                .builder()
                .id(3l)
                .title("Task1")
                .author(AuthorEntity.builder()
                        .name("Jack")
                        .surname("Russle")
                        .position("USER")
                        .build())
                .description("lorem ipsum")
                .dateIssue(BigInteger.valueOf(new Date().getTime()))
                .dateUpdated(BigInteger.valueOf(new Date().getTime()))
                .isActive(true)
                .build();

        List<NoteEntity> noteEntitysForSave = List.of(noteEntityForReturn, noteEntityForReturn1, noteEntityForReturn2);
        //act
        Mockito.when(noteRepository.findAll()).thenReturn(noteEntitysForSave);
        //assert
        List<NoteEntity> noteEntityCheck = noteService.getAllNotes();
        Assertions.assertThat(noteEntityCheck).isNotNull();
        //verify
        Mockito.verify(noteRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void NoteService_GetNoteById_Note() {
        //arrange
        NoteEntity noteEntityForReturn = NoteEntity
                .builder()
                .id(1l)
                .title("Task1")
                .author(AuthorEntity.builder()
                        .name("Jack")
                        .surname("Russle")
                        .position("USER")
                        .build())
                .description("lorem ipsum")
                .dateIssue(BigInteger.valueOf(new Date().getTime()))
                .dateUpdated(BigInteger.valueOf(new Date().getTime()))
                .isActive(true)
                .build();

        //act
        Mockito.when(noteRepository.findById(1L)).thenReturn(Optional.ofNullable(noteEntityForReturn));
        //assert
        NoteEntity noteEntityCheck = noteService.getNoteById(1L);
        Assertions.assertThat(noteEntityCheck).isNotNull();
        //verify
        Mockito.verify(noteRepository, Mockito.times(3)).findById(1L);
    }
    @Test
    public void NoteService_GetNoteById_ReturnsNull() {
        //arrange
        //act
        Mockito.when(noteRepository.findById(1l)).thenReturn(Optional.ofNullable(Mockito.isNull()));
        //assert
        NoteEntity noteEntityCheck = noteService.getNoteById(1l);
        Assertions.assertThat(noteEntityCheck).isNull();
        //verify
        Mockito.verify(noteRepository, Mockito.times(1)).findById(1l);
    }

    @Test
    public void NoteService_GetNoteById_isActiveCheck_ReturnsNull() {
        //arrange
        NoteEntity noteEntityForReturn = NoteEntity
                .builder()
                .id(1l)
                .title("Task1")
                .author(AuthorEntity.builder()
                        .name("Jack")
                        .surname("Russle")
                        .position("USER")
                        .build())
                .description("lorem ipsum")
                .dateIssue(BigInteger.valueOf(new Date().getTime()))
                .dateUpdated(BigInteger.valueOf(new Date().getTime()))
                .isActive(false)
                .build();

        //act
        Mockito.when(noteRepository.findById(1l)).thenReturn(Optional.ofNullable(noteEntityForReturn));
        //assert
        NoteEntity noteEntityCheck = noteService.getNoteById(1l);
        Assertions.assertThat(noteEntityCheck).isNull();
        //verify
        Mockito.verify(noteRepository, Mockito.times(2)).findById(1l);
    }
    @Test
    public void NoteService_UpdateNoteByID_UpdatedNote() {
        //arrange
        NoteEntity noteEntityForReturn = NoteEntity
                .builder()
                .id(1l)
                .title("Task1")
                .author(AuthorEntity.builder()
                        .name("Jack")
                        .surname("Russle")
                        .position("USER")
                        .build())
                .description("lorem ipsum")
                .dateIssue(BigInteger.valueOf(new Date().getTime()))
                .dateUpdated(BigInteger.valueOf(new Date().getTime()))
                .isActive(true)
                .build();

        NoteEntity valuesForUpdate = NoteEntity
                .builder()
                .title("Task100")
                .dateUpdated(BigInteger.valueOf(new Date().getTime()))
                .description("lorem ipsadwadwum")
                .build();
        //act
        Mockito.when(noteRepository.findById(1l)).thenReturn(Optional.of(noteEntityForReturn));
        //assert
        NoteEntity noteEntityCheck = noteService.updateNoteById(1l,valuesForUpdate);
        Assertions.assertThat(noteEntityCheck).isNotNull();
        //verify
        Mockito.verify(noteRepository, Mockito.times(2)).findById(1l);
        Mockito.verify(noteRepository, Mockito.times(1)).save(noteEntityForReturn);
    }
    @Test
    public void NoteService_UpdateNoteByID_ReturnsNull() {
        //arrange

        NoteEntity valuesForUpdate = NoteEntity
                .builder()
                .title("Task100")
                .dateUpdated(BigInteger.valueOf(new Date().getTime()))
                .description("lorem ipsadwadwum")
                .build();
        //act
        Mockito.when(noteRepository.findById(1l)).thenReturn(Optional.ofNullable(Mockito.any(NoteEntity.class)));
        //assert
        NoteEntity noteEntityCheck = noteService.updateNoteById(1L,valuesForUpdate);
        Assertions.assertThat(noteEntityCheck).isNull();
        //verify
        Mockito.verify(noteRepository, Mockito.times(1)).findById(1L);
    }
    @Test
    public void NoteService_DeleteNote_StringMessage() {
        //arrange
        NoteEntity noteEntityForReturn = NoteEntity
                .builder()
                .id(1l)
                .title("Task1")
                .author(AuthorEntity.builder()
                        .name("Jack")
                        .surname("Russle")
                        .position("USER")
                        .build())
                .description("lorem ipsum")
                .dateIssue(BigInteger.valueOf(new Date().getTime()))
                .dateUpdated(BigInteger.valueOf(new Date().getTime()))
                .isActive(true)
                .build();

        //act
        Mockito.when(noteRepository.findById(1l)).thenReturn(Optional.ofNullable(noteEntityForReturn));
        //assert

        String message = noteService.deleteNoteById(1L);
        Assertions.assertThat(message).isEqualTo("Entity was deleted");
        //verify
        Mockito.verify(noteRepository, Mockito.times(3)).findById(1L);
    }
    @Test
    public void NoteService_DeleteNote_ReturnsNull() {
        //arrange


        //act
        Mockito.when(noteRepository.findById(1l)).thenReturn(Optional.ofNullable(Mockito.any(NoteEntity.class)));
        //assert

        String message = noteService.deleteNoteById(1L);
        Assertions.assertThat(message).isNull();
        //verify
        Mockito.verify(noteRepository, Mockito.times(1)).findById(1L);
    }
//todo test for second condition
}
