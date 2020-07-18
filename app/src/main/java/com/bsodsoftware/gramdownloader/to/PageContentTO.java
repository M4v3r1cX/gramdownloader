package com.bsodsoftware.gramdownloader.to;

public class PageContentTO {

    private Graphql graphql;

    public Graphql getGraphql() {
        return graphql;
    }

    public void setGraphql(Graphql graphql) {
        this.graphql = graphql;
    }

    public class Graphql {
        private ShortcodeMedia shortcode_media;

        public ShortcodeMedia getShortcode_media() {
            return shortcode_media;
        }

        public void setShortcode_media(ShortcodeMedia shortcode_media) {
            this.shortcode_media = shortcode_media;
        }
    }

    public class ShortcodeMedia {
        private String __typename;
        private String id;
        private String video_url;

        public String get__typename() {
            return __typename;
        }

        public void set__typename(String __typename) {
            this.__typename = __typename;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }
    }
}
