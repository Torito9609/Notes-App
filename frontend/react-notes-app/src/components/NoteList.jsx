// NoteList.jsx
import React from "react";
import NoteItem from "./NoteItem";

const NoteList = ({
  notes,
  onEditNote,
  onDeleteNote,
  onToggleArchive,
  onUpdateCategories,
}) => {
  return (
    <div>
      <h3>Active Notes</h3>
      {notes.length > 0 ? (
        notes.map((note) => (
          <NoteItem
            key={note.id}
            note={note}
            onEdit={onEditNote}
            onDelete={onDeleteNote}
            onToggleArchive={onToggleArchive}
            onUpdateCategories={onUpdateCategories}
          />
        ))
      ) : (
        <p>No notes found.</p>
      )}
    </div>
  );
};

export default NoteList;
