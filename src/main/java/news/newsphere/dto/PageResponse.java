package news.newsphere.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageResponse<T>{
    private List<T> content;
    private int currentPage;
    private int totalItems;
    private int totalPages;

}
