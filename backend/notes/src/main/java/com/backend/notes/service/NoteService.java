package com.backend.notes.service;

import com.backend.notes.model.Category;
import com.backend.notes.model.Note;
import com.backend.notes.repository.ICategoryRepository;
import com.backend.notes.repository.INoteRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class NoteService implements INoteService{
    @Autowired
    private INoteRepository noteRepository;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    @Transactional
    public Note saveNote(Note note) {
        if(noteRepository.existsByTitle(note.getTitle())){
            throw new EntityExistsException("A note with title: " + note.getTitle() + " already exists.");
        }
        return noteRepository.save(note);
    }

    @Override
    public Note findNoteById(Long id) {
        return noteRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("The note with id: " + id + " does not exist"));
    }

    @Override
    public List<Note> findAllNotes() {
        List<Note> notes = noteRepository.findAll();
        return notes.isEmpty() ? Collections.emptyList() : notes;
    }

    @Override
    @Transactional
    public Note updateNote(Long idUpdate, Note updatedNote) {
        Note existentNote = noteRepository.findById(idUpdate).orElseThrow(()->
                new EntityNotFoundException("The note with id: " + idUpdate + " does not exists."));

        boolean titleExists = noteRepository.existsByTitleAndIdNot(updatedNote.getTitle(), idUpdate);

        if(titleExists){
            throw new EntityExistsException("The note with title " + updatedNote.getTitle() + " already exists");
        }

        existentNote.setTitle(updatedNote.getTitle());
        existentNote.setContent(updatedNote.getContent());
        existentNote.setArchived(updatedNote.isArchived());
        existentNote.setCreatedDate(updatedNote.getCreatedDate());

        return noteRepository.save(existentNote);
    }

    @Override
    @Transactional
    public void deleteNoteById(Long id) {
        Note note = findNoteById(id);
        noteRepository.delete(note);
    }

    @Override
    public List<Note> findNoteByTitle(String title) {
        List<Note> notes = noteRepository.findByTitleContainingIgnoreCase(title);
        return notes.isEmpty() ? Collections.emptyList() : notes;
    }

    @Override
    public List<Note> findActiveNotes() {
        List<Note> notes =  noteRepository.findByArchivedFalse();
        return notes.isEmpty() ? Collections.emptyList() : notes;
    }

    @Override
    public List<Note> findArchivedNotes() {
        List<Note> notes = noteRepository.findByArchivedTrue();
        return notes.isEmpty() ? Collections.emptyList() : notes;
    }

    @Override
    @Transactional
    public Note addCategoryToNote(Long noteId, Long categoryId) {
        Note note = noteRepository.findById(noteId).orElseThrow(()->
                new EntityNotFoundException("The note with id: " + noteId + " does not exists."));

        Category category = categoryRepository.findById(categoryId).orElseThrow(()->
                new EntityNotFoundException("The category with id: " + categoryId + " does not exists."));

        if(note.getCategories().contains(category)){
            throw new IllegalStateException("The note already contains the category with id: " + category.getName());
        }

        note.addCategory(category);
        return noteRepository.save(note);
    }

    @Override
    @Transactional
    public Note removeCategoryFromNote(Long noteId, Long categoryId) {
        Note note = noteRepository.findById(noteId).orElseThrow(()->
                new EntityNotFoundException("The note with id: " + noteId + " does not exists."));

        Category category = categoryRepository.findById(categoryId).orElseThrow(()->
                new EntityNotFoundException("The category with id: " + categoryId + " does not exists."));

        if(!note.getCategories().contains(category)){
            throw new IllegalStateException("This not is not associated with the category: " + category.getName());
        }

        note.removeCategory(category);
        return noteRepository.save(note);
    }

    @Override
    public List<Note> findNotesByCategoryName(String name) {
        List<Note> notes = noteRepository.findDistinctByCategoriesName(name);
        return notes.isEmpty() ? Collections.emptyList() : notes;
    }
}
