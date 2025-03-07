// SearchBar.jsx
import React, { useState, useEffect } from "react";
import axios from "axios";
import NoteItem from "./NoteItem";

const SearchBar = ({
  onEditNote,
  onDeleteNote,
  onToggleArchive,
  onUpdateCategories,
  refresh, // Recibimos el contador para refrescar la búsqueda
}) => {
  const [query, setQuery] = useState("");
  const [searchType, setSearchType] = useState("notes");
  const [results, setResults] = useState([]);
  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState("");

  const fetchCategories = async () => {
    try {
      const response = await axios.get("http://localhost:8080/categories");
      setCategories(response.data);
    } catch (error) {
      console.error("Error fetching categories:", error);
    }
  };

  useEffect(() => {
    fetchCategories();
  }, []);

  // Función para ejecutar la búsqueda sin evento
  const executeSearch = async () => {
    let endpoint = "";
    if (searchType === "notes") {
      endpoint = `http://localhost:8080/notes/search?title=${query}`;
    } else {
      endpoint = `http://localhost:8080/notes/by-category?name=${query}`;
    }
    try {
      const response = await axios.get(endpoint);
      setResults(response.data);
    } catch (error) {
      console.error("Error in search:", error);
    }
  };

  const handleSearch = async (e) => {
    e.preventDefault();
    executeSearch();
  };

  // Cada vez que refresh cambie y haya una consulta activa, se reejecuta la búsqueda
  useEffect(() => {
    if (query) {
      executeSearch();
    }
  }, [refresh]);

  const handleCategoryFilter = async (e) => {
    const catName = e.target.value;
    setSelectedCategory(catName);
    if (catName !== "") {
      try {
        const response = await axios.get(
          `http://localhost:8080/notes/by-category?name=${catName}`
        );
        setResults(response.data);
      } catch (error) {
        console.error("Error filtering by category:", error);
      }
    } else {
      setResults([]);
    }
  };

  const handleResetSearch = () => {
    setQuery("");
    setResults([]);
    setSelectedCategory("");
  };

  return (
    <div className="mb-3">
      <form onSubmit={handleSearch}>
        <div className="input-group">
          <select
            className="form-select"
            value={searchType}
            onChange={(e) => setSearchType(e.target.value)}
          >
            <option value="notes">Notes</option>
            <option value="categories">Categories (by text)</option>
          </select>
          <input
            type="text"
            className="form-control"
            placeholder="Search..."
            value={query}
            onChange={(e) => setQuery(e.target.value)}
          />
          <button className="btn btn-primary" type="submit">
            Search
          </button>
        </div>
      </form>

      {/* Dropdown para filtrar por categoría */}
      <div className="mt-3">
        <label htmlFor="categoryFilter" className="form-label">
          Filter by Category:
        </label>
        <select
          id="categoryFilter"
          className="form-select"
          value={selectedCategory}
          onChange={handleCategoryFilter}
        >
          <option value="">Select a category</option>
          {categories.map((cat) => (
            <option key={cat.id} value={cat.name}>
              {cat.name}
            </option>
          ))}
        </select>
      </div>

      {/* Botón para resetear la búsqueda */}
      <div className="mt-3">
        <button className="btn btn-secondary" onClick={handleResetSearch}>
          Reset Search
        </button>
      </div>

      <div className="mt-3">
        {results.length > 0
          ? results.map((note) => (
              <NoteItem
                key={note.id}
                note={note}
                onEdit={onEditNote}
                onDelete={onDeleteNote}
                onToggleArchive={onToggleArchive}
                onUpdateCategories={onUpdateCategories}
              />
            ))
          : query && <p>No results found.</p>}
      </div>
    </div>
  );
};

export default SearchBar;
