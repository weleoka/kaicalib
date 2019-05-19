package se.ltu.kaicalib.core.domain;

/**
 * Does not need to be persisted, wrapper for title objects to be populated in Thymeleaf forms.
 */
public class TitleWrapper {
    private Title title;

    private Long id;

    public TitleWrapper() {}

    public void setTitle(Title title) {this.title = title;}

    public Title getTitle() { return this.title; }

    public void setId(Long id) { this.id = id; }

    public Long getId() { return this.id; }
}
