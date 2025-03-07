package com.backend.notes.service;

import com.backend.notes.model.Note;

import java.util.List;
import java.util.Optional;

public interface INoteService {

    Note saveNote(Note note);

    Note findNoteById(Long id);

    List<Note> findAllNotes();

    Note updateNote(Long idUpdate, Note updatedNote);

    void deleteNoteById(Long id);

    List<Note> findNoteByTitle(String title);

    List<Note> findActiveNotes();

    List<Note> findArchivedNotes();

    Note addCategoryToNote(Long NoteId, Long categoryId);

    Note removeCategoryFromNote(Long noteId, Long categoryId);

    List<Note> findNotesByCategoryName(String name);
}
