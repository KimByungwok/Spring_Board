package com.study.board.controller;

import com.study.board.Entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


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
    public String boardWritePro(Board board, Model model, MultipartFile file) throws Exception{

        boardService.write(board,file);

        model.addAttribute("message","글 작성이 완료되었습니다");
        model.addAttribute("searchUrl","/board/list");

        return "message";
    }

    @GetMapping("/board/list")
//    domain으로 된 pageable을 사용함
    public String boardList(Model model,
                            @PageableDefault(page = 0,size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                            String searchKeyword){

        Page<Board> list = null;

        if(searchKeyword == null){
            list = boardService.boardList(pageable);
        }
        else {
            list = boardService.boardSearchList(searchKeyword,pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1; //pageable 이 가지고 있는 페이지는 0에서 시작하기 때문에 우리가 보는 부분보다 1이 적음 그래서 + 1 하는 것
        int startPage = Math.max(nowPage - 4,1); // nowPage를 계산 했을 때 1보다 작으면 1페이지를 출력하는 메서드
        int endPage = Math.min(nowPage + 5,list.getTotalPages()); //토탈 페이지보다 넘어갔을 때 마지막 토탈 페이지로 보내주는 메서드

        model.addAttribute("list", list);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

        return "boardlist";
    }

    @GetMapping("board/view")   // localhost8080:board/view?id=1    하면 1번 id값을 가진 게시글을 불러옴
    public String boardview(Model model, Integer id){

        model.addAttribute("board",boardService.boardview(id));

        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id, Model model){

        boardService.boardDelete(id);

        model.addAttribute("message","글 삭제가 완료되었습니다");
        model.addAttribute("searchUrl","/board/list");

        return "message";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id")Integer id, Model model){

        model.addAttribute("board",boardService.boardview(id));



        return "boardmodify";
    }

    @PostMapping("board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board, Model model,MultipartFile file) throws Exception{

        Board boardTemp = boardService.boardview(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp, file);

        model.addAttribute("message","글 수정이 완료되었습니다");
        model.addAttribute("searchUrl","/board/list");

        return "redirect:/board/list";
    }

}
