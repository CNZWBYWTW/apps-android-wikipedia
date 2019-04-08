package org.wikipedia.feed.news;

import android.net.Uri;

import org.wikipedia.dataclient.WikiSite;
import org.wikipedia.dataclient.restbase.page.RbPageSummary;
import org.wikipedia.json.annotations.Required;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static org.wikipedia.dataclient.Service.PREFERRED_THUMB_SIZE;
import static org.wikipedia.util.ImageUrlUtil.getUrlForSize;

public final class NewsItem {
    @SuppressWarnings("unused,NullableProblems") @Required @NonNull private String story;
    @SuppressWarnings("unused,NullableProblems") @NonNull private List<RbPageSummary> links
            = Collections.emptyList();

    @NonNull String story() {
        return story;
    }

    @NonNull public List<RbPageSummary> links() {
        return links;
    }

    @NonNull List<NewsLinkCard> linkCards(WikiSite wiki) {
        List<NewsLinkCard> linkCards = new ArrayList<>();
        for (RbPageSummary link : links) {
            linkCards.add(new NewsLinkCard(link, wiki));
        }
        return linkCards;
    }

    @Nullable public Uri thumb() {
        Uri uri = getFirstImageUri(links);
        return uri != null ? getUrlForSize(uri, PREFERRED_THUMB_SIZE) : null;
    }

    @Nullable Uri featureImage() {
        return getFirstImageUri(links);
    }

    /**
     * Iterate through the CardPageItems associated with the news story's links and return the first
     * thumb URI found.
     */
    @Nullable private Uri getFirstImageUri(List<RbPageSummary> links) {
        for (RbPageSummary link : links) {
            String thumbnail = link.getThumbnailUrl();
            if (thumbnail != null) {
                return Uri.parse(thumbnail);
            }
        }
        return null;
    }
}
