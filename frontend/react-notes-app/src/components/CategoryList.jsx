import React, { useState } from "react";
import axios from "axios";

const SearchBar = () => {
  const [query, setQuery] = useState("");
  const [searchType, setSearchType] = useState("notes");
  const [results, setResults] = useState([]);

  const handleSearch = async (e) => {
    e.preventDefault();
    let endpoint = "";

    if (searchType === "notes") {
      endpoint = `http://localhost:8080/notes/search?title=${query}`;
    } else {
      endpoint = `http://localhost:8080/categories/search?name=${query}`;
    }

    try {
      const response = await axios.get(endpoint);
      setResults(response.data);
    } catch (error) {
      console.error("Error during search:", error);
    }
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
            <option value="categories">Categories</option>
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

      <div className="mt-3">
        {results.length > 0 ? (
          <ul className="list-group">
            {results.map((item) => (
              <li key={item.id} className="list-group-item">
                {searchType === "notes" ? item.title : item.name}
              </li>
            ))}
          </ul>
        ) : (
          query && <p>No results found.</p>
        )}
      </div>
    </div>
  );
};

export default SearchBar;
