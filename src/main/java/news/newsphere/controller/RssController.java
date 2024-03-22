package news.newsphere.controller;

import java.util.List;
import news.newsphere.dto.PageResponse;
import news.newsphere.service.RssService.RssItem;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import news.newsphere.service.RssService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class RssController {
    private final RssService rssService;
@GetMapping("/api/rss")
public ResponseEntity<PageResponse<RssItem>> getRssFeed(Pageable pageable) throws Exception {
    List<RssService.RssItem> items = rssService.fetchRssFeed("https://www.mk.co.kr/rss/30000001/", pageable);
    int totalItems = items.size();
    int totalPages = (int) Math.ceil((double) totalItems / pageable.getPageSize());
    int currentPage = pageable.getPageNumber();

    PageResponse<RssService.RssItem> response = new PageResponse<>(items, currentPage, totalItems, totalPages);
    return ResponseEntity.ok(response);
}
}
