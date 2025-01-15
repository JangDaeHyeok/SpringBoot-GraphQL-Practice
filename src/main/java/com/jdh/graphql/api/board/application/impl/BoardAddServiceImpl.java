package com.jdh.graphql.api.board.application.impl;

import com.jdh.graphql.api.board.application.BoardAddService;
import com.jdh.graphql.api.board.domain.entity.Board;
import com.jdh.graphql.api.board.domain.repository.BoardRepository;
import com.jdh.graphql.api.board.dto.request.BoardAddRequestDTO;
import com.jdh.graphql.api.board.dto.response.BoardGetResponseDTO;
import com.jdh.graphql.api.user.application.UserGetService;
import com.jdh.graphql.api.user.dto.request.UserGetRequestDTO;
import com.jdh.graphql.api.user.dto.response.UserGetResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class BoardAddServiceImpl implements BoardAddService {

    private final BoardRepository boardRepository;

    private final UserGetService userGetService;

    @Override
    public BoardGetResponseDTO addBoard(BoardAddRequestDTO requestDTO) {
        // 게시물 등록 사용자 정보 조회
        List<UserGetResponseDTO> findUserList = userGetService.getUser(getUserGetRequestDTO(requestDTO.getUserId()));

        // 존재하는 사용자인지 체크
        if(findUserList == null || findUserList.isEmpty()) {
            throw new IllegalArgumentException("사용자가 존재하지 않습니다.");
        }

        // 게시물 저장
        Board board = Board.of(requestDTO);
        boardRepository.save(board);

        return BoardGetResponseDTO.of(board);
    }

    private UserGetRequestDTO getUserGetRequestDTO(long id) {
        return new UserGetRequestDTO(id, null, null);
    }
}
