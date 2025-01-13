package com.jdh.graphql.api.board.application.impl;

import com.jdh.graphql.api.board.application.BoardGetService;
import com.jdh.graphql.api.board.domain.repository.BoardRepository;
import com.jdh.graphql.api.board.dto.request.BoardGetRequestDTO;
import com.jdh.graphql.api.board.dto.response.BoardGetResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardGetServiceImpl implements BoardGetService {

    private final BoardRepository boardRepository;

    @Override
    public List<BoardGetResponseDTO> getBoard(BoardGetRequestDTO requestDTO) {
        return boardRepository.findBoardByAttributes(requestDTO.getId(), requestDTO.getUserId(), requestDTO.getTitle(), requestDTO.getContents())
                .stream().map(BoardGetResponseDTO::of)
                .toList();
    }

}
