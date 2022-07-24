package com.study.board.service;

import com.study.board.Entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

// 서비스는 무엇인가 행하는 기능을 구현하는 곳!


@Service
public class BoardService {

    @Autowired  //알아서 빈을 주입해서 심어줌
    private BoardRepository boardRepository;

    // 글 작성 처리
    public void write(Board board){
        boardRepository.save(board);
    }

    // 게시물 리스트 처리
    public List<Board> boardList(){
        return boardRepository.findAll();
    }

    // 특정 게시물 불러오기 처리
    public Board boardview(Integer id){

        return boardRepository.findById(id).get();
    }

    public void boardDelete(Integer id){
        boardRepository.deleteById(id);
    }

}
