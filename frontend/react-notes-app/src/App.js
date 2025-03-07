// App.js
import React, { useState, useEffect } from "react";
import axios from "axios";
import NoteList from "./components/NoteList";
import NoteForm from "./components/NoteForm";
import CategoryForm from "./components/CategoryForm";
import SearchBar from "./components/SearchBar";

function App() {
  const [notes, setNotes] = useState([]);
  const [selectedNote, setSelectedNote] = useState(null);
  const [showArchived, setShowArchived] = useState(false);
  const [searchRefresh, setSearchRefresh] = useState(0);

  const fetchNotes = async () => {
    try {
      const endpoint = showArchived
        ? "http://localhost:8080/notes/archived"
        : "http://localhost:8080/notes/active";
      const response = await axios.get(endpoint);
      setNotes(response.data);
      // También refrescamos la búsqueda
      setSearchRefresh((prev) => prev + 1);
    } catch (error) {
      console.error("Error fetching notes", error);
    }
  };

  useEffect(() => {
    fetchNotes();
  }, [showArchived]);

  const handleEditNote = (note) => {
    setSelectedNote(note);
  };

  const handleSaveNote = async (note) => {
    try {
      let response;
      if (note.id) {
        response = await axios.put(
          `http://localhost:8080/notes/${note.id}`,
          note
        );
      } else {
        response = await axios.post("http://localhost:8080/notes", note);
      }
      console.log("Saved note", response.data);
      setSelectedNote(null);
      fetchNotes();
    } catch (error) {
      console.error("Error saving note", error);
    }
  };

  const handleDeleteNote = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/notes/${id}`);
      fetchNotes();
    } catch (error) {
      console.error("Error deleting note", error);
    }
  };

  const handleToggleArchive = async (id) => {
    try {
      const noteToUpdate = notes.find((note) => note.id === id);
      const updatedNote = { ...noteToUpdate, archived: !noteToUpdate.archived };
      await axios.put(`http://localhost:8080/notes/${id}`, updatedNote);
      if (noteToUpdate.archived && showArchived) {
        setShowArchived(false);
      }
      fetchNotes();
    } catch (error) {
      console.error("Error toggling archive", error);
    }
  };

  // Cuando se actualicen las categorías de una nota, actualizamos el estado principal y refrescamos la búsqueda
  const handleUpdateCategories = (updatedNote) => {
    setNotes((prevNotes) =>
      prevNotes.map((note) => (note.id === updatedNote.id ? updatedNote : note))
    );
    setSearchRefresh((prev) => prev + 1);
  };

  return (
    <div className="container mt-4">
      <h1 className="mb-4">Notes Application</h1>
      <NoteForm onSave={handleSaveNote} initialNote={selectedNote} />
      <SearchBar
        onEditNote={handleEditNote}
        onDeleteNote={handleDeleteNote}
        onToggleArchive={handleToggleArchive}
        onUpdateCategories={handleUpdateCategories}
        refresh={searchRefresh} // pasamos el contador de refresco
      />
      <div className="mb-3">
        <button
          className="btn btn-outline-secondary"
          onClick={() => setShowArchived(!showArchived)}
        >
          {showArchived ? "Show Active" : "Show Archived"}
        </button>
      </div>
      <NoteList
        notes={notes}
        onEditNote={handleEditNote}
        onDeleteNote={handleDeleteNote}
        onToggleArchive={handleToggleArchive}
        onUpdateCategories={handleUpdateCategories}
      />
      <CategoryForm />
    </div>
  );
}

export default App;
