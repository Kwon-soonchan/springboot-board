package com.clover.springboot_board.controller;

import com.clover.springboot_board.dto.BoardDTO;
import com.clover.springboot_board.dto.BoardFileDTO;
import com.clover.springboot_board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/save")
    public String save() {
        return "save";
    }

    @PostMapping("/save")
    public String save(BoardDTO boardDTO) throws IOException {
        boardService.save(boardDTO);
        return "redirect:/list";
    }

    @GetMapping("/list")
    public String findAll(Model model) {
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "list";
    }

    @GetMapping("board/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        //조회수 처리
        boardService.updateHits(id);

        //게시글내용 가져오기
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);

        if(boardDTO.getFileAttached() == 1) {
            List<BoardFileDTO> boardFileDTOList = boardService.findFile(id);
            model.addAttribute("boardFileDTOList", boardFileDTOList);
        }

        return "detail";
    }

    // 수정 버튼 클릭 시, 수정화면으로 넘어가도록 하는 메서도 GET
    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "update";
    }

    // DB에 실질적으로 수정내용을 요청하는 메서드 POST
    @PostMapping("/update/{id}")
    public String update(BoardDTO boardDTO, Model model) {
        //update 요청
        boardService.update(boardDTO);
        // findById로 수정된 내용을 다시 조회
        BoardDTO dto = boardService.findById(boardDTO.getId());
        model.addAttribute("board", dto);

        if (dto.getFileAttached() == 1) {
            List<BoardFileDTO> boardFileDTOList = boardService.findFile(dto.getId());
            model.addAttribute("boardFileDTOList", boardFileDTOList);
        }

        return "detail";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        boardService.delete(id);
        return "redirect:/list";
    }
}
