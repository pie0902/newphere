package news.newsphere.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import news.newsphere.service.RssService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class RssController {
    private final RssService rssService;
    @GetMapping("/api/rss")
    public List<RssService.RssItem> getRssFeed() throws Exception {
        return rssService.fetchRssFeed("https://www.mk.co.kr/rss/30000001/");
    }
}
