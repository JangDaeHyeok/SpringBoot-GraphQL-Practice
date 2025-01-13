package com.jdh.graphql.api.board.application;

import com.jdh.graphql.api.board.dto.request.BoardGetRequestDTO;
import com.jdh.graphql.api.board.dto.response.BoardGetResponseDTO;

import java.util.List;

public interface BoardGetService {

    List<BoardGetResponseDTO> getBoard(BoardGetRequestDTO requestDTO);

}
