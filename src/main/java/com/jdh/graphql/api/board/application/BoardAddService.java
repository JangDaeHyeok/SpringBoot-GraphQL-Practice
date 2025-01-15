package com.jdh.graphql.api.board.application;

import com.jdh.graphql.api.board.dto.request.BoardAddRequestDTO;
import com.jdh.graphql.api.board.dto.response.BoardGetResponseDTO;

public interface BoardAddService {

    BoardGetResponseDTO addBoard(BoardAddRequestDTO requestDTO);

}
