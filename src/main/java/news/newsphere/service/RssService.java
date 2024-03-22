package news.newsphere.service;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
@Service
@RequiredArgsConstructor
public class RssService {
    public List<RssItem> fetchRssFeed(String feedUrl, Pageable pageable) throws Exception {
        URL url = new URL(feedUrl);
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(url));
        List<RssItem> items = feed.getEntries().stream().map(entry -> new RssItem(
            entry.getTitle(),
            entry.getLink(),
            entry.getDescription().getValue()
        )).collect(Collectors.toList());

        // 페이징 처리
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), items.size());
        return new PageImpl<>(items.subList(start, end), pageable, items.size()).getContent();
    }
    @Getter
    @AllArgsConstructor
    public static class RssItem {
        private String title;
        private String link;
        private String description;
    }
}
