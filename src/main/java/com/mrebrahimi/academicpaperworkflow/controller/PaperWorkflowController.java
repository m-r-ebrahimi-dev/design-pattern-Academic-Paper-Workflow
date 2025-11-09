package com.mrebrahimi.academicpaperworkflow.controller;

import com.mrebrahimi.academicpaperworkflow.domain.Paper;
import com.mrebrahimi.academicpaperworkflow.domain.User;
import com.mrebrahimi.academicpaperworkflow.dto.ActionRequest;
import com.mrebrahimi.academicpaperworkflow.dto.AssignRequest;
import com.mrebrahimi.academicpaperworkflow.dto.PaperCreateRequest;
import com.mrebrahimi.academicpaperworkflow.dto.UserCreateRequest;
import com.mrebrahimi.academicpaperworkflow.service.PaperWorkflowService;
import com.mrebrahimi.academicpaperworkflow.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class PaperWorkflowController {

    private final PaperWorkflowService paperService;
    private final UserService userService;

    public PaperWorkflowController(PaperWorkflowService paperService, UserService userService) {
        this.paperService = paperService;
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody UserCreateRequest request) {
        User user = userService.createUser(request.username(), request.fullName(), request.role());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/papers")
    public ResponseEntity<Paper> createPaper(@RequestBody PaperCreateRequest request) {
        Paper paper = paperService.createPaper(request.title(), request.content(), request.authorId());
        return ResponseEntity.ok(paper);
    }

    @GetMapping("/papers/{id}")
    public ResponseEntity<Paper> getPaper(@PathVariable Long id) {
        return ResponseEntity.ok(paperService.getPaperDetails(id));
    }

    @PostMapping("/papers/{id}/submit")
    public ResponseEntity<Void> submitPaper(@PathVariable Long id, @RequestBody ActionRequest request) {
        paperService.submitPaper(id, request.userId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/papers/{id}/assign")
    public ResponseEntity<Void> assignReviewer(@PathVariable Long id, @RequestBody AssignRequest request) {
        paperService.assignToReviewer(id, request.editorId(), request.reviewerId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/papers/{id}/accept")
    public ResponseEntity<Void> acceptPaper(@PathVariable Long id, @RequestBody ActionRequest request) {
        paperService.acceptPaper(id, request.userId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/papers/{id}/reject")
    public ResponseEntity<Void> rejectPaper(@PathVariable Long id, @RequestBody ActionRequest request) {
        paperService.rejectPaper(id, request.userId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/papers/{id}/revise")
    public ResponseEntity<Void> requestRevision(@PathVariable Long id, @RequestBody ActionRequest request) {
        paperService.requestRevision(id, request.userId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/papers/{id}/publish")
    public ResponseEntity<Void> publishPaper(@PathVariable Long id, @RequestBody ActionRequest request) {
        paperService.publishPaper(id, request.userId());
        return ResponseEntity.ok().build();
    }
}