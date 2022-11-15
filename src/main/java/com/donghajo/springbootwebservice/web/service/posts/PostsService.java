package com.donghajo.springbootwebservice.web.service.posts;

import com.donghajo.springbootwebservice.web.domain.posts.Posts;
import com.donghajo.springbootwebservice.web.domain.posts.PostsRepository;
import com.donghajo.springbootwebservice.web.dto.PostsResponseDto;
import com.donghajo.springbootwebservice.web.dto.PostsSaveRequestDto;
import com.donghajo.springbootwebservice.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    @Autowired
    private PostsRepository postsRepository;
    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts post = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. ID = "+ id));
        //dirty checking -> JPA의 영속성을 이용 (쿼리를 날리지 않아도)
        post.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    @Transactional
    public PostsResponseDto findById(Long id){
        Posts post = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. ID = "+ id));
        return new PostsResponseDto(post);
    }
}
