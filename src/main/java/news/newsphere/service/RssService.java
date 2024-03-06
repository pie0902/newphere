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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RssService {
    public List<RssItem> fetchRssFeed(String feedUrl) throws Exception {
        URL url = new URL(feedUrl);
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(url));
        return feed.getEntries().stream().map(entry -> new RssItem(
            entry.getTitle(),
            entry.getLink(),
            entry.getDescription().getValue()
        )).collect(Collectors.toList());
    }
    @Getter
    @AllArgsConstructor
    public static class RssItem {
        private String title;
        private String link;
        private String description;
    }
}
