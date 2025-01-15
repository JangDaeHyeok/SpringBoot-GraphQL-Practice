package com.jdh.graphql.api.board.controller;

import com.jdh.graphql.api.board.application.BoardAddService;
import com.jdh.graphql.api.board.application.BoardGetService;
import com.jdh.graphql.api.board.dto.request.BoardAddRequestDTO;
import com.jdh.graphql.api.board.dto.request.BoardGetRequestDTO;
import com.jdh.graphql.api.board.dto.response.BoardGetResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class BoardController {

    private final BoardGetService boardGetService;

    private final BoardAddService boardAddService;

    @QueryMapping
    public List<BoardGetResponseDTO> getBoards(@Argument BoardGetRequestDTO request) {
        log.info(request.toString());

        return boardGetService.getBoard(request);
    }

    @MutationMapping
    public BoardGetResponseDTO addBoard(@Argument @Valid BoardAddRequestDTO request) {
        log.info(request.toString());

        return boardAddService.addBoard(request);
    }

}
