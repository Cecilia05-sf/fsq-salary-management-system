package com.fsq.fsqsalary.po;


import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
public class BaseQuery {

    private static final Integer DEFAULT_PAGE_SIZE = 20;
    private static final Integer DEFAULT_FIRST_PAGE = 1;
    private Integer pageSize;
    private Integer currentPage;

    public Integer getPageSize() {
        if (this.pageSize == null) {
            this.pageSize = DEFAULT_PAGE_SIZE;
        }

        return this.pageSize;
    }

    public Integer getCurrentPage() {
        if (this.currentPage == null) {
            this.currentPage = DEFAULT_FIRST_PAGE;
        }

        return this.currentPage;
    }

    public int getPageFirstItem() {
        int cPage = this.getCurrentPage();
        if (cPage == 1) {
            return 0;
        } else {
            --cPage;
            int pgSize = this.getPageSize();
            return pgSize * cPage;
        }
    }
}
