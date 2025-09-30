package com.clover.springboot_board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@ToString
public class BoardDTO {

    private long id;
    private String boardWriter;
    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private int boardHits;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int fileAttached;
    private List<MultipartFile> boardFile;
}