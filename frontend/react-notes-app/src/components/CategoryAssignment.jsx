// CategoryAssignment.jsx
import React, { useState, useEffect } from "react";
import axios from "axios";

const CategoryAssignment = ({ noteId, assignedCategories, onUpdate }) => {
  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState("");

  useEffect(() => {
    axios
      .get("http://localhost:8080/categories")
      .then((response) => setCategories(response.data))
      .catch((error) => console.error(error));
  }, []);

  const handleAssign = () => {
    if (selectedCategory) {
      axios
        .post(
          `http://localhost:8080/notes/${noteId}/categories/${selectedCategory}`
        )
        .then((response) => {
          onUpdate(response.data);
        })
        .catch((error) => console.error(error));
    }
  };

  const handleRemove = (catId) => {
    axios
      .delete(`http://localhost:8080/notes/${noteId}/categories/${catId}`)
      .then((response) => {
        onUpdate(response.data);
      })
      .catch((error) => console.error(error));
  };

  return (
    <div>
      <div className="input-group mb-3">
        <select
          className="form-select"
          value={selectedCategory}
          onChange={(e) => setSelectedCategory(e.target.value)}
        >
          <option value="">Select Category</option>
          {categories.map((cat) => (
            <option key={cat.id} value={cat.id}>
              {cat.name}
            </option>
          ))}
        </select>
        <button className="btn btn-secondary" onClick={handleAssign}>
          Assign
        </button>
      </div>
      <div>
        {assignedCategories.map((cat) => (
          <span key={cat.id} className="badge bg-info text-dark me-1">
            {cat.name}
            <button
              type="button"
              className="btn-close btn-close-white ms-1"
              aria-label="Remove"
              onClick={() => handleRemove(cat.id)}
            ></button>
          </span>
        ))}
      </div>
    </div>
  );
};

export default CategoryAssignment;
