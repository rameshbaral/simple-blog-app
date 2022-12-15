package com.ibriztech.springboot.service.impl;

import com.ibriztech.springboot.dto.PostDto;
import com.ibriztech.springboot.entity.Post;
import com.ibriztech.springboot.entity.User;
import com.ibriztech.springboot.mapper.PostMappper;
import com.ibriztech.springboot.repository.PostRepository;
import com.ibriztech.springboot.repository.UserRepository;
import com.ibriztech.springboot.service.PostService;
import com.ibriztech.springboot.util.SecurityUtils;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    ///returns list of PostDto by Searching On Database
    @Override
    public List<PostDto> findAllPosts() {
        List<Post> posts =  postRepository.findAll();
        return posts.stream().map((PostMappper :: mapToPostDto))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> findPostByUser() {
        String email = SecurityUtils.getCurrentUser().getUsername();
        User createdBy = userRepository.findByEmail(email);
        Long userId = createdBy.getId();
        List<Post> posts =postRepository.findPostByUser(userId);
        return posts.stream()
                .map(post -> PostMappper.mapToPostDto(post))
                .collect(Collectors.toList());
    }

    @Override
    public void createPost(PostDto postDto) {
        String email = SecurityUtils.getCurrentUser().getUsername();
        User user = userRepository.findByEmail(email);
        Post post = PostMappper.mapToPost(postDto);
        post.setCreatedBy(user);
        postRepository.save(post);
    }

    @Override
    public PostDto findPostById(Long postId) {
        Post post = postRepository.findById(postId).get();
        return PostMappper.mapToPostDto(post);
    }

    @Override
    public void updatePost(PostDto postDto) {
        String email = SecurityUtils.getCurrentUser().getUsername();
        User createdBy = userRepository.findByEmail(email);
        Post post = PostMappper.mapToPost(postDto);
        post.setCreatedBy(createdBy);
        postRepository.save(post);
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    @Transactional
    public PostDto findPostByUrl(String postUrl) {
        Post post = postRepository.findByUrl(postUrl).get();
        return PostMappper.mapToPostDto(post);
    }

    @Override
    public List<PostDto> searchPosts(String query) {
        List<Post> posts = postRepository.searchPosts(query);
        return posts.stream()
                .map(PostMappper :: mapToPostDto)
                .collect(Collectors.toList());

    }
//generates excel file
    @Override
    public List<PostDto> generateExcel() throws IOException {
        List<Post> posts = postRepository.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("posts");

        Row header = sheet.createRow(0);
        //sets header style
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 14);
        font.setBold(true);
        headerStyle.setFont(font);
        List<String> headerTitles = Arrays.asList("title", "url", "short description", "content");
        // creating headers for Excel
        for (int i = 0; i < headerTitles.size(); i++) {
            Cell headerCell = header.createCell(i);
            headerCell.setCellValue(headerTitles.get(i));
            headerCell.setCellStyle(headerStyle);
        }
        // populating data in excel
        for (int i = 0; i < posts.size(); i++) {
            Row dataRow = sheet.createRow(i+1);
            Integer cellCount = 0;
            Cell cell = dataRow.createCell(cellCount);
            cell.setCellValue(posts.get(i).getTitle());
            Cell cell1 = dataRow.createCell(cellCount+1);
            cell1.setCellValue(posts.get(i).getUrl());
            Cell cell2 = dataRow.createCell(cellCount+2);
            cell2.setCellValue(posts.get(i).getShortDescription());
            Cell cell3 = dataRow.createCell(cellCount+3);
            cell3.setCellValue(posts.get(i).getContent());
        }

        File file = new File("src/files/posts.xlsx");
        workbook.write(new FileOutputStream(file));
        workbook.close();
        return posts.stream().map((PostMappper :: mapToPostDto))
                .collect(Collectors.toList());
    }
// reads from excel file
    @Override
    public List<PostDto> readFromExcel() throws IOException {
        List<Post> posts = postRepository.findAll();
        FileInputStream file = new FileInputStream("src/files/posts.xlsx");
        Workbook workbook  = new XSSFWorkbook(file);
        Sheet sheetAt = workbook.getSheetAt(0);
        for (Row row : sheetAt) {
            for (int i = 0; i < 4; i++) {
                System.out.print(row.getCell(i).getStringCellValue()+" ");
            }
            System.out.println();
        }
        workbook.close();
        file.close();
        return posts.stream().map((PostMappper :: mapToPostDto))
                .collect(Collectors.toList());
    }
}


