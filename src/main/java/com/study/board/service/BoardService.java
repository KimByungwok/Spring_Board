package com.study.board.service;

import com.study.board.Entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.List;
import java.util.UUID;

// 서비스는 무엇인가 행하는 기능을 구현하는 곳!


@Service
public class BoardService {

    @Autowired  //알아서 빈을 주입해서 심어줌
    private BoardRepository boardRepository;

    // 글 작성 처리
    public void write(Board board, MultipartFile file) throws Exception{

        String projectPath = System.getProperty("user.dir")+ "\\src\\main\\resources\\static\\file";

        UUID uuid = UUID.randomUUID();

        String filename = uuid + "_" + file.getOriginalFilename();

        File savefile = new File(projectPath, filename);

        file.transferTo(savefile);

        board.setFilename(filename);
        board.setFilepath("/file/"+filename);

        boardRepository.save(board);
    }

    // 게시물 리스트 처리
    public Page<Board> boardList(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    // 특정 게시물 불러오기 처리
    public Board boardview(Integer id){

        return boardRepository.findById(id).get();
    }

    public void boardDelete(Integer id){
        boardRepository.deleteById(id);
    }

}
