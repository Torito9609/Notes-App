import React, { useState } from "react";
import axios from "axios";

const CategoryForm = ({ onSave }) => {
  const [category, setCategory] = useState("");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (category.trim()) {
      try {
        const response = await axios.post("http://localhost:8080/categories", {
          name: category.trim(),
          isDefault: false,
        });
        setSuccess("Category added successfully.");
        setError("");
        setCategory("");
        if (onSave) onSave(response.data);
      } catch (err) {
        setError("Error adding category.");
        setSuccess("");
        console.error(err);
      }
    }
  };

  return (
    <form onSubmit={handleSubmit} className="mb-3">
      <div className="input-group">
        <input
          type="text"
          className="form-control"
          placeholder="New category"
          value={category}
          onChange={(e) => setCategory(e.target.value)}
        />
        <button className="btn btn-primary" type="submit">
          Add
        </button>
      </div>
      {success && <div className="alert alert-success mt-2">{success}</div>}
      {error && <div className="alert alert-danger mt-2">{error}</div>}
    </form>
  );
};

export default CategoryForm;
