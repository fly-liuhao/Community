package cn.edu.tit.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageInfoDTO {
    private Integer currPage;
    private Integer endPage;
    private Boolean showPrevPage;
    private Boolean showNextPage;
    private Boolean showFirstPage;
    private Boolean showEndPage;
    private List<Integer> pages;

    public PageInfoDTO(int currPage, int totalPage) {
        this.currPage = currPage;
        this.endPage = totalPage;
        this.pages = new ArrayList<Integer>();
        pages.add(currPage);
        for (int i = 1; i <= 3; i++) {
            if ((currPage - i) > 0) {
                pages.add(0, currPage - i);
            }
            if ((currPage + i) <= totalPage) {
                pages.add(currPage + i);
            }
        }
        // 是否显示前一页图标
        if (currPage > 1) {
            showPrevPage = true;
        } else {
            showPrevPage = false;
        }
        // 是否显示后一页图标
        if (currPage < totalPage) {
            showNextPage = true;
        } else {
            showNextPage = false;
        }
        // 是否显示首页图标
        if (currPage - 3 > 1) {
            showFirstPage = true;
        } else {
            showFirstPage = false;
        }
        // 是否显示尾页图标
        if (currPage + 3 < totalPage) {
            showEndPage = true;
        } else {
            showEndPage = false;
        }
    }
}
