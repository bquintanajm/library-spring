package com.dodecaedro.library.infrastructure.controller;

import com.dodecaedro.library.domain.exception.ActiveFinesException;
import com.dodecaedro.library.domain.exception.BorrowMaximumLimitException;
import com.dodecaedro.library.domain.exception.ExpiredBorrowException;
import com.dodecaedro.library.domain.model.Borrow;
import com.dodecaedro.library.domain.service.BorrowService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RepositoryRestController
@RequiredArgsConstructor
public class BorrowController {
	@NonNull private final BorrowService borrowService;

	@PostMapping("/borrows/borrow")
	public @ResponseBody ResponseEntity<?> borrowBook(@RequestBody Borrow borrow)
		throws ExpiredBorrowException, ActiveFinesException, BorrowMaximumLimitException {
		var createdBorrow = borrowService.borrowBook(borrow.getBook(), borrow.getUser());
		var resource = new EntityModel<>(createdBorrow);
		resource.add(linkTo(methodOn(BorrowController.class).borrowBook(borrow)).withSelfRel());
		return ResponseEntity.status(HttpStatus.CREATED).body(resource);
	}

	@PostMapping("/borrows/return")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void returnBook(@RequestBody Borrow borrow) {
		borrowService.returnBook(borrow.getBook(), borrow.getUser());
	}
}
