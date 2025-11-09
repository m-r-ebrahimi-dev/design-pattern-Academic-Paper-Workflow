package com.mrebrahimi.academicpaperworkflow.domain;

import com.mrebrahimi.academicpaperworkflow.state.PaperState;
import com.mrebrahimi.academicpaperworkflow.state.PaperStateListener;
import jakarta.persistence.*;

@Entity
@Table(name = "ACADEMIC_PAPERS")
@EntityListeners(PaperStateListener.class)
public class Paper {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paper_seq")
    @SequenceGenerator(name = "paper_seq", sequenceName = "PAPER_ID_SEQ", allocationSize = 1)
    @Column(name = "PAPER_ID")
    private Long id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Lob
    @Column(name = "CONTENT")
    private String content;

    @Column(name = "AUTHOR_ID", nullable = false)
    private Long authorId;

    @Enumerated(EnumType.STRING)
    @Column(name = "PAPER_STATUS", nullable = false)
    private PaperStatus status = PaperStatus.DRAFT;

    @Transient
    private PaperState currentState;

    public void submit(User user) {
        this.currentState.submit(this, user);
    }

    public void assignToReviewer(User editor, User reviewer) {
        this.currentState.assignToReviewer(this, editor, reviewer);
    }

    public void accept(User editor) {
        this.currentState.accept(this, editor);
    }

    public void reject(User editor) {
        this.currentState.reject(this, editor);
    }

    public void requestRevision(User editor) {
        this.currentState.requestRevision(this, editor);
    }

    public void publish(User editor) {
        this.currentState.publish(this, editor);
    }

    public void changeState(PaperState newState) {
        this.currentState = newState;
        this.status = newState.getStatus();
    }

    public void setCurrentState(PaperState currentState) {
        this.currentState = currentState;
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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public PaperStatus getStatus() {
        return status;
    }
}