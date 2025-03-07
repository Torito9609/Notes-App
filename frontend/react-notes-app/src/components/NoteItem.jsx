import React from "react";
import CategoryAssignment from "./CategoryAssignment";

const NoteItem = ({
  note,
  onDelete,
  onEdit,
  onToggleArchive,
  onUpdateCategories,
}) => {
  const createdDate = new Date(note.createdDate).toLocaleString();

  return (
    <div className="card mb-3">
      <div className="card-body">
        <div className="d-flex justify-content-between align-items-center">
          <h5 className="card-title">{note.title}</h5>
          <small className="text-muted">{createdDate}</small>
        </div>
        <p className="card-text">{note.content}</p>

        <div className="mb-3">
          {note.categories && note.categories.length > 0 ? (
            <div>
              <strong>Categories: </strong>
              {note.categories.map((cat) => (
                <span key={cat.id} className="badge bg-secondary me-1">
                  {cat.name}
                </span>
              ))}
            </div>
          ) : (
            <p className="text-muted">No categories assigned</p>
          )}
        </div>

        <button className="btn btn-warning me-2" onClick={() => onEdit(note)}>
          Edit
        </button>
        <button
          className="btn btn-danger me-2"
          onClick={() => onDelete(note.id)}
        >
          Delete
        </button>
        <button
          className="btn btn-secondary me-2"
          onClick={() => onToggleArchive(note.id)}
        >
          {note.archived ? "Unarchive" : "Archive"}
        </button>

        <CategoryAssignment
          noteId={note.id}
          assignedCategories={note.categories || []}
          onUpdate={onUpdateCategories}
        />
      </div>
    </div>
  );
};

export default NoteItem;
