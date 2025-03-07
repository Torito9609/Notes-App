package com.backend.notes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is mandatory.")
    @Size(max = 255)
    private String title;

    @NotBlank(message = "Content is mandatory")
    @Column(columnDefinition = "TEXT")
    @Size(max = 10000, message = "Content cannot exceed 10,000 characters")
    private String content;

    @NotNull
    private boolean archived;

    @CreationTimestamp
    @PastOrPresent(message = "The creation date must be in the past or present")
    private Date createdDate;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name= "note_category",
            joinColumns = @JoinColumn(name = "note_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"
            )
    )

    private Set<Category> categories = new HashSet<>();

    public void addCategory(Category category) {
        this.categories.add(category);
        category.getNotes().add(this);
    }

    public void removeCategory(Category category) {
        this.categories.remove(category);
        category.getNotes().remove(this);
    }

    public Note() {
    }

    public Note(Long id, String title, String content, boolean archived, Date createdDate, Set<Category> categories) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.archived = archived;
        this.createdDate = createdDate;
        this.categories = categories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
