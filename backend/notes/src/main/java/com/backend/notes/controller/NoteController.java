package com.backend.notes.controller;

import com.backend.notes.model.Note;
import com.backend.notes.service.INoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private INoteService noteService;

    @GetMapping
    public ResponseEntity<?> getNotes(){
        List<Note> notes =  noteService.findAllNotes();
        if(notes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(notes);
    }

    @PostMapping
    public ResponseEntity<?> createNote(@Valid @RequestBody Note note){
        Note createdNote = noteService.saveNote(note);
        return ResponseEntity.status(201).body(createdNote);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id){
        noteService.deleteNoteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editNote(@PathVariable Long id, @Valid @RequestBody Note note){
        Note updatedNote = noteService.updateNote(id, note);
        return ResponseEntity.ok(updatedNote);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Note note = noteService.findNoteById(id);
        return ResponseEntity.ok(note);
    }

    @GetMapping("/search")
    public ResponseEntity<?> findByTitle(@RequestParam("title") String title){
        List<Note> notes = noteService.findNoteByTitle(title);
        if (notes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/active")
    public ResponseEntity<?> findActiveNotes(){
        List<Note> activeNotes = noteService.findActiveNotes();
        if (activeNotes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(activeNotes);
    }

    @GetMapping("/archived")
    public ResponseEntity<?> findArchivedNotes(){
        List<Note> archivedNotes = noteService.findArchivedNotes();
        if (archivedNotes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(archivedNotes);
    }

    @PostMapping("/{noteId}/categories/{categoryId}")
    public ResponseEntity<?> addCategoryToNote(@PathVariable Long noteId, @PathVariable Long categoryId) {
        Note updatedNote = noteService.addCategoryToNote(noteId, categoryId);
        return ResponseEntity.ok(updatedNote);
    }

    @DeleteMapping("/{noteId}/categories/{categoryId}")
    public ResponseEntity<?> removeCategoryFromNote(@PathVariable Long noteId, @PathVariable Long categoryId) {
        Note updatedNote = noteService.removeCategoryFromNote(noteId, categoryId);
        return ResponseEntity.ok(updatedNote);
    }

    @GetMapping("/by-category")
    public ResponseEntity<?> findNotesByCategory(@RequestParam String name){
        List<Note> notes = noteService.findNotesByCategoryName(name);
        if (notes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(notes);
    }
}
