package com.clover.springboot_board.service;

import com.clover.springboot_board.dto.BoardDTO;
import com.clover.springboot_board.dto.BoardFileDTO;
import com.clover.springboot_board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void save(BoardDTO boardDTO) throws IOException {
        if(boardDTO.getBoardFile().get(0).isEmpty()) {
            // 파일없음
            boardDTO.setFileAttached(0);
            boardRepository.save(boardDTO);
        }
        else {
            // 파일있음
            boardDTO.setFileAttached(1);
            // board를 먼저 insert
            BoardDTO savedBoard = boardRepository.save(boardDTO);
            // 파일처리 후 boardfile insert
            for(MultipartFile boardFile : boardDTO.getBoardFile()) {
                String originalFileName = boardFile.getOriginalFilename();

                String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                String storedFileName = UUID.randomUUID().toString() + extension;

                BoardFileDTO boardFileDTO = new BoardFileDTO();
                boardFileDTO.setOriginalFileName(originalFileName);
                boardFileDTO.setStoredFileName(storedFileName);
                boardFileDTO.setBoardId(savedBoard.getId());

                String savePath = "C:/test_uploadfiles/";
                // 실질적으로 파일이 저장되는 코드
                boardFile.transferTo(new File(savePath + storedFileName));
                boardRepository.saveFile(boardFileDTO);
            }
        }
    }

    public List<BoardDTO> findAll() {
        return boardRepository.findAll();
    }

    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    public BoardDTO findById(Long id) {
        return boardRepository.findById(id);
    }

    public void update(BoardDTO boardDTO) {
        // 게시글 수정시간 반영
        boardDTO.setUpdatedAt(LocalDateTime.now());
        boardRepository.update(boardDTO);
    }

    public void delete(Long id) {
        boardRepository.delete(id);
    }

    public List<BoardFileDTO> findFile(Long id) {
        return boardRepository.findFile(id);
    }
}
