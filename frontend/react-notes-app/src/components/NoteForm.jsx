import React, { useState, useEffect } from "react";

const NoteForm = ({ onSave, initialNote }) => {
  const safeNote = initialNote || {};
  const [title, setTitle] = useState(safeNote.title || "");
  const [content, setContent] = useState(safeNote.content || "");

  useEffect(() => {
    const safeNote = initialNote || {};
    setTitle(safeNote.title || "");
    setContent(safeNote.content || "");
  }, [initialNote]);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!title.trim() || !content.trim()) return;

    const newNote = {
      // Si existe un id en initialNote, lo incluimos para que se identifique que es una edición
      ...(safeNote.id && { id: safeNote.id }),
      title: title.trim(),
      content: content.trim(),
      createdDate: new Date().getTime(),
      archived: false,
    };

    onSave(newNote);
    setTitle("");
    setContent("");
  };

  return (
    <form onSubmit={handleSubmit} className="mb-3">
      <div className="mb-3">
        <label htmlFor="noteTitle" className="form-label">
          Title
        </label>
        <input
          id="noteTitle"
          type="text"
          className="form-control"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          placeholder="Enter note title"
          required
        />
      </div>
      <div className="mb-3">
        <label htmlFor="noteContent" className="form-label">
          Content
        </label>
        <textarea
          id="noteContent"
          className="form-control"
          value={content}
          onChange={(e) => setContent(e.target.value)}
          placeholder="Enter note content"
          required
          rows="3"
        />
      </div>
      <button type="submit" className="btn btn-primary">
        Save Note
      </button>
    </form>
  );
};

export default NoteForm;
