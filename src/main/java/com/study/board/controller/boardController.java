package com.study.board.controller;

import com.study.board.Entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class boardController {

    @Autowired
    private BoardService boardService;

    @ResponseBody
    @GetMapping("/")
    public String main(){

        return "Hello Spring!!";
    }


    @GetMapping("/board/write")
    public String boardWriteForm(){

        return "boardwrite";
    }

    @PostMapping("/board/writePro")
    public String boardWritePro(Board board){

        boardService.write(board);

        return "";
    }

    @GetMapping("/board/list")
    public String boardList(Model model){
        model.addAttribute("list", boardService.boardList());

        return "boardlist";
    }

    @GetMapping("board/view")   // localhost8080:board/view?id=1    하면 1번 id값을 가진 게시글을 불러옴
    public String boardview(Model model, Integer id){

        model.addAttribute("board",boardService.boardview(id));

        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id){
        boardService.boardDelete(id);
        return "redirect:/board/list";
    }
}
