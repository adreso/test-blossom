package co.com.blossom.configs.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Class that provides the basic attributes that are returned in
 * functionalities that require pagination
 *
 * @param <T> Type of data that encapsulates the pagination
 */
@Setter
@Getter
public class CustomSlice <T> {
    private List<T> elements;
    private int size;
    private int page;
    private int totalPage;
    private long totalElements;
    private boolean sorted;
    private boolean paged;
    private boolean first;
    private boolean last;

    @Builder
    public CustomSlice(List<T> elements, Page page) {
        this.elements = elements;
        this.first = page.isFirst();
        this.last = page.isLast();
        this.size = page.getSize();
        this.page = page.getNumber();
        this.totalPage = elements.size();
        this.totalElements = page.getTotalElements();
        this.sorted = page.getSort().isSorted();
        this.paged = page.getPageable().isPaged();
    }
}
