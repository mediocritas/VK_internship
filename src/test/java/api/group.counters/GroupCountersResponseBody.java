package api.group.counters;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupCountersResponseBody {

    @JsonProperty("counters")
    private Counters counters;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Counters {
        @JsonProperty("ads_topics")
        private Integer adsTopics;

        @JsonProperty("black_list")
        private Integer blackList;

        @JsonProperty("catalogs")
        private Integer catalogs;

        @JsonProperty("delayed_topics")
        private Integer delayedTopics;

        @JsonProperty("join_requests")
        private Integer joinRequests;

        @JsonProperty("links")
        private Integer links;

        @JsonProperty("maybe")
        private Integer maybe;

        @JsonProperty("members")
        private Integer members;

        @JsonProperty("moderators")
        private Integer moderators;

        @JsonProperty("music_tracks")
        private Integer musicTracks;

        @JsonProperty("own_products")
        private Integer ownProducts;

        @JsonProperty("photo_albums")
        private Integer photoAlbums;

        @JsonProperty("photos")
        private Integer photos;

        @JsonProperty("pinned_topics")
        private Integer pinnedTopics;

        @JsonProperty("presents")
        private Integer presents;

        @JsonProperty("products")
        private Integer products;

        @JsonProperty("promo_on_moderation")
        private Integer promoOnModeration;

        @JsonProperty("suggested_products")
        private Integer suggestedProducts;

        @JsonProperty("suggested_topics")
        private Integer suggestedTopics;

        @JsonProperty("themes")
        private Integer themes;

        @JsonProperty("unpublished_topics")
        private Integer unpublishedTopics;

        @JsonProperty("videos")
        private Integer videos;
    }
}
