package com.mrebrahimi.academicpaperworkflow.service;

import com.mrebrahimi.academicpaperworkflow.domain.Paper;
import com.mrebrahimi.academicpaperworkflow.domain.User;
import com.mrebrahimi.academicpaperworkflow.repository.PaperRepository;
import com.mrebrahimi.academicpaperworkflow.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaperWorkflowService {

    private final PaperRepository paperRepository;
    private final UserRepository userRepository;

    public PaperWorkflowService(PaperRepository paperRepository, UserRepository userRepository) {
        this.paperRepository = paperRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Paper createPaper(String title, String content, Long authorId) {
        User author = findUserById(authorId);

        Paper paper = new Paper();
        paper.setTitle(title);
        paper.setContent(content);
        paper.setAuthorId(author.getId());

        return paperRepository.save(paper);
    }

    @Transactional
    public void submitPaper(Long paperId, Long authorId) {
        Paper paper = findPaperById(paperId);
        User author = findUserById(authorId);

        paper.submit(author);
    }

    @Transactional
    public void assignToReviewer(Long paperId, Long editorId, Long reviewerId) {
        Paper paper = findPaperById(paperId);
        User editor = findUserById(editorId);
        User reviewer = findUserById(reviewerId);

        paper.assignToReviewer(editor, reviewer);
    }

    @Transactional
    public void acceptPaper(Long paperId, Long editorId) {
        Paper paper = findPaperById(paperId);
        User editor = findUserById(editorId);

        paper.accept(editor);
    }

    @Transactional
    public void rejectPaper(Long paperId, Long editorId) {
        Paper paper = findPaperById(paperId);
        User editor = findUserById(editorId);

        paper.reject(editor);
    }

    @Transactional
    public void requestRevision(Long paperId, Long editorId) {
        Paper paper = findPaperById(paperId);
        User editor = findUserById(editorId);

        paper.requestRevision(editor);
    }

    @Transactional
    public void publishPaper(Long paperId, Long editorId) {
        Paper paper = findPaperById(paperId);
        User editor = findUserById(editorId);

        paper.publish(editor);
    }

    @Transactional(readOnly = true)
    public Paper getPaperDetails(Long paperId) {
        return findPaperById(paperId);
    }

    private Paper findPaperById(Long paperId) {
        return paperRepository.findById(paperId)
                .orElseThrow(() -> new EntityNotFoundException("Paper not found with ID: " + paperId));
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
    }
}