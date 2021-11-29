package com.coco.board.web.controller;

import com.coco.board.domain.posts.Posts;
import com.coco.board.service.posts.PostsService;
import com.coco.board.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 화면 연결 Controller
 */


@Controller
@RequiredArgsConstructor
public class PostsIndexController {

    private final PostsService postsService;


    @GetMapping("/")                 /* default page = 0, size = 10  */
    public String index(Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable) {
        Page<Posts> list = postsService.pageList(pageable);

        model.addAttribute("posts", list);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("nextCheck", list.hasNext());
        model.addAttribute("preCheck", list.hasPrevious());

        return "index";
    }

    @GetMapping("/posts/write")
    public String write() {
        return "posts-write";
    }

    @GetMapping("/posts/read/{id}")
    public String read(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        postsService.updateView(id); // views ++
        model.addAttribute("posts", dto);

        return "posts-read";
    }

    @GetMapping("/posts/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("posts", dto);

        return "posts-update";
    }

    @GetMapping("/posts/search")
    public String search(String keyword, Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC)
           Pageable pageable) {
        Page<Posts> searchList = postsService.search(keyword, pageable);

        model.addAttribute("searchList", searchList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("nextCheck", searchList.hasNext());
        model.addAttribute("preCheck", searchList.hasPrevious());

        return "posts-search";
    }
}